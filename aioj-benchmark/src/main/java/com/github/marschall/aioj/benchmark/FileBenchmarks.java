package com.github.marschall.aioj.benchmark;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public final class FileBenchmarks {


  void run(String[] args) throws IOException {
    if (args.length < 2) {
      throw new IllegalBenchmarkArgumentExcetpion("usage: <benchmark> <options> <file>");
    }
    String benchmarkName = args[0];
    String fileName = args[args.length - 1];
    List<String> options = Arrays.asList(args).subList(0, args.length);

    FileBenchmark benchmark = instantiate(benchmarkName, options);

    long start = System.currentTimeMillis();
    long sum = benchmark.read(fileName);
    long end = System.currentTimeMillis();

    System.out.printf("took %d ms sum is %d %n", end - start, sum);
  }

  private FileBenchmark instantiate(String benchmark, List<String> options) {
    switch (benchmark) {
      case "aioj-read":
        return instantiateAiojRead(options);
      case "aioj-pread":
        return instantiateAiojPread(options);
      case "aioj-mmap":
        return instantiateAiojMmap(options);
      case "FileChannel":
        return instantiateFileChannel(options);
      case "FileInputStream":
        return instantiateFileInputStream(options);
      case "MappedByteBuffer":
        return instantiateMappedByteBuffer(options);
      default:
        throw new IllegalBenchmarkArgumentExcetpion("unknown benchmark: " + benchmark);
    }
  }

  private FileBenchmark instantiateMappedByteBuffer(List<String> options) {
    boolean force = Boolean.parseBoolean(options.get(0));
    return new MappedByteBufferBenchmark(force);
  }

  private FileBenchmark instantiateFileInputStream(List<String> options) {
    int blockSize = Integer.parseInt(options.get(0));
    return new FileInputStreamBenchmark(blockSize);
  }

  private FileBenchmark instantiateFileChannel(List<String> options) {
    int bufferSize = Integer.parseInt(options.get(0));
    boolean offHeap = Boolean.parseBoolean(options.get(1));
    return new FileChannelBenchmark(bufferSize, offHeap);
  }

  private FileBenchmark instantiateAiojMmap(List<String> options) {
    boolean madvise = Boolean.parseBoolean(options.get(0));
    return new AiojMmapBenchmark(madvise);
  }

  private FileBenchmark instantiateAiojPread(List<String> options) {
    int bufferSize = Integer.parseInt(options.get(0));
    boolean direct = Boolean.parseBoolean(options.get(1));
    return new AiojPreadBechmark(bufferSize, direct);
  }

  private FileBenchmark instantiateAiojRead(List<String> options) {
    int bufferSize = Integer.parseInt(options.get(0));
    boolean direct = Boolean.parseBoolean(options.get(1));
    return new AiojPreadBechmark(bufferSize, direct);
  }

  public static void main(String[] args) throws IOException {
    FileBenchmarks benchmarks = new FileBenchmarks();
    try {
      benchmarks.run(args);
    } catch (IllegalBenchmarkArgumentExcetpion e) {
      System.err.println(e.getMessage());
      System.exit(-1);
    }
    System.exit(0);
  }

}
