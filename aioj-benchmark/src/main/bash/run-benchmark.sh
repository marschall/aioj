#!/bin/bash

set -e

function run_benchmark_for_file() {
  file = $1
  shift
  
  echo 1 > /proc/sys/vm/drop_caches
  
  $JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
    -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
    com.github.marschall.aioj.benchmark.FileBenchmarks \
    $@ $file
}

function run_benchmark_for_10g() {
  run_benchmark_for_file src/main/input/10g.bin $@
}

function run_benchmark_for_100g() {
  run_benchmark_for_file src/main/input/100g.bin $@
}

# has to be run with sudo -E

# $JAVA_HOME/bin/java \ -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
#   com.github.marschall.aioj.benchmark.FileBenchmarks aioj-mmap true src/main/input/100g.bin
# 
# $JAVA_HOME/bin/java -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
#   com.github.marschall.aioj.benchmark.FileBenchmarks aioj-mmap false src/main/input/100g.bin

echo 1 > /proc/sys/vm/drop_caches

$JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
  -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileBenchmarks \
  FileChannel 8192 false src/main/input/100g.bin

echo 1 > /proc/sys/vm/drop_caches

$JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
  -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileBenchmarks \
  FileChannel 8192 true src/main/input/100g.bin

echo 1 > /proc/sys/vm/drop_caches

$JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
  -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileBenchmarks \
  FileChannel 131072 false src/main/input/100g.bin

echo 1 > /proc/sys/vm/drop_caches

$JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
  -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileBenchmarks \
  FileChannel 131072 true src/main/input/100g.bin

echo 1 > /proc/sys/vm/drop_caches

$JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
  -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileBenchmarks \
  FileInputStream 8192 src/main/input/100g.bin

echo 1 > /proc/sys/vm/drop_caches

$JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
  -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileBenchmarks \
  FileInputStream 131072 src/main/input/100g.bin

echo 1 > /proc/sys/vm/drop_caches

$JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
  -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileBenchmarks \
  MappedByteBuffer true src/main/input/100g.bin

echo 1 > /proc/sys/vm/drop_caches

$JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
  -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileBenchmarks \
  MappedByteBuffer false src/main/input/100g.bin

# run_benchmark_for_10g MappedByteBuffer false
