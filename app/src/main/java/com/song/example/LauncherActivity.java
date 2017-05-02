package com.song.example;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.song.example.account.AccountConstants;
import com.song.example.account.AccountTestActivity;
import com.song.example.dagger.DaggerTestActivity;
import com.song.example.nestedscroll.NestedScrollTestActivity;
import com.song.example.okhttp.OkhttpTestActivity;
import com.song.example.provider.ContentProviderTestActivity;
import com.song.example.rxjava.RxJavaTestActivity;
import com.song.example.scroller.ScrollerTestActivity;
import com.song.example.service.ServiceTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LauncherActivity extends AppCompatActivity {
    public static final String ACCOUNT_TYPE = AccountConstants.ACCOUNT_TYPE;
    public static final String AUTHORITY = AccountConstants.AUTHORITY;

    @BindView(R.id.dagger_test)
    Button daggerTest;
    @BindView(R.id.contentprovider_test)
    Button contentproviderTest;
    @BindView(R.id.scroller_test)
    Button scrollerTest;
    @BindView(R.id.nestedscroll_test)
    Button nestedscrollTest;
    @BindView(R.id.service_test)
    Button serviceTest;
    @BindView(R.id.rxjava_test)
    Button rxjavaTest;
    @BindView(R.id.account_test)
    Button accountTest;
    @BindView(R.id.okhttp_test)
    Button okhttpTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);
        OnClickListener onClickListener = new OnClickListener(this);

        daggerTest.setOnClickListener(onClickListener);
        contentproviderTest.setOnClickListener(onClickListener);
        scrollerTest.setOnClickListener(onClickListener);
        nestedscrollTest.setOnClickListener(onClickListener);
        serviceTest.setOnClickListener(onClickListener);
        rxjavaTest.setOnClickListener(onClickListener);
        okhttpTest.setOnClickListener(onClickListener);
        accountTest.setOnClickListener(onClickListener);
    }

    static class OnClickListener implements View.OnClickListener {

        Context context;

        public OnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.dagger_test:
                    intent = new Intent(context, DaggerTestActivity.class);
                    break;
                case R.id.contentprovider_test:
                    intent = new Intent(context, ContentProviderTestActivity.class);
                    break;
                case R.id.scroller_test:
                    intent = new Intent(context, ScrollerTestActivity.class);
                    break;
                case R.id.nestedscroll_test:
                    intent = new Intent(context, NestedScrollTestActivity.class);
                    break;
                case R.id.service_test:
                    intent = new Intent(context, ServiceTestActivity.class);
                    break;
                case R.id.rxjava_test:
                    intent = new Intent(context, RxJavaTestActivity.class);
                    break;
                case R.id.okhttp_test:
                    intent = new Intent(context, OkhttpTestActivity.class);
                    break;
                case R.id.account_test:
                    //intent = new Intent(context, AccountTestActivity.class);
                    Log.d(LogTag.TAG, "click account_test button");
                    AccountManager am = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("op1", "op1");
                    bundle.putInt("op2", 2);
                    bundle.putDouble("op3", 3.33);
                    am.addAccount(ACCOUNT_TYPE, "auth_token_type", new String[]{"f1", "f2"}, bundle, null, null, null);
                    break;
            }
            if (intent != null) {
                context.startActivity(intent);
            }
        }
    }
}
