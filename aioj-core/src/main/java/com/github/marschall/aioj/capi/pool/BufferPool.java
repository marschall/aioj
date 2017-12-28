package com.github.marschall.aioj.capi.pool;

import java.nio.ByteBuffer;

public interface BufferPool {

  ByteBuffer getBuffer();

  void releaseBuffer(ByteBuffer buffer);

}
