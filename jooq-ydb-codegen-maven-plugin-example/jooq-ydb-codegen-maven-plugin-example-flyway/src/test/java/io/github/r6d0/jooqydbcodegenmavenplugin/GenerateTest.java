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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Checks generated files.
 *
 * @author Andrey_Yurzanov
 */
class GenerateTest {
  @Test
  void checkGeneratedFilesTest() {
    var basePath = System.getenv("GENERATE_DIRECTORY");

    Assertions.assertTrue(Files.exists(Paths.get(basePath, "FlywayTest.class")));
    Assertions.assertTrue(Files.exists(Paths.get(basePath, "records/FlywayTestRecord.class")));
  }
}
