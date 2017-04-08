package com.song.example.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.song.example.R;

public class ServiceTestActivity extends AppCompatActivity implements View.OnClickListener {
    static final String TAG = "LauncherActivity";

    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        IntentFilter filter = new IntentFilter();
        Log.e("remotebbbb","broadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcat");
        filter.addAction(Intent.ACTION_MEDIA_BUTTON);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("remoteaaa","broadcat");
            }
        },filter);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, MessengerActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent2 = new Intent(this, BinderActivity.class);
                startActivity(intent2);
                break;
        }
    }


}
