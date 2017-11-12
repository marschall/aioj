package com.github.marschall.aioj.benchmark;

import java.nio.ByteBuffer;

final class BufferUtils {

  private BufferUtils() {
    throw new AssertionError("not instantiable");
  }

  static long sum(ByteBuffer buffer, int read) {
    if (buffer.hasArray()) {
      return sum(buffer.array(), read);
    } else {
      return slowSum(buffer, read);
    }
  }

  private static long slowSum(ByteBuffer buffer, int read) {
    long sum = 0L;
    for (int i = 0; i < read; i++) {
      sum += buffer.get(i);
    }
    return sum;
  }

  static long sum(byte[] buffer, int read) {
    long sum = 0L;
    for (int i = 0; i < read; i++) {
      sum += buffer[i];
    }
    return sum;
  }

}
