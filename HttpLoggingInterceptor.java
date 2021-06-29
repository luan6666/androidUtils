package com.smz.lexunuser.base.net;


import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * okhttp 拦截器
 */

public class HttpLoggingInterceptor implements Interceptor {
    private final Charset UTF8 = StandardCharsets.UTF_8;

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String body = null;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (charset != null) {
                body = buffer.readString(charset);
            }
        }

        Log.e("okhttp/",
                "发送请求: method：" + request.method()
                        + "\nurl：" + request.url()
                        + "\nheader：" + request.headers()
                        + "\nbody: " + body);

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        String rBody = null;

//        if(HttpEngine.hasBody(response)) {
        BufferedSource source = responseBody.source();
        // Buffer the entire body.
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        rBody = buffer.clone().readString(charset);

        Log.e("smz",
                "收到响应: code:" + response.code()
                        + "\nurl：" + response.request().url()
                        + "\nbody：" + body
                        + "\nResponse: " + rBody);

        return response;
    }

}
