package com.github.marschall.aioj.lowlevel;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

import com.github.marschall.aioj.capi.iocb;

public final class IoControlBlock {

  private Consumer<IoControlBlock> data;
  short aio_lio_opcode;
  int aio_fildes;

  private ByteBuffer buf;
  private long nbytes;
  private long offset;

  enum Command {

    /**
     * Positioned read, corresponds to pread() system call.
     */
    POSITIONED_READ(iocb.IOCB_CMD_PREAD),

    /**
     * Positioned write,´ corresponds to pwrite() system call.
     */
    POSITIONED_WRITE(iocb.IOCB_CMD_PWRITE),

    /**
     * Sync file’s data and metadata with disk; corresponds to fsync() system call.
     */
    FSYNC(iocb.IOCB_CMD_FSYNC),

    /**
     * Sync file’s data and metadata with disk, but only metadata needed to access modified file data is written; corresponds to fdatasync() system call.
     */
    FDATASYNC(iocb.IOCB_CMD_FDSYNC),
    /* These two are experimental.
     * public static final int IOCB_CMD_PREADX = 4,
     * public static final int IOCB_CMD_POLL = 5,
     */

    /**
     * Vectored positioned read, sometimes called "scattered input", corresponds to preadv() system call.
     */
    POSITIONED_SCATTERED_READ(iocb.IOCB_CMD_PREADV),

    /**
     * Vectored positioned write, sometimes called "gathered output", corresponds to pwritev() system call.
     */
    POSITIONED_GATHERED_WRITE(iocb.IOCB_CMD_PWRITEV);


    private final int value;

    Command(int value) {
      this.value = value;
    }
  }

}
