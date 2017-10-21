package com.github.marschall.aioj.capi;

import java.nio.ByteBuffer;

public class iocb {

  private int data;
  short aio_lio_opcode;
  int aio_fildes;

  private ByteBuffer buf;
  private long nbytes;
  private long offset;

}
