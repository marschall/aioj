package com.github.marschall.aioj.capi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public final class LibIo {

  static {
    LibraryLoader.assertInitialized();
  }

  private static final int INVALID_FD = -1;

  public static int open(byte[] pathname, int flags, int mode) throws IOException {
    Objects.requireNonNull(pathname, "pathspec");
    int fd = open0(pathname, flags, mode, pathname.length);
    if (fd == INVALID_FD) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  // http://man7.org/linux/man-pages/man2/open.2.html
  private static native int open0(byte[] pathname, int flags, int mode, int len) throws IOException;

  public static int open(byte[] pathname, int flags) throws IOException {
    Objects.requireNonNull(pathname, "pathspec");
    int fd = open0(pathname, flags, pathname.length);
    if (fd == INVALID_FD) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  // http://man7.org/linux/man-pages/man2/open.2.html
  private static native int open0(byte[] pathname, int flags, int len) throws IOException;

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

  public static native void mmap(ByteBuffer buffer, long length, int prot, int flags, int fd, long offset);

  public static native int munmap(ByteBuffer buffer);

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
