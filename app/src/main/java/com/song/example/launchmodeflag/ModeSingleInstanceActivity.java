package com.song.example.launchmodeflag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.song.example.R;

public class ModeSingleInstanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_single_instance);
        Log.d("ABCD", "ModeSingleInstanceActivity onCreate");
        Util.initVisibility(this, R.id.root);

        findViewById(R.id.toA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeSingleInstanceActivity.this, AActivity.class);
                intent.putExtra("SHOW", new int[]{R.id.toSingleInstance});
                startActivity(intent);
            }
        });

        findViewById(R.id.toSingleInstance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeSingleInstanceActivity.this, ModeSingleInstanceActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("ABCD", "ModeSingleInstanceActivity onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ABCD", "ModeSingleInstanceActivity onDestroy");
    }
}
