package com.smz.lexunuser.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * @Author ldx
 * @CreateDate 2020-8-25 10:59:33
 */
public class ToastUtil {

    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    public static void shortToast(Context context, Object content) {
        if (isShow) {
            if (Looper.myLooper() == null) {
                Looper.prepare();
                Toast.makeText(context.getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
                Looper.loop();
            } else {
                Toast.makeText(context.getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
