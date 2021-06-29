package com.smz.lexunuser.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.smz.lexunuser.R;
import com.smz.lexunuser.ui.fragment_home.home.HomeBean;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

public class BannerAdapter extends BaseBannerAdapter<HomeBean.BannerListBean, BannerAdapter.NetViewHolder> {
    Activity activity;

    public BannerAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onBind(NetViewHolder holder, HomeBean.BannerListBean data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public NetViewHolder createViewHolder(@NonNull ViewGroup parent, View itemView, int viewType) {
        return new NetViewHolder(itemView);
    }


    @Override
    public int getLayoutId(int viewType) {
        return R.layout.banner_item;
    }

    class NetViewHolder extends BaseViewHolder<HomeBean.BannerListBean> {
        ImageView imageView;
        TextView textView;
        LinearLayout bannerBG;

        public NetViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = findView(R.id.banner_image);
        }

        @Override
        public void bindData(HomeBean.BannerListBean data, int position, int pageSize) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickListener.onItemClick(position);
                        }
                    });
                }
            });

            Glide.with(activity)
                    .load(ConstantUtil.OSS_URL + data.getImg_url())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                    .into(imageView);
        }

    }

    OnClickListener onClickListener;

    public void setOnItemClick(OnClickListener onItemClick) {
        this.onClickListener = onItemClick;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }
}
