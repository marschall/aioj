package com.github.marschall.aioj.capi;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class PropertyTest {

  @Test
  void test() {
    String javaLibraryPath = System.getProperty("java.library.path");
    assertNotNull(javaLibraryPath);
    Path path = Paths.get(javaLibraryPath);
    assertTrue(Files.exists(path));
    assertTrue(Files.isDirectory(path));
  }

}
