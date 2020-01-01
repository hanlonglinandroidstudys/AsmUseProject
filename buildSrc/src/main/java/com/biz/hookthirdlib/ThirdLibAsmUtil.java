package com.biz.hookthirdlib;


import com.android.tools.r8.code.E;
import com.biz.hookactivity.ActivityClassVisitor;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.io.IOException;
import java.io.InputStream;

import static org.objectweb.asm.Opcodes.ACC_PRIVATE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ACC_SUPER;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.IFNONNULL;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.PUTSTATIC;
import static org.objectweb.asm.Opcodes.RETURN;
import static org.objectweb.asm.Opcodes.V1_7;

public class ThirdLibAsmUtil {
    public static byte[] getResultClass(InputStream inputStream){
        try {
            return dump();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用 ASM bytecode viewer 生成 新的ThirdLibSDK 类的jvm字节码
     * @return
     * @throws Exception
     */
    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V1_7, ACC_PUBLIC | ACC_SUPER, "com/example/thirdlib/ThirdLibSDK", null, "java/lang/Object", null);

        classWriter.visitSource("ThirdLibSDK.java", null);

        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "instance", "Lcom/example/thirdlib/ThirdLibSDK;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PRIVATE, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(10, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(11, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Lcom/example/thirdlib/ThirdLibSDK;", null, label0, label2, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "getInstance", "()Lcom/example/thirdlib/ThirdLibSDK;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(16, label0);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/example/thirdlib/ThirdLibSDK", "instance", "Lcom/example/thirdlib/ThirdLibSDK;");
            Label label1 = new Label();
            methodVisitor.visitJumpInsn(IFNONNULL, label1);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(17, label2);
            methodVisitor.visitTypeInsn(NEW, "com/example/thirdlib/ThirdLibSDK");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/example/thirdlib/ThirdLibSDK", "<init>", "()V", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "com/example/thirdlib/ThirdLibSDK", "instance", "Lcom/example/thirdlib/ThirdLibSDK;");
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(19, label1);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitFieldInsn(GETSTATIC, "com/example/thirdlib/ThirdLibSDK", "instance", "Lcom/example/thirdlib/ThirdLibSDK;");
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(2, 0);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "doSubmit", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(27, label0);
            methodVisitor.visitLdcInsn(Type.getType("Lcom/example/thirdlib/ThirdLibSDK;"));
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
            methodVisitor.visitLdcInsn("\u7b2c\u4e09\u65b9\u5e93\u6267\u884c\u5904\u7406\u903b\u8f91");
            methodVisitor.visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            methodVisitor.visitInsn(POP);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(28, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Lcom/example/thirdlib/ThirdLibSDK;", null, label0, label2, 0);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
