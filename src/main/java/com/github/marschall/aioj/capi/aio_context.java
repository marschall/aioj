package com.github.marschall.aioj.capi;

public class aio_context {

  // int io_setup(unsigned nr, aio_context_t *ctxp)
  public static native long io_setup(int nr);

  // int io_cancel(aio_context_t ctx_id, struct iocb *iocb, struct io_event *result);

  public static native int io_destroy(long ctx);
//  int io_submit(aio_context_t ctx, long nr, struct iocb **iocbpp);
//  int io_getevents(aio_context_t ctx, long min_nr, long nr,
//      struct io_event *events, struct timespec *timeout);

}
