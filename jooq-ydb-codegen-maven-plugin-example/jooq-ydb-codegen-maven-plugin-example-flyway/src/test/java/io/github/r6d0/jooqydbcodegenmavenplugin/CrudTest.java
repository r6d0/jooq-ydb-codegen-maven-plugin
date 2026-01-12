/*
 *    Copyright 2025 Andrey Yurzanov
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

package io.github.r6d0.jooqydbcodegenmavenplugin;

import io.github.r6d0.jooqydbcodegenmavenplugin.testcontainers.ContainerProperties;
import io.github.r6d0.jooqydbcodegenmavenplugin.testcontainers.YDBContainer;
import io.github.r6d0.jooqydbcodegenmavenplugin.testcontainers.YDBDatabaseContainerProvider;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.ydb.jooq.CloseableYdbDSLContext;
import tech.ydb.jooq.YDB;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import static io.github.r6d0.jooqydbcodegenmavenplugin.default_schema.tables.FlywayTest.FLYWAY_TEST;

/**
 * Checks CRUD operations.
 *
 * @author Andrey_Yurzanov
 */
class CrudTest {
  private static YDBContainer ydb;
  private static CloseableYdbDSLContext context;

  @BeforeAll
  static void beforeAll() {
    var provider = new YDBDatabaseContainerProvider(new ContainerProperties());
    ydb = (YDBContainer) provider.newInstance();
    ydb.start();

    MigrateResult result = Flyway
      .configure()
      .dataSource(ydb.getJdbcUrl(), ydb.getUsername(), ydb.getPassword())
      .locations(System.getenv("FLYWAY_DIRECTORY"))
      .load()
      .migrate();

    if (!result.success) {
      throw new RuntimeException(result.exceptionObject);
    }

    context = YDB.using(ydb.getJdbcUrl(), ydb.getUsername(), ydb.getPassword());
  }

  @AfterAll
  static void afterAll() {
    ydb.stop();
    context.close();
  }

  @BeforeEach
  void beforeEach() {
    context
      .deleteFrom(FLYWAY_TEST)
      .where(FLYWAY_TEST.ID.isNotNull())
      .execute();
  }

  @Test
  void insertTest() {
    context
      .insertInto(FLYWAY_TEST)
      .columns(FLYWAY_TEST.ID, FLYWAY_TEST.TEXT, FLYWAY_TEST.INSERT_DATE)
      .values(1L, UUID.randomUUID().toString(), LocalDate.now())
      .execute();

    Assertions.assertTrue(
      context
        .selectFrom(FLYWAY_TEST)
        .fetch()
        .stream()
        .anyMatch(it -> Objects.equals(it.getId(), 1L))
    );
  }

  @Test
  void updateTest() throws SQLException {
    context
      .insertInto(FLYWAY_TEST)
      .columns(FLYWAY_TEST.ID, FLYWAY_TEST.TEXT, FLYWAY_TEST.INSERT_DATE)
      .values(1L, UUID.randomUUID().toString(), LocalDate.now())
      .execute();

    String newValue = UUID.randomUUID().toString();
    context
      .update(FLYWAY_TEST)
      .set(DSL.field(FLYWAY_TEST.TEXT.getName()), newValue)
      .where(DSL.field(FLYWAY_TEST.ID.getName()).eq(1L))
      .execute();

    Assertions.assertTrue(
      context
        .selectFrom(FLYWAY_TEST)
        .fetch()
        .stream()
        .anyMatch(it -> Objects.equals(it.getId(), 1L) && Objects.equals(it.getText(), newValue))
    );
  }
}
