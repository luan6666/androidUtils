package com.smz.lexunuser.ali;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;


import com.alipay.sdk.app.AuthTask;
import com.smz.lexunuser.util.SharedPreferenceUtil;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * APP支付宝登录
 * <p>
 * 这里只是为了方便,直接向加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class ALiLoginUtils {

    public Activity activity;
    private OnSuccessListener onSuccessListener;
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2021002103667401";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088811482810012";
    /**
     * 支付宝账户登录授权业务：入参target_id值 可自定义，保证唯一性即可
     */
    public static final String TARGET_ID = "lexun_retail";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC2HrOD9NN7ONpU4Eo8fvjdgEX97Cu6AdqQGbH8muRvhGKHY7PIGIvDMotg5qirHT0C4vkooLDRYG7UaQFgIezmGxztNC5GmT7Vu8TU5F8wjGORx194H+WYw6Irk4kEqktqDCsOpRk5skq3oFCPoIMUsSfHQfV1+p9GbnRivSTarojPUHm/mMlCkZ6DZO1OrOpwp13ws3ehcgnlQHRNlxBeKsZ3Qtkbsu2OK7QaTPE/mczha81cefzDwpF1iqNQxEoIJJnORD9o04UbJDwNeCpIrmzLB6zExaICWci0YpCu7Hy5NL/8B5bkaREv17VWoPldRO/FWXbQ+6OOX4bjcA0JAgMBAAECggEAMg2mEPJM3WT2vLUYMiBDjb0ff3ZVXj38L/af8Gkxt2SPH97LHoeAghl2LR+346+ZgR2YlQ31CtaSH1bsxdsNHQD+pkLdJRIhLuT4Z/qMHu+2ve4p0v4RQDckbw2RbvLO30qQ2umNUUorNuScv5zB6gH2iNEcAVzsQZNB8ZFCe8ddQWWqgC8vCq6hnklEFHA9t6TIzxN48JALvr3V3YJPRZq6cZj7/I+qgcgLkD5CufEWI/vgaMYA5nJvz8nIxH5njvGtWnVh75vgiNi0U2FDwNELU07W4MAxJpRUkby7yrOrq1zkIrdOxzu74S2CgbSuQwI7+U7bskGqNEX1t+1/MQKBgQDqm0ul/AiMw1+sUF+4qw/3u3sKPCTxVTjzylZ53+poQPwzDawPBmlfaUYIZ+NionfGyeVHHCok3gx7JD6WMk6+XczOYbCN8HzSN8nmORxv+zDjNlrsF22lFep5odbzvprHOHQNNV6OdYyPXijFW4LMUM25d4LhSvf64KPkwd/SNwKBgQDGuiVKYrjlUVl0CK6lEO7W90/oxURVdlXnye+Kf4XuGUmi3w0hcO+hVtU4vXjr50Tt27CIHjlOe2jLacb0huf8YboHZmXsLtyGItZ7Le7QRkJL8wRFqEwUP/wVgoX2iz7qGpVikJpYGh4gMWaSEwHAPYVh6k8Je9GRDtUEdNx6vwKBgQCJ3gMBou+fUPz7NSxbSGOU0J8Ir7mB/PQACCrzagvFglLy6ZjwJ3Nq92GJdQuiHhnHP0Q2mq2FhtvMzcrd1uiBZHwD2FK5xtaDbvd+DJfSksny7DFYRphPiGaHXf0EtkEcy+cfD/rmOtwEMHBDMnjV+rI6yKmoHbdGvhZySyBt1wKBgECCSHyfE1oNUDZkYxzeJEnPujbbYlVDtpspaALTZoxl1Qtz0HVcjc0XvNtQViJtzmhP+jwVY5DgaqItUhIqgL/ikVq5mLhUde1s/aa6wF0HkZnJ50cdXKTuKExrNEVV6jRgMMHwIsoaoFDe+GbOmLDox5WDU+jGMr8dy3RI4xFNAoGBAM6hCsZOIzVcy1FDk+IPq3Xw71NyTC/7LSeHlC9MiFn6bTY1idM+y0/0dNBWxmb5+Dr81X7KlA20scYfH8pFxnNdgQ1Vq0bmSSmb8Cq01an5uR9+sc6tJu3vEVEUWETgkTC+hEt15e8pR/xH0CRqOCr3Klb6Xj74VRPkrlAV3Z/H";

    public static final String RSA_PRIVATE = "";

    private static final int SDK_AUTH_FLAG = 2;

    public ALiLoginUtils(WeakReference<Activity> activity) {
        this.activity = activity.get();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    String resultCode = authResult.getResultCode();
                    Log.e("ALiLoginActivity", "=====resultStatus=====" + resultStatus);
                    Log.e("ALiLoginActivity", "=====resultCode=====" + resultCode);

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Log.e("ALiLoginActivity", "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                        Log.e("ALiLoginActivity", "授权成功\n" + authResult.getAlipayOpenId());
                        SharedPreferenceUtil.addContent(activity, "ali", authResult.getAlipayOpenId());

                        onSuccessListener.onSuccess(authResult.getAlipayOpenId());
//                        Toast.makeText(activity,"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    } else {
                        // 其他状态值则为授权失败
                        Log.e("ALiLoginActivity", "授权失败\n" + String.format("authCode:%s", authResult.getAuthCode()));
                        onSuccessListener.onFild(String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 支付宝账户授权业务
     */
    public void authV2() {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;

        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(activity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    public void setOnSuccess(OnSuccessListener onSuccess) {
        this.onSuccessListener = onSuccess;
    }

    public interface OnSuccessListener {
        void onSuccess(String id);

        void onFild(String msg);
    }
}