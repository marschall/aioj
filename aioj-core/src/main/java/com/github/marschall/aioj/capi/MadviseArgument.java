package com.github.marschall.aioj.capi;

/**
 * <h2>Conventional advice values</h2>
 * The advice values listed below allow an application to tell the kernel how it expects to use some mapped or shared memory areas, so that the kernel can choose appropriate read-ahead and caching techniques.  These advice values do not influence the semantics  of
 * the  application  (except  in  the  case  of  MADV_DONTNEED),  but  may  influence its performance.  All of the advice values listed here have analogs in the POSIX-specified posix_madvise(3) function, and the values have the same meanings, with the exception of
 * MADV_DONTNEED.
 *
 * <h2>Linux-specific advice values</h2>
 * The following Linux-specific advice values have no counterparts in the POSIX-specified posix_madvise(3), and may or may not have counterparts in the madvise() interface available on other implementations.  Note that some of these operations change the semantics
 * of memory accesses.
 */
public final class MadviseArgument {

  private MadviseArgument() {
    throw new AssertionError("not instantiable");
  }

  /**
   * No special treatment. This is the default.
   */
  public static final int MADV_NORMAL = 0;

  /**
   * Expect page references in random order. (Hence, read ahead may be less useful than normally.)
   */
  public static final int MADV_RANDOM = 1;

  /**
   * Expect page references in sequential order. (Hence, pages in the given range can be aggressively read ahead, and may be freed soon after they are accessed.)
   */
  public static final int MADV_SEQUENTIAL = 2;


  /**
   * Expect access in the near future. (Hence, it might be a good idea to read some pages ahead.)
   */
  public static final int MADV_WILLNEED = 3;

  /**
   * Do not expect access in the near future. (For the time being, the
   * application is finished with the given range, so the kernel can free
   * resources associated with it.)
   * <p>
   * After a successful MADV_DONTNEED operation, the semantics of memory access
   * in the specified region are changed: subsequent accesses of pages in the
   * range will succeed, but will result in either repopulating the memory
   * contents from the up-to-date con‐ tents of the underlying mapped file (for
   * shared file mappings, shared anonymous mappings, and shmem-based techniques
   * such as System V shared memory segments) or zero-fill-on-demand pages for
   * anonymous private mappings.
   * <p>
   * Note that, when applied to shared mappings, MADV_DONTNEED might not lead to
   * immediate freeing of the pages in the range. The kernel is free to delay
   * freeing the pages until an appropriate moment. The resident set size (RSS)
   * of the calling process will be immediately reduced however.
   * <p>
   * MADV_DONTNEED cannot be applied to locked pages, Huge TLB pages, or
   * VM_PFNMAP pages. (Pages marked with the kernel-internal VM_PFNMAP flag are
   * special memory areas that are not managed by the virtual memory subsystem.
   * Such pages are typically created by device drivers that map the pages into
   * user space.)
   */
  public static final int MADV_DONTNEED = 4;

  /**
   * Free up a given range of pages and its associated backing store. This is
   * equivalent to punching a hole in the corresponding byte range of the
   * backing store (see fallocate(2)). Subsequent accesses in the specified
   * address range will see bytes containing zero.
   * <p>
   * The specified address range must be mapped shared and writable. This flag
   * cannot be applied to locked pages, Huge TLB pages, or VM_PFNMAP pages.
   * <p>
   * In the initial implementation, only tmpfs(5) is supported MADV_REMOVE; but
   * since Linux 3.5, any filesystem which supports the fallocate(2)
   * FALLOC_FL_PUNCH_HOLE mode also supports MADV_REMOVE. Hugetlbfs will fail
   * with the error EINVAL and other filesys‐ tems fail with the error
   * EOPNOTSUPP.
   *
   * @since Linux 2.6.16
   */
  public static final int MADV_REMOVE = 9;


  /**
   * Do not make the pages in this range available to the child after a fork(2).
   * This is useful to prevent copy-on-write semantics from changing the
   * physical location of a page if the parent writes to it after a fork(2).
   * (Such page relocations cause prob‐ lems for hardware that DMAs into the
   * page.)
   *
   * @since Linux 2.6.16
   */
  public static final int MADV_DONTFORK = 10;


  /**
   * Undo the effect of MADV_DONTFORK, restoring the default behavior, whereby a
   * mapping is inherited across fork(2).
   *
   * @since Linux 2.6.16
   */
  public static final int MADV_DOFORK = 11;

  /**
   * Poison the pages in the range specified by addr and length and handle
   * subsequent references to those pages like a hardware memory corruption.
   * This operation is available only for privileged (CAP_SYS_ADMIN) processes.
   * This operation may result in the calling process receiving a SIGBUS and the
   * page being unmapped.
   * <p>
   * This feature is intended for testing of memory error-handling code; it is
   * available only if the kernel was configured with CONFIG_MEMORY_FAILURE.
   *
   * @since Linux 2.6.32
   */
  public static final int MADV_HWPOISON = 100;

