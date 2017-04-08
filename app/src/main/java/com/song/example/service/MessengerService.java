package com.song.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wangsongtao on 2016/12/25.
 */

public class MessengerService extends Service {

    static final String TAG = "MyService";

    public static final int CMD_MESSAGE1 = 1;
    public static final int CMD_MESSAGE2 = 2;
    public static final int CMD_MESSAGE3 = 3;


    int count = 0;
    int count2 = 0;
    int count3 = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage");
            Messenger replyTo;
            switch (msg.what) {
                case CMD_MESSAGE1:
                    Log.d(TAG, "收到message1");
                    replyTo = msg.replyTo;
                    if (replyTo != null) {
                        Message reply = Message.obtain();
                        try {
                            reply.what = CMD_MESSAGE1;
                            Bundle data = new Bundle();
                            data.putString("reply", "来自Service的数字：" + (count++));
                            reply.obj = data;
                            replyTo.send(reply);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CMD_MESSAGE2:
                    Log.d(TAG, "收到message2");
                    replyTo = msg.replyTo;
                    if (replyTo != null) {
                        Message reply = Message.obtain();
                        try {
                            reply.what = CMD_MESSAGE2;
                            Bundle data = new Bundle();
                            data.putString("reply", "来自Service的数字：" + (count2++));
                            reply.obj = data;
                            replyTo.send(reply);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CMD_MESSAGE3:
                    Log.d(TAG, "收到message3");
                    replyTo = msg.replyTo;
                    if (replyTo != null) {
                        Message reply = Message.obtain();
                        try {
                            reply.what = CMD_MESSAGE3;
                            Bundle data = new Bundle();
                            data.putString("reply", "来自Service的数字：" + (count3++));
                            reply.obj = data;
                            replyTo.send(reply);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(handler).getBinder();
    }
}
