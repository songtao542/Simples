package com.song.example.account;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TestAccountService extends Service {
    Authenticator mAuthenticator;

    public TestAccountService() {
    }

    private Authenticator getAuthenticator() {
        if (mAuthenticator == null) {
            mAuthenticator = new Authenticator(this);
        }
        return mAuthenticator;
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (AccountManager.ACTION_AUTHENTICATOR_INTENT.equals(intent.getAction())) {
            return getAuthenticator().getIBinder();
        } else {
            return null;
        }
    }
}
