package io.github.jooqydbcodegenmavenplugin.flyway;

import lombok.Data;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * The properties of the flyway migration tool.
 *
 * @author Andrey_Yurzanov
 */
@Data
public class FlywayProperties {
  @Parameter(required = true)
  private String location = "classpath:db/migration";
}
