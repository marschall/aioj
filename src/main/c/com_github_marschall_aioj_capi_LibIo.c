#include <jni.h>

#include <sys/types.h> // open
#include <sys/stat.h>  // open
#include <sys/mman.h>  // mincore
#include <fcntl.h>     // open
#include <unistd.h>    // close, mincore
#include <stdlib.h>    // malloc
#include <string.h>    // strerror_r
#include <errno.h>     // errno

#include "com_github_marschall_aioj_capi_LibIo.h"


int throwIoException(JNIEnv *env, int errorCode)
{

  char message[256];
  int messageSuccess = strerror_r(errorCode, message, sizeof(message));
  if (messageSuccess != 0)
  {
    return -1;
  }

  jclass ioException = (*env)->FindClass(env, "java/io/IOException");
  if (ioException == NULL)
  {
     return -1;
  }

  int success = (*env)->ThrowNew(env, ioException, message);
  (*env)->DeleteLocalRef(env, ioException); // not strictly necessary
  return success;
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

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_open0___3BIII
  (JNIEnv *env, jclass clazz, jbyteArray jpathname, jint flags, jint mode, jint jpathnamelen)
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

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_open0___3BII
  (JNIEnv *env, jclass clazz, jbyteArray jpathname, jint flags, jint jpathnamelenlen)
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

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_close0
  (JNIEnv *env, jclass clazz, jint fd)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");

  int result = close(fd);
  if (result == -1)
  {
    throwIoException(env, errno);
  }
  return result;
}

//JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_mincore0
//  (JNIEnv *env, jclass clazz, jlong addr, jlong length, jbyteArray jvec, jint arrayLength)
//{
//  _Static_assert (sizeof(jlong) == sizeof(void *), "sizeof(jlong) == sizeof(void *)");
//  _Static_assert (sizeof(jlong) == sizeof(size_t), "sizeof(jlong) == sizeof(size_t)");
//  _Static_assert (sizeof(jbyte) == sizeof(unsigned char), "sizeof(jbyte) == sizeof(unsigned char)");
//
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

