#include "class_builder.h"
#include <malloc.h>

struct java_context_impl {
    JNIEnv *env;
    jobject *this;
    int parameter_count;
    jobject **parameters;
};

typedef void *class_builder;

jobject *java_context_get_this(java_context context) {
    return ((struct java_context_impl *) context)->this;
}

int java_context_get_parameter_count(java_context context) {
    return ((struct java_context_impl *) context)->parameter_count;
}

jobject *java_context_get_parameter(java_context context, int index) {
    struct java_context_impl ctx = *((struct java_context_impl *) context);

    if (index < 0 || index >= ctx.parameter_count) {
        return NULL;
    } else {
        return ctx.parameters[index];
    }
}

JNIEnv *java_context_get_jni_env(java_context context) {
    return ((struct java_context_impl *) context)->env;
}

struct class_builder_impl {
    JNIEnv *env;
    char *extend;
    char **implement;
    char **keys;
    void (**values)(java_context context);
};

class_builder create_class_builder(JNIEnv *env) {
    struct class_builder_impl *impl = malloc(sizeof(struct class_builder_impl));
    impl->env = env;
    return impl;
}

void class_builder_extend(class_builder builder, char *clazz) {}

void class_builder_implement(class_builder builder, char *clazz) {}

void class_builder_bridge(class_builder builder, char *name, char *descriptor, void (*method)(java_context context)) {}

jclass class_builder_build(class_builder builder) {}
