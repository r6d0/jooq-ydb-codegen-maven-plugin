# jooq-ydb-codegen-maven-plugin
The Maven plugin for generating jOOQ records from YDB using Testcontainers.

## How to start
To start using the plugin, add to pom.xml:
```xml
<plugin>
  <groupId>io.github.jooqydbcodegenmavenplugin</groupId>
  <artifactId>jooq-ydb-codegen-maven-plugin</artifactId>
  <version>${jooq-ydb-codegen-maven-plugin.version}</version>
  <executions>
    <execution>
      <goals>
        <goal>generate</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <!-- Flyway migration -->
    <flyway>
      <location>filesystem:./src/main/resources/db/migration</location>
    </flyway>
    
    <!-- jOOQ configuration. See documentation - https://www.jooq.org/doc/latest/manual/code-generation/codegen-configuration/ -->
    <jooq>
      <generator>
        <name>org.jooq.codegen.KotlinGenerator</name>
        <database>
          <name>tech.ydb.jooq.codegen.YdbDatabase</name>
          <excludes>.sys.*</excludes>
        </database>
      </generator>
    </jooq>
  </configuration>
</plugin>
```

## Advanced options

### Docker container
To set up a docker container, add it to pom.xml:
```xml
<plugin>
  <groupId>io.github.jooqydbcodegenmavenplugin</groupId>
  <artifactId>jooq-ydb-codegen-maven-plugin</artifactId>
  <version>${jooq-ydb-codegen-maven-plugin.version}</version>
  <executions>
    <execution>
      <goals>
        <goal>generate</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    ...
    
    <container>
      <!-- Docker image. The default value is below -->
      <image>ydbplatform/local-ydb:latest</image>
      <!-- Username for JDBC connection. The default value is empty -->
      <username></username>
      <!-- Password for JDBC connection. The default value is empty -->
      <password></password>
      <!-- Open docker container ports. The default value is below -->
      <!-- ATTENTION! The first port will be used for the JDBC URL -->
      <ports>
        <port>2136:2136</port>
        <port>2135:2135</port>
        <port>8765:8765</port>
        <port>9092:9092</port>
      </ports>
      <!-- The host of the docker container. The default value is below -->
      <host>localhost</host>
      <!-- The name of the database. The default value is below -->
      <database>local</database>
    </container>
    
    ...
  </configuration>
</plugin>
```