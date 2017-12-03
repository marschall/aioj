package com.github.marschall.aioj.capi;

public final class OpenArgument {

  private OpenArgument() {
    throw new AssertionError("not instantiable");
  }

  public static final int O_RDONLY = 0;
  public static final int O_WRONLY = 1;
  public static final int O_RDWR = 2;

  /**
   * The file is opened in append mode. Before each write(2), the file offset is
   * positioned at the end of the file, as if with lseek(2). The modification of
   * the file offset and the write operation are performed as a single atomic
   * step.
   * <p>
   * O_APPEND may lead to corrupted files on NFS filesystems if more than one
   * process appends data to a file at once. This is because NFS does not
   * support appending to a file, so the client kernel has to simulate it, which
   * can't be done without a race condi‐ tion.
   */
  public static final int O_APPEND = 1024;

  /**
   * Enable signal-driven I/O: generate a signal (SIGIO by default, but this can
   * be changed via fcntl(2)) when input or output becomes possible on this file
   * descriptor. This feature is available only for terminals, pseudoterminals,
   * sockets, and (since Linux 2.6) pipes and FIFOs. See fcntl(2) for further
   * details. See also BUGS, below.
   */
  public static final int O_ASYNC = 8192;

  /**
   * Enable the close-on-exec flag for the new file
   * descriptor. Specifying this flag permits a program to avoid additional
   * fcntl(2) F_SETFD operations to set the FD_CLOEXEC flag.
   * <p>
   * Note that the use of this flag is essential in some multithreaded programs,
   * because using a separate fcntl(2) F_SETFD operation to set the FD_CLOEXEC
   * flag does not suffice to avoid race conditions where one thread opens a
   * file descriptor and attempts to set its close-on-exec flag using fcntl(2)
   * at the same time as another thread does a fork(2) plus execve(2). Depending
   * on the order of execution, the race may lead to the file descriptor
   * returned by open() being unintentionally leaked to the program exe‐ cuted
   * by the child process created by fork(2). (This kind of race is in principle
   * possible for any system call that creates a file descriptor whose
   * close-on-exec flag should be set, and various other Linux system calls
   * provide an equivalent of the O_CLOEXEC flag to deal with this problem.)
   *
   * @since Linux 2.6.23
   */
  public static final int O_CLOEXEC = 524288;

  /**
   * If the file does not exist, it will be created.
   * <p>
   * The owner (user ID) of the new file is set to the effective user ID of the
   * process.
   * <p>
   * The group ownership (group ID) of the new file is set either to the
   * effective group ID of the process (System V semantics) or to the group ID
   * of the parent directory (BSD semantics). On Linux, the behavior depends on
   * whether the set-group-ID mode bit is set on the parent directory: if that
   * bit is set, then BSD semantics apply; otherwise, System V semantics apply.
   * For some filesystems, the behavior also depends on the bsdgroups and
   * sysvgroups mount options described in mount(8)).
   * <p>
   * The mode argument specifies the file mode bits be applied when a new file
   * is created. This argument must be supplied when O_CREAT or O_TMPFILE is
   * specified in flags; if neither O_CREAT nor O_TMPFILE is specified, then
   * mode is ignored. The effective mode is modified by the process's umask in
   * the usual way: in the absence of a default ACL, the mode of the created
   * file is (mode &amp; ~umask). Note that this mode applies only to future
   * accesses of the newly created file; the open() call that creates a
   * read-only file may well return a read/write file descriptor.
   */
  public static final int O_CREAT = 64;

  /**
   * Try to minimize cache effects of the I/O to and from this file. In general
   * this will degrade performance, but it is useful in special situations, such
   * as when applications do their own caching. File I/O is done directly
   * to/from user-space buffers. The O_DIRECT flag on its own makes an effort to
   * transfer data synchronously, but does not give the guarantees of the O_SYNC
   * flag that data and necessary metadata are transferred. To guarantee
   * synchronous I/O, O_SYNC must be used in addition to O_DIRECT. See NOTES
   * below for further discussion.
   * <p>
   * A semantically similar (but deprecated) interface for block devices is
   * described in raw(8).
   */
  public static final int O_DIRECT = 16384;

