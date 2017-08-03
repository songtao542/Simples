package com.song.example.jni;

import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.song.example.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JNITestActivity extends AppCompatActivity {

    private static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";


    Lame mLame;

    public void progress(float progress) {
        Log.d("JNITestActivity", "currentThread = " + Thread.currentThread().getName());
        Log.d("JNITestActivity", "progress = " + progress);
    }

    @BindView(R.id.listView)
    ListView mListView;

    MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        ButterKnife.bind(this);
        Log.d("JNITestActivity", "ExternalStorageDirectory=" + Environment.getExternalStorageDirectory());
        mLame = new Lame();
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("LAME Version:" + mLame.version());

        if (PackageManager.PERMISSION_GRANTED == checkSelfPermission(WRITE_EXTERNAL_STORAGE)) {
            convertWavToMp3();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, 1232);
        }
    }

    private void convertWavToMp3() {
        convertWavToMp3("xiaohuang.wav", "xiaohuang.mp3", new ProgressListener() {
            @Override
            public void onProgress(final float progress) {
                Log.d("JNITestActivity", "xiaohuang progress = " + progress);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.update("xiaohuang.wav", progress);
                    }
                });
            }
        });
        mAdapter.add(new Progress("xiaohuang.wav", "xiaohuang.mp3"));
        convertWavToMp3("houchuang.wav", "houchuang.mp3", new ProgressListener() {
            @Override
            public void onProgress(final float progress) {
                Log.d("JNITestActivity", "houchuang progress = " + progress);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.update("houchuang.wav", progress);
                    }
                });
            }
        });
        mAdapter.add(new Progress("houchuang.wav", "houchuang.mp3"));
    }


    private void convertWavToMp3(final String wav, final String mp3, final ProgressListener listener) {
        new Thread() {
            @Override
            public void run() {
                File external = Environment.getExternalStorageDirectory();

                Log.d("JNITestActivity", "Thread = " + Thread.currentThread().getName());
//                File wav = new File(external, "xiaohuang.wav");
//                File mp3 = new File(external, "testmp3.mp3");
                File fwav = new File(external, wav);
                File fmp3 = new File(external, mp3);
                Log.d("JNITestActivity", "wav exist = " + fwav.exists());
                Log.d("JNITestActivity", "wav name = " + fwav.getName());

                if (fwav.exists()) {
                    if (!fmp3.exists()) {
                        try {
                            fmp3.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fmp3.exists()) {
                        mLame.wavToMp3(fwav.getAbsolutePath(), fmp3.getAbsolutePath(), listener);
                    }
                }
            }
        }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (WRITE_EXTERNAL_STORAGE.equals(permissions[i])) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    convertWavToMp3();
                }
            }
        }
    }

    private class Progress {
        public String name;
        public float progress;
        public String target;

        public Progress(String name, String target) {
            this.name = name;
            this.target = target;
        }
    }

    private class MyAdapter extends BaseAdapter {

        ArrayList<Progress> datas;
        HashMap<String, Progress> indexMap;

        public void add(Progress data) {
            if (datas == null) {
                datas = new ArrayList<>();
            }
            if (indexMap == null) {
                indexMap = new HashMap<>();
            }
            datas.add(data);
            indexMap.put(data.name, data);
            notifyDataSetChanged();
        }

        public void update(String name, float progress) {
            if (indexMap != null) {
                Progress p = indexMap.get(name);
                if (p != null) {
                    p.progress = progress;
                    notifyDataSetChanged();
                }
            }
        }

        @Override
        public int getCount() {
            if (datas != null) {
                return datas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (datas != null) {
                return datas.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_jni_list_item, parent, false);
                Holder h = new Holder(convertView);
                convertView.setTag(h);
            }
            Holder holder = (Holder) convertView.getTag();
            holder.setData(datas.get(position));
            return convertView;
        }


        class Holder {
            TextView textView;
            TextView progress;
            ProgressBar progressBar;

            public Holder(View view) {
                textView = (TextView) view.findViewById(R.id.textView);
                progress = (TextView) view.findViewById(R.id.progress);
                progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            }

            public void setData(Progress data) {
                textView.setText(data.name + "  -->  " + data.target);
                progressBar.setProgress((int) (data.progress * progressBar.getMax()));
                progress.setText(((int) (data.progress * 1000)) / 10f + "%");
            }
        }
    }

}
