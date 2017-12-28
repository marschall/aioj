package com.github.marschall.aioj.capi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

public final class LibIo {

  private static final int PATH_MAX = 4096;

  private static final int FILENAME_MAX = 4096;

  private static final int NULL = 0;

  static {
    LibraryLoader.assertInitialized();
  }

  /**
   * Transfer data between file descriptors.
   * <p>
   * sendfile() copies data between one file descriptor and another. Because
   * this copying is done within the kernel, sendfile() is more efficient than
   * the combination of read(2) and write(2), which would require transferring
   * data to and from user space.
   *
   * @param out_fd
   *          should be a descriptor opened for writing. If it is a regular
   *          file, then sendfile() changes the file offset appropriately.
   * @param in_fd
   *          should be a file descriptor opened for reading, must correspond to
   *          a file which supports mmap(2)-like operations (i.e., it cannot be
   *          a socket).
   * @param offset
   *          If offset is not NULL, then it points to a variable holding the
   *          file offset from which sendfile() will start reading data from
   *          in_fd. When sendfile() returns, this variable will be set to the
   *          offset of the byte following the last byte that was read. If
   *          offset is not NULL, then sendfile() does not modify the file
   *          offset of in_fd; otherwise the file offset is adjusted to reflect
   *          the number of bytes read from in_fd. If offset is NULL, then data
   *          will be read from in_fd starting at the file offset, and the file
   *          offset will be updated by the call.
   * @param count
   *          is the number of bytes to copy between the file descriptors.
   * @return If the transfer was successful, the number of bytes written to
   *         out_fd is returned. Note that a successful call to sendfile() may
   *         write fewer bytes than requested; the caller should be prepared to
   *         retry the call if there were unsent bytes.
   * @throws IOException
   *           if the call fails
   * @see <a href=
   *      "http://man7.org/linux/man-pages/man2/sendfile.2.html">sendfile(2)</a>
   */
  public static long sendfile(int out_fd, int in_fd, long offset, long count) throws IOException {
    long transferred = sendfile0(out_fd, in_fd, offset, count);
    if (transferred == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not sendfile()");
    }
    return transferred;
  }

  private static native long sendfile0(int out_fd, int in_fd, long offset, long count) throws IOException;

