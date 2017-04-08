package com.song.example.nestedscroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.song.example.Example;
import com.song.example.R;

public class NestedScrollTestActivity extends AppCompatActivity {

    NestedScrollChildView mNestedScrollChildView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scroll_test);
        mNestedScrollChildView = (NestedScrollChildView) findViewById(R.id.nestedScrollChildView);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(Example.NAME);
    }
}
