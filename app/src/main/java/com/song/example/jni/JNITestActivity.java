package com.song.example.jni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.song.example.R;

public class JNITestActivity extends AppCompatActivity {

    static {
        System.loadLibrary("lame-lib");
    }

    public native String stringFromJNI();
    public native String wavToMp3(String wavPath,String mp3Path);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(stringFromJNI());
        textView.append("wtm:"+wavToMp3("/home/song/src/Test.wav","/home/song/src/Test.mp3"));
    }
}
