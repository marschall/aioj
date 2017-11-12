package com.github.marschall.aioj.capi;

import java.time.Duration;
import java.util.Objects;

public final class LibAio {

  public static long io_setup(int nr) {
    if (nr <= 0) {
      throw new IllegalArgumentException();
    }
    return io_setup0(nr);
  }

  // int io_setup(unsigned nr, aio_context_t *ctxp)
  private static native long io_setup0(int nr);

  // int io_cancel(aio_context_t ctx_id, struct iocb *iocb, struct io_event *result);

  public static int io_destroy(long ctx) {
    return io_destroy0(ctx);
  }

  private static native int io_destroy0(long ctx);

  public static int io_submit(long ctx, StructIocb[] iocbpp) {
    Objects.requireNonNull(iocbpp, "iocbpp");
    return io_submit0(ctx, iocbpp.length, iocbpp);
  }

  public static int io_submit(long ctx, int nr, StructIocb[] iocbpp) {
    Objects.requireNonNull(iocbpp, "iocbpp");
    if ((nr < 0) || (nr > iocbpp.length)) {
      throw new IllegalArgumentException();
    }
    return io_submit0(ctx, nr, iocbpp);
  }

  private static native int io_submit0(long ctx, long nr, StructIocb[] iocbpp);

//  private static int io_submit(long ctx, int mode, int fd, long fileOffset, ByteBuffer buffer) {
//    // FIXME
//    return -1;
//  }
//
//  private static int io_submit(long ctx, int mode, int fd, long fileOffset, ByteBuffer buffer, int bufferOffset, int length) {
//    // FIXME
//    return -1;
//  }
//
//  private static int fsync(long ctx, int fd) {
//    // FIXME
//    return -1;
//  }
//
//  private static int fdatasync(long ctx, int fd) {
//    // FIXME
//    return -1;
//  }

  public static int io_getevents(long ctx, int min_nr, int nr, StructIoEvent[] events, Duration timeout) {
    // array length can only be ints, therefore only ints are allowed
    Objects.requireNonNull(events, "events");
    if ((min_nr < 0) || (nr < 0) || (nr > events.length)) {
      throw new IllegalArgumentException();
    }
    if ((timeout == null) || timeout.isZero()) {
      return io_getevents0(ctx, min_nr, nr, events);
    } else {
      if (timeout.isNegative()) {
        throw new IllegalArgumentException();
      }
      return io_getevents0(ctx, min_nr, nr, events, timeout.getSeconds(), timeout.getNano());
    }

  }

  // C api expects longs
  private static native int io_getevents0(long ctx, long min_nr, long nr, StructIoEvent[] events, long seconds, long nanoseconds);

  // C api expects longs
  private static native int io_getevents0(long ctx, long min_nr, long nr, StructIoEvent[] events);

}
