package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.capi.OpenArgument.O_DIRECT;
import static com.github.marschall.aioj.capi.OpenArgument.O_RDONLY;

import java.io.IOException;

import com.github.marschall.aioj.lowlevel.FileDescriptor;

class AiojPreadBechmark implements FileBenchmark {

  private final int bufferSize;
  private final boolean direct;

  AiojPreadBechmark(int bufferSize, boolean direct) {
    this.bufferSize = bufferSize;
    this.direct = direct;
  }

  @Override
  public long read(String fileName) throws IOException {
    int flags;
    if (this.direct) {
      flags = O_RDONLY | O_DIRECT;
    } else {
      flags = O_RDONLY;
    }
    try (FileDescriptor fileDescriptor = FileDescriptor.open(fileName, flags)) {

    }
    return 0;
  }

  @Override
  public String getDescription() {
    if (this.direct) {
      return "aioj pread with " + ByteFormatUtils.formatBufferSize(this.bufferSize) + " buffer size, direct IO";
    } else {
      return "aioj pread with " + ByteFormatUtils.formatBufferSize(this.bufferSize) + " buffer size, non-direct IO";
    }
  }

}
