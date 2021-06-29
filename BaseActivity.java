package com.smz.lexunuser.base.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.noober.background.BackgroundLibrary;
import com.smz.lexunuser.ui.login.StartActivity;
import com.smz.lexunuser.util.ActivityUtil;
import com.smz.lexunuser.util.SharedPreferenceUtil;
import com.smz.lexunuser.util.net.NetWorkBroadcastReceiver;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends FragmentActivity {
    public static NetWorkBroadcastReceiver.NetEvent netEvent;
    private Unbinder unbinder;
    private ProgressDialog pd1;

    @SuppressLint("checkResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BackgroundLibrary.inject2(this);
        super.onCreate(null);
        PushAgent.getInstance(this).onAppStart();
        setContentView(setLayoutId());

        unbinder = ButterKnife.bind(this);
        ImmersionBar.with(this).init();

        initView();
        initData();
        ActivityUtil.addActivity(this);
    }

    public void showLoading(String content) {
        if (pd1 == null) {
            pd1 = new ProgressDialog(this);
            //依次设置标题,内容,是否用取消按钮关闭,是否显示进度
            pd1.setMessage(content);
            pd1.setCancelable(true);
            //这里是设置进度条的风格,HORIZONTAL是水平进度条,SPINNER是圆形进度条
            pd1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd1.setIndeterminate(true);
        }
        if (!pd1.isShowing()) {
            pd1.show();
        }
    }

    public void hideLoading() {
        try {
            if (pd1 != null) {
                pd1.dismiss();
                pd1 = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getToken() {
        String aa = SharedPreferenceUtil.getContent(this, "token", "").toString();
        if (aa.equals("")) {
            Intent intent = new Intent(this, StartActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
            return "";
        }
        return aa;

    }

    public String getStore() {
        return SharedPreferenceUtil.getContent(this, "store", "").toString();
    }

    public String getStoreAddress() {
        return SharedPreferenceUtil.getContent(this, "storeAddress", "").toString();
    }

    public String getStorePhone() {
        return SharedPreferenceUtil.getContent(this, "storePhone", "").toString();
    }

    public int getStoreId() {
        return (int) SharedPreferenceUtil.getContent(this, "storeId", -1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 设置屏幕横竖屏切换
     *
     * @param screenRoate true  竖屏     false  横屏
     */
    private void setScreenRoate(Boolean screenRoate) {
        if (screenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏模式
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz    目标activity
     * @param bundle 数据
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    protected abstract void initData();

    protected abstract void initView();

    public abstract int setLayoutId();

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
        ActivityUtil.removeActivity(this);
        if (pd1 != null) {
            pd1.dismiss();
        }
    }
}
