package com.github.marschall.aioj.lowlevel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;

import com.github.marschall.aioj.capi.LibAio;
import com.github.marschall.aioj.capi.StructIoEvent;

public final class AioContext implements AutoCloseable {

  private final long ctx;

  private AioContext(long ctx) {
    this.ctx = ctx;
  }

  public static AioContext setUp(int nr) {
    long ctx = LibAio.io_setup(nr);
    // FIXME
    return new AioContext(ctx);
  }

  public void getEventsNonBlocking(int min, int max, StructIoEvent[] events) {
    LibAio.io_getevents(this.ctx, min, max, events, Duration.ZERO);
  }

  public void getEventsBlocking(int min, int max, StructIoEvent[] events, Duration timeout) {
    LibAio.io_getevents(this.ctx, min, max, events, timeout);
  }

  public void getAllEventsBlocking(int count, StructIoEvent[] events) {
    LibAio.io_getevents(this.ctx, count, count, events, null);
  }

  public void read(FileDescriptor fileDescriptor, long position, ByteBuffer buffer) {

  }

  public void write(FileDescriptor fileDescriptor, long position, ByteBuffer buffer) {

  }

  // https://linux.die.net/man/3/clock_gettime
  public void getEvents(long min, long max, long seconds, long nanos) {
    if (min < 0L) {
      throw new  IllegalArgumentException("negative count");
    }
    if (max < 0L) {
      throw new  IllegalArgumentException("negative count");
    }
    if (seconds < 0L) {
      throw new  IllegalArgumentException("negative timeout");
    }
    if (nanos < 0L) {
      throw new  IllegalArgumentException("negative timeout");
    }
  }

  public void getEvents(long min, long max, Duration timeout) {
    if (timeout == null) {

    }
    if (timeout.equals(Duration.ZERO)) {

    }
    if (timeout.isNegative()) {
      throw new  IllegalArgumentException("negative timeout");
    }

  }

  public void cancel(IoControlBlock controlBlock) throws IOException {

  }

  @Override
  public void close() throws IOException {
    LibAio.io_destroy(this.ctx);
    // TODO Auto-generated method stub

  }

}
