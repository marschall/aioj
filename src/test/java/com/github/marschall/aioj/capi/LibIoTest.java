package com.github.marschall.aioj.capi;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class LibIoTest {

  @Test
  void open() throws IOException {
    byte[] pathspec = Paths.get("pom.xml").toAbsolutePath().toString().getBytes();
    int fd = LibIo.open(pathspec, OpenArgument.O_RDONLY);
    LibIo.close(fd);
  }

}
