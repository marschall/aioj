package com.github.marschall.aioj.lowlevel;

import java.io.IOException;
import java.time.Duration;

import com.github.marschall.aioj.capi.aio_context;

public final class AioContext implements AutoCloseable {

  private final long ctx;

  private AioContext(long ctx) {
    this.ctx = ctx;
  }

  public static AioContext setUp(int nr) {
    long ctx = aio_context.io_setup(nr);
    // FIXME
    return new AioContext(ctx);
  }

  public void getEventsNonBlocking(long min, long max) {

  }

  public void getEventsBlocking(long min, long max) {

  }

  // https://linux.die.net/man/3/clock_gettime
  public void getEvents(long min, long max, long seconds, long nanos) {

  }

  public void getEvents(long min, long max, Duration timeout) {

  }

  public void cancel(IoControlBlock controlBlock) throws IOException {

  }

  @Override
  public void close() throws IOException {
    aio_context.io_destroy(this.ctx);
    // TODO Auto-generated method stub

  }

}
