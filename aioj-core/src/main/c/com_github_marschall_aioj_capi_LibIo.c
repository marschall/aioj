#include <jni.h>

#include <sys/types.h>    // open
#include <sys/stat.h>     // open
#include <sys/mman.h>     // mincore
#include <sys/sendfile.h>
#include <fcntl.h>        // open
#include <unistd.h>       // close, mincore
#include <stdlib.h>       // malloc
#include <string.h>       // strerror_r
#include <errno.h>        // errno
#include <limits.h>       // PATH_MAX
#include <stdio.h>        // FILENAME_MAX

#include "jniUtil.h"
#include "com_github_marschall_aioj_capi_LibIo.h"

_Static_assert (com_github_marschall_aioj_capi_LibIo_PATH_MAX == PATH_MAX, "com_github_marschall_aioj_capi_LibIo_PATH_MAX == PATH_MAX");
_Static_assert (com_github_marschall_aioj_capi_LibIo_FILENAME_MAX == FILENAME_MAX, "com_github_marschall_aioj_capi_LibIo_FILENAME_MAX == FILENAME_MAX");
_Static_assert (com_github_marschall_aioj_capi_LibIo_NULL == NULL, "com_github_marschall_aioj_capi_LibIo_NULL == NULL");

jfieldID stDevFieldID;
jfieldID stInoFieldID;
jfieldID stModeFieldID;
jfieldID stNlinkFieldID;
jfieldID stUidFieldID;
jfieldID stGidFieldID;
jfieldID stRdevFieldID;
jfieldID stSizeFieldID;
jfieldID stBlksizeFieldID;
jfieldID stBlocksFieldID;
jfieldID stAtimSecFieldID;
jfieldID stAtimNsecFieldID;
jfieldID stMtimSecFieldID;
jfieldID stMtimNsecFieldID;
jfieldID stCtimSecFieldID;
jfieldID stCtimNsecFieldID;

void initFieldIds(JNIEnv *env, jclass structStatClass)
{
  stDevFieldID = (*env)->GetFieldID(env, structStatClass, "st_dev", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_ino", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_mode", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_nlink", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_uid", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_gid", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_rdev", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_size", "J");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_blksize", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_blocks", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_atim_sec", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_atim_nsec", "J");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_mtim_sec", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_mtim_nsec", "J");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_ctim_sec", "I");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }

  (*env)->GetFieldID(env, structStatClass, "st_ctim_nsec", "J");
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return;
  }
}

int throwIoException(JNIEnv *env, int errorCode)
{
  return throwJniException(env, errorCode, "java/io/IOException");
}

unsigned char *jbyteArrayToUnsignedCharStar(JNIEnv *env, jbyteArray array, jint len)
{
  _Static_assert (sizeof(jbyte) == sizeof(unsigned char), "sizeof(jbyte) == sizeof(unsigned char)");

  unsigned char *buf = malloc(sizeof(unsigned char) * (size_t) len);
  if (buf == NULL)
  {
    return NULL;
  }

  (*env)->GetByteArrayRegion(env, array, 0, len, (jbyte *) buf);
 
  /* GetByteArrayRegion throws ArrayIndexOutOfBoundsException on invalid indices */
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return NULL;
  }
 
  return buf;
}


jlong sendfile0
  (JNIEnv *env, jint out_fd, jint in_fd, jlong offset, jlong count)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  _Static_assert (sizeof(off_t) == sizeof(jlong), "sizeof(off_t) == sizeof(jlong)");
  _Static_assert (sizeof(size_t) == sizeof(jlong), "sizeof(size_t) == sizeof(jlong)");
  _Static_assert (sizeof(ssize_t) == sizeof(jlong), "sizeof(ssize_t) == sizeof(jlong)");

  ssize_t transferred = sendfile(out_fd, in_fd, offset, count);

  if (transferred == -1)
  {
    /* save the error code */
    int errorcode = errno;
    throwIoException(env, errorcode);
    /* we will end up returning -1 from C but an IOException upon entry into Java */
  }
  return transferred;
}

