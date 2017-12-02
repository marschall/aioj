package com.github.marschall.aioj.benchmark;

import static org.junit.jupiter.api.Assertions.*;

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

}
