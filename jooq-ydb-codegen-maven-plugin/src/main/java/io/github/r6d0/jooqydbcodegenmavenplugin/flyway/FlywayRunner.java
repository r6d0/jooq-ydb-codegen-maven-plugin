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

package io.github.r6d0.jooqydbcodegenmavenplugin.flyway;

import io.github.r6d0.jooqydbcodegenmavenplugin.PluginProperties;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;

/**
 * The runner for flyway migration executing.
 *
 * @author Andrey_Yurzanov
 */
@RequiredArgsConstructor
public class FlywayRunner {
  private final PluginProperties properties;

  /**
   * Runs flyway migration.
   *
   * @throws java.lang.RuntimeException when migration fails
   */
  public void run() throws RuntimeException {
    MigrateResult result = Flyway
      .configure()
      .dataSource(properties.getJdbcUrl(), properties.getUsername(), properties.getPassword())
      .locations(properties.getLocation())
      .load()
      .migrate();

    if (!result.success) {
      throw new RuntimeException(result.exceptionObject);
    }
  }
}
