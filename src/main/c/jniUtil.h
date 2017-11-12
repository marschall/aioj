#include <jni.h>

int throwJniException(JNIEnv *env, int errorCode, const char *exceptionClassName);

int throwIllegalStateException(JNIEnv *env, const char *message);
