package com.github.marschall.aioj.capi;

import java.nio.ByteBuffer;
import java.util.Objects;

final class BufferAssertions {

  private BufferAssertions() {
    throw new AssertionError("not instantiable");
  }

  static void requireDirect(ByteBuffer buffer) {
    Objects.requireNonNull(buffer, "buffer");
    if (!buffer.isDirect()) {
      throw new IllegalArgumentException("only direct buffers allowed");
    }
  }

}
