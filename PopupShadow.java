package com.smz.lexunuser.util;

import android.app.Activity;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 设置popup外边显示阴影
 */
public class PopupShadow {
    public static void popOutShadow(Activity activity, PopupWindow popupWindow) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;//设置阴影透明度
        activity.getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }
}
