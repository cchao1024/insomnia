package com.cchao.insomnia.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author cchao
 * @version 2019-05-05.
 */
public class GridSpaceDividerDecoration extends RecyclerView.ItemDecoration {

    int mSpace;
    int mSpanCount;

    public GridSpaceDividerDecoration(int spacing, int spanCount) {
        this.mSpace = spacing;
        this.mSpanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mSpace;
        outRect.right = mSpace;
        outRect.top = 0;
        outRect.left = 0;
        if (parent.getChildAdapterPosition(view) < mSpanCount) {
            outRect.left = mSpace;
        }
    }
}