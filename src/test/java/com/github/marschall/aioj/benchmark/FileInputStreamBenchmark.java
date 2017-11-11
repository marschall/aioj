package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.benchmark.BufferUtils.sum;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputStreamBenchmark implements FileBenchmark {

  private final int blockSize;

  public FileInputStreamBenchmark(int blockSize) {
    this.blockSize = blockSize;
  }

  @Override
  public long read(String filename) throws IOException {
    byte[] buffer = new byte[this.blockSize];
    long sum = 0L;
    try (FileInputStream stream = new FileInputStream(filename)) {
      int read = stream.read(buffer);
      while (read != -1) {
        sum += sum(buffer, read);
        read = stream.read(buffer);
      }
    }
    return sum;
  }

}
