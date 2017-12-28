package com.github.marschall.aioj.benchmark;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCreator {

  FileCreator() {
    super();
  }

  void createFile(String fileName, long size) throws IOException {
    int bufferSize = 4096;

    ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);
    for (int i = 0; i < bufferSize; i++) {
      buffer.put(i, (byte) i);
    }

    Path path = Paths.get(fileName);
    Files.createDirectories(path.getParent());
    try (FileChannel channel = FileChannel.open(path, WRITE, CREATE_NEW)) {
      long written = 0L;
      while (written < size) {
        int toWrite = Math.toIntExact(Math.min(bufferSize, size - written));
        buffer.position(0);
        buffer.limit(toWrite);
        this.writeAll(buffer, channel);
        written += toWrite;
      }
    }
  }

  private void writeAll(ByteBuffer buffer, WritableByteChannel channel) throws IOException {
    while (buffer.hasRemaining()) {
      channel.write(buffer);
    }
  }

  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("usage: size file");
      System.exit(-1);
    }
    long fileSize = Long.parseLong(args[0]);
    String fileName = args[1];

    FileCreator creator = new FileCreator();
    try {
      creator.createFile(fileName, fileSize);
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println(e.getMessage());
      System.exit(-1);
    }

    System.exit(0);
  }

}
