package com.biz.hookthirdlib;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.biz.hookactivity.HookActivityAsmUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

/**
 * Transform 插入Activity进入和退出的埋点逻辑
 */
public class ThirdLibTransform extends Transform {
    @Override
    public String getName() {
        return ThirdLibTransform.class.getSimpleName();
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return true;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        Collection<TransformInput> inputs = transformInvocation.getInputs();
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        for (TransformInput input : inputs) {
            Collection<DirectoryInput> directoryInputs = input.getDirectoryInputs();
            Collection<JarInput> jarInputs = input.getJarInputs();

            for (DirectoryInput directoryInput : directoryInputs) {
                File dstDir = outputProvider.getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.DIRECTORY);
                transformDir(directoryInput.getFile(),dstDir);
            }

            for (JarInput jarInput : jarInputs) {
                File dstFile = outputProvider.getContentLocation(jarInput.getFile().getAbsolutePath(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR);
                transformJar(jarInput.getFile(),dstFile);
            }
        }
    }

    private void transformDir(File srcDir, File dstDir) {
        if(dstDir.exists()){
            try {
                FileUtils.forceDelete(dstDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileUtils.forceMkdir(dstDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String srcDirPath=srcDir.getAbsolutePath();
//        String dstDirPath = dstDir.getAbsolutePath();
//        for (File file : srcDir.listFiles()) {
//            String dstFilePath=file.getAbsolutePath().replace(srcDirPath,dstDirPath);
//            File dstFile=new File(dstFilePath);
//            if(file.isDirectory()){
//                transformDir(file,dstFile);
//            }else if(file.isFile()){
//                transformSingleClassFile(file,dstFile);
//            }
//        }
        try {
            FileUtils.copyDirectory(srcDir,dstDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 转化jar
     *
     * 步骤：
     *      1.解析jar包中所有class
     *      2.找到需要插入的class,对其修改后写入； 其他class原封写入
     *      3.输出
     * @param srcJarFile
     * @param dstJarFile
     */
    private void transformJar(File srcJarFile, File dstJarFile) {
        try {
            JarFile jarFile=new JarFile(srcJarFile);
            String tempJarFilePath=srcJarFile.getParent()+File.separator+"tempClasses.jar";
            File tempJarFile=new File(tempJarFilePath);
            JarOutputStream jarOutputStream=new JarOutputStream(new FileOutputStream(tempJarFile));
            Enumeration<JarEntry> entries = jarFile.entries();
            while(entries.hasMoreElements()){
                JarEntry jarEntry = entries.nextElement();
                String jarEntryName = jarEntry.getName();
                ZipEntry zipEntry=new ZipEntry(jarEntryName);
                InputStream inputStream = jarFile.getInputStream(zipEntry);
                System.out.println("jarEntryName--->"+jarEntryName);
                if(jarEntryName.equals("com/example/thirdlib/ThirdLibSDK.class")){
                    System.out.println("找到ThirdLibSDK,进行插桩---->");
                    // 转化后写入
                    jarOutputStream.putNextEntry(zipEntry);
                    jarOutputStream.write(ThirdLibAsmUtil.getResultClass(inputStream));
                }else{
                    // 直接写入
                    jarOutputStream.putNextEntry(zipEntry);
                    jarOutputStream.write(IOUtils.toByteArray(inputStream));
                }
                jarOutputStream.closeEntry();
            }
            jarOutputStream.close();
            jarFile.close();
            FileUtils.copyFile(tempJarFile,dstJarFile);
            FileUtils.forceDelete(tempJarFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void transformSingleClassFile(File srcFile, File dstFile) {
        try {
            FileUtils.copyFile(srcFile,dstFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
