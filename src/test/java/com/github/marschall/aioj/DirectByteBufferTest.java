package com.github.marschall.aioj;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

class DirectByteBufferTest {

  @Test
  void test() {
    ByteBuffer buffer = ByteBuffer.allocateDirect(512);
    buffer.position(127);
    buffer.limit(255);

    ByteBuffer slice = buffer.slice();
  }

}
