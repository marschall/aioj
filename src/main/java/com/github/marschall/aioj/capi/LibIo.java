package com.github.marschall.aioj.capi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public final class LibIo {

  public static int open(byte[] pathname, int flags, int mode) throws IOException {
    Objects.requireNonNull(pathname, "pathspec");
    return open0(pathname, flags, mode, pathname.length);
  }

  // http://man7.org/linux/man-pages/man2/open.2.html
  private static native int open0(byte[] pathname, int flags, int mode, int len) throws IOException;

  public static int open(byte[] pathname, int flags) throws IOException {
    Objects.requireNonNull(pathname, "pathspec");
    return open0(pathname, flags);
  }

  // http://man7.org/linux/man-pages/man2/open.2.html
  private static native int open0(byte[] pathname, int flags) throws IOException;

  public static native int close(int fd);

  // https://linux.die.net/man/2/fadvise
  // http://man7.org/linux/man-pages/man2/posix_fadvise.2.html
  public static int fadvise(int fd, long offset, long len, int advice) {
    return fadvise0(fd, offset, len, advice);
  }

  private static native int fadvise0(int fd, long offset, long len, int advice);

  // http://insights.oetiker.ch/linux/fadvise/
  // http://man7.org/linux/man-pages/man2/mincore.2.html
  private static native int mincore(long addr, long length, byte[] vec);

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
