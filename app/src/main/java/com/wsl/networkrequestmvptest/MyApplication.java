package com.wsl.networkrequestmvptest;

import android.app.Application;

import com.wsl.networkrequestmvp.base.NetWorkRequestMvpModule;

/**
 * @package com.wsl.networkrequestmvp
 * @fileName MyApplication
 * @date 2020/4/13 11:38 AM
 * @auther 老色
 * @describe TODO
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        NetWorkRequestMvpModule.init("http://www.energy-link.com.cn/",1,this);

    }
}

