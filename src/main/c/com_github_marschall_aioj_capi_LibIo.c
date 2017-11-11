#include <jni.h>

#include <sys/types.h> // open
#include <sys/stat.h>  // open
#include <fcntl.h>     // open
#include <unistd.h>    // close
#include <stdlib.h>    // malloc
#include <string.h>    // strerror
#include <errno.h>     // errno

#include "com_github_marschall_aioj_capi_LibIo.h"


int throwIoException(JNIEnv *env, int errorcode)
{
  jclass ioexception = (*env)->FindClass(env, "java/io/IOException");
  if (ioexception == NULL)
   {
     return -1;
   }
  char *message = strerror(errorcode);
  int success = (*env)->ThrowNew(env, ioexception, message);
  (*env)->DeleteLocalRef(env, ioexception); // not strictly necessary
  free(message);
  return success;
}

char *jbyteArrayToCharStart(JNIEnv *env, jbyteArray array, jint len)
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

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_open0___3BIII
  (JNIEnv *env, jclass clazz, jbyteArray jpathname, jint flags, jint mode, jint len)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  _Static_assert (sizeof(jint) == sizeof(mode_t), "sizeof(jint) == sizeof(mode_t)");
  char *pathname = jbyteArrayToCharStart(env, jpathname, len);
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
  (JNIEnv *env, jclass clazz, jbyteArray jpathname, jint flags, jint len)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  char *pathname = jbyteArrayToCharStart(env, jpathname, len);
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

