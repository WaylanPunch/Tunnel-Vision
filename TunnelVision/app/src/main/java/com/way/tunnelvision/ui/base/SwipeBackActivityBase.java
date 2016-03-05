package com.way.tunnelvision.ui.base;

import com.way.tunnelvision.ui.widget.SwipeBackLayout;

/**
 * Created by pc on 2016/3/5.
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
}
