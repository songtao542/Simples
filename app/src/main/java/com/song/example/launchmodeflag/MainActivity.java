package com.song.example.launchmodeflag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.song.example.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.toA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AActivity.class);
                //intent.putExtra("SHOW", new int[]{R.id.toB});
                startActivity(intent);
            }
        });

        findViewById(R.id.toSingleInstance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModeSingleInstanceActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.toSingleTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ModeSingleTaskActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.toOtherTaskAffinity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OtherTaskAffinityActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("ABCD", "MainActivity onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ABCD", "MainActivity onDestroy");
    }
}
