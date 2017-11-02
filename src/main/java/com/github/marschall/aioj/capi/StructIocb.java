package com.github.marschall.aioj.capi;

import java.nio.ByteBuffer;
import java.util.Objects;

public final class StructIocb {

  /**
   * Positioned read, corresponds to pread() system call.
   */
  public static final int IOCB_CMD_PREAD = 0;

  /**
   * Positioned write, corresponds to pwrite() system call.
   */
  public static final int IOCB_CMD_PWRITE = 1;

  /**
   * Sync file's data and metadata with disk; corresponds to fsync() system call.
   */
  public static final int IOCB_CMD_FSYNC = 2;

  /**
   * Sync file's data and metadata with disk, but only metadata needed to access modified file data is written; corresponds to fdatasync() system call.
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

  public void read(int fd, ByteBuffer buf, long offset, int nbytes, Object data) {
    Objects.requireNonNull(buf, "buf");
    if (!buf.isDirect()) {
      throw new IllegalArgumentException("only direct buffers supported");
    }
    if (offset < 0) {
      throw new IllegalArgumentException();
    }
    // ByteBuffer supports only int lengths
    if ((nbytes < 0) || ((offset + nbytes) > buf.capacity())) {
      throw new IllegalArgumentException();
    }
    this.aio_lio_opcode = IOCB_CMD_PREAD;
    this.aio_fildes = fd;
    this.data = data;

    this.buf = null;
    this.offset = offset;
    this.nbytes = nbytes;
  }

  public void write(int fd, ByteBuffer buf, long offset, int nbytes, Object data) {
    Objects.requireNonNull(buf, "buf");
    if (!buf.isDirect()) {
      throw new IllegalArgumentException("only direct buffers supported");
    }
    if (offset < 0) {
      throw new IllegalArgumentException();
    }
    // ByteBuffer supports only int offsets
    if ((nbytes < 0) || ((offset + nbytes) > buf.capacity())) {
      throw new IllegalArgumentException();
    }
    this.aio_lio_opcode = IOCB_CMD_PWRITE;
    this.aio_fildes = fd;
    this.data = data;

    this.buf = null;
    this.offset = offset;
    this.nbytes = nbytes;
  }

  public void fdatasync(int fd, Object data) {
    this.aio_lio_opcode = IOCB_CMD_FDSYNC;
    this.aio_fildes = fd;
    this.data = data;

    this.buf = null;
    this.nbytes = 0L;
    this.offset = 0L;
  }

  public void fsync(int fd, Object data) {
    this.aio_lio_opcode = IOCB_CMD_FSYNC;
    this.aio_fildes = fd;
    this.data = data;

    this.buf = null;
    this.nbytes = 0L;
    this.offset = 0L;
  }

}
