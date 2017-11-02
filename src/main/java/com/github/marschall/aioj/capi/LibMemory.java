package com.github.marschall.aioj.capi;

import java.nio.ByteBuffer;
import java.util.Objects;

public final class LibMemory {
  public static ByteBuffer allocateAligned(int alignment, int size) {
    if (alignment < 0) {
      throw new IllegalArgumentException("Negative alignment: " + alignment);
    }
    if (size < 0) {
      throw new IllegalArgumentException("Negative size: " + size);
    }
    ByteBuffer buffer = allocateAligned0(alignment, size);
    if (buffer == null) {
      throw new AllocationFailedException();
    }
    return buffer;
  }


  // https://linux.die.net/man/3/posix_memalign
  private static native ByteBuffer allocateAligned0(int alignment, int size);

  public static void free(ByteBuffer buffer) {
    Objects.requireNonNull(buffer, "buffer");
    if (!buffer.isDirect()) {
      throw new IllegalArgumentException("only direct buffers can be free()ed");
    }
    free0(buffer);
  }

  private static native void free0(ByteBuffer buffer);

  public static native void mlock(ByteBuffer buffer);

  public static native void unmlock(ByteBuffer buffer);

  public static native void madvise(ByteBuffer buffer, int advice);

}
