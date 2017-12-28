package com.github.marschall.aioj.nio;

import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.time.Instant;
import java.util.Set;

import com.github.marschall.aioj.capi.StructStat;

public final class Stat implements PosixFileAttributes {

  private final StructStat structStat;

  private Stat(StructStat structStat) {
    this.structStat = structStat;
  }

  public static Stat valueOf(StructStat structStat) {
    return new Stat(structStat);
  }

  private static FileTime fileTime(int sec, long nsec) {
    return FileTime.from(Instant.ofEpochSecond(sec, nsec));
  }

  @Override
  public FileTime lastModifiedTime() {
    return fileTime(this.structStat.st_mtim_sec, this.structStat.st_mtim_nsec);
  }

  @Override
  public FileTime lastAccessTime() {
    return fileTime(this.structStat.st_atim_sec, this.structStat.st_atim_nsec);
  }

  @Override
  public FileTime creationTime() {
    return fileTime(this.structStat.st_ctim_sec, this.structStat.st_ctim_nsec);
  }

  private int getFileType() {
    return this.structStat.st_mode & StructStat.S_IFMT;
  }

  @Override
  public boolean isRegularFile() {
    return this.getFileType() == StructStat.S_IFREG;
  }

  @Override
  public boolean isDirectory() {
    return this.getFileType() == StructStat.S_IFDIR;
  }

  @Override
  public boolean isSymbolicLink() {
    return this.getFileType() == StructStat.S_IFLNK;
  }

  @Override
  public boolean isOther() {
    return !(this.isRegularFile() || this.isDirectory() || this.isSymbolicLink());
  }

  @Override
  public long size() {
    return this.structStat.st_size;
  }

  @Override
  public Object fileKey() {
    // the API suggests returning null is OK
    return null;
  }

  @Override
  public UserPrincipal owner() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GroupPrincipal group() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<PosixFilePermission> permissions() {
    // TODO Auto-generated method stub
    return null;
  }

}
