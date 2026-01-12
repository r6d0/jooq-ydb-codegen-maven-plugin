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

import lombok.Data;
import org.testcontainers.utility.DockerImageName;
import tech.ydb.jdbc.YdbDriver;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The properties of the docker container.
 *
 * @author Andrey_Yurzanov
 */
@Data
public class ContainerProperties {
  private String image = DEFAULT_IMAGE;
  private String username = DEFAULT_USERNAME;
  private String password = DEFAULT_PASSWORD;
  private List<String> ports = DEFAULT_PORTS;
  private String host = DEFAULT_HOST;
  private String database = DEFAULT_DATABASE;

  private static final int PORT_SIZE = 2;
  private static final int JDBC_PORT_INDEX = 0;
  private static final int EXPOSED_PORT_INDEX = 1;
  private static final String DEFAULT_JDBC_PORT = "2136";
  private static final String JDBC_URL_PREFIX = "jdbc:ydb:grpc://";
  private static final String PORT_SEPARATOR = ":";
  private static final String PATH_SEPARATOR = "/";

  /**
   * The default container image.
   */
  public static final String DEFAULT_IMAGE = "ydbplatform/local-ydb:latest";
  /**
   * The default database username.
   */
  public static final String DEFAULT_USERNAME = "";
  /**
   * The default database password.
   */
  public static final String DEFAULT_PASSWORD = "";
  /**
   * The default database host.
   */
  public static final String DEFAULT_HOST = "localhost";
  /**
   * The default database name.
   */
  public static final String DEFAULT_DATABASE = "local";
  /**
   * The default database ports.
   */
  public static final List<String> DEFAULT_PORTS = List.of(
    DEFAULT_JDBC_PORT + PORT_SEPARATOR + DEFAULT_JDBC_PORT,
    "2135:2135",
    "8765:8765",
    "9092:9092"
  );

  /**
   * Returns instance of the {@link DockerImageName}.
   *
   * @return instance of the {@link DockerImageName}
   */
  public DockerImageName getDockerImage() {
    return DockerImageName.parse(image);
  }

  /**
   * Returns open ports for the docker container.
   *
   * @return open ports for the docker container
   */
  public List<Integer> getExposedPorts() {
    return ports
      .stream()
      .map(it -> it.split(PORT_SEPARATOR))
      .filter(it -> it.length == PORT_SIZE)
      .map(it -> it[EXPOSED_PORT_INDEX])
      .map(Integer::parseInt)
      .collect(Collectors.toList());
  }

  /**
   * Returns JDBC URL.
   *
   * @return JDBC URL
   */
  public String getJdbcUrl() {
    var port = ports
      .stream()
      .map(it -> it.split(PORT_SEPARATOR))
      .filter(it -> it.length == PORT_SIZE)
      .map(it -> it[JDBC_PORT_INDEX])
      .findFirst()
      .orElse(DEFAULT_JDBC_PORT);

    return String.join(
      "",
      JDBC_URL_PREFIX,
      host,
      PORT_SEPARATOR,
      port,
      PATH_SEPARATOR,
      database
    );
  }

  /**
   * Returns name of the JDBC driver.
   *
   * @return name of the JDBC driver
   */
  public String getDriver() {
    return YdbDriver.class.getCanonicalName();
  }
}
