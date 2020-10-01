#include "fabriclangC.h"
#include "Bridge.h"
#include <jni.h>
#include <stdio.h>
#include <malloc.h>
#include <libnet.h>

static JNIEnv *env;
int inDevEnv = 0;

char* concat(const char* a, const char* b) {
    char* buffer = calloc(strlen(a) + strlen(b) + 1);
    strcat(buffer, a);
    strcat(buffer, b);
    return buffer;
}

JNIEXPORT void JNICALL Java_io_github_hydos_fabriclangc_CBridge_parseJniEnv(JNIEnv *jniEnv, jclass instance, jboolean parsedDevEnv){
    env = jniEnv;
    inDevEnv = parsedDevEnv;

    jclass cls = (*env)->FindClass(env, "io/github/hydos/fabriclangc/JavaInteraction");
    jmethodID mid = (*env)->GetStaticMethodID(env, cls, "printHello", "()V");
    (*env)->CallStaticVoidMethod(env, cls, mid);
}

void *mc_new(int classId, char *constructorDescriptor, char **constructorParameters, struct java_parameters parameters) {
    jclass class = (*env)->FindClass(env, &"net/minecraft/class_" [ classId]);
    jmethodID jmethodId = (*env)->GetMethodID(env, class, "<init>", constructorDescriptor);
    return (*env)->NewObject(env, class, jmethodId);
}

void *mc_invokestatic(int classId, int methodId, struct java_parameters parameters) {
    //TODO: somehow get a methods descriptor. maybe call java code to grab intermediaries?
    jclass cls = (*env)->FindClass(env, &"net/minecraft/class_" [ classId]);
    jmethodID mid = (*env)->GetStaticMethodID(env, cls, &"method_"[methodId], 0); //TODO: descriptor
    (*env)->CallStaticVoidMethod(env, cls, mid);
    return 0;
}

void *mc_invokevirtual(int classId, int methodId, void *object, struct java_parameters parameters) {
    return 0;
}

void *mc_invokeinterface(int classId, int methodId, void *object, struct java_parameters parameters) {
    return 0;
}

// For raw java
void *java_new(char *className, char **constructorParameters, struct java_parameters parameters) {
    return 0;
}

void *java_invokestatic(char *className, char *methodName, char *methodDescriptor, struct java_parameters parameters) {
    jclass cls = (*env)->FindClass(env, className);
    jmethodID mid = (*env)->GetStaticMethodID(env, cls, methodName, methodDescriptor);
    (*env)->CallStaticVoidMethod(env, cls, mid);
    return 0;
}

void *java_invokevirtual(char *className, char *methodName, char *methodDescriptor, void *object, struct java_parameters parameters) {
    return 0;
}

void *java_invokeinterface(char *className, char *methodName, char *methodDescriptor, void *object, struct java_parameters parameters) {
    return 0;
}
