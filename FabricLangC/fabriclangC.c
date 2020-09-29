#include "fabriclangC.h"
#include "Bridge.h"
#include <jni.h>
#include <stdio.h>
#include <malloc.h>

static JNIEnv *env;

JNIEXPORT void JNICALL Java_io_github_hydos_fabriclangc_CBridge_parseJniEnv(JNIEnv *jniEnv){
    env = jniEnv;

    jclass cls = (*env)->FindClass(env, "io/github/hydos/fabriclangc/TestClass");
    jmethodID mid = (*env)->GetStaticMethodID(env, cls, "printHello", "()V");
    (*env)->CallStaticVoidMethod(env, cls, mid);
}

void *mc_new(int classId, char **constructorParameters, struct java_parameters parameters) {
    return NULL;
}

void *mc_invokestatic(int classId, int methodId, struct java_parameters parameters) {
    return NULL;
}

void *mc_invokevirtual(int classId, int methodId, void *object, struct java_parameters parameters) {
    return NULL;
}

void *mc_invokeinterface(int classId, int methodId, void *object, struct java_parameters parameters) {
    return NULL;
}

// For raw java
void *java_new(char *className, char **constructorParameters, struct java_parameters parameters) {
    return NULL;
}

void *java_invokestatic(char *className, char *methodName, char *methodDescriptor, struct java_parameters parameters) {
    return NULL;
}

void *java_invokevirtual(char *className, char *methodName, char *methodDescriptor, void *object, struct java_parameters parameters) {
    return NULL;
}

void *java_invokeinterface(char *className, char *methodName, char *methodDescriptor, void *object, struct java_parameters parameters) {
    return NULL;
}
