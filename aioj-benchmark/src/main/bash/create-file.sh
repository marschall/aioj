#!/bin/bash

set -e

VERSION=0.1.0-SNAPSHOT

if [ ! -f src/main/input/10g.bin ]; then
  $JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
    -cp target/aioj-benchmark-${VERSION}.jar \
    com.github.marschall.aioj.benchmark.FileCreator \
    10737418240 src/main/input/10g.bin
fi

if [ ! -f src/main/input/100g.bin ]; then
  $JAVA_HOME/bin/java -XX:+UseSerialGC -Xmx128m -Xms128m \
    -cp target/aioj-benchmark-${VERSION}.jar \
    com.github.marschall.aioj.benchmark.FileCreator \
    107374182400 src/main/input/100g.bin
fi
