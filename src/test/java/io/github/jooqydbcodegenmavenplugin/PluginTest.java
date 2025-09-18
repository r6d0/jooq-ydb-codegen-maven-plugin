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

package io.github.jooqydbcodegenmavenplugin;

import io.github.jooqydbcodegenmavenplugin.flyway.FlywayProperties;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests of {@link io.github.jooqydbcodegenmavenplugin.Plugin}.
 *
 * @author Andrey_Yurzanov
 */
class PluginTest {
  private static final String BASE_PATH = String.join(
    "",
    "./target/generated-sources/jooq/org/jooq/generated/tables/"
  );
  private static final String EXPECTED_TABLE_PATH = BASE_PATH + "Test.java";
  private static final String EXPECTED_RECORD_PATH = BASE_PATH + "records/TestRecord.java";

  @Test
  void executeFlywaySuccessTest() throws MojoExecutionException, MojoFailureException {
    Plugin plugin = new Plugin();
    plugin.setFlyway(new FlywayProperties());

    plugin.execute();

    Assertions.assertTrue(Files.exists(Paths.get(EXPECTED_TABLE_PATH)));
    Assertions.assertTrue(Files.exists(Paths.get(EXPECTED_RECORD_PATH)));
  }
}