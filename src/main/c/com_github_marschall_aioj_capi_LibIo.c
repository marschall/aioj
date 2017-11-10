#include <jni.h>

#include <sys/types.h> // open
#include <sys/stat.h> // open
#include <fcntl.h> // open
#include <stdlib.h> // malloc
#include <string.h> // strerror
#include <errno.h> // errno

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

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibIo_open0___3BIII
  (JNIEnv *env, jclass clazz, jbyteArray jpathname, jint flags, jint mode, jint len)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  _Static_assert (sizeof(jint) == sizeof(mode_t), "sizeof(jint) == sizeof(mode_t)");
  char *pathname = malloc(sizeof(char) * (size_t) len + 1);
  (*env)->GetByteArrayRegion (env, jpathname, 0, len, (jbyte *) pathname);
  pathname[len] = 0; // terminator
  
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
