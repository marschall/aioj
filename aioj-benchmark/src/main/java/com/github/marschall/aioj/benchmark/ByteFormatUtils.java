package com.github.marschall.aioj.benchmark;

final class ByteFormatUtils {

  private ByteFormatUtils() {
    throw new AssertionError("not instantiable");
  }

  static String formatBufferSize(int bufferSize) {
    if (bufferSize >= 1024 * 1024 && bufferSize % (1024 * 1024) == 0) {
      return bufferSize / (1024 * 1024) + "m";
    }
    if (bufferSize >= 1024 && bufferSize % 1024 == 0) {
      return bufferSize / 1024 + "k";
    }
    return bufferSize + "b";
  }

}
