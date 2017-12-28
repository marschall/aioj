package com.github.marschall.aioj.capi.pool;

import java.nio.ByteBuffer;

import com.github.marschall.aioj.capi.LibMemory;

/**
 * A simple {@link BufferPool} that always allocates and frees a {@link ByteBuffer}.
 */
public final class NoBufferPool implements BufferPool {

  private final int bufferSize;
  private final long alignment;

  private NoBufferPool(long alignment, int bufferSize) {
    this.bufferSize = bufferSize;
    this.alignment = alignment;
  }

  public static BufferPool newInstance(long alignment, int bufferSize) {
    return new NoBufferPool(alignment, bufferSize);
  }

  @Override
  public ByteBuffer getBuffer() {
    return LibMemory.allocateAligned(this.alignment, this.bufferSize);
  }

  @Override
  public void releaseBuffer(ByteBuffer buffer) {
    LibMemory.free(buffer);
  }

}
