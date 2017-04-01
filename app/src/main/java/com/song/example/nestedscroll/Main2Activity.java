package com.song.example.nestedscroll;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;


import com.song.example.R;
import com.song.example.provider.ContentAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Main2Activity extends Activity {
    RecyclerView mContentList;

    ContentAdapter mContentAdapter;

    TabLayout mTabLayout;
    Toolbar mToolbar;
    boolean mHide = false;
    int mHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if (!mHide) {
                    if (mHeight == 0) {
                        mHeight = mTabLayout.getHeight();
                    }
                    animate(mHeight, 0);
                    mHide = true;
                } else {
                    animate(0, mHeight);
                    mHide = false;
                }
            }
        });


        mContentList = (RecyclerView) findViewById(R.id.contentList);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mContentList.setLayoutManager(lm);
        mContentAdapter = new ContentAdapter(this);
        mContentList.setAdapter(mContentAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
    }

    Method setHeaderTopBottomOffset = null;

    private void animate(int from, int to) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateTabLayout((Integer) animation.getAnimatedValue());
            }
        });

        final AppBarLayout appBarLayout = (AppBarLayout) mToolbar.getParent();
        final AppBarLayout.Behavior b = (AppBarLayout.Behavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
        int offset = appBarLayout.getTotalScrollRange();
        int coffset = b.getTopAndBottomOffset();

        Class bclass = b.getClass();
        //CoordinatorLayout parent, V header, int newOffset
        setHeaderTopBottomOffset = null;
        try {
            setHeaderTopBottomOffset = bclass.getSuperclass().getDeclaredMethod("setHeaderTopBottomOffset", CoordinatorLayout.class, View.class, int.class);
            setHeaderTopBottomOffset.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("FFFFFFFFFFFF", "coffset->" + coffset + " -- " + offset);

        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(coffset, 0);
        valueAnimator1.setDuration(200);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d("onAnimationUpdate", "value-->" + animation.getAnimatedValue());
//                try {
//                    setHeaderTopBottomOffset.invoke(b, (CoordinatorLayout) appBarLayout.getParent(), appBarLayout, (Integer) animation.getAnimatedValue());
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
                b.setTopAndBottomOffset((Integer) animation.getAnimatedValue());
            }
        });
        set.setDuration(200);
        set.playTogether(valueAnimator1, valueAnimator);
        set.start();
    }

    private void updateTabLayout(int height) {
        ViewGroup.LayoutParams lp = mTabLayout.getLayoutParams();
        lp.height = height;
        mTabLayout.setLayoutParams(lp);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<String> datas = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            datas.add("DATA" + i);
        }
        mContentAdapter.addAll(datas);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mContentAdapter.clear();
    }


}
