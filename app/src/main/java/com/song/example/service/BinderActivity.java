package com.song.example.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.song.example.R;

public class BinderActivity extends AppCompatActivity implements View.OnClickListener {
    static final String TAG = "MainActivity2";
    Button bt1;
    Button bt2;
    Button bt3;
    Button bt4;

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;

    IMyService iMyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_messenger_impl);

        Intent intent = new Intent();
        intent.setAction("com.aper.testapp.MyService2");
        intent.setPackage("com.aper.testapp");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        bt1 = (Button) findViewById(R.id.button);
        bt2 = (Button) findViewById(R.id.button2);
        bt3 = (Button) findViewById(R.id.button3);
        bt4 = (Button) findViewById(R.id.button4);

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        t3 = (TextView) findViewById(R.id.textView3);
        t4 = (TextView) findViewById(R.id.textView4);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                try {
                    Point current = iMyService.current();
                    t1.setText("当前点：" + current);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button2:
                try {
                    double d = iMyService.distance(new Point(9, 10));
                    t2.setText("当前点与(9,10)之间距离：" + d);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button3:
                try {
                    boolean isCoincide = iMyService.isCoincide(new Point(3, 4));
                    t3.setText("当前点是否与(3,4)重合：" + isCoincide);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button4:
                try {
                    double d1 = iMyService.distanceBetween(new Point(5, 6), new Point(12, 15));
                    t4.setText("(5,6)与(12,15)之间距离：" + d1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            iMyService = IMyService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyService = null;
        }
    };
}
