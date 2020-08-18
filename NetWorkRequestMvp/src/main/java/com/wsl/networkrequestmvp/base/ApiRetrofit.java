package com.wsl.networkrequestmvp.base;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.wsl.networkrequestmvp.BuildConfig;
import com.wsl.networkrequestmvp.base.convert.MyGsonConverterFactory;
import com.wsl.networkrequestmvp.base.cookie.CookieManger;
import com.wsl.networkrequestmvp.base.gson.DoubleDefault0Adapter;
import com.wsl.networkrequestmvp.base.gson.IntegerDefault0Adapter;
import com.wsl.networkrequestmvp.base.gson.LongDefault0Adapter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * File descripition:   创建Retrofit
 */

public class ApiRetrofit {
    String TAG="ApiRetrofit";
    public final String BASE_SERVER_URL = NetWorkRequestMvpModule.getBASE_SERVER_URL();

    private static ApiRetrofit apiRetrofit;
    private Retrofit retrofit;

    private static Gson gson;


    private ApiRetrofit() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder
                .addInterceptor(logInterceptor)
                .cookieJar(new CookieManger(NetWorkRequestMvpModule.getContext()))
                .connectTimeout(NetWorkRequestMvpModule.getDEFAULT_TIMEOUT(), TimeUnit.SECONDS)
                .writeTimeout(NetWorkRequestMvpModule.getDEFAULT_TIMEOUT(), TimeUnit.SECONDS)
                .readTimeout(NetWorkRequestMvpModule.getDEFAULT_TIMEOUT(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//错误重联

        if(NetWorkRequestMvpModule.getHeaderInterceptor()!=null){
            httpClientBuilder.addInterceptor(NetWorkRequestMvpModule.getHeaderInterceptor());
        }




        Log.d(TAG," BASE_SERVER_URL:"+BASE_SERVER_URL);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create(buildGson()))//添加json转换框架(正常转换框架)
                .addConverterFactory(MyGsonConverterFactory.create(buildGson()))//添加json自定义（根据需求）
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClientBuilder.build())
                .build();

    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }

    /**
     * 增加后台返回""和"null"的处理
     * @return
     */
    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .create();
        }
        return gson;
    }

    public static ApiRetrofit getInstance() {
        if (apiRetrofit == null) {
            synchronized (Object.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new ApiRetrofit();
                }
            }
        }
        return apiRetrofit;
    }


    /**
     * 请求访问quest    打印日志
     * response拦截器
     */
    private Interceptor logInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();

            Logger.wtf(TAG, "----------Request Start----------------");
            Logger.wtf(TAG, "| " + request.toString() + "===========" + request.headers().toString());
            Log.d(TAG,"| " + request.toString() + "===========" + request.headers().toString());
            Logger.json(content);
            Logger.i(content);
            Logger.wtf(TAG, "----------Request End:" + duration + "毫秒----------");

            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    };


}
