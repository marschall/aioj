package com.github.marschall.aioj.capi;

import java.nio.ByteBuffer;

public final class aio_context {

  // int io_setup(unsigned nr, aio_context_t *ctxp)
  public static native long io_setup(int nr);

  // int io_cancel(aio_context_t ctx_id, struct iocb *iocb, struct io_event *result);

  public static native int io_destroy(long ctx);

  public static native int io_submit(long ctx, long[] iocbpp);
//  int io_submit(aio_context_t ctx, long nr, struct iocb **iocbpp);

  private static int io_submit(long ctx, int mode, int fd, long fileOffset, ByteBuffer buffer) {
    // FIXME
    return -1;
  }

  private static int io_submit(long ctx, int mode, int fd, long fileOffset, ByteBuffer buffer, int bufferOffset, int length) {
    // FIXME
    return -1;
  }

  private static int fsync(long ctx, int fd) {
    // FIXME
    return -1;
  }

  private static int fdatasync(long ctx, int fd) {
    // FIXME
    return -1;
  }

//  int io_getevents(aio_context_t ctx, long min_nr, long nr,
//      struct io_event *events, struct timespec *timeout);

}
