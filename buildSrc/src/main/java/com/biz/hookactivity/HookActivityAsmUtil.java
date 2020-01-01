package com.biz.hookactivity;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.io.InputStream;

public class HookActivityAsmUtil {
    public static byte[] getResultActivityClass(InputStream inputStream){
        try {
            ClassReader cr=new ClassReader(inputStream);
            ClassWriter cw=new ClassWriter(cr,ClassWriter.COMPUTE_MAXS);
            cr.accept(new ActivityClassVisitor(cw),ClassReader.EXPAND_FRAMES);
            return cw.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
