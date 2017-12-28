package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.capi.OpenArgument.O_RDONLY;
import static com.github.marschall.aioj.capi.OpenArgument.O_DIRECT;

import java.io.IOException;

import com.github.marschall.aioj.lowlevel.FileDescriptor;

class AiojReadBenchmark implements FileBenchmark {

  private final int bufferSize;
  private final boolean direct;

  AiojReadBenchmark(int bufferSize, boolean direct) {
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
      // FIXME
    }
    return 0;
  }

  @Override
  public String getDescription() {
    if (this.direct) {
      return "aioj read with " + ByteFormatUtils.formatBufferSize(this.bufferSize) + " buffer size, direct IO";
    } else {
      return "aioj read with " + ByteFormatUtils.formatBufferSize(this.bufferSize) + " buffer size, non-direct IO";
    }
  }

}
