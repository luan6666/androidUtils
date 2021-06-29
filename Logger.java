package com.smz.lexunuser.base.net;

/**
 * @Author ldx
 * @CreateDate 2020/9/19 9:57
 * @Description
 */


import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

public class Logger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Log.d("okhttp-->",message);
    }
}
