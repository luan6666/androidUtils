package com.smz.lexunuser.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 为RecycleView 设置间隔
 */
public class TopItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public TopItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.top = space;
    }
}