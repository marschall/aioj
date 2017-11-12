package com.github.marschall.aioj.capi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class LibMemoryTest {

  @Test
  void getPageSize() {
    int pageSize = LibMemory.getPageSize();
    assertEquals(4096, pageSize);
  }

  @Test
  void allocateAlignedSuccess() {
    assumeTrue(System.getProperty("TRAVIS", "false").equals("false"));
    ByteBuffer buffer = LibMemory.allocateAligned(512, 8192);
    assertNotNull(buffer);
    LibMemory.free(buffer);
  }

  @Test
  void allocateAlignedNotMultiple() {
    assertThrows(IllegalArgumentException.class, () -> LibMemory.allocateAligned(512, 128));
  }

  @Test
  void allocateAlignedNotPowerOfTwo() {
    assertThrows(IllegalArgumentException.class, () -> LibMemory.allocateAligned(511, 8192));
  }

  @Test
  @Disabled("somehow doesn't throw")
  void allocateAlignedTooSmall() {
    AllocationFailedException exception = assertThrows(AllocationFailedException.class, () -> LibMemory.allocateAligned(2, 32));
    assertNotEquals("allocation failed", exception.getMessage());
  }

  @Test
  void getDirectBufferAddress() {
    ByteBuffer buffer = ByteBuffer.allocateDirect(512);
    long address = LibMemory.getDirectBufferAddress(buffer);


    buffer.position(128);
    buffer.limit(128 + 256);

    assertEquals(buffer.capacity(), LibMemory.getDirectBufferCapacity(buffer));
    assertEquals(512, LibMemory.getDirectBufferCapacity(buffer));

    ByteBuffer slice = buffer.slice();

    assertEquals(128L, LibMemory.getDirectBufferAddress(slice) - address);

    assertEquals(buffer.capacity(), LibMemory.getDirectBufferCapacity(buffer));
    assertEquals(256, LibMemory.getDirectBufferCapacity(slice));
  }

  @Test
  @Disabled("needs mmap()ed area")
  void madvise() {
    ByteBuffer buffer = ByteBuffer.allocateDirect(512);
    assertEquals(0, LibMemory.madvise(buffer, MadviseArgument.MADV_SEQUENTIAL));
  }

  @Test
  void mlock() {
    ByteBuffer buffer = ByteBuffer.allocateDirect(512);
    assertEquals(0, LibMemory.mlock(buffer));
    assertEquals(0, LibMemory.munlock(buffer));
  }

}