  /**
   * If pathname is not a directory, cause the open to fail. This flag was added
   * in kernel version 2.1.126, to avoid denial-of-service problems if
   * opendir(3) is called on a FIFO or tape device.
   */
  public static final int O_DIRECTORY = 65536;

  /**
   * Write operations on the file will complete according to the requirements of
   * synchronized I/O data integrity completion.
   * <p>
   * By the time write(2) (and similar) return, the output data has been
   * transferred to the underlying hardware, along with any file metadata that
   * would be required to retrieve that data (i.e., as though each write(2) was
   * followed by a call to fdatasync(2)). See NOTES below.
   */
  public static final int O_DSYNC = 4096;

  /**
   * Ensure that this call creates the file: if this flag is specified in
   * conjunction with O_CREAT, and pathname already exists, then open() will
   * fail.
   * <p>
   * When these two flags are specified, symbolic links are not followed: if
   * pathname is a symbolic link, then open() fails regardless of where the
   * symbolic link points to.
   * <p>
   * In general, the behavior of O_EXCL is undefined if it is used without
   * O_CREAT. There is one exception: on Linux 2.6 and later, O_EXCL can be used
   * without O_CREAT if pathname refers to a block device. If the block device
   * is in use by the system (e.g., mounted), open() fails with the error EBUSY.
   * <p>
   * On NFS, O_EXCL is supported only when using NFSv3 or later on kernel 2.6 or
   * later. In NFS environments where O_EXCL support is not provided, programs
   * that rely on it for performing locking tasks will contain a race condition.
   * Portable programs that want to perform atomic file locking using a
   * lockfile, and need to avoid reliance on NFS support for O_EXCL, can create
   * a unique file on the same filesystem (e.g., incorporating hostname and
   * PID), and use link(2) to make a link to the lockfile. If link(2) returns 0,
   * the lock is successful. Otherwise, use stat(2) on the unique file to check
   * if its link count has increased to 2, in which case the lock is also
   * successful.
   */
  public static final int O_EXCL = 128;

  /**
   * Do not update the file last access time (st_atime in the inode) when the
   * file is read(2).
   * <p>
   * This flag can be employed only if one of the following conditions is true:
   * <p>
   * The effective UID of the process matches the owner UID of the file.
   * <p>
   * The calling process has the CAP_FOWNER capability in its user namespace and
   * the owner UID of the file has a mapping in the namespace.
   * <p>
   * This flag is intended for use by indexing or backup programs, where its use
   * can significantly reduce the amount of disk activity. This flag may not be
   * effective on all filesystems. One example is NFS, where the server
   * maintains the access time.
   */
  public static final int O_NOATIME = 262144;

  /**
   * If pathname refers to a terminal device—see tty(4)—it will not become the process's controlling terminal even if the process does not have one.
   */
  public static final int O_NOCTTY = 256;

  /**
   * If pathname is a symbolic link, then the open fails, with the error ELOOP.
   * Symbolic links in earlier components of the pathname will still be
   * followed. (Note that the ELOOP error that can occur in this case is
   * indistinguishable from the case where an open fails because there are too
   * many symbolic links found while resolving components in the prefix part of
   * the pathname.)
   * <p>
   * This flag is a FreeBSD extension, which was added to Linux in version
   * 2.1.126, and has subsequently been standardized in POSIX.1-2008.
   *
   * @see #O_PATH
   */
  public static final int O_NOFOLLOW = 131072;

  /**
   * When possible, the file is opened in nonblocking mode. Neither the open()
   * nor any subsequent operations on the file descriptor which is returned will
   * cause the calling process to wait.
   * <p>
   * Note that this flag has no effect for regular files and block devices; that
   * is, I/O operations will (briefly) block when device activity is required,
   * regardless of whether O_NONBLOCK is set. Since O_NONBLOCK semantics might
   * eventually be implemented, applications should not depend upon blocking
   * behavior when specifying this flag for regular files and block devices.
   * <p>
   * For the handling of FIFOs (named pipes), see also fifo(7). For a discussion
   * of the effect of O_NONBLOCK in conjunction with mandatory file locks and
   * with file leases, see fcntl(2).
   */
  public static final int O_NONBLOCK = 2048;

