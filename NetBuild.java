package com.smz.lexunuser.base.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smz.lexunuser.MyApp;
import com.smz.lexunuser.net.ApiService;
import com.smz.lexunuser.util.ConstantUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @Author ldx
 * @CreateDate 2020/8/27 11:08
 * @Description 构建retrofit请求
 */
public class NetBuild {

    private static NetBuild netBuild;

    public synchronized static NetBuild newInstance() {
        synchronized (MyApp.newInstance().getApplicationContext()) {
            if (netBuild == null) {
                netBuild = new NetBuild();
            }
        }

        return netBuild;
    }

    public static ApiService service() {
        return createRetrofit().create(ApiService.class);
    }

    private static Retrofit createRetrofit() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return new Retrofit.Builder()
                .baseUrl(ConstantUtil.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .client(getOkhttp())
                .build();
    }

    private static OkHttpClient getOkhttp() {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(new HttpLoggingInterceptor())
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                                    @NotNull
                                    @Override
                                    public Response intercept(@NotNull Chain chain) throws IOException {
                                        Request original = chain.request();
                                        Request.Builder requestBuilder = original.newBuilder();
                                        Request request = requestBuilder.build();
                                        return chain.proceed(request);
                                    }
                                }
                );
        return builder.build();
    }
}
