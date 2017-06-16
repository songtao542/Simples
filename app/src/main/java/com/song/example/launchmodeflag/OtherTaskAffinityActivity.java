package com.song.example.launchmodeflag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.song.example.R;

public class OtherTaskAffinityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_task_affinity);
        Log.d("ABCD", "OtherTaskAffinityActivity onCreate");
        Util.initVisibility(this, R.id.root);
        findViewById(R.id.toA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTaskAffinityActivity.this, AActivity.class);
                intent.putExtra("SHOW", new int[]{R.id.toB});
                startActivity(intent);
            }
        });

        findViewById(R.id.toB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTaskAffinityActivity.this, BActivity.class);
                intent.putExtra("SHOW", new int[]{R.id.toC});
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("ABCD", "OtherTaskAffinityActivity onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ABCD", "OtherTaskAffinityActivity onDestroy");
    }
}
