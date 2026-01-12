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

import io.github.r6d0.jooqydbcodegenmavenplugin.flyway.FlywayPluginProperties;
import io.github.r6d0.jooqydbcodegenmavenplugin.jooq.JooqPluginProperties;
import io.github.r6d0.jooqydbcodegenmavenplugin.testcontainers.ContainerPluginProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.maven.project.MavenProject;
import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Strategy;
import org.jooq.meta.jaxb.Target;
import tech.ydb.jooq.codegen.YdbGeneratorStrategy;

import java.util.Objects;

/**
 * The properties of the plugin.
 *
 * @author Andrey_Yurzanov
 */
@Data
@RequiredArgsConstructor
public class PluginProperties {
  private final MavenProject project;
  private final ContainerPluginProperties container;
  private final FlywayPluginProperties flyway;
  private final JooqPluginProperties jooq;

  private static final String DEFAULT_GENERATOR_STRATEGY = DefaultGeneratorStrategy.class.getCanonicalName();

  /**
   * Returns the location of the flyway scripts.
   *
   * @return the location of the flyway scripts
   */
  public String getLocation() {
    return flyway.getLocation();
  }

  /**
   * Returns JDBC URL.
   *
   * @return JDBC URL
   */
  public String getJdbcUrl() {
    return container
      .getContainerProperties()
      .getJdbcUrl();
  }

  /**
   * Returns the username for JDBC connection.
   *
   * @return the username for JDBC connection
   */
  public String getUsername() {
    return container.getUsername();
  }

  /**
   * Returns the password for JDBC connection.
   *
   * @return the password for JDBC connection
   */
  public String getPassword() {
    return container.getPassword();
  }

  /**
   * Returns the jOOQ configuration.
   *
   * @return the jOOQ configuration
   */
  public Configuration getConfiguration() {
    var configuration = new Configuration();
    configuration.setJdbc(configureJdbc(jooq.getJdbc()));
    configuration.setGenerator(configureGenerator(jooq.getGenerator()));
    return configuration;
  }

  private Jdbc configureJdbc(Jdbc jdbc) {
    var result = new Jdbc();
    if (jdbc != null) {
      result.setDriver(jdbc.getDriver());
      result.setUrl(jdbc.getUrl());
      result.setUser(jdbc.getUser());
      result.setUsername(jdbc.getUsername());
      result.setPassword(jdbc.getPassword());
      result.setAutoCommit(jdbc.isAutoCommit());
      result.setInitScript(jdbc.getInitScript());
      result.setInitSeparator(jdbc.getInitSeparator());
      result.setProperties(jdbc.getProperties());
    }

    // TODO. Method 'getContainerProperties' creates a new instance of 'ContainerProperties' every call.
    if (result.getDriver() == null) {
      result.setDriver(
        container
          .getContainerProperties()
          .getDriver()
      );
    }

    if (result.getUrl() == null) {
      result.setUrl(
        container
          .getContainerProperties()
          .getJdbcUrl()
      );
    }

    if (result.getUsername() == null) {
      result.setUsername(container.getUsername());
    }

    if (result.getPassword() == null) {
      result.setPassword(container.getPassword());
    }
    return result;
  }

  private Generator configureGenerator(Generator generator) {
    var result = new Generator();
    if (generator != null) {
      result.setName(generator.getName());
      result.setStrategy(generator.getStrategy());
      result.setDatabase(generator.getDatabase());
      result.setGenerate(generator.getGenerate());
      result.setTarget(generator.getTarget());
    }

    var strategy = result.getStrategy();
    if (strategy == null) {
      strategy = new Strategy();
      result.setStrategy(strategy);
    }

    // Sets YdbGeneratorStrategy as default strategy
    if (Objects.equals(strategy.getName(), DEFAULT_GENERATOR_STRATEGY)) {
      strategy.setName(YdbGeneratorStrategy.class.getCanonicalName());
    }

    if (result.getTarget() == null) {
      result.setTarget(new Target());
    }
    return result;
  }
}
