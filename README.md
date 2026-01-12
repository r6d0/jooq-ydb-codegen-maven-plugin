# jooq-ydb-codegen-maven-plugin
The Maven plugin for generating jOOQ records from YDB using Testcontainers.

## How to start
To start using the plugin, add it to pom.xml:
```xml
<dependencies>
  <dependency>
    <groupId>org.jooq</groupId>
    <artifactId>jooq</artifactId>
    <version>${jooq.version}</version>
  </dependency>
  <dependency>
    <groupId>tech.ydb.jdbc</groupId>
    <artifactId>ydb-jdbc-driver</artifactId>
    <version>${ydb-jdbc-driver.version}</version>
  </dependency>
  <dependency>
    <groupId>tech.ydb.dialects</groupId>
    <artifactId>jooq-ydb-dialect</artifactId>
    <version>${jooq-ydb-dialect.version}</version>
  </dependency>  
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>io.github.r6d0</groupId>
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
          <location>filesystem:${project.basedir}/src/main/resources/db/migration</location>
        </flyway>

        <!-- jOOQ configuration. See documentation - https://www.jooq.org/doc/latest/manual/code-generation/codegen-configuration/ -->
        <jooq>
          <generator>
            <database>
              <name>tech.ydb.jooq.codegen.YdbDatabase</name>
              <excludes>.sys.*</excludes>
            </database>
          </generator>
        </jooq>
      </configuration>
    </plugin>
  </plugins>
</build>
```

## Advanced options

### Docker container
To set up a docker container, add it to pom.xml:
```xml
<build>
  <plugins>
    <plugin>
      <groupId>io.github.r6d0</groupId>
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
  </plugins>
</build>
```

You can find more examples here: [jooq-ydb-codegen-maven-plugin-example](./jooq-ydb-codegen-maven-plugin-example) 