package com.song.example.jni;

/**
 * Created by song on 17-8-3.
 */

public class Lame {
    static {
        System.loadLibrary("lame-lib");
    }

    public native String version();

    public native void wavToMp3(String wavPath, String mp3Path);

    public native void wavToMp3(String wavPath, String mp3Path, ProgressListener listener);
}
