package com.github.marschall.aioj.capi;

public final class LseekArgument {

  private LseekArgument() {
    throw new AssertionError("not instantiable");
  }

  /** The offset is set to offset bytes. */
  public static final int SEEK_SET = 0;

  /** The offset is set to its current location plus offset bytes. */
  public static final int SEEK_CUR = 0;

  /** The offset is set to the size of the file plus offset bytes. */
  public static final int SEEK_END = 0;

  /**
   * Adjust the file offset to the next location in the file greater than or
   * equal to offset containing data. If offset points to data, then the file
   * offset is set to offset.
   */
  public static final int SEEK_DATA = 0;

  /**
   * Adjust the file offset to the next hole in the file greater than or equal
   * to offset. If offset points into the middle of a hole, then the file offset
   * is set to offset. If there is no hole past offset, then the file offset is
   * adjusted to the end of the file (i.e., there is an implicit hole at the end
   * of any file).
   */
  public static final int SEEK_HOLE = 0;

}
