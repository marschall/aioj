package com.github.marschall.aioj.benchmark;

public class AiojReadBenchmark {

  private final int bufferSize;
  private final boolean direct;

  public AiojReadBenchmark(int bufferSize, boolean direct) {
    this.bufferSize = bufferSize;
    this.direct = direct;
  }

}
