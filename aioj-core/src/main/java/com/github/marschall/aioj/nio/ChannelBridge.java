package com.github.marschall.aioj.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.util.Objects;

import com.github.marschall.aioj.capi.LibIo;
import com.github.marschall.aioj.capi.LseekArgument;
import com.github.marschall.aioj.capi.StructStat;

public final class ChannelBridge {

  private ChannelBridge() {
    throw new AssertionError("not instantiable");
  }

  static final class AiojSeekableByteChannel implements SeekableByteChannel {

    private final int fd;

    AiojSeekableByteChannel(int fd) {
      this.fd = fd;
    }

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
      requireDirect(dst);
      return 0;
    }

    @Override
    public int write(ByteBuffer src) throws IOException {
      requireDirect(src);
      // TODO Auto-generated method stub
      return 0;
    }

    @Override
    public long position() throws IOException {
      // TODO Auto-generated method stub
      return LibIo.lseek(this.fd, 0, LseekArgument.SEEK_CUR);
    }

    @Override
    public SeekableByteChannel position(long newPosition) throws IOException {
      // TODO Auto-generated method stub
      if (newPosition < 0L) {
        throw new IllegalArgumentException("negative position");
      }
      LibIo.lseek(this.fd, newPosition, LseekArgument.SEEK_SET);
      return this;
    }

    @Override
    public long size() throws IOException {
      // TODO Auto-generated method stub
      StructStat statbuf = new StructStat();
      LibIo.fstat(fd, statbuf);
      return statbuf.st_size;
    }

    @Override
    public SeekableByteChannel truncate(long size) throws IOException {
      // TODO Auto-generated method stub
      if (size < 0L) {
        throw new IllegalArgumentException("negative size");
      }
      LibIo.ftruncate(this.fd, size);
      return this;
    }

    static void requireDirect(ByteBuffer buffer) {
      Objects.requireNonNull(buffer, "buffer");
      if (!buffer.isDirect()) {
        throw new IllegalArgumentException("only direct buffers allowed");
      }
    }

  }

}
