package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.benchmark.BufferUtils.sum;
import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

class FileChannelBenchmark implements FileBenchmark {

  private final int bufferSize;
  private final boolean offHeap;

  FileChannelBenchmark(int bufferSize, boolean offHeap) {
    this.bufferSize = bufferSize;
    this.offHeap = offHeap;
  }

  @Override
  public long read(String fileName) throws IOException {
    ByteBuffer buffer = this.createBuffer();
    long sum = 0L;
    try (FileChannel channel = FileChannel.open(Paths.get(fileName), READ)) {
      int read = channel.read(buffer);
      while (read != -1) {
        sum += sum(buffer, read);
        read = channel.read(buffer);
      }
    }
    return sum;
  }

  private ByteBuffer createBuffer() {
    if (this.offHeap) {
      return ByteBuffer.allocateDirect(this.bufferSize);
    } else {
      return ByteBuffer.allocate(this.bufferSize);
    }
  }

  @Override
  public String getDescription() {
    if (this.offHeap) {
      return "FileChannel with " + ByteFormatUtils.formatBufferSize(this.bufferSize) + " offheap buffer size";
    } else {
      return "FileChannel with " + ByteFormatUtils.formatBufferSize(this.bufferSize) + " heap buffer size";
    }
  }

}
