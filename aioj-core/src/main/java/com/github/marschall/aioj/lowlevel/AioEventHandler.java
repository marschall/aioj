package com.github.marschall.aioj.lowlevel;

@FunctionalInterface
public interface AioEventHandler {

  void completed(Object data, long length, int code);

}
