#include <jni.h>

#include <sys/types.h> // open
#include <sys/stat.h>  // open
#include <sys/mman.h>  // mincore
#include <fcntl.h>     // open
#include <unistd.h>    // close, mincore
#include <stdlib.h>    // malloc
#include <string.h>    // strerror_r
#include <errno.h>     // errno
#include <limits.h>     // PATH_MAX
/* #include <stdio.h>     // FILENAME_MAX */

#include "jniUtil.h"
#include "com_github_marschall_aioj_capi_LibIo.h"

int throwIoException(JNIEnv *env, int errorCode)
{
  return throwJniException(env, errorCode, "java/io/IOException");
}

char *jbyteArrayToCString(JNIEnv *env, jbyteArray array, jint len)
{
  _Static_assert (sizeof(jbyte) == sizeof(char), "sizeof(jbyte) == sizeof(char)");

  char *buf = malloc(sizeof(char) * (size_t) len + 1);
  if (buf == NULL)
  {
    return NULL;
  }

  (*env)->GetByteArrayRegion(env, array, 0, len, (jbyte *) buf);
 
  // GetByteArrayRegion throws ArrayIndexOutOfBoundsException on invalid indices
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return NULL;
  }
  buf[len] = 0; // terminator
 
  return buf;
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
 
  // GetByteArrayRegion throws ArrayIndexOutOfBoundsException on invalid indices
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return NULL;
  }
 
  return buf;
}


jint openByte3
  (JNIEnv *env, jbyteArray jpathname, jint jpathnamelen, jint flags, jint mode)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  _Static_assert (sizeof(jint) == sizeof(mode_t), "sizeof(jint) == sizeof(mode_t)");

  char *pathname = jbyteArrayToCString(env, jpathname, jpathnamelen);
  if (pathname == NULL)
  {
    return -1;
  }
  
  int fd = open(pathname, flags, mode);
  
  // save the error code
  int errorcode = 0;
  if (fd == -1)
  {
    errorcode = errno;
  }
  
  // free pathname as early as possible
  free(pathname);
  
  if (fd == -1)
  {
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
  jsize utf8len = (*env)->GetStringUTFRegion(env, jpathname);
  if (utf8len >= PATH_MAX)
  {
    throwIoException(env, ENAMETOOLONG);
    // we return -1 from C but an IOException upon entry into Java
    return -1;
  }
  pathname[utf8len] = 0;

  (*env)->GetStringUTFRegion(env, jpathname, 0, jpathnamelen, &pathname);
  // GetStringUTFRegion throws StringIndexOutOfBoundsException on invalid indices
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return -1;
  }

  int fd = open(pathname, flags, mode);

  if (fd == -1)
  {
    // save the error code
    int errorcode = errno;
    throwIoException(env, errorcode);
    // we will end up returning -1 from C but an IOException upon entry into Java
  }
  return fd;
}

jint openByte2
  (JNIEnv *env, jbyteArray jpathname, jint jpathnamelenlen, jint flags)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");

  char *pathname = jbyteArrayToCString(env, jpathname, jpathnamelenlen);
  if (pathname == NULL)
  {
    return -1;
  }
  
  
  int fd = open(pathname, flags);
  // save the error code
  int errorcode = 0;
  if (fd == -1)
  {
    errorcode = errno;
  }
  
  // free pathname as early as possible
  free(pathname);
  
  if (fd == -1)
  {
    throwIoException(env, errorcode);
  }
  return fd;
}

jint openString2
  (JNIEnv *env, jstring jpathname, jint jpathnamelenlen, jint flags)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");

  char pathname[PATH_MAX];
  jsize utf8len = (*env)->GetStringUTFRegion(env, jpathname);
  if (utf8len >= PATH_MAX)
  {
    throwIoException(env, ENAMETOOLONG);
    // we return -1 from C but an IOException upon entry into Java
    return -1;
  }
  pathname[utf8len] = 0;

  (*env)->GetStringUTFRegion(env, jpathname, 0, jpathnamelen, &pathname);
  // GetStringUTFRegion throws StringIndexOutOfBoundsException on invalid indices
  if ((*env)->ExceptionCheck(env) == JNI_TRUE)
  {
    return -1;
  }


  int fd = open(pathname, flags);
  if (fd == -1)
  {
    // save the error code
    int errorcode = errno;
    throwIoException(env, errorcode);
    // we will end up returning -1 from C but an IOException upon entry into Java
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

