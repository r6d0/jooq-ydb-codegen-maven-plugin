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
import io.github.jooqydbcodegenmavenplugin.flyway.FlywayRunner;
import io.github.jooqydbcodegenmavenplugin.jooq.JooqProperties;
import io.github.jooqydbcodegenmavenplugin.jooq.JooqRunner;
import io.github.jooqydbcodegenmavenplugin.testcontainers.ContainerProperties;
import io.github.jooqydbcodegenmavenplugin.testcontainers.YDBDatabaseContainerProvider;
import lombok.Getter;
import lombok.Setter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * The plugin for generate jOOQ records.
 *
 * @author Andrey_Yurzanov
 */
@Getter
@Setter
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class Plugin extends AbstractMojo {
  @Parameter(property = "project", required = true, readonly = true)
  private MavenProject project;
  @Parameter
  private FlywayProperties flyway;
  @Parameter
  private ContainerProperties container = new ContainerProperties();
  @Parameter
  private JooqProperties jooq = new JooqProperties();

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    var log = getLog();
    var provider = new YDBDatabaseContainerProvider(container);
    try (var image = provider.newInstance()) {
      image.start();
      log.info("YDB container is started");

      var properties = new PluginProperties(project, container, flyway, jooq);
      log.info(properties.toString());
      if (flyway != null) {
        var runner = new FlywayRunner(properties);
        runner.run();
        log.info("Flyway migration is completed");
      }

      var runner = new JooqRunner(properties);
      runner.run();
      log.info("jOOQ generation is completed");
    } catch (Exception exception) {
      log.error(exception);
    }
  }
}
