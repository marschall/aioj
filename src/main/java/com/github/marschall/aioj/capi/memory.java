package com.github.marschall.aioj.capi;

import java.nio.ByteBuffer;

public final class memory {

  // https://linux.die.net/man/3/posix_memalign
  public static native ByteBuffer allocateAligned(int alignment, long size);


  public native void free(ByteBuffer buffer);

}
