package com.github.marschall.aioj.capi;

import java.nio.ByteBuffer;
import java.util.Objects;

public final class LibMemory {

  private LibMemory() {
    throw new AssertionError("not instantiable");
  }

  public static ByteBuffer allocateAligned(long alignment, int size) {
    // ByteBuffer supports only int indices
    if (alignment < 0) {
      throw new IllegalArgumentException("Negative alignment: " + alignment);
    }
    if (size < 0) {
      throw new IllegalArgumentException("Negative size: " + size);
    }
    ByteBuffer buffer = aligned_alloc0(alignment, size);
    if (buffer == null) {
      throw new AllocationFailedException();
    }
    return buffer;
  }


  // https://linux.die.net/man/3/posix_memalign
  private static native ByteBuffer aligned_alloc0(long alignment, long size);

  public static void free(ByteBuffer buffer) {
    requireDirect(buffer);
    free0(buffer);
  }

  private static native void free0(ByteBuffer buffer);

  // https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetDirectBufferAddress
  public static long getDirectBufferAddress(ByteBuffer buffer) {
    requireDirect(buffer);
    return getDirectBufferAddress0(buffer);
  }

  private static native long getDirectBufferAddress0(ByteBuffer buffer);

  // https://docs.oracle.com/javase/8/docs/technotes/guides/jni/spec/functions.html#GetDirectBufferCapacity
  public static long getDirectBufferCapacity(ByteBuffer buffer) {
    requireDirect(buffer);
    return getDirectBufferCapacity0(buffer);
  }

  private static native long getDirectBufferCapacity0(ByteBuffer buffer);

  public static int mlock(ByteBuffer buffer) {
    requireDirect(buffer);
    return mlock0(buffer);
  }

  private static native int mlock0(ByteBuffer buffer);

  public static int unmlock(ByteBuffer buffer) {
    requireDirect(buffer);
    return unmlock0(buffer);
  }

  private static native int unmlock0(ByteBuffer buffer);

  public static int madvise(ByteBuffer buffer, int advice) {
    requireDirect(buffer);
    return madvise0(buffer, advice);
  }

  private static native int madvise0(ByteBuffer buffer, int advice);

  public static int getPageSize() {
    return getpagesize0();
  }

  private static native int getpagesize0();

  private static void requireDirect(ByteBuffer buffer) {
    Objects.requireNonNull(buffer, "buffer");
    if (!buffer.isDirect()) {
      throw new IllegalArgumentException("only direct buffers allowed");
    }
  }

}
