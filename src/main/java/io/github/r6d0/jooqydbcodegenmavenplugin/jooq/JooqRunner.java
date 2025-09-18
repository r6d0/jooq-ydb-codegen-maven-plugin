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

package io.github.r6d0.jooqydbcodegenmavenplugin.jooq;

import io.github.r6d0.jooqydbcodegenmavenplugin.PluginProperties;
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
