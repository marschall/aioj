#include <jni.h>

#include <unistd.h> // getpagesize
#include <stdlib.h> // aligned_alloc free
#include <sys/mman.h> // mlock unmlock
#include <errno.h>

#include "jniUtil.h"

int throwAllocationFailedException(JNIEnv *env, int errorCode)
{
  return throwJniException(env, errorCode, "com/github/marschall/aioj/capi/AllocationFailedException");
}

JNIEXPORT jobject JNICALL Java_com_github_marschall_aioj_capi_LibMemory_aligned_1alloc0
  (JNIEnv *env, jclass clazz, jlong alignment, jlong size)
{
  _Static_assert (sizeof(jlong) == sizeof(size_t), "sizeof(jlong) == sizeof(size_t)");

  void *buf = aligned_alloc((size_t) alignment, (size_t) size);
  if (buf != NULL)
  {
    return (*env)->NewDirectByteBuffer(env, buf, size);
  }
  else
  {
    throwAllocationFailedException(env, errno);
    return NULL;
  }
}

JNIEXPORT void JNICALL Java_com_github_marschall_aioj_capi_LibMemory_free0
  (JNIEnv *env, jclass clazz, jobject buf)
{
  void *ptr = (*env)->GetDirectBufferAddress(env, buf);
  if (ptr == NULL)
  {
    throwIllegalStateException(env, "GetDirectBufferAddress returned NULL");
    return;
  }
  free(ptr);
}

JNIEXPORT jlong JNICALL Java_com_github_marschall_aioj_capi_LibMemory_getDirectBufferAddress0
  (JNIEnv *env, jclass clazz, jobject buf)
{
  _Static_assert (sizeof(jlong) == sizeof(void *), "sizeof(jlong) == sizeof(void *)");

  return (jlong) (*env)->GetDirectBufferAddress(env, buf);
}

JNIEXPORT jlong JNICALL Java_com_github_marschall_aioj_capi_LibMemory_getDirectBufferCapacity0
  (JNIEnv *env, jclass clazz, jobject buf)
{
  return (*env)->GetDirectBufferCapacity(env, buf);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibMemory_mlock0
  (JNIEnv *env, jclass clazz, jobject buf)
{
  _Static_assert (sizeof(jlong) == sizeof(size_t), "sizeof(jlong) == sizeof(size_t)");

  void *addr = (*env)->GetDirectBufferAddress(env, buf);
  if (addr == NULL)
  {
    throwIllegalStateException(env, "GetDirectBufferAddress returned NULL");
    return;
  }
  
  jlong capacity = (*env)->GetDirectBufferCapacity(env, buf);
  if (capacity == -1)
  {
    throwIllegalStateException(env, "GetDirectBufferCapacity returned -1");
    return -1;
  }
  
  size_t len = (size_t) capacity;
  
  return mlock(addr, len);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibMemory_unmlock0
  (JNIEnv *env, jclass clazz, jobject buf)
{
  _Static_assert (sizeof(jlong) == sizeof(size_t), "sizeof(jlong) == sizeof(size_t)");

  void *addr = (*env)->GetDirectBufferAddress(env, buf);
  if (addr == NULL)
  {
    throwIllegalStateException(env, "GetDirectBufferAddress returned NULL");
    return;
  }
  
  jlong capacity = (*env)->GetDirectBufferCapacity(env, buf);
  if (capacity == -1)
  {
    throwIllegalStateException(env, "GetDirectBufferCapacity returned -1");
    return -1;
  }
  
  size_t len = (size_t) capacity;
  return munlock(addr, len);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibMemory_madvise0
  (JNIEnv *env, jclass clazz, jobject buf, jint advice)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  _Static_assert (sizeof(jlong) == sizeof(size_t), "sizeof(jlong) == sizeof(size_t)");

  void *addr = (*env)->GetDirectBufferAddress(env, buf);
  if (addr == NULL)
  {
    throwIllegalStateException(env, "GetDirectBufferAddress returned NULL");
    return;
  }
  
  jlong capacity = (*env)->GetDirectBufferCapacity(env, buf);
  if (capacity == -1)
  {
    throwIllegalStateException(env, "GetDirectBufferCapacity returned -1");
    return -1;
  }
  
  size_t length = (size_t) capacity;
  return madvise(addr, length, advice);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_aioj_capi_LibMemory_getpagesize0
  (JNIEnv *env, jclass clazz)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");

  return getpagesize();
}

