package com.github.marschall.aioj.benchmark;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ByteFormatUtilsTest {

  @Test
  void formatBufferSizeK() {
    assertEquals("8k", ByteFormatUtils.formatBufferSize(8192));
  }

  @Test
  void formatBufferSizeM() {
    assertEquals("8m", ByteFormatUtils.formatBufferSize(8388608));
  }

  @Test
  void formatBufferSizeB() {
    assertEquals("8b", ByteFormatUtils.formatBufferSize(8));
  }

  @Test
  void formatToughputB() {
    assertEquals("0.5 b/s", ByteFormatUtils.formatToughput(1L, 2L));
  }

  @Test
  void formatToughputK() {
    assertEquals("1.1 k/s", ByteFormatUtils.formatToughput(1126L, 1L));
  }

  @Test
  void formatToughputM() {
    assertEquals("1.5 m/s", ByteFormatUtils.formatToughput(1572864L, 1L));
  }

  @Test
  void formatToughputG() {
    assertEquals("1.5 g/s", ByteFormatUtils.formatToughput(1610612736L, 1L));
  }

}
