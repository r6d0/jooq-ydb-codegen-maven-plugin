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