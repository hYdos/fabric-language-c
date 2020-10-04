#ifndef FABRICLANGC_CLASS_BUILDER_H
#define FABRICLANGC_CLASS_BUILDER_H

#include <jni.h>

typedef void *java_context;
typedef void *class_builder;

/// Gets the value of 'this' from context
jobject *java_context_get_this(java_context context);

/// Gets the parameter count
int java_context_get_parameter_count(java_context context);

/// Gets the parameter, or null if it's not within range
jobject *java_context_get_parameter(java_context context, int index);

/// Gets the JNI environment
JNIEnv *java_context_get_jni_env(java_context context);

/// Creates a new class builder, which implements no classes and extends Object
class_builder create_class_builder(JNIEnv *env);

/// Makes a class builder extend a class by name
void class_builder_extend(class_builder builder, char *clazz);

/// Makes a class builder implement a method by name
void class_builder_implement(class_builder builder, char *clazz);

/// Makes a class builder bridge a method
void class_builder_bridge(class_builder builder, char *name, char *descriptor, void (*method)(java_context context));

/// Builds a class
jclass class_builder_build(class_builder builder);

#endif
