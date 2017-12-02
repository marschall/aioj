package com.github.marschall.aioj.benchmark;

import java.io.IOException;

interface FileBenchmark {

  long read(String fileName) throws IOException;

  String getDescription();

}
