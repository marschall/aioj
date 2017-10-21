package com.github.marschall.aioj.capi;

public class fd {

  // http://man7.org/linux/man-pages/man2/open.2.html
  public static native int open();

  public static native int close(int fd);

  // https://linux.die.net/man/2/fadvise
  public static native int fadvise(int fd);

  // https://linux.die.net/man/2/fstat
  // https://www.quora.com/Why-does-O_DIRECT-require-I-O-to-be-512-byte-aligned
  // ioctl(2) BLKSSZGET
  // http://man7.org/linux/man-pages/man2/open.2.html
  // https://stackoverflow.com/questions/19747663/where-are-ioctl-parameters-such-as-0x1268-blksszget-actually-specified)
  // https://stackoverflow.com/questions/8416241/block-device-information-without-mounting-in-linux
  // http://support.fccps.cz/download/adv/frr/geom.c
  // https://people.redhat.com/msnitzer/docs/io-limits.txt
  // https://gist.github.com/amitsaha/4563032

  // BLKSSZGET
  public static native int getLogicalBlocksize(int fd);

}
