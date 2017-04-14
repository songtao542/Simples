package com.song.example.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.song.example.LogTag;
import com.song.example.R;
import com.song.example.dagger.ActivityScope;
import com.song.example.dagger.ApplicationComponent;
import com.song.example.dagger.DaggerTestActivity;
import com.song.example.dagger.SimpleApplication;
import com.song.example.dagger.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Component;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class RxJavaTestActivity extends AppCompatActivity {


    @ActivityScope
    @Component(dependencies = ApplicationComponent.class, modules = ApiModule.class)
    interface ActivityComponent {
        void inject(RxJavaTestActivity activity);
    }

    @BindView(R.id.text)
    TextView mText;

    @Inject
    Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);
        ButterKnife.bind(this);

        ApplicationComponent appComponent = ((SimpleApplication) getApplication()).getComponent();
        DaggerRxJavaTestActivity_ActivityComponent.builder().applicationComponent(appComponent).build().inject(this);


        mApi.getUser("songtao542")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.d(LogTag.TAG, "doOnSubscribe thread:" + Thread.currentThread().getName());
                    }
                })
                .doOnNext(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        Log.d(LogTag.TAG, "doOnNext thread:" + Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(@NonNull User user) throws Exception {
                        Log.d(LogTag.TAG, "subscribe thread:" + Thread.currentThread().getName());
                        Log.d(LogTag.TAG, "User:" + user);
                        mText.setText("User:" + user);
                    }
                });
    }


}
