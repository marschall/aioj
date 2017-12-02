package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.benchmark.BufferUtils.sum;

import java.io.FileInputStream;
import java.io.IOException;

class FileInputStreamBenchmark implements FileBenchmark {

  private final int blockSize;

  FileInputStreamBenchmark(int blockSize) {
    this.blockSize = blockSize;
  }

  @Override
  public long read(String fileName) throws IOException {
    byte[] buffer = new byte[this.blockSize];
    long sum = 0L;
    try (FileInputStream stream = new FileInputStream(fileName)) {
      int read = stream.read(buffer);
      while (read != -1) {
        sum += sum(buffer, read);
        read = stream.read(buffer);
      }
    }
    return sum;
  }

}
