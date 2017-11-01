package com.github.marschall.aioj.capi;

import java.nio.ByteBuffer;

public final class StructIocb {

  /**
   * Positioned read, corresponds to pread() system call.
   */
  public static final int IOCB_CMD_PREAD = 0;

  /**
   * Positioned write,´ corresponds to pwrite() system call.
   */
  public static final int IOCB_CMD_PWRITE = 1;

  /**
   * Sync file’s data and metadata with disk; corresponds to fsync() system call.
   */
  public static final int IOCB_CMD_FSYNC = 2;

  /**
   * Sync file’s data and metadata with disk, but only metadata needed to access modified file data is written; corresponds to fdatasync() system call.
   */
  public static final int IOCB_CMD_FDSYNC = 3;
  /* These two are experimental.
   * public static final int IOCB_CMD_PREADX = 4,
   * public static final int IOCB_CMD_POLL = 5,
   */

  /**
   * Defined in the header file, but is not used anywhere else in the kernel.
   */
  public static final int IOCB_CMD_NOOP = 6;

  /**
   * Vectored positioned read, sometimes called "scattered input", corresponds to preadv() system call.
   */
  public static final int IOCB_CMD_PREADV = 7;

  /**
   * Vectored positioned write, sometimes called "gathered output", corresponds to pwritev() system call.
   */
  public static final int IOCB_CMD_PWRITEV = 8;

  private Object data;
  short aio_lio_opcode;
  int aio_fildes;

  private ByteBuffer buf;
  private long nbytes;
  private long offset;

}
