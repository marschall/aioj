package com.github.marschall.aioj.capi;

import static com.github.marschall.aioj.capi.OpenArgument.O_RDONLY;
import static com.github.marschall.aioj.capi.OpenArgument.O_RDWR;
import static com.github.marschall.aioj.capi.OpenArgument.O_TMPFILE;
import static com.github.marschall.aioj.capi.OpenArgument.S_IRUSR;
import static com.github.marschall.aioj.capi.OpenArgument.S_IWUSR;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class LibIoTest {

  @Test
  void openExists() throws IOException {
    byte[] pathspec = Paths.get("pom.xml").toAbsolutePath().toString().getBytes();
    int fd = LibIo.open(pathspec, O_RDONLY);
    LibIo.close(fd);
  }

  @Test
  void openNotExists() throws IOException {
    byte[] pathspec = Paths.get("pom.txt").toAbsolutePath().toString().getBytes();
    IOException ioException = assertThrows(IOException.class, () -> LibIo.open(pathspec, O_RDONLY));
    assertNotEquals("could not open() file", ioException.getMessage());
  }

  @Test
  void openTempFile() throws IOException {
    byte[] pathspec = Paths.get("pom.txt").toAbsolutePath().getParent().toString().getBytes();
    int fd = LibIo.open(pathspec, O_RDWR | O_TMPFILE, S_IRUSR | S_IWUSR);
    LibIo.close(fd);
  }

}
