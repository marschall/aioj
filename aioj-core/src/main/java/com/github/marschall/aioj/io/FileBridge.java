package com.github.marschall.aioj.io;

import java.io.File;
import java.io.IOException;

import com.github.marschall.aioj.lowlevel.FileDescriptor;

public final class FileBridge {

  private FileBridge() {
    throw new AssertionError("not instantiable");
  }


  public static FileDescriptor open(File file, int flags) throws IOException {
    return FileDescriptor.open(file.toString(), flags);
  }

  public static FileDescriptor open(File file, int flags, int mode) throws IOException {
    return FileDescriptor.open(file.toString(), flags, mode);
  }

}
