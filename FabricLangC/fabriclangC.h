#ifndef FABRICLANGAUGEC_FABRICLANGC_H
#define FABRICLANGAUGEC_FABRICLANGC_H

struct java_parameters {
    int count;
    void* pointers;
};

// For intermediary mapped stuff. Will automatically remap
void* mc_new(int classId, char *constructorDescriptor, char** constructorParameters, struct java_parameters parameters);
void* mc_invokestatic(int classId, int methodId, struct java_parameters parameters);
void* mc_invokevirtual(int classId, int methodId, void* object, struct java_parameters parameters);
void* mc_invokeinterface(int classId, int methodId, void* object, struct java_parameters parameters);

// For raw java
void* java_new(char* className, char** constructorParameters, struct java_parameters parameters);
void* java_invokestatic(char* className, char* methodName, char* methodDescriptor, struct java_parameters parameters);
void* java_invokevirtual(char* className, char* methodName, char* methodDescriptor, void* object, struct java_parameters parameters);
void* java_invokeinterface(char* className, char* methodName, char* methodDescriptor, void* object, struct java_parameters parameters);

#endif //FABRICLANGAUGEC_FABRICLANGC_H
