package com.github.marschall.aioj.capi;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class LibIoTest {

  @Test
  void openExists() throws IOException {
    byte[] pathspec = Paths.get("pom.xml").toAbsolutePath().toString().getBytes();
    int fd = LibIo.open(pathspec, OpenArgument.O_RDONLY);
    LibIo.close(fd);
  }

  @Test
  void openNotExists() throws IOException {
    byte[] pathspec = Paths.get("pom.txt").toAbsolutePath().toString().getBytes();
    assertThrows(IOException.class, () -> LibIo.open(pathspec, OpenArgument.O_RDONLY));
  }

}
