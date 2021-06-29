package com.smz.yongji.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.smz.yongji.R;
import com.smz.yongji.base.BaseActivity;
import com.smz.yongji.base.net.BaseCallBack;
import com.smz.yongji.base.net.BaseRes;
import com.smz.yongji.base.net.NetBuild;
import com.smz.yongji.bean.CacheData;
import com.smz.yongji.bean.CallectionBean;
import com.smz.yongji.bean.H5ReturnBean;
import com.smz.yongji.bean.NewsDetail;
import com.smz.yongji.bean.SendUser;
import com.smz.yongji.utils.ActivityUtil;
import com.smz.yongji.utils.CacheUtil;
import com.smz.yongji.utils.ConstantUtil;
import com.smz.yongji.utils.GsonUtils;
import com.smz.yongji.utils.LogUtil;
import com.smz.yongji.utils.SharedPreferenceUtil;
import com.smz.yongji.utils.ToastUtil;
import com.smz.yongji.web.tencentx5.config.WebProgress;
import com.smz.yongji.web.tencentx5.utils.StatusBarUtil;
import com.tencent.smtt.sdk.WebSettings;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.ycbjie.webviewlib.InterWebListener;
import com.ycbjie.webviewlib.VideoWebListener;
import com.ycbjie.webviewlib.WvWebView;
import com.ycbjie.webviewlib.X5WebChromeClient;
import com.ycbjie.webviewlib.X5WvWebView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class TestActivity extends BaseActivity {
    @BindView(R.id.webview_detail)
    X5WvWebView webView;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_gun_title)
    TextView tvGunTitle;
    @BindView(R.id.pb_progress)
    WebProgress mProgressBar;
    @BindView(R.id.shoucang)
    ImageView callec;

    private String mUrl;
    private String mId;
    private Toolbar mTitleToolBar;
    // 可滚动的title 使用简单 没有渐变效果，文字两旁有阴影
    private String mTitle;
    private String token;

    private boolean isFirst = true;

    @Override
    protected int initLayout() {
        return R.layout.activity_test;
    }

    private final String JS_REFRESH_PAGE = "smz://common/refreshPage";


    @Override
    protected void initView() {

        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + ";shanmuzhi");
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        initBriage();
        getIntentData();
        initTitle();
    }

    private void initBriage() {
        webView.registerHandler("smz://member/memberInfo", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                Log.e("TestActivity memberInfo", data.toString());
                JSONObject jsonObject = null;
                String aa = "";
                String cc = "";
                try {
                    jsonObject = new JSONObject(data.toString());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    cc = jsonObject1.getString("forceLogin");
                    aa = jsonObject1.getString("notCallLogin");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                BaseRes<SendUser> baseRes = new BaseRes<SendUser>();
                if (aa.equals("1")) {
                    baseRes.setCode(300);
                    callback.onResult(baseRes);
                }


                SendUser sendUser1 = new SendUser();
                if ((cc.equals("1"))) {
                    baseRes.setCode(300);
                    Intent intent = new Intent(TestActivity.this, LoginActivity.class);
                    startActivityForResult(intent, 10010);
                } else {
                    baseRes.setCode(200);
                    sendUser1.setToken(token);
                    baseRes.setData(sendUser1);
                }


                JSONObject bb = null;
                try {
                    bb = new JSONObject(new Gson().toJson(baseRes));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("json", bb.toString());
                callback.onResult(bb);
            }
        });

        webView.registerHandler("smz://member/setPassword", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                Intent intent = new Intent(TestActivity.this, ResetPassActivity.class);
                startActivity(intent);
                TestActivity.this.finish();
            }
        });
        webView.registerHandler("smz://public/share", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                Log.e("TestActivity share", data.toString());
                JSONObject jsonObject = null;
                String title = "";
                String detail = "";
                String url = "";
                String image = "";
                String platFormType = "";
                try {
                    jsonObject = new JSONObject(data.toString());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    title = jsonObject1.getString("title");
                    detail = jsonObject1.getString("detail");
                    url = jsonObject1.getString("url");
                    image = jsonObject1.getString("image");
                    platFormType = jsonObject1.getString("platFormType");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                shareContent(title, detail, url, image, platFormType);
            }
        });
        webView.registerHandler("smz://public/saveInfo", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                Log.e("TestActivity saveInfo", data.toString());
                CacheData cacheData = GsonUtils.parse(data.toString(), CacheData.class);
                SharedPreferenceUtil.addContent(TestActivity.this, cacheData.getKey(), cacheData.getValue());
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(GsonUtils.toGson(new H5ReturnBean()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResult(jsonObject);

            }
        });
        webView.registerHandler("smz://public/removeCache", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                CacheUtil.clearAllCache(TestActivity.this);
                ToastUtil.shortToast(TestActivity.this, "缓存已清空");
            }
        });

        webView.registerHandler("smz://member/logout", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                SharedPreferenceUtil.clearAll(TestActivity.this);
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
                TestActivity.this.finish();
            }
        });
        webView.registerHandler("smz://public/openPhoneWebView", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");

                Log.e("TestActivity", data.toString());
                JSONObject jsonObject = null;
                String aa = "";
                try {
                    jsonObject = new JSONObject(data.toString());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    aa = jsonObject1.getString("url");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("TestActivity", aa.toString());

                Uri content_url = Uri.parse(aa);
                intent.setData(content_url);
                startActivity(intent);
                TestActivity.this.finish();
            }
        });
        webView.registerHandler("smz://navigation/style", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                Log.e("TestActivity", data.toString());
                JSONObject jsonObject = null;
                String aa = "";
                String right = "";
                try {
                    jsonObject = new JSONObject(data.toString());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    aa = jsonObject1.getString("naviTitle");
                    right = jsonObject1.getString("rightStyle");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("TestActivity", aa.toString() + right.toString());
                tvGunTitle.setText(aa);
                if (right == null || right.equals("")) {
                    return;
                }
                if (right.equals("unCollection")) {
                    callec.setImageResource(R.mipmap.shoucang);
                } else {
                    callec.setImageResource(R.mipmap.shoucang_1);
                }

            }
        });

        webView.registerHandler("smz://public/openNewWebView", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                Log.e("TestActivity", data.toString());
                JSONObject jsonObject = null;
                String aa = "";
                try {
                    jsonObject = new JSONObject(data.toString());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    aa = jsonObject1.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (aa.contains("dangjian/detail")) {
                    mId = aa.replace(ConstantUtil.BASE_H5 + "dangjian/detail?id=", "");
                }
                Log.e("TestActivity", aa + mId);
                TestActivity.loadUrl(TestActivity.this, aa, "", mId);
            }
        });

        webView.registerHandler("smz://public/back", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                Log.e("TestActivity  back", data.toString());
                if (webView.canGoBack()) {
                    webView.goBack();

                } else {
                    finish();
                }
            }
        });
        webView.registerHandler("smz://common/rightButtonAction", new WvWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WvWebView.WVJBResponseCallback<Object> callback) {
                Log.e("TestActivity  refresh", data.toString());
                webView.reLoadView();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void initData() {

    }

    private void shareContent(String title, String detail, String url, String image, String type) {
        UMWeb web = new UMWeb(url);//网址
        web.setTitle(title);//标题
        UMImage umImage;
        if (image == null || image.equals("")) {
            umImage = new UMImage(TestActivity.this, (R.mipmap.logo));
        } else {
            umImage = new UMImage(TestActivity.this, image);
        }

        web.setThumb(umImage);  //缩略图
        web.setDescription(detail);//描述

        if (type == null || type.equals("")) {
            new ShareAction(TestActivity.this).withMedia(web).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener).open();
        } else if (type.equals("1")) {//微信
            new ShareAction(TestActivity.this)
                    .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                    .withMedia(web)//分享内容
                    .setCallback(shareListener)//回调监听器
                    .share();
        } else if (type.equals("2")) {//朋友圈
            new ShareAction(TestActivity.this)
                    .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                    .withMedia(web)//分享内容
                    .setCallback(shareListener)//回调监听器
                    .share();
        } else if (type.equals("4")) {//qq
            new ShareAction(TestActivity.this)
                    .setPlatform(SHARE_MEDIA.QQ)//传入平台
                    .withMedia(web)//分享内容
                    .setCallback(shareListener)//回调监听器
                    .share();
        } else if (type.equals("0")) {//微博
            new ShareAction(TestActivity.this)
                    .setPlatform(SHARE_MEDIA.SINA)//传入平台
                    .withMedia(web)//分享内容
                    .setCallback(shareListener)//回调监听器
                    .share();
        }


    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onResume() {
        super.onResume();
        token = SharedPreferenceUtil.getContent(this, "token", "").toString();
        LogUtil.E(isFirst + "");
        if (!isFirst) {
            refreshWebView();
        }
        isFirst = false;

        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(true);
        }
    }


    private void refreshWebView() {
        webView.callHandler(JS_REFRESH_PAGE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
    private VideoWebListener webListener = new VideoWebListener() {
        @Override
        public void showVideoFullView() {

        }

        @Override
        public void hindVideoFullView() {

        }

        @Override
        public void showWebView() {

        }

        @Override
        public void hindWebView() {

        }
    };
    private InterWebListener interWebListener = new InterWebListener() {
        @Override
        public void hindProgressBar() {
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void showErrorView(int type) {

        }


        @Override
        public void startProgress(int newProgress) {
            mProgressBar.setProgress(newProgress);
        }

        @Override
        public void showTitle(String title) {

        }
    };

    private void initTitle() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0);
        mProgressBar = findViewById(R.id.pb_progress);
        mProgressBar.setColor(ContextCompat.getColor(this, R.color.colorPrimary), ContextCompat.getColor(this, R.color.colorAccent));
        mProgressBar.show();
        webView.getX5WebChromeClient().setWebListener(interWebListener);
        webView.getX5WebChromeClient().setVideoWebListener(webListener);
        webView.getX5WebViewClient().setWebListener(interWebListener);

        webView = findViewById(R.id.webview_detail);
        mTitleToolBar = findViewById(R.id.title_tool_bar);
        tvGunTitle = findViewById(R.id.tv_gun_title);
        tvGunTitle.setText(mTitle);
        callec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callecs(mId);
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUrl.contains("ke.qq.com")) {
                    finish();
                }
                if (webView.canGoBack()) {
                    webView.goBack();
                    webView.reload();
                    Log.e("TestActivity", "reload click");
                } else {
                    finish();
                }
            }
        });
    }


    private void callecs(String id) {
        Log.e("callecs", id);
        if (token.equals("")) {
            startActivity(new Intent(TestActivity.this, LoginActivity.class));
            TestActivity.this.finish();
            return;
        }
        if (mUrl.contains("shijian")) {
            NetBuild.service().callecWenmingNews(token, String.valueOf(id)).enqueue(new BaseCallBack<CallectionBean>() {
                @Override
                public void success(BaseRes<CallectionBean> baseRes) {
                    callec.setImageResource(R.mipmap.shoucang_1);
                }

                @Override
                public void fail(String msg) {
                    ToastUtil.shortToast(TestActivity.this, msg);
                }
            });
        } else {
            NetBuild.service().callecNews(token, String.valueOf(id)).enqueue(new BaseCallBack<CallectionBean>() {
                @Override
                public void success(BaseRes<CallectionBean> baseRes) {
                    callec.setImageResource(R.mipmap.shoucang_1);
                }

                @Override
                public void fail(String msg) {
                    ToastUtil.shortToast(TestActivity.this, msg);
                }
            });
        }

    }

    private void getIntentData() {

        mUrl = getIntent().getStringExtra("mUrl");
        mId = getIntent().getStringExtra("mId");
        if (mUrl == null) {
            return;
        }
        if (!mUrl.contains("list")) {
            if (mUrl.contains("dangjian/detail") || mUrl.contains("?id=")) {
                callec.setVisibility(View.VISIBLE);
                if (mUrl.contains("help/detail") || mUrl.contains("huodong/detail")
                        || mUrl.contains("help/info") || mUrl.contains("jiceng/detail")
                        || mUrl.contains("banner/detail") || mUrl.contains("zuzhi/detail") || mUrl.contains("wenjuan/index")) {
                    callec.setVisibility(View.GONE);
                }
            } else {
                callec.setVisibility(View.GONE);
            }

        } else {
            callec.setVisibility(View.GONE);
        }
        if (mUrl.contains("news/detail?id=") && mUrl != null) {
            getNewsDetail();
        }
        LogUtil.E("TestActivity 读取的网站是" + mUrl);
        mTitle = getIntent().getStringExtra("mTitle");
        webView.loadUrl(mUrl);
    }

    public void getNewsDetail() {
        NetBuild.service().getNewsDetail(token, mId).enqueue(new BaseCallBack<NewsDetail>() {
            @Override
            public void success(BaseRes<NewsDetail> baseRes) {
                if (baseRes.result.getCollection() != 1) {
                    callec.setImageResource(R.mipmap.shoucang);
                } else {
                    callec.setImageResource(R.mipmap.shoucang_1);
                }
            }

            @Override
            public void fail(String msg) {

            }
        });
    }

    @Override
    public void onNetChange(int netMobile) {

    }

    /**
     * 打开网页:
     *
     * @param mContext 上下文
     * @param mUrl     要加载的网页url
     * @param mTitle   标题
     */
    public static void loadUrl(Context mContext, String mUrl, String mTitle, String id) {
        Intent intent = new Intent(mContext, TestActivity.class);
        intent.putExtra("mUrl", mUrl);
        intent.putExtra("mId", id);
        intent.putExtra("mTitle", mTitle == null ? "加载中..." : mTitle);
        mContext.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == X5WebChromeClient.FILE_CHOOSER_RESULT_CODE) {
            webView.getX5WebChromeClient().uploadMessage(data, resultCode);
        } else if (requestCode == X5WebChromeClient.FILE_CHOOSER_RESULT_CODE_5) {
            webView.getX5WebChromeClient().uploadMessageForAndroid5(data, resultCode);
        } else if (requestCode == 10010) {
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(TestActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(TestActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(TestActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };
}