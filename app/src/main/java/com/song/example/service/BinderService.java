package com.song.example.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.song.example.service.IMyService;

public class BinderService extends Service {

    Point current = new Point(3, 4);

    MyServiceInner serviceInner;

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        Log.e("remotebbbb","broadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcatbroadcat");
        filter.addAction(Intent.ACTION_MEDIA_BUTTON);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("remoteaaa","broadcat");
            }
        },filter);
    }


    @Override
    public IBinder onBind(Intent intent) {
        if (serviceInner == null) {
            serviceInner = new MyServiceInner();
        }
        return serviceInner;
    }



    private double distance(Point p1, Point p2) {
        int dx = (p1.x - p2.x);
        int dy = (p1.y - p2.y);
        return Math.sqrt(dx * dx + dy * dy);
    }

    class MyServiceInner extends IMyService.Stub {
        @Override
        public Point current() throws RemoteException {
            return current;
        }

        @Override
        public boolean isCoincide(Point point) throws RemoteException {
            return current.equals(point);
        }

        @Override
        public double distance(Point point) throws RemoteException {
            return BinderService.this.distance(current, point);
        }

        @Override
        public double distanceBetween(Point point, Point point2) throws RemoteException {
            return BinderService.this.distance(point, point2);
        }
    }
}
