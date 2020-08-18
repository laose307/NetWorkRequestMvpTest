package com.wsl.networkrequestmvp.base;

import android.content.Context;

import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.security.Principal;

import okhttp3.Interceptor;
import retrofit2.Retrofit;

/**
 * @package com.wsl.networkrequestmvp.base
 * @fileName NetWorkRequestMvpModule
 * @date 2020/4/13 10:59 AM
 * @auther 老色
 * @describe TODO
 */
public class NetWorkRequestMvpModule {

    //服务器地址
    private static String BASE_SERVER_URL;

    //服务器返回成功的 code
    private static int SUC_CODE;

    private static Context context;

    //超时时间
    private static int DEFAULT_TIMEOUT = 15;

    //添加请求头信息
    private static Interceptor headerInterceptor;

    //是否显示打印信息
    private static boolean  isShowLog=false;


    /**
     *
     * @param BASE_SERVER_URL
     * @param SUC_CODE
     * @param context
     */
    private NetWorkRequestMvpModule(String BASE_SERVER_URL, int SUC_CODE, Context context) {

    }
    public  static  void  init(String _BASE_SERVER_URL, int _SUC_CODE, Context _context){
        initLog();
        BASE_SERVER_URL = _BASE_SERVER_URL;
        SUC_CODE = _SUC_CODE;
       context = _context;

    }


    private static void initLog(){

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 是否显示线程信息 默认显示 上图Thread Infrom的位置
                .methodCount(0)         // 展示方法的行数 默认是2  上图Method的行数
                .methodOffset(7)        // 内部方法调用向上偏移的行数 默认是0
//                .logStrategy(customLog) // 改变log打印的策略一种是写本地，一种是logcat显示 默认是后者（当然也可以自己定义）
                .tag("network")   // 自定义全局tag 默认：PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }


    public static String getBASE_SERVER_URL() {
        return BASE_SERVER_URL;
    }

    public static void setBASE_SERVER_URL(String BASE_SERVER_URL_) {
        BASE_SERVER_URL = BASE_SERVER_URL_;
    }

    public static int getSUC_CODE() {
        return SUC_CODE;
    }

    public static void setSUC_CODE(int SUC_CODE_) {
        SUC_CODE = SUC_CODE_;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context_) {
        context = context_;
    }

    public static int getDEFAULT_TIMEOUT() {
        return DEFAULT_TIMEOUT;
    }

    public static void setDEFAULT_TIMEOUT(int DEFAULT_TIMEOUT_) {
        DEFAULT_TIMEOUT = DEFAULT_TIMEOUT_;
    }

    public static Interceptor getHeaderInterceptor() {
        return headerInterceptor;
    }

    public static void setHeaderInterceptor(Interceptor headerInterceptor) {
        NetWorkRequestMvpModule.headerInterceptor = headerInterceptor;
    }

    public static boolean isShowLog() {
        return isShowLog;
    }

    public static void setShowLog(boolean showLog) {
        isShowLog = showLog;
    }
}
