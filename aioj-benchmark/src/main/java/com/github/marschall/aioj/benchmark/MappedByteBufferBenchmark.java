package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.benchmark.BufferUtils.sum;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

class MappedByteBufferBenchmark implements FileBenchmark {

  private final boolean force;

  MappedByteBufferBenchmark(boolean force) {
    this.force = force;
  }

  @Override
  public long read(String fileName) throws IOException {
    long sum = 0L;
    try (FileChannel channel = FileChannel.open(Paths.get(fileName), READ)) {
      long size = channel.size();
      int increment = Integer.MAX_VALUE;
      for (long position = 0; position < size; position += increment) {
        long mapSize = Math.min(size, position + increment) - position;
        MappedByteBuffer buffer = channel.map(READ_ONLY, position, mapSize);
        if (this.force) {
          buffer.force();
        }
        sum  += sum(buffer, Math.toIntExact(mapSize));
      }
    }
    return sum;
  }

  @Override
  public String getDescription() {
    if (this.force) {
      return "MappedByteBuffer with force";
    } else {
      return "MappedByteBuffer without force";
    }
  }

}
