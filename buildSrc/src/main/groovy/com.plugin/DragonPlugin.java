package com.plugin;

import com.android.build.gradle.AppExtension;
import com.biz.hookactivity.HookActivityTransform;
import com.biz.hookthirdlib.ThirdLibTransform;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class DragonPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        registerTransform(project);
    }

    // 注册transform
    private void registerTransform(Project project) {
        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        appExtension.registerTransform(new HookActivityTransform());

        appExtension.registerTransform(new ThirdLibTransform());
    }
}
