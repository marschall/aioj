package com.github.marschall.aioj.lowlevel;

import java.io.IOException;

public final class FileDescriptor implements AutoCloseable {

  private final int fd;

  private FileDescriptor(int fd) {
    this.fd = fd;
  }


  public static FileDescriptor open() throws IOException {
    // FIXME
    return null;
  }


  public int getLogicalBlocksize() throws IOException {
    return com.github.marschall.aioj.capi.fd.getLogicalBlocksize(this.fd);
  }

  public void fadvise(int flags) throws IOException {

  }

  @Override
  public void close() throws IOException {
    com.github.marschall.aioj.capi.fd.close(this.fd);
    // TODO Auto-generated method stub

  }

}