jint fstatByte3
  (JNIEnv *env, jint fd, jobject statbuf)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");

  struct stat sb;
  int returnValue = fstat(fd, &sb);
  if (returnValue == -1)
  {
    /* save the error code */
    int errorcode = errno;
    throwIoException(env, errorcode);
  }

  (*env)->SetIntField(env, statbuf, stDevFieldID, sb.st_dev);
  (*env)->SetIntField(env, statbuf, stInoFieldID, sb.st_ino);
  (*env)->SetIntField(env, statbuf, stModeFieldID, sb.st_mode);
  (*env)->SetIntField(env, statbuf, stNlinkFieldID, sb.st_nlink);
  (*env)->SetIntField(env, statbuf, stUidFieldID, sb.st_uid);
  (*env)->SetIntField(env, statbuf, stGidFieldID, sb.st_gid);
  (*env)->SetIntField(env, statbuf, stRdevFieldID, sb.st_rdev);
  (*env)->SetLongField(env, statbuf, stSizeFieldID, sb.st_size);
  (*env)->SetIntField(env, statbuf, stBlksizeFieldID, sb.st_blksize);
  (*env)->SetIntField(env, statbuf, stBlocksFieldID, sb.st_blocks);
  (*env)->SetIntField(env, statbuf, stAtimSecFieldID, sb.st_atim.tv_sec);
  (*env)->SetLongField(env, statbuf, stAtimNsecFieldID, sb.st_atim.tv_nsec);
  (*env)->SetIntField(env, statbuf, stMtimSecFieldID, sb.st_mtim.tv_sec);
  (*env)->SetLongField(env, statbuf, stMtimNsecFieldID, sb.st_mtim.tv_nsec);
  (*env)->SetIntField(env, statbuf, stCtimSecFieldID, sb.st_ctim.tv_sec);
  (*env)->SetLongField(env, statbuf, stCtimNsecFieldID, sb.st_ctim.tv_nsec);

  return 0;
}

jint openByte3
  (JNIEnv *env, jbyteArray jpathname, jint jpathnamelen, jint flags, jint mode)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  _Static_assert (sizeof(jint) == sizeof(mode_t), "sizeof(jint) == sizeof(mode_t)");

  char pathname[PATH_MAX];
  (*env)->GetByteArrayRegion(env, jpathname, 0, jpathnamelen, (jbyte *) pathname);
  // GetByteArrayRegion throws ArrayIndexOutOfBoundsException on invalid indices
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return -1;
  }
  buf[jpathnamelen] = 0; // terminator
  
  int fd = open(pathname, flags, mode);
  
  if (fd == -1)
  {
    // save the error code
    int errorcode = errno;
    throwIoException(env, errorcode);
  }
  
  return fd;
}


jint openString3
  (JNIEnv *env, jstring jpathname, jint jpathnamelen, jint flags, jint mode)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  _Static_assert (sizeof(jint) == sizeof(mode_t), "sizeof(jint) == sizeof(mode_t)");

  char pathname[PATH_MAX];
  jsize utf8len = (*env)->GetStringUTFLength(env, jpathname);
  if (utf8len >= PATH_MAX)
  {
    throwIoException(env, ENAMETOOLONG);
    /* we return -1 from C but an IOException upon entry into Java */
    return -1;
  }

  (*env)->GetStringUTFRegion(env, jpathname, 0, jpathnamelen, &pathname);
  /* GetStringUTFRegion throws StringIndexOutOfBoundsException on invalid indices */
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return -1;
  }
  /* JNI does not guarantee 0 terminator https://stackoverflow.com/questions/16694239/java-native-code-string-ending */
  pathname[utf8len] = 0;

  int fd = open(pathname, flags, mode);

  if (fd == -1)
  {
    /* save the error code */
    int errorcode = errno;
    throwIoException(env, errorcode);
    /* we will end up returning -1 from C but an IOException upon entry into Java */
  }
  return fd;
}

jint openByte2
  (JNIEnv *env, jbyteArray jpathname, jint jpathnamelenlen, jint flags)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");

  char pathname[PATH_MAX];
  (*env)->GetByteArrayRegion(env, jpathname, 0, jpathnamelen, (jbyte *) pathname);
  /* GetByteArrayRegion throws ArrayIndexOutOfBoundsException on invalid indices */
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return NULL;
  }
  buf[jpathnamelen] = 0; /* terminator */
  
  
  int fd = open(pathname, flags);

  if (fd == -1)
  {
    /* save the error code */
    int errorcode = errno;
    throwIoException(env, errorcode);
    /* we will end up returning -1 from C but an IOException upon entry into Java */
  }

  return fd;
}

