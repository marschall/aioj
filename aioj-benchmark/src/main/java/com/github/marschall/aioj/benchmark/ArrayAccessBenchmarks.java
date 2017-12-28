package com.github.marschall.aioj.benchmark;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Benchmarks designed to measure the relative cost of accessing arrays
 * or different kinds of ByteBuffers.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
public class ArrayAccessBenchmarks {

  @Param("1024")
  public int bufferSize;

  private ByteBuffer heapBuffer;
  private ByteBuffer directBuffer;
  private byte[] array;

  @Setup
  public void setup() {
    this.array = new byte[bufferSize];
    this.heapBuffer = ByteBuffer.allocate(bufferSize);
    this.directBuffer = ByteBuffer.allocateDirect(bufferSize);
  }

  @Benchmark
  public void array(Blackhole blackhole) {
    for (byte b : array) {
      blackhole.consume(b);
    }
  }

  @Benchmark
  public void heapBufferAbsolute(Blackhole blackhole) {
    int capacity = this.heapBuffer.capacity();
    for (int i = 0; i < capacity; i++) {
      byte b = this.heapBuffer.get(i);
      blackhole.consume(b);
    }
  }

  @Benchmark
  public void heapBufferRelative(Blackhole blackhole) {
    this.heapBuffer.position(0);
    int capacity = this.heapBuffer.capacity();
    for (int i = 0; i < capacity; i++) {
      byte b = this.heapBuffer.get();
      blackhole.consume(b);
    }
  }

  @Benchmark
  public void directBufferAbsolute(Blackhole blackhole) {
    int capacity = this.directBuffer.capacity();
    for (int i = 0; i < capacity; i++) {
      byte b = this.heapBuffer.get(i);
      blackhole.consume(b);
    }
  }

  @Benchmark
  public void directBufferRelative(Blackhole blackhole) {
    this.directBuffer.position(0);
    int capacity = this.directBuffer.capacity();
    for (int i = 0; i < capacity; i++) {
      byte b = this.directBuffer.get();
      blackhole.consume(b);
    }
  }

}
