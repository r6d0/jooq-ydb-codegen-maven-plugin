package io.github.jooqydbcodegenmavenplugin.flyway;

import io.github.jooqydbcodegenmavenplugin.PluginProperties;
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
