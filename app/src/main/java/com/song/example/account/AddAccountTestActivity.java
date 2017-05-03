package com.song.example.account;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.song.example.LogTag;
import com.song.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddAccountTestActivity extends AccountAuthenticatorActivity {

    public static final String ACCOUNT_TYPE = AccountConstants.ACCOUNT_TYPE;
    public static final String AUTHORITY = AccountConstants.AUTHORITY;

    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_test);
        ButterKnife.bind(this);
    }


    public void handleAdd(View view) {

        String username = mUsername.getText().toString();

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "User name or password must be not empty", Toast.LENGTH_SHORT).show();
        } else {
            Log.i(LogTag.TAG, "handleLogin");
            AccountManager accountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
            final Account account = new Account(username, ACCOUNT_TYPE);
//            if (mRequestNewAccount) {
            accountManager.addAccountExplicitly(account, password, null);
//                // Set contacts sync for this account.
//                ContentResolver.setSyncAutomatically(account, ContactsContract.AUTHORITY, true);
//            } else {
            //accountManager.setPassword(account, password);
//            }

            ContentResolver.setIsSyncable(account, AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(account, AUTHORITY, true);

            final Intent intent = new Intent();
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
            setAccountAuthenticatorResult(intent.getExtras());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
