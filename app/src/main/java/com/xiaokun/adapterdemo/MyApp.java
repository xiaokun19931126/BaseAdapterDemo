package com.xiaokun.adapterdemo;

import android.app.Application;
import android.content.Context;

import com.xiaokun.http_library.RxHttpUtils;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/01/16
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */

public class MyApp extends Application
{
    public static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this;
        RxHttpUtils.init(this);

        RxHttpUtils.getInstance()
                .config()
                .setBaseUrl("http://gank.io/api/")
                .setWriteTimeOut(10)
                .setConnectTimeOut(10)
                .setReadTimeOut(10)
                .setLog(BuildConfig.DEBUG);
    }
}
