#include <jni.h>

#include <unistd.h> // getpagesize


JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibMemory_getpagesize0
  (JNIEnv *env, jclass clazz)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  return getpagesize();
}

