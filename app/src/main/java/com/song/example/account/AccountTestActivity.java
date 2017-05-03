package com.song.example.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.song.example.LogTag;
import com.song.example.R;
import com.song.example.dagger.DaggerTestActivity;
import com.song.example.nestedscroll.NestedScrollTestActivity;
import com.song.example.okhttp.OkhttpTestActivity;
import com.song.example.provider.ContentProviderTestActivity;
import com.song.example.rxjava.RxJavaTestActivity;
import com.song.example.scroller.ScrollerTestActivity;
import com.song.example.service.ServiceTestActivity;
import com.song.example.tile.TileTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AccountTestActivity extends AppCompatActivity {
    public static final String ACCOUNT_TYPE = AccountConstants.ACCOUNT_TYPE;
    public static final String AUTHORITY = AccountConstants.AUTHORITY;

    @BindView(R.id.addAccount)
    Button mAddAccount;
    @BindView(R.id.confirimAccount)
    Button mConfirmAccount;
    @BindView(R.id.textview)
    TextView mTextView;

    AccountManager mAccountManager;
    Account[] accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_test);
        ButterKnife.bind(this);

        OnClickListener listener = new OnClickListener(this);
        mAddAccount.setOnClickListener(listener);
        mConfirmAccount.setOnClickListener(listener);

        mAccountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);

        accounts = mAccountManager.getAccountsByType(ACCOUNT_TYPE);

        for (Account ac : accounts) {
            mTextView.append(ac.name + ":" + ac.type + "\n");
        }

    }

    static class OnClickListener implements View.OnClickListener {

        AccountTestActivity context;

        public OnClickListener(AccountTestActivity context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addAccount:
                    Log.d(LogTag.TAG, "click addAccount button");
                    Bundle bundle = new Bundle();
                    bundle.putString("op1", "op1");
                    bundle.putInt("op2", 2);
                    bundle.putDouble("op3", 3.33);
                    context.mAccountManager.addAccount(ACCOUNT_TYPE, "auth_token_type", new String[]{"f1", "f2"}, bundle, context, null, null);
                    break;
                case R.id.confirimAccount:
                    Log.d(LogTag.TAG, "click confirimAccount button");
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("op1", "op1");
                    bundle1.putInt("op2", 2);
                    bundle1.putDouble("op3", 3.33);
                    context.mAccountManager.confirmCredentials(context.accounts[0], bundle1, context, null, null);
                    break;
            }
        }
    }
}