  /**
   * Get file status. fstat() is identical to stat(), except that the file to be
   * stat-ed is specified by the file descriptor fd.
   *
   * @param fd
   *          the file to be stat-ed
   * @param statbuf
   *          to be filled
   * @throws IOException
   *           if the call fails
   * @see <a href="https://linux.die.net/man/2/fstat">fstat(2)</a>
   */
  public static void fstat(int fd, StructStat statbuf) throws IOException {
    Objects.requireNonNull(statbuf, "statbuf");
    int result = fstat0(fd, statbuf);
    if (result != 0) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not fstat() file");
    }
  }

  private static native int fstat0(int fd, StructStat statbuf) throws IOException;

  /**
   * Get file status. stats the file pointed to by path and fills in buf.
   *
   * @param pathName the file pointed to
   * @param statbuf
   *          to be filled
   * @throws IOException
   *           if the call fails
   * @see <a href="https://linux.die.net/man/2/fstat">stat(2)</a>
   */
  public static void stat(byte[] pathName, StructStat statbuf) throws IOException {
    Objects.requireNonNull(pathName, "pathName");
    if (pathName.length > PATH_MAX) {
      throw new IllegalArgumentException("pathName too long");
    }
    Objects.requireNonNull(statbuf, "statbuf");
    int result = stat0(pathName, statbuf);
    if (result != 0) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not stat() file");
    }
  }

  private static native int stat0(byte[] pathName, StructStat statbuf) throws IOException;

  /**
   * Get file status. stats the file pointed to by path and fills in buf.
   *
   * @param pathName the file pointed to
   * @param statbuf
   *          to be filled
   * @throws IOException
   *           if the call fails
   * @see <a href="https://linux.die.net/man/2/fstat">stat(2)</a>
   */
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

  /**
   * Open and possibly create a file.
   * <p>
   * The open() system call opens the file specified by pathname. If the
   * specified file does not exist, it may optionally (if
   * {@link OpenArgument#O_CREAT} is specified in flags) be created by open().
   * <p>
   *
   * The return value of open() is a file descriptor, a small, nonnegative
   * integer that is used in subsequent system calls (read(2), write(2),
   * lseek(2), fcntl(2), etc.) to refer to the open file. The file descriptor
   * returned by a successful call will be the lowest-numbered file descriptor
   * not currently open for the process.
   * <p>
   *
   * By default, the new file descriptor is set to remain open across an
   * execve(2) (i.e., the FD_CLOEXEC file descriptor flag described in fcntl(2)
   * is initially disabled); the {@link OpenArgument#O_CLOEXEC} flag, described
   * below, can be used to change this default. The file offset is set to the
   * beginning of the file (see lseek(2)).
   * <p>
   *
   * A call to open() creates a new open file description, an entry in the
   * system-wide table of open files. The open file description records the file
   * offset and the file status flags (see below). A file descriptor is a
   * reference to an open file description; this reference is unaffected if
   * pathname is subsequently removed or modified to refer to a different file.
   * For further details on open file descriptions, see NOTES.
   * <p>
   *
   * The argument flags must include one of the following access modes:
   * {@link OpenArgument#O_RDONLY}, {@link OpenArgument#O_WRONLY}, or
   * {@link OpenArgument#O_RDWR}. These request opening the file read- only,
   * write-only, or read/write, respectively.
   * <p>
   *
   * In addition, zero or more file creation flags and file status flags can be
   * bitwise-or'd in flags. The file creation flags are
   * {@link OpenArgument#O_CLOEXEC}, {@link OpenArgument#O_CREAT},
   * {@link OpenArgument#O_DIRECTORY}, {@link OpenArgument#O_EXCL},
   * {@link OpenArgument#O_NOCTTY}, {@link OpenArgument#O_NOFOLLOW},
   * {@link OpenArgument#O_TMPFILE}, and {@link OpenArgument#O_TRUNC}. The file
   * status flags are all of the remaining flags listed below. The distinction
   * between these two groups of flags is that the file creation flags affect
   * the semantics of the open operation itself, while the file status flags
   * affect the semantics of subsequent I/O operations. The file status flags
   * can be retrieved and (in some cases) modified; see fcntl(2) for details.
   * <p>
   * The full list of file creation flags and file status flags is as follows:
   * <ul>
   * <li>{@link OpenArgument#O_APPEND}</li>
   * <li>{@link OpenArgument#O_ASYNC}</li>
   * <li>{@link OpenArgument#O_CLOEXEC}</li>
   * <li>{@link OpenArgument#O_CREAT}</li>
   * <li>{@link OpenArgument#O_DIRECT}</li>
   * <li>{@link OpenArgument#O_DIRECTORY}</li>
   * <li>{@link OpenArgument#O_DSYNC}</li>
   * <li>{@link OpenArgument#O_EXCL}</li>
   * <li>{@link OpenArgument#O_NOATIME}</li>
   * <li>{@link OpenArgument#O_NOCTTY}</li>
   * <li>{@link OpenArgument#O_NOFOLLOW}</li>
   * <li>{@link OpenArgument#O_NONBLOCK}</li>
   * <li>{@link OpenArgument#O_NDELAY}</li>
   * <li>{@link OpenArgument#O_PATH}</li>
   * <li>{@link OpenArgument#O_SYNC}</li>
   * <li>{@link OpenArgument#O_TMPFILE}</li>
   * <li>{@link OpenArgument#O_TRUNC}</li>
   * <ul>
   *
   * @param pathName
   * @param flags
   * @param mode
   * @return
   * @throws IOException
   * @see <a href="http://man7.org/linux/man-pages/man2/open.2.html">open(2)</a>
   * @see OpenArgument
   */
  public static int open(byte[] pathName, int flags, int mode) throws IOException {
    Objects.requireNonNull(pathName, "pathName");
    if (pathName.length > PATH_MAX) {
      throw new IllegalArgumentException("pathName too long");
    }
    int fd = open0(pathName, pathName.length, flags, mode);
    if (fd == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  private static native int open0(byte[] pathName, int pathNameLength, int flags, int mode) throws IOException;


  /**
   * Open and possibly create a file.
   * <p>
   * The open() system call opens the file specified by pathname. If the
   * specified file does not exist, it may optionally (if
   * {@link OpenArgument#O_CREAT} is specified in flags) be created by open().
   * <p>
   *
   * The return value of open() is a file descriptor, a small, nonnegative
   * integer that is used in subsequent system calls (read(2), write(2),
   * lseek(2), fcntl(2), etc.) to refer to the open file. The file descriptor
   * returned by a successful call will be the lowest-numbered file descriptor
   * not currently open for the process.
   * <p>
   *
   * By default, the new file descriptor is set to remain open across an
   * execve(2) (i.e., the FD_CLOEXEC file descriptor flag described in fcntl(2)
   * is initially disabled); the {@link OpenArgument#O_CLOEXEC} flag, described
   * below, can be used to change this default. The file offset is set to the
   * beginning of the file (see lseek(2)).
   * <p>
   *
   * A call to open() creates a new open file description, an entry in the
   * system-wide table of open files. The open file description records the file
   * offset and the file status flags (see below). A file descriptor is a
   * reference to an open file description; this reference is unaffected if
   * pathname is subsequently removed or modified to refer to a different file.
   * For further details on open file descriptions, see NOTES.
   * <p>
   *
   * The argument flags must include one of the following access modes:
   * {@link OpenArgument#O_RDONLY}, {@link OpenArgument#O_WRONLY}, or
   * {@link OpenArgument#O_RDWR}. These request opening the file read- only,
   * write-only, or read/write, respectively.
   * <p>
   *
   * In addition, zero or more file creation flags and file status flags can be
   * bitwise-or'd in flags. The file creation flags are
   * {@link OpenArgument#O_CLOEXEC}, {@link OpenArgument#O_CREAT},
   * {@link OpenArgument#O_DIRECTORY}, {@link OpenArgument#O_EXCL},
   * {@link OpenArgument#O_NOCTTY}, {@link OpenArgument#O_NOFOLLOW},
   * {@link OpenArgument#O_TMPFILE}, and {@link OpenArgument#O_TRUNC}. The file
   * status flags are all of the remaining flags listed below. The distinction
   * between these two groups of flags is that the file creation flags affect
   * the semantics of the open operation itself, while the file status flags
   * affect the semantics of subsequent I/O operations. The file status flags
   * can be retrieved and (in some cases) modified; see fcntl(2) for details.
   * <p>
   * The full list of file creation flags and file status flags is as follows:
   * <ul>
   * <li>{@link OpenArgument#O_APPEND}</li>
   * <li>{@link OpenArgument#O_ASYNC}</li>
   * <li>{@link OpenArgument#O_CLOEXEC}</li>
   * <li>{@link OpenArgument#O_CREAT}</li>
   * <li>{@link OpenArgument#O_DIRECT}</li>
   * <li>{@link OpenArgument#O_DIRECTORY}</li>
   * <li>{@link OpenArgument#O_DSYNC}</li>
   * <li>{@link OpenArgument#O_EXCL}</li>
   * <li>{@link OpenArgument#O_NOATIME}</li>
   * <li>{@link OpenArgument#O_NOCTTY}</li>
   * <li>{@link OpenArgument#O_NOFOLLOW}</li>
   * <li>{@link OpenArgument#O_NONBLOCK}</li>
   * <li>{@link OpenArgument#O_NDELAY}</li>
   * <li>{@link OpenArgument#O_PATH}</li>
   * <li>{@link OpenArgument#O_SYNC}</li>
   * <li>{@link OpenArgument#O_TMPFILE}</li>
   * <li>{@link OpenArgument#O_TRUNC}</li>
   * <ul>
   *
   * @param pathName
   * @param flags
   * @param mode
   * @return
   * @throws IOException
   * @see <a href="http://man7.org/linux/man-pages/man2/open.2.html">open(2)</a>
   * @see OpenArgument
   */
  public static int open(String pathName, int flags, int mode) throws IOException {
    Objects.requireNonNull(pathName, "pathName");
    int fd = open0(pathName, pathName.length(), flags, mode);
    if (fd == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  private static native int open0(String pathName, int pathNameLength, int flags, int mode) throws IOException;


  /**
   * Open and possibly create a file.
   * <p>
   * The open() system call opens the file specified by pathname. If the
   * specified file does not exist, it may optionally (if
   * {@link OpenArgument#O_CREAT} is specified in flags) be created by open().
   * <p>
   *
   * The return value of open() is a file descriptor, a small, nonnegative
   * integer that is used in subsequent system calls (read(2), write(2),
   * lseek(2), fcntl(2), etc.) to refer to the open file. The file descriptor
   * returned by a successful call will be the lowest-numbered file descriptor
   * not currently open for the process.
   * <p>
   *
   * By default, the new file descriptor is set to remain open across an
   * execve(2) (i.e., the FD_CLOEXEC file descriptor flag described in fcntl(2)
   * is initially disabled); the {@link OpenArgument#O_CLOEXEC} flag, described
   * below, can be used to change this default. The file offset is set to the
   * beginning of the file (see lseek(2)).
   * <p>
   *
   * A call to open() creates a new open file description, an entry in the
   * system-wide table of open files. The open file description records the file
   * offset and the file status flags (see below). A file descriptor is a
   * reference to an open file description; this reference is unaffected if
   * pathname is subsequently removed or modified to refer to a different file.
   * For further details on open file descriptions, see NOTES.
   * <p>
   *
   * The argument flags must include one of the following access modes:
   * {@link OpenArgument#O_RDONLY}, {@link OpenArgument#O_WRONLY}, or
   * {@link OpenArgument#O_RDWR}. These request opening the file read- only,
   * write-only, or read/write, respectively.
   * <p>
   *
   * In addition, zero or more file creation flags and file status flags can be
   * bitwise-or'd in flags. The file creation flags are
   * {@link OpenArgument#O_CLOEXEC}, {@link OpenArgument#O_CREAT},
   * {@link OpenArgument#O_DIRECTORY}, {@link OpenArgument#O_EXCL},
   * {@link OpenArgument#O_NOCTTY}, {@link OpenArgument#O_NOFOLLOW},
   * {@link OpenArgument#O_TMPFILE}, and {@link OpenArgument#O_TRUNC}. The file
   * status flags are all of the remaining flags listed below. The distinction
   * between these two groups of flags is that the file creation flags affect
   * the semantics of the open operation itself, while the file status flags
   * affect the semantics of subsequent I/O operations. The file status flags
   * can be retrieved and (in some cases) modified; see fcntl(2) for details.
   * <p>
   * The full list of file creation flags and file status flags is as follows:
   * <ul>
   * <li>{@link OpenArgument#O_APPEND}</li>
   * <li>{@link OpenArgument#O_ASYNC}</li>
   * <li>{@link OpenArgument#O_CLOEXEC}</li>
   * <li>{@link OpenArgument#O_CREAT}</li>
   * <li>{@link OpenArgument#O_DIRECT}</li>
   * <li>{@link OpenArgument#O_DIRECTORY}</li>
   * <li>{@link OpenArgument#O_DSYNC}</li>
   * <li>{@link OpenArgument#O_EXCL}</li>
   * <li>{@link OpenArgument#O_NOATIME}</li>
   * <li>{@link OpenArgument#O_NOCTTY}</li>
   * <li>{@link OpenArgument#O_NOFOLLOW}</li>
   * <li>{@link OpenArgument#O_NONBLOCK}</li>
   * <li>{@link OpenArgument#O_NDELAY}</li>
   * <li>{@link OpenArgument#O_PATH}</li>
   * <li>{@link OpenArgument#O_SYNC}</li>
   * <li>{@link OpenArgument#O_TMPFILE}</li>
   * <li>{@link OpenArgument#O_TRUNC}</li>
   * <ul>
   *
   * @param pathName
   * @param flags
   * @return
   * @throws IOException
   * @see <a href="http://man7.org/linux/man-pages/man2/open.2.html">open(2)</a>
   * @see OpenArgument
   */
  public static int open(byte[] pathName, int flags) throws IOException {
    Objects.requireNonNull(pathName, "pathname");
    if (pathName.length > PATH_MAX) {
      throw new IllegalArgumentException("pathName too long");
    }
    int fd = open0(pathName, pathName.length, flags);
    if (fd == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  private static native int open0(byte[] pathName, int pathNameLength, int flags) throws IOException;


  /**
   * Open and possibly create a file.
   * <p>
   * The open() system call opens the file specified by pathname. If the
   * specified file does not exist, it may optionally (if
   * {@link OpenArgument#O_CREAT} is specified in flags) be created by open().
   * <p>
   *
   * The return value of open() is a file descriptor, a small, nonnegative
   * integer that is used in subsequent system calls (read(2), write(2),
   * lseek(2), fcntl(2), etc.) to refer to the open file. The file descriptor
   * returned by a successful call will be the lowest-numbered file descriptor
   * not currently open for the process.
   * <p>
   *
   * By default, the new file descriptor is set to remain open across an
   * execve(2) (i.e., the FD_CLOEXEC file descriptor flag described in fcntl(2)
   * is initially disabled); the {@link OpenArgument#O_CLOEXEC} flag, described
   * below, can be used to change this default. The file offset is set to the
   * beginning of the file (see lseek(2)).
   * <p>
   *
   * A call to open() creates a new open file description, an entry in the
   * system-wide table of open files. The open file description records the file
   * offset and the file status flags (see below). A file descriptor is a
   * reference to an open file description; this reference is unaffected if
   * pathname is subsequently removed or modified to refer to a different file.
   * For further details on open file descriptions, see NOTES.
   * <p>
   *
   * The argument flags must include one of the following access modes:
   * {@link OpenArgument#O_RDONLY}, {@link OpenArgument#O_WRONLY}, or
   * {@link OpenArgument#O_RDWR}. These request opening the file read- only,
   * write-only, or read/write, respectively.
   * <p>
   *
   * In addition, zero or more file creation flags and file status flags can be
   * bitwise-or'd in flags. The file creation flags are
   * {@link OpenArgument#O_CLOEXEC}, {@link OpenArgument#O_CREAT},
   * {@link OpenArgument#O_DIRECTORY}, {@link OpenArgument#O_EXCL},
   * {@link OpenArgument#O_NOCTTY}, {@link OpenArgument#O_NOFOLLOW},
   * {@link OpenArgument#O_TMPFILE}, and {@link OpenArgument#O_TRUNC}. The file
   * status flags are all of the remaining flags listed below. The distinction
   * between these two groups of flags is that the file creation flags affect
   * the semantics of the open operation itself, while the file status flags
   * affect the semantics of subsequent I/O operations. The file status flags
   * can be retrieved and (in some cases) modified; see fcntl(2) for details.
   * <p>
   * The full list of file creation flags and file status flags is as follows:
   * <ul>
   * <li>{@link OpenArgument#O_APPEND}</li>
   * <li>{@link OpenArgument#O_ASYNC}</li>
   * <li>{@link OpenArgument#O_CLOEXEC}</li>
   * <li>{@link OpenArgument#O_CREAT}</li>
   * <li>{@link OpenArgument#O_DIRECT}</li>
   * <li>{@link OpenArgument#O_DIRECTORY}</li>
   * <li>{@link OpenArgument#O_DSYNC}</li>
   * <li>{@link OpenArgument#O_EXCL}</li>
   * <li>{@link OpenArgument#O_NOATIME}</li>
   * <li>{@link OpenArgument#O_NOCTTY}</li>
   * <li>{@link OpenArgument#O_NOFOLLOW}</li>
   * <li>{@link OpenArgument#O_NONBLOCK}</li>
   * <li>{@link OpenArgument#O_NDELAY}</li>
   * <li>{@link OpenArgument#O_PATH}</li>
   * <li>{@link OpenArgument#O_SYNC}</li>
   * <li>{@link OpenArgument#O_TMPFILE}</li>
   * <li>{@link OpenArgument#O_TRUNC}</li>
   * <ul>
   *
   * @param pathName
   * @param flags
   * @return
   * @throws IOException
   * @see <a href="http://man7.org/linux/man-pages/man2/open.2.html">open(2)</a>
   * @see OpenArgument
   */
  public static int open(String pathname, int flags) throws IOException {
    Objects.requireNonNull(pathname, "pathname");
    int fd = open0(pathname, pathname.length(), flags);
    if (fd == -1) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not open() file");
    }
    return fd;
  }

  private static native int open0(String pathName, int pathNameLength, int flags) throws IOException;

  /**
   * Close a file descriptor.
   * <p>
   * close() closes a file descriptor, so that it no longer refers to any file
   * and may be reused. Any record locks (see fcntl(2)) held on the file it was
   * associated with, and owned by the process, are removed (regardless of the
   * file descriptor that was used to obtain the lock).
   *
   * @param fd
   *          is the last file descriptor referring to the underlying open file
   *          description (see open(2)), the resources associated with the open
   *          file description are freed; if the file descriptor was the last
   *          reference to a file which has been removed using unlink(2), the
   *          file is deleted.
   * @throws IOException
   *           if the call fails <a href=
   *           "http://man7.org/linux/man-pages/man2/close.2.html">close(2)</a>
   */
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

  /**
   * Map files or devices into memory.
   * <p>
   * mmap() creates a new mapping in the virtual address space of the calling
   * process. The starting address for the new mapping is specified in addr. The
   * length argument specifies the length of the mapping (which must be greater
   * than 0).
   * <p>
   * If addr is NULL, then the kernel chooses the address at which to create the
   * mapping; this is the most portable method of creating a new mapping. If
   * addr is not NULL, then the kernel takes it as a hint about where to place
   * the mapping; on Linux, the mapping will be created at a nearby page
   * boundary. The address of the new mapping is returned as the result of the
   * call.
   * <p>
   * The contents of a file mapping (as opposed to an anonymous mapping; see
   * MAP_ANONYMOUS below), are initialized using length bytes starting at offset
   * offset in the file (or other object) referred to by the file descriptor fd.
   * offset must be a multiple of the page size as returned by
   * sysconf(_SC_PAGE_SIZE).
   * <p>
   * The prot argument describes the desired memory protection of the mapping
   * (and must not conflict with the open mode of the file). It is either
   * {@link MmapArgument#PROT_NONE} or the bitwise OR of one or more of the
   * following flags:
   * <ul>
   * <li>{@link MmapArgument#PROT_EXEC}</li>
   * <li>{@link MmapArgument#PROT_EXEC}</li>
   * <li>{@link MmapArgument#PROT_EXEC}</li>
   * </ul>
   * The flags argument determines whether updates to the mapping are visible to
   * other processes mapping the same region, and whether updates are carried
   * through to the underlying file. This behavior is determined by including
   * exactly one of the following values in flags:
   * <p>
   * <ul>
   * <li>{@link MmapArgument#MAP_SHARED}</li>
   * <li>{@link MmapArgument#MAP_PRIVATE}</li>
   * </ul>
   * In addition, zero or more of the following values can be ORed in flags:
   * <p>
   * <ul>
   * <li>{@link MmapArgument#MAP_32BIT}</li>
   * <li>{@link MmapArgument#MAP_ANONYMOUS}</li>
   * <li>{@link MmapArgument#MAP_DENYWRITE}</li>
   * <li>{@link MmapArgument#MAP_GROWSDOWN}</li>
   * <li>{@link MmapArgument#MAP_HUGETLB}</li>
   * <li>{@link MmapArgument#MAP_LOCKED}</li>
   * <li>{@link MmapArgument#MAP_NONBLOCK}</li>
   * <li>{@link MmapArgument#MAP_NORESERVE}</li>
   * <li>{@link MmapArgument#MAP_POPULATE}</li>
   * <li>{@link MmapArgument#MAP_STACK}</li>
   * <li>{@link MmapArgument#MAP_UNINITIALIZED}</li>
   * </ul>
   * Of the above flags, only MAP_FIXED is specified in POSIX.1-2001 and
   * POSIX.1-2008. However, most systems also support {@link MmapArgument#MAP_ANONYMOUS}.
   * <p>
   * Memory mapped by mmap() is preserved across fork(2), with the same
   * attributes.
   * <p>
   * A file is mapped in multiples of the page size. For a file that is not a
   * multiple of the page size, the remaining memory is zeroed when mapped, and
   * writes to that region are not written out to the file. The effect of
   * changing the size of the underlying file of a mapping on the pages that
   * correspond to added or removed regions of the file is unspecified.
   *
   * @param buffer
   * @param length
   * @param prot
   * @param flags
   * @param fd
   * @param offset
   * @return
   * @throws IOException
   *           if the call fails
   * @see <a href="http://man7.org/linux/man-pages/man2/mmap.2.html">mmap(2)</a>
   * @see MmapArgument
   */
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

  /**
   * Unmap files or devices into memory.
   * <p>
   * The munmap() system call deletes the mappings for the specified address
   * range, and causes further references to addresses within the range to
   * generate invalid memory references. The region is also automatically
   * unmapped when the process is terminated. On the other hand, closing the
   * file descriptor does not unmap the region.
   *
   * <p>
   * The address addr must be a multiple of the page size (but length need not
   * be). All pages containing a part of the indicated range are unmapped, and
   * subsequent references to these pages will generate SIGSEGV. It is not an
   * error if the indicated range does not contain any mapped pages.
   *
   * @param buffer
   * @throws IOException
   * @see <a href="http://man7.org/linux/man-pages/man2/mmap.2.html">mmap(2)</a>
   */
  public static void munmap(ByteBuffer buffer) throws IOException {
    BufferAssertions.requireDirect(buffer);
    int result = munmap0(buffer, buffer.capacity());
    if (result != 0) {
      // this shouldn't happen, JNI should already have thrown an exception
      throw new IOException("could not munmap() address");
    }
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
