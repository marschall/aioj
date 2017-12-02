package com.github.marschall.aioj.benchmark;

import java.nio.ByteBuffer;

/**
 * Benchmarks designed to measure the relative cost of accessing arrays
 * or different kinds of ByteBuffers.
 */
public class ArrayAccessBenchmarks {

  private ByteBuffer heapBuffer;
  private ByteBuffer directBuffer;
  private byte[] array;

  private void setUp(int bufferSize) {
    this.array = new byte[bufferSize];
    this.heapBuffer = ByteBuffer.allocate(bufferSize);
    this.directBuffer = ByteBuffer.allocateDirect(bufferSize);
  }

  public void array() {
    for (byte b : array) {
      // consume
    }
  }

  public void heapBufferAbsolute() {
    int capacity = this.heapBuffer.capacity();
    for (int i = 0; i < capacity; i++) {
      this.heapBuffer.get(i);
    }
  }

  public void heapBufferRelative() {
    this.heapBuffer.position(0);
    int capacity = this.heapBuffer.capacity();
    for (int i = 0; i < capacity; i++) {
      this.heapBuffer.get();
    }
  }

  public void directBufferAbsolute() {
    int capacity = this.directBuffer.capacity();
    for (int i = 0; i < capacity; i++) {
      this.heapBuffer.get(i);
    }
  }

  public void directBufferRelative() {
    this.directBuffer.position(0);
    int capacity = this.directBuffer.capacity();
    for (int i = 0; i < capacity; i++) {
      this.directBuffer.get();
    }
  }

}
