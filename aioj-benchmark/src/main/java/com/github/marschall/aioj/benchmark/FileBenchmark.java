package com.github.marschall.aioj.benchmark;

import java.io.IOException;

public interface FileBenchmark {

  long read(String filename) throws IOException;

}
