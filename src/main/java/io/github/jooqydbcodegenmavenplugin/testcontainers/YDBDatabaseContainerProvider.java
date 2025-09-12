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
