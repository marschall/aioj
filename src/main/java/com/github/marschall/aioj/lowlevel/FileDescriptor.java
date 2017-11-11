package com.github.marschall.aioj.lowlevel;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.marschall.aioj.capi.LibIo;

public final class FileDescriptor implements AutoCloseable {

  private final int fd;

  private FileDescriptor(int fd) {
    this.fd = fd;
  }

  public static FileDescriptor open(String pathname, int flags) throws IOException {
    int fd = LibIo.open(null, flags);
    return new FileDescriptor(fd);
  }

  public static FileDescriptor open(String pathname, int flags, int mode) throws IOException {
    int fd = LibIo.open(null, flags, mode);
    return new FileDescriptor(fd);
  }


  public int getBlockSize() throws IOException {
    return LibIo.getBlockSize(this.fd);
  }

  public void fadvise(long offset, long len, int flags) throws IOException {
    LibIo.fadvise(this.fd, offset, len, flags);
  }

  public void mmap(ByteBuffer buffer, long length, int prot, int flags, long offset) {
    LibIo.mmap(buffer, length, prot, flags, this.fd, offset);
  }

  @Override
  public void close() throws IOException {
    LibIo.close(this.fd);
  }

}
