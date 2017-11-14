package com.github.marschall.aioj.benchmark;

public class AiojPreadBechmark {

  private final int bufferSize;
  private final boolean direct;

  public AiojPreadBechmark(int bufferSize, boolean direct) {
    this.bufferSize = bufferSize;
    this.direct = direct;
  }

}
