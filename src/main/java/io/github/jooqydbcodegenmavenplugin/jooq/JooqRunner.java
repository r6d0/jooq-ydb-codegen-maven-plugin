package io.github.jooqydbcodegenmavenplugin.jooq;

import io.github.jooqydbcodegenmavenplugin.PluginProperties;
import lombok.RequiredArgsConstructor;
import org.jooq.codegen.GenerationTool;

/**
 * The runner for jOOQ code generation executing.
 *
 * @author Andrey_Yurzanov
 */
@RequiredArgsConstructor
public class JooqRunner {
  private final PluginProperties properties;

  /**
   * Runs jOOQ code generation.
   */
  public void run() throws Exception {
    var configuration = properties.getConfiguration();
    GenerationTool.generate(configuration);

    var project = properties.getProject();
    if (project != null) {
      project.addCompileSourceRoot(configuration.getGenerator().getTarget().getDirectory());
    }
  }
}
