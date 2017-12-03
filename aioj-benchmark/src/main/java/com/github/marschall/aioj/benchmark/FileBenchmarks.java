package com.github.marschall.aioj.benchmark;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class FileBenchmarks {


  void run(String[] args) throws IOException {
    if (args.length < 2) {
      throw new IllegalBenchmarkArgumentExcetpion("usage: <benchmark> <options> <file>");
    }
    String benchmarkName = args[0];
    String fileName = args[args.length - 1];
    List<String> options = Arrays.asList(args).subList(1, args.length - 1);

    FileBenchmark benchmark = this.instantiate(benchmarkName, options);

    long start = System.currentTimeMillis();
    long sum = benchmark.read(fileName);
    long end = System.currentTimeMillis();

    this.reportResult(fileName, benchmark, end - start, sum);
  }

  private void reportResult(String fileName, FileBenchmark benchmark, long milliseconds, long sum) throws IOException {
    long fileSize = Files.size(Paths.get(fileName));
    long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
    System.out.printf("%s%ntook %d ms, throughput %s%nsum is %d %n----%n",
            benchmark.getDescription(), milliseconds, ByteFormatUtils.formatToughput(fileSize, seconds), sum);
  }

  private FileBenchmark instantiate(String benchmark, List<String> options) {
    switch (benchmark) {
      case "aioj-read":
        return this.instantiateAiojRead(options);
      case "aioj-pread":
        return this.instantiateAiojPread(options);
      case "aioj-mmap":
        return this.instantiateAiojMmap(options);
      case "FileChannel":
        return this.instantiateFileChannel(options);
      case "FileInputStream":
        return this.instantiateFileInputStream(options);
      case "MappedByteBuffer":
        return this.instantiateMappedByteBuffer(options);
      default:
        throw new IllegalBenchmarkArgumentExcetpion("unknown benchmark: " + benchmark);
    }
  }

  private FileBenchmark instantiateMappedByteBuffer(List<String> options) {
    boolean force = Boolean.parseBoolean(options.get(0));
    return new MappedByteBufferBenchmark(force);
  }

  private FileBenchmark instantiateFileInputStream(List<String> options) {
    int bufferSize = Integer.parseInt(options.get(0));
    return new FileInputStreamBenchmark(bufferSize);
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