  /**
   * When possible, the file is opened in nonblocking mode. Neither the open()
   * nor any subsequent operations on the file descriptor which is returned will
   * cause the calling process to wait.
   * <p>
   * Note that this flag has no effect for regular files and block devices; that
   * is, I/O operations will (briefly) block when device activity is required,
   * regardless of whether O_NONBLOCK is set. Since O_NONBLOCK semantics might
   * eventually be implemented, applications should not depend upon blocking
   * behavior when specifying this flag for regular files and block devices.
   * <p>
   * For the handling of FIFOs (named pipes), see also fifo(7). For a discussion
   * of the effect of O_NONBLOCK in conjunction with mandatory file locks and
   * with file leases, see fcntl(2).
   */
  public static final int O_NDELAY = 2048;

  /**
   * Obtain a file descriptor that can be used for two
   * purposes: to indicate a location in the filesystem tree and to perform
   * operations that act purely at the file descriptor level. The file itself is
   * not opened, and other file operations (e.g., read(2), write(2), fchmod(2),
   * fchown(2), fgetxattr(2), ioctl(2), mmap(2)) fail with the error EBADF.
   *
   * The following operations can be performed on the resulting file descriptor:
   *
   * close(2); fchdir(2) (since Linux 3.5); fstat(2) (since Linux 3.6);
   * fstatfs(2) (since Linux 3.12).
   *
   * Duplicating the file descriptor (dup(2), fcntl(2) F_DUPFD, etc.).
   *
   * Getting and setting file descriptor flags (fcntl(2) F_GETFD and F_SETFD).
   *
   * Retrieving open file status flags using the fcntl(2) F_GETFL operation: the
   * returned flags will include the bit O_PATH.
   *
   * Passing the file descriptor as the dirfd argument of openat() and the other
   * "*at()" system calls. This includes linkat(2) with AT_EMPTY_PATH (or via
   * procfs using AT_SYMLINK_FOLLOW) even if the file is not a directory.
   *
   * Passing the file descriptor to another process via a UNIX domain socket
   * (see SCM_RIGHTS in unix(7)).
   *
   * When O_PATH is specified in flags, flag bits other than O_CLOEXEC,
   * O_DIRECTORY, and O_NOFOLLOW are ignored.
   *
   * If pathname is a symbolic link and the O_NOFOLLOW flag is also specified,
   * then the call returns a file descriptor referring to the symbolic link.
   * This file descriptor can be used as the dirfd argument in calls to
   * fchownat(2), fstatat(2), linkat(2), and readlinkat(2) with an empty
   * pathname to have the calls operate on the symbolic link.
   *
   * If pathname refers to an automount point that has not yet been triggered,
   * so no other filesystem is mounted on it, then the call returns a file
   * descriptor referring to the automount directory without triggering a mount.
   * fstatfs(2) can then be used to determine if it is, in fact, an untriggered
   * automount point (.f_type == AUTOFS_SUPER_MAGIC).
   *
   * @since Linux 2.6.39
   */
  public static final int O_PATH = 2097152;

  /**
   * Write operations on the file will complete according to the requirements of
   * synchronized I/O file integrity completion (by contrast with the
   * synchronized I/O data integrity completion provided by O_DSYNC.)
   * <p>
   * By the time write(2) (and similar) return, the output data and associated
   * file metadata have been transferred to the underlying hardware (i.e., as
   * though each write(2) was followed by a call to fsync(2)). See NOTES below.
   */
  public static final int O_SYNC = 1052672;

