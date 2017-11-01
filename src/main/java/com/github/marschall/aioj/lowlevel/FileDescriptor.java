package com.github.marschall.aioj.lowlevel;

import java.io.IOException;
import java.nio.ByteBuffer;

public final class FileDescriptor implements AutoCloseable {

  private final int fd;

  private FileDescriptor(int fd) {
    this.fd = fd;
  }

  public static FileDescriptor open(String pathname, int flags) throws IOException {
    int fd = com.github.marschall.aioj.capi.fd.open(null, flags);
    // FIXME
    return new FileDescriptor(fd);
  }

  public static FileDescriptor open(String pathname, int flags, int mode) throws IOException {
    int fd = com.github.marschall.aioj.capi.fd.open(null, flags, mode);
    // FIXME
    return new FileDescriptor(fd);
  }


  public int getLogicalBlocksize() throws IOException {
    return com.github.marschall.aioj.capi.fd.getLogicalBlocksize(this.fd);
  }

  public void fadvise(long offset, long len, int flags) throws IOException {
    com.github.marschall.aioj.capi.fd.fadvise(this.fd, offset, len, flags);
  }

  public void mmap(ByteBuffer buffer, long length, int prot, int flags, long offset) {
    com.github.marschall.aioj.capi.fd.mmap(buffer, length, prot, flags, this.fd, offset);
  }

  @Override
  public void close() throws IOException {
    com.github.marschall.aioj.capi.fd.close(this.fd);
    // TODO Auto-generated method stub

  }

}
