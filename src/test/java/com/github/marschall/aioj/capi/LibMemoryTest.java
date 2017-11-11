package com.github.marschall.aioj.capi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Disabled;

public class LibMemoryTest {

//  @Test
  public void getPageSize() {
    int pageSize = LibMemory.getPageSize();
    assertEquals(4096, pageSize);
  }

//  @Test
  public void allocateAligned() {
    ByteBuffer buffer = LibMemory.allocateAligned(512, 8192);
    assertNotNull(buffer);
    LibMemory.free(buffer);
  }

//  @Test
  public void getDirectBufferAddress() {
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

//  @Test
  @Disabled("needs mmap()ed area")
  public void madvise() {
    ByteBuffer buffer = ByteBuffer.allocateDirect(512);
    assertEquals(0, LibMemory.madvise(buffer, MadviseArgument.MADV_SEQUENTIAL));
  }

//  @Test
  public void mlock() {
    ByteBuffer buffer = ByteBuffer.allocateDirect(512);
    assertEquals(0, LibMemory.mlock(buffer));
    assertEquals(0, LibMemory.unmlock(buffer));
  }

}
