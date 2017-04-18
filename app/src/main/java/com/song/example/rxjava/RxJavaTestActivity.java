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

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Component;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
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
    CompositeDisposable mCompositeDisposable;

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
                        Log.d(LogTag.TAG, "doOnNext user:" + user);
                        user.name = "wang.song.tao";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(LogTag.TAG, "subscribe thread:" + Thread.currentThread().getName());
                        Log.d(LogTag.TAG, "User:" + user);
                        mText.setText("User:" + user);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        Observable.fromArray(1, 2, 3, 4, 5)
//                .compose(new MyTransformer())
//                .map(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(@NonNull Integer integer) throws Exception {
//                        Log.d(LogTag.TAG, "map:" + integer);
//                        return integer * 10;
//                    }
//                })
//                .flatMap(new Function<Integer, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
//                        Log.d(LogTag.TAG, "flatMap:" + integer);
//                        return Observable.fromArray(integer + "a", integer + "b", integer + "c", integer + "d");
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        Log.d(LogTag.TAG, "subscribe:" + s);
//                    }
//                });

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public String toString() {
                return "hash:" + hashCode();
            }

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d(LogTag.TAG, "ObservableOnSubscribe-subscribe:" + e);
                e.setDisposable(new Disposable() {
                    boolean dispose = false;

                    @Override
                    public void dispose() {
                        dispose = true;
                        Log.d(LogTag.TAG, "ObservableEmitter-dispose:" + this);
                    }

                    @Override
                    public boolean isDisposed() {
                        return dispose;
                    }

                    @Override
                    public String toString() {
                        return "hash:" + hashCode();
                    }
                });
                e.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        Log.d(LogTag.TAG, "ObservableEmitter-cancel:" + this);
                    }

                    @Override
                    public String toString() {
                        return "hash:" + hashCode();
                    }
                });
                //Thread.sleep(10000);
                e.onNext(11);
                e.onNext(22);
                e.onNext(33);
                e.onNext(44);
                e.onComplete();
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(LogTag.TAG, "subscribe onSubscribe:" + d.getClass().getName());
                        //d.dispose();
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(LogTag.TAG, "subscribe onNext:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LogTag.TAG, "subscribe onError:" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(LogTag.TAG, "subscribe onComplete:");
                    }
                });


        Flowable
                .create(new FlowableOnSubscribe<Integer>() {

                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
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
