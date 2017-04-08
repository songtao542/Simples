package com.song.example.scroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.song.example.R;


public class ScrollerTestActivity extends AppCompatActivity {

    ScrollerExampleView mScrollerExampleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_test);
        mScrollerExampleView = (ScrollerExampleView) findViewById(R.id.scrollerExampleView);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollerExampleView.startScroll(10000);
            }
        });
    }
}
