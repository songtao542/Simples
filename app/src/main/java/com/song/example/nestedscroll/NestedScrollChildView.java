package com.song.example.nestedscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.song.example.LogTag;


/**
 * Created by le on 3/20/17.
 */

public class NestedScrollChildView extends LinearLayout implements NestedScrollingChild {

    NestedScrollingChildHelper mNSHelper;

    public NestedScrollChildView(Context context) {
        super(context);
    }

    public NestedScrollChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NestedScrollChildView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private NestedScrollingChildHelper getNSHelper() {
        if (mNSHelper == null) {
            mNSHelper = new NestedScrollingChildHelper(this);
            mNSHelper.setNestedScrollingEnabled(true);
        }
        return mNSHelper;
    }

    @Override
    public boolean startNestedScroll(int nestedScrollAxes) {
        boolean b = getNSHelper().startNestedScroll(nestedScrollAxes);
        Log.d( LogTag.TAG, "startNestedScroll:" + b + " axes:" + nestedScrollAxes);
        return b;
    }

    @Override
    public void stopNestedScroll() {
        getNSHelper().stopNestedScroll();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return getNSHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getNSHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getNSHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getNSHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    private float mLastMotionY = 0;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private float mNestedYOffset = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = event.getY();
                Log.d(LogTag.TAG, "onTouchEvent ACTION_DOWN Y:" + mLastMotionY);
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = (int) (mLastMotionY - event.getY());
                Log.d(LogTag.TAG, "onTouchEvent ACTION_MOVE deltaY:" + deltaY);
                if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                    deltaY -= mScrollConsumed[1];
                    event.offsetLocation(0, mScrollOffset[1]);
                    mNestedYOffset += mScrollOffset[1];
                }
                dispatchNestedScroll(0, 0, 0, deltaY, mScrollOffset);
                mLastMotionY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
        }
        return true;
    }
}
