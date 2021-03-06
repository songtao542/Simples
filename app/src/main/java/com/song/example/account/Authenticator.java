package com.song.example.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.song.example.LogTag;

/**
 * Created by le on 4/27/17.
 */

public class Authenticator extends AbstractAccountAuthenticator {
    private Context mContext;

    public Authenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
//        Bundle b = new Bundle();
//        b.putString(AccountManager.KEY_ACCOUNT_NAME, "songtao");
//        b.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
//        return b;

        Log.d(LogTag.TAG, "addAccount() !!!");
        Log.d(LogTag.TAG, "accountType=" + accountType);
        Log.d(LogTag.TAG, "authTokenType=" + authTokenType);
        if (requiredFeatures != null) {
            for (int i = 0; i < requiredFeatures.length; i++) {
                Log.d(LogTag.TAG, "requiredFeatures[" + i + "]=" + requiredFeatures[i]);
            }
        }
        Log.d(LogTag.TAG, "options=" + options);
        Log.d(LogTag.TAG, "addAccount() !!!");
        final Intent intent = new Intent(mContext, AddAccountTestActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;

    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        Log.d(LogTag.TAG, "confirmCredentials() !!!");
        Log.d(LogTag.TAG, "account=" + account);
        Log.d(LogTag.TAG, "options=" + options);
        Log.d(LogTag.TAG, "confirmCredentials() !!!");
        final Intent intent = new Intent(mContext, ConfirmCredentialsTestActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, account.name);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, account.type);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
