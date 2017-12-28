package com.github.marschall.aioj.benchmark;

import static com.github.marschall.aioj.benchmark.BufferUtils.sum;
import static com.github.marschall.aioj.capi.MadviseArgument.MADV_DONTNEED;
import static com.github.marschall.aioj.capi.MadviseArgument.MADV_SEQUENTIAL;
import static com.github.marschall.aioj.capi.MadviseArgument.MADV_WILLNEED;
import static com.github.marschall.aioj.capi.MmapArgument.MAP_SHARED;
import static com.github.marschall.aioj.capi.MmapArgument.PROT_READ;
import static com.github.marschall.aioj.capi.OpenArgument.O_RDONLY;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.marschall.aioj.capi.LibIo;
import com.github.marschall.aioj.capi.LibMemory;
import com.github.marschall.aioj.lowlevel.FileDescriptor;

class AiojMmapBenchmark implements FileBenchmark {

  private final boolean madvise;

  AiojMmapBenchmark(boolean madvise) {
    this.madvise = madvise;
  }

  @Override
  public long read(String fileName) throws IOException {
    long sum = 0L;
    try (FileDescriptor fileDescriptor = FileDescriptor.open(fileName, O_RDONLY)) {
      long size = fileDescriptor.fstat().st_size;
      int increment = Integer.MAX_VALUE;
      for (long position = 0; position < size; position += increment) {
        int mapSize = Math.toIntExact(Math.min(size, position + increment) - position);
        int prot = PROT_READ;
        int flags = MAP_SHARED;
        ByteBuffer buffer = fileDescriptor.mmap(mapSize, prot, flags, mapSize);

        try {
          if (this.madvise) {
            LibMemory.madvise(buffer, MADV_WILLNEED | MADV_SEQUENTIAL);
          }
          sum  += sum(buffer, Math.toIntExact(mapSize));
          if (this.madvise) {
            LibMemory.madvise(buffer, MADV_DONTNEED);
          }
        } finally {
          LibIo.munmap(buffer);
        }
      }
    }
    return sum;
  }

  @Override
  public String getDescription() {
    if (this.madvise) {
      return "aioj mmap with madvise";
    } else {
      return "aioj mmap without madvise";
    }
  }

}
