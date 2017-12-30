package com.github.marschall.aioj.capi;

public final class FadviseArgument {

  private FadviseArgument() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Indicates that the application has no advice to give about its access
   * pattern for the specified data. If no advice is given for an open file,
   * this is the default assumption.
   */
  public static final int POSIX_FADV_NORMAL = 0;

  /**
   * The application expects to access the specified data sequentially (with
   * lower offsets read before higher ones).
   */
  public static final int POSIX_FADV_SEQUENTIAL = 0;

  /**
   * The specified data will be accessed in random order.
   */
  public static final int POSIX_FADV_RANDOM = 0;

  /**
   * The specified data will be accessed only once.
   * <p>
   * In kernels before 2.6.18, {@link #POSIX_FADV_NOREUSE} had the same
   * semantics as {@link #POSIX_FADV_WILLNEED}. This was probably a bug; since
   * kernel 2.6.18, this flag is a no-op.
   */
  public static final int POSIX_FADV_NOREUSE = 0;

  /**
   * The specified data will be accessed in the near future.
   * <p>
   * {@link #POSIX_FADV_WILLNEED} initiates a nonblocking read of the specified
   * region into the page cache. The amount of data read may be decreased by the
   * kernel depending on virtual memory load. (A few megabytes will usually be
   * fully satisfied, and more is rarely useful.)
   */
  public static final int POSIX_FADV_WILLNEED = 0;

  /**
   * The specified data will not be accessed in the near future.
   * <p>
   * {@link #POSIX_FADV_DONTNEED} attempts to free cached pages associated with
   * the specified region. This is useful, for example, while streaming large
   * files. A program may periodically request the kernel to free cached data
   * that has already been used, so that more useful cached pages are not
   * discarded instead.
   * <p>
   * Requests to discard partial pages are ignored. It is preferable to preserve
   * needed data than discard unneeded data. If the application requires that
   * data be considered for discarding, then offset and len must be
   * page-aligned.
   * <p>
   * The implementation may attempt to write back dirty pages in the specified
   * region, but this is not guaranteed. Any unwritten dirty pages will not be
   * freed. If the application wishes to ensure that dirty pages will be
   * released, it should call fsync(2) or fdatasync(2) first.
   */
  public static final int POSIX_FADV_DONTNEED = 0;

}
