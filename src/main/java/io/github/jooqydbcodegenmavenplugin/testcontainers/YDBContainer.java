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

import lombok.Setter;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.utility.DockerImageName;
import tech.ydb.jdbc.YdbDriver;

/**
 * The JDBC container for YDB.
 *
 * @author Andrey_Yurzanov
 */
@Setter
public class YDBContainer<T extends YDBContainer<T>> extends JdbcDatabaseContainer<T> {
  private String username;
  private String password;
  private String jdbcUrl;

  protected static final String NAME = "local-ydb";
  private static final String DEFAULT_TEST_QUERY = "select 1;";

  /**
   * Creates new instance by the docker image.
   *
   * @param image name of the docker image
   */
  public YDBContainer(DockerImageName image) {
    super(image);
  }

  @Override
  public String getDriverClassName() {
    return YdbDriver.class.getCanonicalName();
  }

  @Override
  public String getJdbcUrl() {
    return jdbcUrl;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  protected String getTestQueryString() {
    return DEFAULT_TEST_QUERY;
  }
}
