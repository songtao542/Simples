package com.song.example.account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by le on 4/27/17.
 */

public class SyncService extends Service {

    private SyncAdapter mSyncAdapter;

    private SyncAdapter getSyncAdapter() {
        if (mSyncAdapter == null) {
            mSyncAdapter = new SyncAdapter(this, true);
        }
        return mSyncAdapter;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return getSyncAdapter().getSyncAdapterBinder();
    }
}
