package com.github.marschall.aioj.lowlevel;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class IoControlBlock {

  private Consumer<IoControlBlock> data;
  short aio_lio_opcode;
  int aio_fildes;

  private ByteBuffer buf;
  private long nbytes;
  private long offset;

}
