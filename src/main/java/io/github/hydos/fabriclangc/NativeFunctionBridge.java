package io.github.hydos.fabriclangc;

import com.chocohead.mm.api.ClassTinkerers;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.fabricmc.loader.launch.common.FabricLauncherBase;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class NativeFunctionBridge {

    private static final Set<String> USED = new HashSet<>();

    private final Object2LongMap<String> methodMap = new Object2LongOpenHashMap<>();
    private final Set<String> implement = new HashSet<>();
    private String extend = "java/lang/Object";

    public void extend(String clazz) {
        this.extend = clazz;
    }

    public void implement(String clazz) {
        this.implement.add(clazz);
    }

    public void addMethod(String name, String descriptor, long functionPointer) {
        this.methodMap.put(name + ";;;" + descriptor, functionPointer);
    }

    public Class<?> createAndLoad() throws ClassNotFoundException {
        ClassLoader classLoader = FabricLauncherBase.getLauncher().getTargetClassLoader();
        String name = findNewName(String.valueOf(hashCode()));

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) {
            @Override
            protected ClassLoader getClassLoader() {
                // Make sure this is on a proper class loader
                return classLoader;
            }
        };

        writer.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, name, null, extend, implement.toArray(new String[0]));

        for (Constructor<?> constructor : Class.forName(extend.replace('/', '.'), false, classLoader).getDeclaredConstructors()) {
            if (Modifier.isPublic(constructor.getModifiers()) || Modifier.isProtected(constructor.getModifiers())) {
                String constructorDescriptor = Type.getConstructorDescriptor(constructor);
                MethodVisitor methodVisitor = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>", constructorDescriptor, null, null);
                methodVisitor.visitCode();

                methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

                int i = 1;
                for (Class<?> parameterType : constructor.getParameterTypes()) {
                    methodVisitor.visitVarInsn(Type.getType(parameterType).getOpcode(Opcodes.ILOAD), i++);
                }

                methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, extend, "<init>", constructorDescriptor, false);
                methodVisitor.visitInsn(Opcodes.RETURN);
                methodVisitor.visitEnd();
            }
        }

        for (Object2LongMap.Entry<String> entry : methodMap.object2LongEntrySet()) {
            String[] parts = entry.getKey().split(";;;");
            Type[] argumentTypes = Type.getArgumentTypes(parts[1]);
            Type returnType = Type.getReturnType(parts[1]);
            long id = entry.getLongValue();

            MethodVisitor methodVisitor = writer.visitMethod(Opcodes.ACC_PUBLIC, parts[0], parts[1], null, null);
            methodVisitor.visitCode();
            methodVisitor.visitLdcInsn(id);
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitLdcInsn(argumentTypes.length);
            methodVisitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");

            int i = 1;
            for (Type argument : argumentTypes) {
                methodVisitor.visitInsn(Opcodes.DUP);
                methodVisitor.visitLdcInsn(i++);
                methodVisitor.visitVarInsn(argument.getOpcode(Opcodes.ILOAD), i);

                if (argument.getSort() == Type.BOOLEAN) {
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                } else if (argument.getSort() == Type.BYTE) {
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                } else if (argument.getSort() == Type.SHORT) {
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                } else if (argument.getSort() == Type.CHAR) {
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                } else if (argument.getSort() == Type.INT) {
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                } else if (argument.getSort() == Type.FLOAT) {
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                } else if (argument.getSort() == Type.LONG) {
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                } else if (argument.getSort() == Type.DOUBLE) {
                    methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                }

                methodVisitor.visitInsn(Opcodes.AASTORE);
            }

            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "io/github/hydos/fabriclangc/bridge/CBridge", "passToNative", "(JLjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", false);

            if (returnType.getSort() == Type.BOOLEAN) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
            } else if (returnType.getSort() == Type.BYTE) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B", false);
            } else if (returnType.getSort() == Type.SHORT) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S", false);
            } else if (returnType.getSort() == Type.CHAR) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C", false);
            } else if (returnType.getSort() == Type.INT) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            } else if (returnType.getSort() == Type.FLOAT) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F", false);
            } else if (returnType.getSort() == Type.LONG) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
            } else if (returnType.getSort() == Type.DOUBLE) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
            }

            methodVisitor.visitInsn(returnType.getOpcode(Opcodes.IRETURN));
            methodVisitor.visitEnd();
        }

        writer.visitEnd();

        if (ClassTinkerers.define(name, writer.toByteArray())) {
            return Class.forName(name.replace('/', '.'), true, classLoader);
        }

        return null;
    }

    private static String findNewName(String id) {
        int i = 0;
        String s;

        while (USED.contains(s = "me/ramidzkh/generated/flc/Generated" + id + "$" + i)) {
            i++;
        }

        if (!USED.add(s)) {
            // what?
            return findNewName(s);
        }

        return s;
    }
}
