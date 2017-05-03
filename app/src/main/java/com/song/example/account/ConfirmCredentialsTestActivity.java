package com.song.example.account;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.song.example.LogTag;
import com.song.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConfirmCredentialsTestActivity extends AccountAuthenticatorActivity {

    @BindView(R.id.textView1)
    TextView mUserName;
    @BindView(R.id.edittext)
    EditText mEditText;
    @BindView(R.id.button)
    Button mConfirm;

    private static final String PWD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_credentials_test);
        ButterKnife.bind(this);

        String username = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String type = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        Log.d(LogTag.TAG, "ConfirmCredentialsTestActivity user name:" + username + " type:" + type);
        mUserName.setText(username);
    }

    public void handleConfrim(View view) {
        Log.d(LogTag.TAG, "handleConfrim");
        String password = mEditText.getText().toString();
        if (PWD.equals(password)) {
            String username = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            String type = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);

            final Intent intent = new Intent();
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, type);
            intent.putExtra(AccountManager.KEY_BOOLEAN_RESULT, true);
            setAccountAuthenticatorResult(intent.getExtras());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
