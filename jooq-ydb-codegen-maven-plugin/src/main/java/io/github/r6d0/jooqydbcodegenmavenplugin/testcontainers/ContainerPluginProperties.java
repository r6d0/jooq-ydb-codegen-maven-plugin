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

package io.github.r6d0.jooqydbcodegenmavenplugin.testcontainers;

import java.util.List;

import lombok.Data;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * The properties of the docker container.
 *
 * @author Andrey_Yurzanov
 */
@Data
public class ContainerPluginProperties {
  @Parameter(required = true)
  private String image = ContainerProperties.DEFAULT_IMAGE;
  @Parameter
  private String username = ContainerProperties.DEFAULT_USERNAME;
  @Parameter
  private String password = ContainerProperties.DEFAULT_PASSWORD;
  @Parameter
  private List<String> ports = ContainerProperties.DEFAULT_PORTS;
  @Parameter
  private String host = ContainerProperties.DEFAULT_HOST;
  @Parameter
  private String database = ContainerProperties.DEFAULT_DATABASE;

  /**
   * Returns properties of docker container.
   *
   * @return properties of docker container.
   */
  public ContainerProperties getContainerProperties() {
    ContainerProperties properties = new ContainerProperties();
    properties.setImage(image);
    properties.setUsername(username);
    properties.setPassword(password);
    properties.setPorts(ports);
    properties.setHost(host);
    properties.setDatabase(database);
    return properties;
  }
}
