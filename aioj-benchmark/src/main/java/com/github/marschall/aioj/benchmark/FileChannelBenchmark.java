package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.benchmark.BufferUtils.sum;
import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class FileChannelBenchmark implements FileBenchmark {

  private final int bufferSize;
  private final boolean offHead;

  public FileChannelBenchmark(int bufferSize, boolean offHead) {
    this.bufferSize = bufferSize;
    this.offHead = offHead;
  }

  @Override
  public long read(String filename) throws IOException {
    ByteBuffer buffer = this.createBuffer();
    long sum = 0L;
    try (FileChannel channel = FileChannel.open(Paths.get(filename), READ)) {
      int read = channel.read(buffer);
      while (read != -1) {
        sum += sum(buffer, read);
        read = channel.read(buffer);
      }
    }
    return sum;
  }

  private ByteBuffer createBuffer() {
    if (this.offHead) {
      return ByteBuffer.allocateDirect(this.bufferSize);
    } else {
      return ByteBuffer.allocate(this.bufferSize);
    }
  }

}
