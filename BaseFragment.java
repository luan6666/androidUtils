package com.smz.lexunuser.base.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smz.lexunuser.ui.login.StartActivity;
import com.smz.lexunuser.util.SharedPreferenceUtil;

import butterknife.ButterKnife;

/**
 * @Author ldx
 * @CreateDate 2020/8/25 11:03
 */
public abstract class BaseFragment extends Fragment {
    private View mContentView;
    private Context mContext;

    public abstract void initData(Bundle savedInstanceState);

    public abstract void initView();

    public abstract void loadData();

    public abstract int getLayoutId();

    private ProgressDialog pd1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mContentView);
        mContext = getContext();
        initView();
        initData(savedInstanceState);
        loadData();
        return mContentView;

    }

    public void showLoading(String content) {
        if (pd1 == null) {
            pd1 = new ProgressDialog(requireContext());
            //依次设置标题,内容,是否用取消按钮关闭,是否显示进度
            pd1.setMessage(content);
            pd1.setCancelable(false);
            //这里是设置进度条的风格,HORIZONTAL是水平进度条,SPINNER是圆形进度条
            pd1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd1.setIndeterminate(true);
        }
        if (!pd1.isShowing()) {
            pd1.show();
        }

    }

    public void hideLoading() {
        if (pd1 != null) {
            pd1.dismiss();
            pd1.dismiss();
            pd1.dismiss();
            pd1.dismiss();
            pd1 = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    public String getToken() {
        String aa = SharedPreferenceUtil.getContent(requireContext(), "token", "").toString();
        if (aa.equals("")) {
            Intent intent = new Intent(requireContext(), StartActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
            return null;
        }
        return aa;
    }

    public View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;

    }


}
