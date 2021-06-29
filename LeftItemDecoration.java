package com.smz.lexunuser.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class LeftItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public LeftItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.left = space;
    }
}