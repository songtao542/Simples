package com.song.example.launchmodeflag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.song.example.R;

public class CActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        Log.d("ABCD", "CActivity onCreate");
        Util.initVisibility(this, R.id.root);
        findViewById(R.id.toSingleTop).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CActivity.this, ModeSingleTopActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.toSingleTask).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CActivity.this, ModeSingleTaskActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.toD).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CActivity.this, DActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.toD1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CActivity.this, DActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });
        findViewById(R.id.toOA1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.other.app.OA");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        findViewById(R.id.toOA2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.other.app.OA");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("ABCD", "CActivity onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ABCD", "CActivity onDestroy");
    }
}
