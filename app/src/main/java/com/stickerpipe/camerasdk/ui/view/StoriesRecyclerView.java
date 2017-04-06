package com.stickerpipe.camerasdk.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author Dmitry Nezhydenko (dehimb@gmail.com)
 */

public class StoriesRecyclerView extends RecyclerView {
    public StoriesRecyclerView(Context context) {
        super(context);
    }

    public StoriesRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StoriesRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        velocityX *= 0.2;
        return super.fling(velocityX, velocityY);
    }
}