  /**
   * Create an unnamed temporary file. The pathname argument
   * specifies a directory; an unnamed inode will be created in that directory's
   * filesystem. Anything written to the resulting file will be lost when the
   * last file descriptor is closed, unless the file is given a name.
   * <p>
   * O_TMPFILE must be specified with one of O_RDWR or O_WRONLY and, optionally,
   * O_EXCL. If O_EXCL is not specified, then linkat(2) can be used to link the
   * temporary file into the filesystem, making it permanent, using code like
   * the following:
   *
   * <pre><code>
   * char path[PATH_MAX];
   * fd = open("/path/to/dir", O_TMPFILE | O_RDWR,
   *                         S_IRUSR | S_IWUSR);
   *
   * // File I/O on 'fd'
   *
   * snprintf(path, PATH_MAX,  "/proc/self/fd/%d", fd);
   * linkat(AT_FDCWD, path, AT_FDCWD, "/path/for/file",
   *                         AT_SYMLINK_FOLLOW);
   * </code></pre>
   *
   * In this case, the open() mode argument determines the file permission mode,
   * as with O_CREAT.
   *
   * Specifying O_EXCL in conjunction with O_TMPFILE prevents a temporary file
   * from being linked into the filesystem in the above manner. (Note that the
   * meaning of O_EXCL in this case is different from the meaning of O_EXCL
   * otherwise.)
   *
   * There are two main use cases for O_TMPFILE:
   *
   * Improved tmpfile(3) functionality: race-free creation of temporary files
   * that (1) are automatically deleted when closed; (2) can never be reached
   * via any pathname; (3) are not subject to symlink attacks; and (4) do not
   * require the caller to devise unique names.
   * <p>
   * Creating a file that is initially invisible, which is then populated with
   * data and adjusted to have appropriate filesystem attributes (fchown(2),
   * fchmod(2), fsetxattr(2), etc.) before being atomically linked into the
   * filesystem in a fully formed state (using linkat(2) as described above).
   * <p>
   * O_TMPFILE requires support by the underlying filesystem; only a subset of
   * Linux filesystems provide that support. In the initial implementation,
   * support was provided in the ext2, ext3, ext4, UDF, Minix, and shmem
   * filesystems. Support for other filesys‐ tems has subsequently been added as
   * follows: XFS (Linux 3.15); Btrfs (Linux 3.16); F2FS (Linux 3.16); and ubifs
   * (Linux 4.9)
   *
   * @since Linux 3.11
   */
  public static final int O_TMPFILE = 4259840;

  /**
   * If the file already exists and is a regular file and the access mode allows
   * writing (i.e., is O_RDWR or O_WRONLY) it will be truncated to length 0. If
   * the file is a FIFO or terminal device file, the O_TRUNC flag is ignored.
   * Otherwise, the effect of O_TRUNC is unspecified.
   */
  public static final int O_TRUNC = 512;


  /**
   * user (file owner) has read, write, and execute permission.
   */
  public static final int S_IRWXU = 448;

  /**
   * user has read permission
   */
  public static final int S_IRUSR = 256;

  /**
   * user has write permission
   */
  public static final int S_IWUSR = 128;

  /**
   * user has execute permission
   */
  public static final int S_IXUSR = 64;

  /**
   * group has read, write, and execute permission
   */
  public static final int S_IRWXG = 56;

  /**
   * group has read permission
   */
  public static final int S_IRGRP = 32;

  /**
   * group has write permission
   */
  public static final int S_IWGRP = 16;

  /**
   * group has execute permission
   */
  public static final int S_IXGRP = 8;

  /**
   * others have read, write, and execute permission
   */
  public static final int S_IRWXO = 7;

  /**
   * others have read permission
   */
  public static final int S_IROTH = 4;

  /**
   * others have write permission
   */
  public static final int S_IWOTH = 2;

  /**
   * others have execute permission
   */
  public static final int S_IXOTH = 1;

  /**
   * set-user-ID bit
   */
  public static final int S_ISUID = 2048;

  /**
   * set-group-ID bit (see inode(7)).
   */
  public static final int S_ISGID = 1024;

  /**
   * sticky bit (see inode(7)).
   */
  public static final int S_ISVTX = 512;

}