  /**
   * Enable Kernel Samepage Merging (KSM) for the pages in the range specified
   * by addr and length. The kernel regularly scans those areas of user memory
   * that have been marked as mergeable, looking for pages with identical
   * content. These are replaced by a single write-protected page (which is
   * automatically copied if a process later wants to update the content of the
   * page). KSM merges only private anonymous pages (see mmap(2)).
   * <p>
   * The KSM feature is intended for applications that generate many instances
   * of the same data (e.g., virtualization systems such as KVM). It can consume
   * a lot of processing power; use with care. See the Linux kernel source file
   * Documentation/vm/ksm.txt for more details.
   * <p>
   * The MADV_MERGEABLE and MADV_UNMERGEABLE operations are available only if
   * the kernel was configured with CONFIG_KSM.
   *
   * @since Linux 2.6.32
   */
  public static final int MADV_MERGEABLE = 12;

  /**
   * Undo the effect of an earlier MADV_MERGEABLE operation on the specified
   * address range; KSM unmerges whatever pages it had merged in the address
   * range specified by addr and length.
   *
   * @since Linux 2.6.32
   */
  public static final int MADV_UNMERGEABLE = 13;

  /**
   * Soft offline the pages in the range specified by addr and length. The
   * memory of each page in the specified range is preserved (i.e., when next
   * accessed, the same content will be visible, but in a new physical page
   * frame), and the original page is offlined (i.e., no longer used, and taken
   * out of normal memory management). The effect of the MADV_SOFT_OFFLINE
   * operation is invisible to (i.e., does not change the semantics of) the
   * calling process.
   * <p>
   * This feature is intended for testing of memory error-handling code; it is
   * available only if the kernel was configured with CONFIG_MEMORY_FAILURE.
   *
   * @since Linux 2.6.33
   */
  public static final int MADV_SOFT_OFFLINE = 101;

  /**
   * Enable Transparent Huge Pages (THP) for pages in the range specified by
   * addr and length. Currently, Transparent Huge Pages work only with private
   * anonymous pages (see mmap(2)). The kernel will regularly scan the areas
   * marked as huge page candidates to replace them with huge pages. The kernel
   * will also allocate huge pages directly when the region is naturally aligned
   * to the huge page size (see posix_memalign(2)).
   * <p>
   * This feature is primarily aimed at applications that use large mappings of
   * data and access large regions of that memory at a time (e.g.,
   * virtualization systems such as QEMU). It can very easily waste memory
   * (e.g., a 2 MB mapping that only ever accesses 1 byte will result in 2 MB of
   * wired memory instead of one 4 KB page). See the Linux kernel source file
   * Documentation/vm/transhuge.txt for more details.
   * <p>
   * The MADV_HUGEPAGE and MADV_NOHUGEPAGE operations are available only if the
   * kernel was configured with CONFIG_TRANSPARENT_HUGEPAGE.
   *
   * @since Linux 2.6.38
   */
  public static final int MADV_HUGEPAGE = 14;

  /**
   * Ensures that memory in the address range specified by addr and length will
   * not be collapsed into huge pages.
   *
   * @since Linux 2.6.38
   */
  public static final int MADV_NOHUGEPAGE = 15;

  /**
   * Exclude from a core dump those pages in the range specified by addr and
   * length. This is useful in applications that have large areas of memory that
   * are known not to be useful in a core dump. The effect of MADV_DONTDUMP
   * takes precedence over the bit mask that is set via the
   * /proc/[pid]/coredump_filter file (see core(5)).
   *
   * @since Linux 3.4
   */
  public static final int MADV_DONTDUMP = 16;

  /**
   * Undo the effect of an earlier MADV_DONTDUMP.
   *
   * @since Linux 3.4
   */
  public static final int MADV_DODUMP = 17;

  /**
   * The application no longer requires the pages in the range specified by addr
   * and len. The kernel can thus free these pages, but the freeing could be
   * delayed until memory pressure occurs. For each of the pages that has been
   * marked to be freed but has not yet been freed, the free operation will be
   * canceled if the caller writes into the page. After a successful MADV_FREE
   * operation, any stale data (i.e., dirty, unwritten pages) will be lost when
   * the kernel frees the pages. However, subsequent writes to pages in the
   * range will succeed and then kernel cannot free those dirtied pages, so that
   * the caller can always see just written data. If there is no subsequent
   * write, the kernel can free the pages at any time. Once pages in the range
   * have been freed, the caller will see zero-fill-on-demand pages upon
   * subsequent page references.
   * <p>
   * The MADV_FREE operation can be applied only to private anonymous pages (see
   * mmap(2)). On a swapless system, freeing pages in a given range happens
   * instantly, regardless of memory pressure.
   *
   * @since Linux 4.5
   */
  public static final int MADV_FREE = 8;

}
