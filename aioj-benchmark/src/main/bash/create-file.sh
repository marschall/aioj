#!/bin/bash

$JAVA_HOME/bin/java -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileCreator 10737418240 src/main/input/10g.bin

$JAVA_HOME/bin/java -cp target/aioj-benchmark-0.1.0-SNAPSHOT.jar \
  com.github.marschall.aioj.benchmark.FileCreator 107374182400 src/main/input/100g.bin