package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.benchmark.BufferUtils.sum;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class MappedByteBufferBenchmark implements FileBenchmark {

  private final boolean force;

  public MappedByteBufferBenchmark(boolean force) {
    this.force = force;
  }

  @Override
  public long read(String filename) throws IOException {
    long sum = 0L;
    try (FileChannel channel = FileChannel.open(Paths.get(filename), READ)) {
      long size = channel.size();
      int increment = Integer.MAX_VALUE;
      for (int i = 0; i < size; i += increment) {
        long mapSize = Math.min(size, i + increment) - i;
        MappedByteBuffer buffer = channel.map(READ_ONLY, i, mapSize);
        if (this.force) {
          buffer.force();
          sum  += sum(buffer, Math.toIntExact(mapSize));
        }
      }
    }
    return sum;
  }

}
