package com.song.example.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.song.example.R;

public class MessengerActivity extends AppCompatActivity implements View.OnClickListener {
    static final String TAG = "MessengerActivity";
    Messenger remoteMessenger;

    Button send1;
    Button send2;
    Button send3;

    TextView t1;
    TextView t2;
    TextView t3;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MessengerService.CMD_MESSAGE1:
                    Log.d(TAG, "收到message1回复");
                    t1.setText("来自Service的回复：" + ((Bundle) msg.obj).getString("reply"));
                    break;
                case MessengerService.CMD_MESSAGE2:
                    Log.d(TAG, "收到message2回复");
                    t2.setText("来自Service的回复：" + ((Bundle) msg.obj).getString("reply"));
                    break;
                case MessengerService.CMD_MESSAGE3:
                    Log.d(TAG, "收到message3回复");
                    t3.setText("来自Service的回复：" + ((Bundle) msg.obj).getString("reply"));
                    break;
            }
        }
    };

    Messenger messenger = new Messenger(handler);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_messenger_impl);

        Intent intent = new Intent();
        intent.setAction("com.song.example.service.MessengerService");
        intent.setPackage(getPackageName());
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        send1 = (Button) findViewById(R.id.button);
        send2 = (Button) findViewById(R.id.button2);
        send3 = (Button) findViewById(R.id.button3);

        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);
        t3 = (TextView) findViewById(R.id.textView3);

        send1.setOnClickListener(this);
        send2.setOnClickListener(this);
        send3.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick messenger=" + messenger);
        if (messenger == null) return;
        Message msg;
        switch (v.getId()) {
            case R.id.button:
                msg = Message.obtain();
                msg.what = MessengerService.CMD_MESSAGE1;
                msg.replyTo = messenger;
                try {
                    remoteMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button2:
                msg = Message.obtain();
                msg.what = MessengerService.CMD_MESSAGE2;
                msg.replyTo = messenger;
                try {
                    remoteMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button3:
                msg = Message.obtain();
                msg.what = MessengerService.CMD_MESSAGE3;
                msg.replyTo = messenger;
                try {
                    remoteMessenger.send(msg);
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
            remoteMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteMessenger = null;
        }
    };
}
