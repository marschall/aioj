package com.github.marschall.aioj.capi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

class LibMemoryTest {

  @Test
  void getPageSize() {
    int pageSize = LibMemory.getPageSize();
    assertEquals(4096, pageSize);
  }

  @Test
  void allocateAligned() {
    ByteBuffer buffer = LibMemory.allocateAligned(512, 8192);
    assertNotNull(buffer);
    LibMemory.free(buffer);
  }

}
