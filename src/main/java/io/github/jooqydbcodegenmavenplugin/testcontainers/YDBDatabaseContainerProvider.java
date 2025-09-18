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

package io.github.jooqydbcodegenmavenplugin.testcontainers;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.JdbcDatabaseContainerProvider;
import org.testcontainers.utility.DockerImageName;

/**
 * The provider of {@link io.github.jooqydbcodegenmavenplugin.testcontainers.YDBContainer}.
 *
 * @author Andrey_Yurzanov
 */
@RequiredArgsConstructor
public class YDBDatabaseContainerProvider extends JdbcDatabaseContainerProvider {
  private final ContainerProperties properties;

  @Override
  public boolean supports(String type) {
    return Objects.equals(type, YDBContainer.NAME);
  }

  @Override
  public JdbcDatabaseContainer newInstance() {
    return newInstance((String) null);
  }

  @Override
  public JdbcDatabaseContainer newInstance(String tag) {
    DockerImageName image = properties.getDockerImage();
    if (tag != null) {
      image = image.withTag(tag);
    }

    var container = new YDBContainer<>(image);
    container.setJdbcUrl(properties.getJdbcUrl());
    container.setUsername(properties.getUsername());
    container.setPassword(properties.getPassword());
    container.setExposedPorts(properties.getExposedPorts());
    container.setPortBindings(properties.getPorts());

    container.withCreateContainerCmdModifier(
      cmd -> cmd.withHostName(properties.getHost())
    );
    return container;
  }
}
