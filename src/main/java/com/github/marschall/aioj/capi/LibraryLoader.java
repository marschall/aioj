package com.github.marschall.aioj.capi;

final class LibraryLoader {

  static final boolean INITIALIZED;

  static {
    // -Djava.library.path=...
    String javaLibraryPath = System.getProperty("java.library.path");
    boolean initialized = false;
    try {
      Runtime.getRuntime().loadLibrary("aioj-0.1.0-SNAPSHOT");
      initialized = true;
      INITIALIZED = initialized;
    } catch (RuntimeException | Error e) {
      throw e;
    }
//    String libraryName = System.mapLibraryName("libaioj-0.1.0-SNAPSHOT");
//    System.load("/home/marschall/Documents/workspaces/default/aioj/target/nar/aioj-0.1.0-SNAPSHOT-amd64-Linux-gpp-jni/lib/amd64-Linux-gpp/jni/" + libraryName);
  }

  static void assertInitialized() {
    if (!INITIALIZED) {
      throw new IllegalStateException("not initialized");
    }
  }

  private LibraryLoader() {
    throw new AssertionError("not instantiable");
  }

}
