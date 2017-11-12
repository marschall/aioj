package com.github.marschall.aioj.capi;

public final class MmapArgument {

  private MmapArgument() {
    throw new AssertionError("not instantiable");
  }

  /**
   * Pages may be executed.
   */
  public static final int PROT_EXEC = 4;

  /**
   * Pages may be read.
   */
  public static final int PROT_READ = 1;

  /**
   * Pages may be written.
   */
  public static final int PROT_WRITE = 2;

  /**
   * Pages may not be accessed.
   */
  public static final int PROT_NONE = 0;

  /**
   * Share this mapping.
   * <p>
   * Updates to the mapping are visible to other processes mapping the same
   * region, and (in the case of file-backed mappings) are carried through to
   * the underlying file. (To precisely control when updates are carried through
   * to the underlying file requires the use of msync(2).)
   */
  public static final int MAP_SHARED = 1;

  /**
   * Create a private copy-on-write mapping. Updates to the mapping are not
   * visible to other processes mapping the same file, and are not carried
   * through to the underlying file. It is unspecified whether changes made to
   * the file after the mmap() call are visible in the mapped region.
   */
  public static final int MAP_PRIVATE = 2;

  /**
   * Put the mapping into the first 2 Gigabytes of the process address space.
   * This flag is supported only on x86-64, for 64-bit programs. It was added to
   * allow thread stacks to be allocated somewhere in the first 2 GB of memory,
   * so as to improve context-switch performance on some early 64-bit
   * processors. Modern x86-64 processors no longer have this performance
   * problem, so use of this flag is not required on those systems. The
   * MAP_32BIT flag is ignored when MAP_FIXED is set.
   *
   * @since Linux 2.4.20, 2.6
   */
  public static final int MAP_32BIT = 64;

  /**
   * The mapping is not backed by any file; its contents are initialized to
   * zero. The fd argument is ignored; however, some implementations require fd
   * to be -1 if MAP_ANONYMOUS (or MAP_ANON) is specified, and portable
   * applications should ensure this. The offset argument should be zero. The
   * use of MAP_ANONYMOUS in conjunction with MAP_SHARED is supported on Linux
   * only since kernel 2.4.
   */
  public static final int MAP_ANONYMOUS = 32;

  /**
   * Don't interpret addr as a hint: place the mapping at exactly that address.
   * addr must be a multiple of the page size. If the memory region specified by
   * addr and len overlaps pages of any existing mapping(s), then the overlapped
   * part of the existing mapping(s) will be discarded. If the specified address
   * cannot be used, mmap() will fail. Because requiring a fixed address for a
   * mapping is less portable, the use of this option is discouraged.
   */
  public static final int MAP_FIXED = 16;

  /**
   * This flag is used for stacks. It indicates to the kernel virtual memory
   * system that the mapping should extend downward in memory. The return
   * address is one page lower than the memory area that is actually created in
   * the process's virtual address space. Touching an address in the "guard"
   * page below the mapping will cause the mapping to grow by a page. This
   * growth can be repeated until the mapping grows to within a page of the high
   * end of the next lower mapping, at which point touching the "guard" page
   * will result in a SIGSEGV signal.
   */
  public static final int MAP_GROWSDOWN = 256;

  /**
   * Allocate the mapping using "huge pages." See the Linux kernel source file
   * Documentation/vm/hugetlbpage.txt for further information, as well as NOTES,
   * below.
   */
  public static final int MAP_HUGETLB = 262144;

  /**
   * Mark the mmaped region to be locked in the same way as mlock(2). This
   * implementation will try to populate (prefault) the whole range but the mmap
   * call doesn't fail with ENOMEM if this fails. Therefore major faults might
   * happen later on. So the semantic is not as strong as mlock(2). One should
   * use mmap() plus mlock(2) when major faults are not acceptable after the
   * initialization of the mapping. The MAP_LOCKED flag is ignored in older
   * kernels.
   *
   * @since Linux 2.5.37
   */
  public static final int MAP_LOCKED = 8192;

  /**
   * This flag is meaningful only in conjunction with MAP_POPULATE. Don't
   * perform read-ahead: create page tables entries only for pages that are
   * already present in RAM. Since Linux 2.6.23, this flag causes MAP_POPULATE
   * to do noth‐ ing. One day, the combination of MAP_POPULATE and MAP_NONBLOCK
   * may be reimplemented.
   *
   * @since Linux 2.5.46
   */
  public static final int MAP_NONBLOCK = 65536;

  /**
   * Do not reserve swap space for this mapping. When swap space is reserved,
   * one has the guarantee that it is possible to modify the mapping. When swap
   * space is not reserved one might get SIGSEGV upon a write if no physical
   * memory is available. See also the discussion of the file
   * /proc/sys/vm/overcommit_memory in proc(5). In kernels before 2.6, this flag
   * had effect only for private writable mappings.
   */
  public static final int MAP_NORESERVE = 16384;

  /**
   * Populate (prefault) page tables for a mapping. For a file mapping, this
   * causes read-ahead on the file. This will help to reduce blocking on page
   * faults later. MAP_POPULATE is supported for private mappings only since
   * Linux 2.6.23.
   *
   * @since Linux 2.5.46
   */
  public static final int MAP_POPULATE = 32768;

  /**
   * Allocate the mapping at an address suitable for a process or thread stack.
   * This flag is currently a no-op, but is used in the glibc threading
   * implementation so that if some architectures require special treatment for
   * stack allocations, support can later be transparently implemented for
   * glibc.
   *
   * @since Linux 2.6.33
   */
  public static final int MAP_STACK = 131072;

}
