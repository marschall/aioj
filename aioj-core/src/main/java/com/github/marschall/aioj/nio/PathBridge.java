package com.github.marschall.aioj.nio;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.github.marschall.aioj.lowlevel.FileDescriptor;

public final class PathBridge {

  private PathBridge() {
    throw new AssertionError("not instantiable");
  }


  public static FileDescriptor open(Path path, int flags) throws IOException {
    requireDefaultFileSystem(path);
    return FileDescriptor.open(path.toString(), flags);
  }

  public static FileDescriptor open(Path path, int flags, int mode) throws IOException {
    requireDefaultFileSystem(path);
    return FileDescriptor.open(path.toString(), flags, mode);
  }

  private static void requireDefaultFileSystem(Path path) {
    if (path.getFileSystem() != FileSystems.getDefault()) {
      throw new IllegalArgumentException("only default file system is supported");
    }
  }

}
