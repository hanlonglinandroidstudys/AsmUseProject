package com.biz.hookactivity;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class ActivityMethodVisitor extends AdviceAdapter {
    private String methodName;

    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param api           the ASM API version implemented by this visitor. Must be one of {@link
     *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param name          the method's name.
     * @param descriptor    the method's descriptor (see {@link Type Type}).
     */
    protected ActivityMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
        methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        if ("onCreate".equals(methodName)) {
            visitVarInsn(ALOAD, 0);
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
            visitLdcInsn("enter Activity");
            visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            visitInsn(POP);
        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if ("onDestroy".equals(methodName)) {
            visitVarInsn(ALOAD, 0);
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
            visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
            visitLdcInsn("exit Activity");
            visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            visitInsn(POP);
        }
    }
}
