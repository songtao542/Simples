package com.song.example.launchmodeflag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.song.example.R;

public class ModeSingleTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_single_task);
        Log.d("ABCD", "ModeSingleTaskActivity onCreate");
        Util.initVisibility(this, R.id.root);
        findViewById(R.id.toA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeSingleTaskActivity.this, AActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.toB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeSingleTaskActivity.this, BActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.toSingleTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeSingleTaskActivity.this, ModeSingleTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("ABCD", "ModeSingleTaskActivity onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ABCD", "ModeSingleTaskActivity onDestroy");
    }
}
