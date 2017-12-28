package com.github.marschall.aioj.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

public final class ChannelBridge {

  private ChannelBridge() {
    throw new AssertionError("not instantiable");
  }

  static final class AiojSeekableByteChannel implements SeekableByteChannel {

    @Override
    public boolean isOpen() {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public void close() throws IOException {
      // TODO Auto-generated method stub

    }

    @Override
    public int read(ByteBuffer dst) throws IOException {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public long position() throws IOException {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public SeekableByteChannel position(long newPosition) throws IOException {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public long size() throws IOException {
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public SeekableByteChannel truncate(long size) throws IOException {
      // TODO Auto-generated method stub
      return null;
    }

  }

}
