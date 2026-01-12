/*
 *    Copyright 2026 Andrey Yurzanov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.github.r6d0.jooqydbcodegenmavenplugin

import io.github.r6d0.jooqydbcodegenmavenplugin.default_schema.tables.FlywayTest
import io.github.r6d0.jooqydbcodegenmavenplugin.testcontainers.ContainerProperties
import io.github.r6d0.jooqydbcodegenmavenplugin.testcontainers.YDBContainer
import io.github.r6d0.jooqydbcodegenmavenplugin.testcontainers.YDBDatabaseContainerProvider
import org.flywaydb.core.Flyway
import org.jooq.impl.DSL
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tech.ydb.jooq.CloseableYdbDSLContext
import tech.ydb.jooq.YDB
import java.time.LocalDate
import java.util.UUID


/**
 * Checks CRUD operations.
 *
 * @author Andrey_Yurzanov
 */
class CrudTest {
  @BeforeEach
  fun beforeEach() {
    context
      .deleteFrom(FlywayTest.FLYWAY_TEST)
      .where(FlywayTest.FLYWAY_TEST.ID.isNotNull())
      .execute()
  }

  @Test
  fun insertTest() {
    context
      .insertInto(FlywayTest.FLYWAY_TEST)
      .columns(
        FlywayTest.FLYWAY_TEST.ID,
        FlywayTest.FLYWAY_TEST.TEXT,
        FlywayTest.FLYWAY_TEST.INSERT_DATE
      )
      .values(1L, UUID.randomUUID().toString(), LocalDate.now())
      .execute()

    Assertions.assertTrue(
      context
        .selectFrom(FlywayTest.FLYWAY_TEST)
        .fetch()
        .stream()
        .anyMatch { it.id == 1L }
    )
  }

  @Test
  fun updateTest() {
    context
      .insertInto(FlywayTest.FLYWAY_TEST)
      .columns(
        FlywayTest.FLYWAY_TEST.ID,
        FlywayTest.FLYWAY_TEST.TEXT,
        FlywayTest.FLYWAY_TEST.INSERT_DATE
      )
      .values(1L, UUID.randomUUID().toString(), LocalDate.now())
      .execute()

    val newValue: String = UUID.randomUUID().toString()
    context
      .update(FlywayTest.FLYWAY_TEST)
      .set(DSL.field(FlywayTest.FLYWAY_TEST.TEXT.name), newValue)
      .where(DSL.field(FlywayTest.FLYWAY_TEST.ID.name).eq(1L))
      .execute()

    Assertions.assertTrue(
      context
        .selectFrom(FlywayTest.FLYWAY_TEST)
        .fetch()
        .stream()
        .anyMatch { it.id == 1L && it.text == newValue }
    )
  }

  companion object {
    @JvmStatic
    private lateinit var ydb: YDBContainer<*>

    @JvmStatic
    private lateinit var context: CloseableYdbDSLContext

    @BeforeAll
    @JvmStatic
    fun beforeAll() {
      val provider = YDBDatabaseContainerProvider(ContainerProperties())
      ydb = provider.newInstance() as YDBContainer<*>
      ydb.start()

      val result = Flyway
        .configure()
        .dataSource(ydb.jdbcUrl, ydb.username, ydb.password)
        .locations(System.getenv("FLYWAY_DIRECTORY"))
        .load()
        .migrate()

      if (!result.success) {
        throw RuntimeException(result.exceptionObject)
      }

      context = YDB.using(ydb.jdbcUrl, ydb.username, ydb.password)
    }

    @AfterAll
    @JvmStatic
    fun afterAll() {
      ydb.stop()
      context.close()
    }
  }
}