package com.github.marschall.aioj.benchmark;

final class ByteFormatUtils {

  private ByteFormatUtils() {
    throw new AssertionError("not instantiable");
  }

  static String formatBufferSize(int bufferSize) {
    if ((bufferSize >= (1024 * 1024)) && ((bufferSize % (1024 * 1024)) == 0)) {
      return (bufferSize / (1024 * 1024)) + "m";
    }
    if ((bufferSize >= 1024) && ((bufferSize % 1024) == 0)) {
      return (bufferSize / 1024) + "k";
    }
    return bufferSize + "b";
  }

  static String formatToughput(long fileSize, long seconds) {
    double bytesPerSecond = (double) fileSize / (double) seconds;

    if (bytesPerSecond > (1024 * 1024 * 1024)) {
      return String.format("%.1f g/s", bytesPerSecond / (1024 * 1024 * 1024));
    }

    if (bytesPerSecond > (1024 * 1024)) {
      return String.format("%.1f m/s", bytesPerSecond / (1024 * 1024));
    }

    if (bytesPerSecond > 1024) {
      return String.format("%.1f k/s", bytesPerSecond / 1024);
    }

    return String.format("%.1f b/s", bytesPerSecond);
  }

}
