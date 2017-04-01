package com.song.example.nestedscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.support.v4.view.NestedScrollingParent;

import com.song.example.LogTag;

/**
 * Created by le on 3/20/17.
 */

public class NestedScrollParentView extends LinearLayout implements NestedScrollingParent {

    NestedScrollingParentHelper mNSHelper;

    public NestedScrollParentView(Context context) {
        super(context);
    }

    public NestedScrollParentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollParentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NestedScrollParentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NestedScrollingParentHelper getNSHelper() {
        if (mNSHelper == null) {
            mNSHelper = new NestedScrollingParentHelper(this);
        }
        return mNSHelper;
    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
        getNSHelper().onNestedScrollAccepted(child, target, axes);
        Log.d(LogTag.TAG, "Parent onNestedScrollAccepted(" + child.getClass().getSimpleName() + "," + target.getClass().getSimpleName() + "," + axes + ")");
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
        Log.d(LogTag.TAG, "Parent onStartNestedScroll(" + child.getClass().getSimpleName() + "," + target.getClass().getSimpleName() + "," + nestedScrollAxes + ")");
        return true;
    }

    @Override
    public void onStopNestedScroll(View child) {
        getNSHelper().onStopNestedScroll(child);
        Log.d(LogTag.TAG, "Parent onStopNestedScroll(" + child.getClass().getSimpleName() + ")");
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(LogTag.TAG, "Parent onNestedScroll(" + target.getClass().getSimpleName() + "," + dxConsumed + "," + dyConsumed + "," + dxUnconsumed + "," + dyUnconsumed + ")");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.d(LogTag.TAG, "Parent onNestedPreScroll(" + target.getClass().getSimpleName() + "," + dx + "," + dy + "," + "{" + consumed[0] + "," + consumed[1] + "})");
        if (dy < 0) {
            View first = getChildAt(0);
            int consumedY = 0;
            Log.d(LogTag.TAG, "Parent onNestedPreScroll first.getTop()=" + first.getTop());
            int unVisiableH = -first.getTop();
            if (unVisiableH > 0) {
                if (-dy > unVisiableH) {
                    consumedY = unVisiableH;
                } else {
                    consumedY = -dy;
                }

                Log.d(LogTag.TAG, "Parent onNestedPreScroll consumedY=" + consumedY);
                first.offsetTopAndBottom(consumedY);
                //target.offsetTopAndBottom(consumedY);
            }
            consumed[1] = consumedY;
        } else {
            View first = getChildAt(0);
            int consumedY = 0;
            int visiableH = first.getHeight() + first.getTop();
            Log.d(LogTag.TAG, "Parent onNestedPreScroll unVisiableY=" + visiableH);
            if (visiableH > 0) {
                if (dy > visiableH) {
                    consumedY = visiableH;
                } else {
                    consumedY = dy;
                }
                Log.d(LogTag.TAG, "Parent onNestedPreScroll consumedY1=" + consumedY);
                first.offsetTopAndBottom(-consumedY);
                //target.offsetTopAndBottom(-consumedY);
            }
            consumed[1] = consumedY;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(LogTag.TAG, "Parent onNestedFling(" + target.getClass().getSimpleName() + "," + velocityX + "," + velocityY + "," + consumed + ")");
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.d(LogTag.TAG, "Parent onNestedPreFling(" + target.getClass().getSimpleName() + "," + velocityX + "," + velocityY + ")");
        return true;
    }
}
