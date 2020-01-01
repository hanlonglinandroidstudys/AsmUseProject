package com.example.thirdlib;

import android.util.Log;

/**
 * 模拟第三方库的SDK的方法
 */
public class ThirdLibSDK {

    private ThirdLibSDK() {
    }

    private static ThirdLibSDK instance;

    public static ThirdLibSDK getInstance() {
        if (instance == null) {
            instance = new ThirdLibSDK();
        }
        return instance;
    }

    /**
     * 处理
     */
    public void doSubmit() {
//        Log.e(ThirdLibSDK.class.getSimpleName(), "doSubmit: 出bug了！！");
        Log.e(ThirdLibSDK.class.getSimpleName(), "第三方库执行处理逻辑");
    }
}
