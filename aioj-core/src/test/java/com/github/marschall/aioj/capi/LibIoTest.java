package com.github.marschall.aioj.capi;

import static com.github.marschall.aioj.capi.MadviseArgument.MADV_DONTNEED;
import static com.github.marschall.aioj.capi.MmapArgument.MAP_SHARED;
import static com.github.marschall.aioj.capi.MmapArgument.PROT_NONE;
import static com.github.marschall.aioj.capi.OpenArgument.O_RDONLY;
import static com.github.marschall.aioj.capi.OpenArgument.O_RDWR;
import static com.github.marschall.aioj.capi.OpenArgument.O_TMPFILE;
import static com.github.marschall.aioj.capi.OpenArgument.S_IRUSR;
import static com.github.marschall.aioj.capi.OpenArgument.S_IWUSR;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
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

  @Test
  void mmap() throws IOException {
    Path pomXml = Paths.get("pom.xml").toAbsolutePath();
    long size = Files.size(pomXml);

    byte[] pathspec = pomXml.toString().getBytes();
    int fd = LibIo.open(pathspec, O_RDONLY);
    try {
      ByteBuffer buffer = LibIo.mmap(null, Math.toIntExact(size), PROT_NONE, MAP_SHARED, fd, 0L);
      assertNotNull(buffer);
      try {
        LibMemory.madvise(buffer, MADV_DONTNEED);
      } finally {
        LibIo.munmap(buffer);
      }
    } finally {
      LibIo.close(fd);
    }
  }

}
