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
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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

        Observable.fromArray(1, 2, 3, 4, 5)
                .compose(new MyTransformer())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        Log.d(LogTag.TAG, "map:" + integer);
                        return integer * 10;
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                        Log.d(LogTag.TAG, "flatMap:" + integer);
                        return Observable.fromArray(integer + "a", integer + "b", integer + "c", integer + "d");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.d(LogTag.TAG, "subscribe:" + s);
                    }
                });
    }

    static class MyTransformer implements ObservableTransformer<Integer, Integer> {


        @Override
        public ObservableSource<Integer> apply(@NonNull Observable<Integer> upstream) {
            return upstream
                    .map(new Function<Integer, String>() {
                        @Override
                        public String apply(@NonNull Integer integer) throws Exception {
                            Log.d(LogTag.TAG, "MyTransformer:map:1:" + integer);
                            return "" + 100 + integer;
                        }
                    }).flatMap(new Function<String, ObservableSource<String>>() {
                        @Override
                        public ObservableSource<String> apply(@NonNull String s) throws Exception {
                            Log.d(LogTag.TAG, "MyTransformer:flatMap:" + s);
                            return Observable.fromArray(s + "1", s + "2", s + "3", s + "4", s + "5", s + "6");
                        }
                    }).map(new Function<String, Integer>() {
                        @Override
                        public Integer apply(@NonNull String s) throws Exception {
                            Log.d(LogTag.TAG, "MyTransformer:map:2:" + s);
                            return Integer.parseInt(s);
                        }
                    });
        }
    }

}
