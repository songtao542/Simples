package com.song.example.launchmodeflag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.song.example.R;

public class ModeSingleTopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_single_top);
        Log.d("ABCD","ModeSingleTopActivity onCreate");
        Util.initVisibility(this, R.id.root);
        findViewById(R.id.toSingleTop).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSingleTopActivity.this, ModeSingleTopActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.toB).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSingleTopActivity.this, BActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("ABCD","ModeSingleTopActivity onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ABCD", "ModeSingleTopActivity onDestroy");
    }
}