jint openString2
  (JNIEnv *env, jstring jpathname, jint jpathnamelenlen, jint flags)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");

  char pathname[PATH_MAX];
  jsize utf8len = (*env)->GetStringUTFLength(env, jpathname);
  if (utf8len >= PATH_MAX)
  {
    throwIoException(env, ENAMETOOLONG);
    /* we return -1 from C but an IOException upon entry into Java */
    return -1;
  }

  (*env)->GetStringUTFRegion(env, jpathname, 0, jpathnamelen, &pathname);
  /* GetStringUTFRegion throws StringIndexOutOfBoundsException on invalid indices */
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return -1;
  }

  /* JNI does not guarantee 0 terminator https://stackoverflow.com/questions/16694239/java-native-code-string-ending */
  pathname[utf8len] = 0;


  int fd = open(pathname, flags);
  if (fd == -1)
  {
    /* save the error code */
    int errorcode = errno;
    throwIoException(env, errorcode);
    /* we will end up returning -1 from C but an IOException upon entry into Java */
  }

  return fd;
}


jint close0
  (JNIEnv *env, jint fd)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");

  int result = close(fd);
  if (result == -1)
  {
    throwIoException(env, errno);
  }
  return result;
}

jobject mmap0
  (JNIEnv *env, jobject buffer, jlong length, jint prot, jint flags, jint fd, jlong offset)
{
  _Static_assert (sizeof(jlong) == sizeof(size_t), "sizeof(jlong) == sizeof(size_t)");
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  _Static_assert (sizeof(jlong) == sizeof(off_t), "sizeof(jlong) == sizeof(off_t)");

  void *addr;
  if ((*env)->IsSameObject(env, buffer, NULL))
  {
    addr = NULL;
  }
  else
  {
    addr = (*env)->GetDirectBufferAddress(env, buffer);
  }
  
  void *result = mmap(addr, (size_t) length, (int) prot, (int) flags, (int) fd, (off_t) offset);
  if (result == MAP_FAILED)
  {
    throwIoException(env, errno);
    return NULL;
  }
  else
  {
    return (*env)->NewDirectByteBuffer(env, result, length);
  }
}

jint munmap0
  (JNIEnv *env, jobject buffer, jlong length)
{
  _Static_assert (sizeof(jlong) == sizeof(size_t), "sizeof(jlong) == sizeof(size_t)");
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");

  void *addr = (*env)->GetDirectBufferAddress(env, buffer);
  // TODO null check
  int result = munmap(addr, (size_t) length);
  if (result != 0)
  {
    throwIoException(env, errno);
  }
  return result;
}

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_open0___3BIII
  (JNIEnv *env, jclass clazz, jbyteArray jpathname, jint jpathnamelen, jint flags, jint mode)
{
  return openByte3(env, jpathname, jpathnamelen, flags, mode);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_open0___3BII
  (JNIEnv *env, jclass clazz, jbyteArray jpathname, jint jpathnamelen, jint flags)
{
  return openByte2(env, jpathname, jpathnamelen, flags);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_close0
  (JNIEnv *env, jclass clazz, jint fd)
{
  return close0(env,  fd);
}

//JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_mincore0
//  (JNIEnv *env, jclass clazz, jlong addr, jlong length, jbyteArray jvec, jint arrayLength)
//{
//  _Static_assert (sizeof(jlong) == sizeof(void *), "sizeof(jlong) == sizeof(void *)");
//  _Static_assert (sizeof(jlong) == sizeof(size_t), "sizeof(jlong) == sizeof(size_t)");
//  _Static_assert (sizeof(jbyte) == sizeof(unsigned char), "sizeof(jbyte) == sizeof(unsigned char)");
//  TODO GetByteArrayElements
//  unsigned char *vec = jbyteArrayToUnsignedCharStar(env, jvec, arrayLength);
//  if (vec == NULL)
//  {
//    return -1;
//  }
//  int result = mincore((void *) addr, (size_t) length, vec);
//  // save the error code
//  int errorcode = 0;
//  if (result == -1)
//  {
//    errorcode = errno;
//  }
//  
//  free(vec);
//  if (result == -1)
//  {
//    throwIoException(env, errorcode);
//  }
//  
//  (*env)->SetByteArrayRegion(env, jvec, 0, arrayLength, (jbyte *) vec);
//  // SetByteArrayRegion throws ArrayIndexOutOfBoundsException on invalid indices
//  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
//  {
//    return -1;
//  }
//  
//  return result;
//}

JNIEXPORT jobject JNICALL Java_com_github_marschall_aioj_capi_LibIo_mmap0
  (JNIEnv *env, jclass clazz, jobject buffer, jlong length, jint prot, jint flags, jint fd, jlong offset)
{
  return mmap0(env, buffer, length, prot, flags, fd, offset);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_munmap0
  (JNIEnv *env, jclass clazz, jobject buffer, jlong length)
{
  return munmap0(env, buffer, length);
}

