package com.github.marschall.aioj.capi;

public final class StructStat {

  /** ID of device containing file */
  public int /* dev_t     */ st_dev;

  /** Inode number */
  public int /* ino_t     */ st_ino;

  /** File type and mode */
  public int /* mode_t    */ st_mode;

  /** Number of hard links */
  public int /* nlink_t   */ st_nlink;

  /** User ID of owner */
  public int /* uid_t     */ st_uid;

  /** Group ID of owner */
  public int /* gid_t     */ st_gid;

  /** Device ID (if special file) */
  public int /* dev_t     */ st_rdev;

  /** Total size, in bytes */
  public long /* off_t     */ st_size;

  /** Block size for filesystem I/O */
  public int /* blksize_t */ st_blksize;

  /** Number of 512B blocks allocated */
  public int /* blkcnt_t  */ st_blocks;

  // https://www.gnu.org/software/libc/manual/html_node/Elapsed-Time.html
  public int /* struct timespec time_t */ st_atim;  /* Time of last access */
  public long /* struct timespec long */ st_atim_nano;
  public int /* struct timespec time_t */ st_mtim;  /* Time of last modification */
  public long /* struct timespec long */ st_mtim_nano;  /* Time of last modification */
  public int /* struct timespec time_t */ st_ctim;  /* Time of last status change */
  public long /* struct timespec long */ st_ctim_nano;  /* Time of last status change */

}
