package com.github.marschall.aioj.lowlevel;

@FunctionalInterface
public interface AioEvent {

  void completed(Object data, long length, int code);

}
