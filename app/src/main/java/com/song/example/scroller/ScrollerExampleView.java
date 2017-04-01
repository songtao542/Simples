package com.song.example.scroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Scroller;

import com.song.example.LogTag;

/**
 * Created by le on 3/21/17.
 */

public class ScrollerExampleView extends View {

    Scroller mScroller;
    Paint mPaint;

    public ScrollerExampleView(Context context) {
        super(context);
    }

    public ScrollerExampleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollerExampleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollerExampleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint getPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(0xff000000);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics()));
            mPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getContext().getResources().getDisplayMetrics()));
        }
        return mPaint;
    }

    public void startScroll(int dy) {
        Log.d(LogTag.TAG, "scrollY==" + getScrollY());
        getScroller().startScroll(0, 0, 0, getScroller().getFinalY() + dy);
        postInvalidate();
    }

    private Scroller getScroller() {
        if (mScroller == null) {
            mScroller = new Scroller(this.getContext());
        }
        return mScroller;
    }

    @Override
    public void computeScroll() {
        Scroller scroller = getScroller();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int scrollY = getScrollY();
        canvas.drawText("" + scrollY, 0, getHeight() / 2 + scrollY, getPaint());
        canvas.drawText("height:" + getHeight(), 0, getHeight() / 2 + scrollY + 100, getPaint());

        int y = scrollY;
        int c = scrollY / 550;
        int i = 0;
        while (y - scrollY < getHeight()) {
            y = c * 550 + i * 550;
            i++;
            getPaint().setColor(0xff000000);
            canvas.drawCircle(getWidth() / 2, y - 250, 100, getPaint());
            getPaint().setColor(0xffffffff);
            canvas.drawText("" + (c + i), getWidth() / 2 - 50, y - 250, getPaint());
        }
        computeScroll();
    }
}
