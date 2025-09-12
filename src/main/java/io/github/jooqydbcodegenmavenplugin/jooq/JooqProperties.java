package io.github.jooqydbcodegenmavenplugin.jooq;

import lombok.Data;
import org.apache.maven.plugins.annotations.Parameter;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;

/**
 * The properties of jOOQ code generator.
 *
 * @author Andrey_Yurzanov
 */
@Data
public class JooqProperties {
  @Parameter
  private Jdbc jdbc;
  @Parameter
  private Generator generator;
}
