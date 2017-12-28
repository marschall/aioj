package com.github.marschall.aioj.capi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public final class LibIo {

  private static final int PATH_MAX = 4096;

  private static final int FILENAME_MAX = 4096;

  static {
    LibraryLoader.assertInitialized();
  }

  public static void fstat(int fd, StructStat statbuf) throws IOException {
    Objects.requireNonNull(statbuf, "statbuf");
    int result = fstat0(fd, statbuf);
    if (result != 0) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not fstat() file");
    }
  }

  private static native int fstat0(int fd, StructStat statbuf) throws IOException;

  public static void stat(byte[] pathName, StructStat statbuf) throws IOException {
    // TODO compare against PATH_MAX
    Objects.requireNonNull(pathName, "pathName");
    Objects.requireNonNull(statbuf, "statbuf");
    int result = stat0(pathName, statbuf);
    if (result != 0) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not stat() file");
    }
  }

  private static native int stat0(byte[] pathName, StructStat statbuf) throws IOException;

  public static void stat(String pathName, StructStat statbuf) throws IOException {
    Objects.requireNonNull(pathName, "pathName");
    Objects.requireNonNull(statbuf, "statbuf");
    int result = stat0(pathName, statbuf);
    if (result != 0) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not stat() file");
    }
  }

  private static native int stat0(String pathName, StructStat statbuf) throws IOException;

  public static int open(byte[] pathName, int flags, int mode) throws IOException {
    Objects.requireNonNull(pathName, "pathName");
    int fd = open0(pathName, pathName.length, flags, mode);
    if (fd == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  // http://man7.org/linux/man-pages/man2/open.2.html
  private static native int open0(byte[] pathName, int pathNameLength, int flags, int mode) throws IOException;

  public static int open(String pathName, int flags, int mode) throws IOException {
    Objects.requireNonNull(pathName, "pathName");
    int fd = open0(pathName, pathName.length(), flags, mode);
    if (fd == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  // http://man7.org/linux/man-pages/man2/open.2.html
  private static native int open0(String pathName, int pathNameLength, int flags, int mode) throws IOException;

  public static int open(byte[] pathname, int flags) throws IOException {
    Objects.requireNonNull(pathname, "pathname");
    int fd = open0(pathname, pathname.length, flags);
    if (fd == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  // http://man7.org/linux/man-pages/man2/open.2.html
  private static native int open0(byte[] pathName, int pathNameLength, int flags) throws IOException;

  public static int open(String pathname, int flags) throws IOException {
    Objects.requireNonNull(pathname, "pathname");
    int fd = open0(pathname, pathname.length(), flags);
    if (fd == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  // http://man7.org/linux/man-pages/man2/open.2.html
  private static native int open0(String pathName, int pathNameLength, int flags) throws IOException;

  public static void close(int fd)  throws IOException {
    int result = close0(fd);
    if (result == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not close() file");
    }
  }

  private static native int close0(int fd) throws IOException;

  public static long lseek(int fd, long offset, int whence)  throws IOException {
    long result = lseek0(fd, offset, whence);
    if (result == (offset - 1)) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not lseek() file");
    }
    return result;
  }

  // https://linux.die.net/man/2/lseek
  private static native long lseek0(int fd, long offset, int whence) throws IOException;

  // https://linux.die.net/man/2/fadvise
  // http://man7.org/linux/man-pages/man2/posix_fadvise.2.html
  public static int fadvise(int fd, long offset, long len, int advice) {
    return fadvise0(fd, offset, len, advice);
  }

  private static native int fadvise0(int fd, long offset, long len, int advice);

  public static int mincore(ByteBuffer buffer, long length, byte[] vec) throws IOException {
    Objects.requireNonNull(vec, "vec");
    if (length > buffer.capacity()) {
      throw new IllegalArgumentException("length too large");
    }
    if (length < 0) {
      throw new IllegalArgumentException("length is negative");
    }
    int result = mincore0(buffer, length, vec, vec.length);
    if (result == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not mincore() address");
    }
    return result;
  }

  // http://insights.oetiker.ch/linux/fadvise/
  // http://man7.org/linux/man-pages/man2/mincore.2.html
  private static native int mincore0(ByteBuffer buffer, long length, byte[] vec, int arrayLength) throws IOException;

  public static boolean isInPageCache(byte b) {
    return (b & 1) == 1;
  }

  public static ByteBuffer mmap(ByteBuffer buffer, int length, int prot, int flags, int fd, long offset) throws IOException {
    if (buffer != null) {

    }
    ByteBuffer result = mmap0(buffer, length, prot, flags, fd, offset);
    if (result == null) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not mmap() address");
    }
    return result;
  }

  private static native ByteBuffer mmap0(ByteBuffer buffer, long length, int prot, int flags, int fd, long offset) throws IOException;

  public static int munmap(ByteBuffer buffer) throws IOException {
    BufferAssertions.requireDirect(buffer);
    return munmap0(buffer, buffer.capacity());
  }

  private static native int munmap0(ByteBuffer buffer, long length) throws IOException;

  // https://linux.die.net/man/2/fstat
  // https://www.quora.com/Why-does-O_DIRECT-require-I-O-to-be-512-byte-aligned
  // ioctl(2) BLKSSZGET
  // http://man7.org/linux/man-pages/man2/open.2.html
  // https://stackoverflow.com/questions/19747663/where-are-ioctl-parameters-such-as-0x1268-blksszget-actually-specified)
  // https://stackoverflow.com/questions/8416241/block-device-information-without-mounting-in-linux
  // http://support.fccps.cz/download/adv/frr/geom.c
  // https://people.redhat.com/msnitzer/docs/io-limits.txt
  // https://gist.github.com/amitsaha/4563032
  // https://stackoverflow.com/questions/12939703/getting-disk-sector-size-without-raw-filesystem-permission
  // BLKSSZGET
  // AKA logical block size of controller
  public static native int getLogicalSectorSize(int fd);

  public static native int getBlockSize(int fd);

}
