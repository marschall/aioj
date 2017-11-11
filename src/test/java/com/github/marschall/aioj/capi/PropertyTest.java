package com.github.marschall.aioj.capi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class PropertyTest {

  @Test
  void test() {
    String javaLibraryPath = System.getProperty("java.library.path");
    assertNotNull(javaLibraryPath);
    System.err.println(javaLibraryPath);
  }

}
