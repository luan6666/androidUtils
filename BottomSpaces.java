package com.smz.lexunuser.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class BottomSpaces extends RecyclerView.ItemDecoration {
    private int space;

    public BottomSpaces(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildPosition(view) == 0)
            outRect.bottom = space;
    }
}
