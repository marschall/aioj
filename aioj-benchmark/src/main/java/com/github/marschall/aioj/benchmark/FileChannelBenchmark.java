package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.benchmark.BufferUtils.sum;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannelBenchmark implements FileBenchmark {

  private final int blockSize;
  private final boolean direct;

  public FileChannelBenchmark(int blockSize, boolean direct) {
    this.blockSize = blockSize;
    this.direct = direct;
  }

  @Override
  public long read(String filename) throws IOException {
    ByteBuffer buffer = this.createBuffer();
    long sum = 0L;
    try (FileChannel channel = FileChannel.open(Paths.get(filename), StandardOpenOption.READ)) {
      int read = channel.read(buffer);
      while (read != -1) {
        sum += sum(buffer, read);
        read = channel.read(buffer);
      }
    }
    return sum;
  }

  private ByteBuffer createBuffer() {
    if (this.direct) {
      return ByteBuffer.allocateDirect(this.blockSize);
    } else {
      return ByteBuffer.allocate(this.blockSize);
    }
  }

}
