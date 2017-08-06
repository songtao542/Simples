package com.song.example;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.song.example.account.AccountTestActivity;
import com.song.example.camera.CameraTestActivity;
import com.song.example.dagger.DaggerTestActivity;
import com.song.example.jni.JNITestActivity;
import com.song.example.jni.PlayerActivity;
import com.song.example.nestedscroll.NestedScrollTestActivity;
import com.song.example.okhttp.OkhttpTestActivity;
import com.song.example.provider.ContentProviderTestActivity;
import com.song.example.rxjava.RxJavaTestActivity;
import com.song.example.scroller.ScrollerTestActivity;
import com.song.example.service.ServiceTestActivity;
import com.song.example.tile.TileTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LauncherActivity extends AppCompatActivity {

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
    @BindView(R.id.tile_test)
    Button tileTest;
    @BindView(R.id.camera_test)
    Button cameraTest;
    @BindView(R.id.jni_test)
    Button jniTest;
    @BindView(R.id.jni_player)
    Button jniPlayer;

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
        tileTest.setOnClickListener(onClickListener);
        accountTest.setOnClickListener(onClickListener);
        cameraTest.setOnClickListener(onClickListener);
        jniTest.setOnClickListener(onClickListener);
        jniPlayer.setOnClickListener(onClickListener);
    }

    static class OnClickListener implements View.OnClickListener {

        Activity context;

        public OnClickListener(Activity context) {
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
                case R.id.tile_test:
                    intent = new Intent(context, TileTestActivity.class);
                    break;
                case R.id.account_test:
                    intent = new Intent(context, AccountTestActivity.class);
                    break;
                case R.id.camera_test:
                    intent = new Intent(context, CameraTestActivity.class);
                    break;
                case R.id.jni_test:
                    intent = new Intent(context, JNITestActivity.class);
                    break;
                    case R.id.jni_player:
                    intent = new Intent(context, PlayerActivity.class);
                    break;
            }
            if (intent != null) {
                context.startActivity(intent);
            }
        }
    }
}
