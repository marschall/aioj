package com.github.marschall.aioj.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class StreamBridge {

  private StreamBridge() {
    throw new AssertionError("not instantiable");
  }

  static final class AiojOutputStream extends OutputStream {

    AiojOutputStream() {
      // TODO Auto-generated constructor stub
    }

    @Override
    public void write(int b) throws IOException {
      // TODO Auto-generated method stub
    }
  }

  static final class AiojInputStream extends InputStream {

    AiojInputStream() {
      // TODO Auto-generated constructor stub
    }

    @Override
    public int read() throws IOException {
      // TODO Auto-generated method stub
      return 0;
    }

  }

}
