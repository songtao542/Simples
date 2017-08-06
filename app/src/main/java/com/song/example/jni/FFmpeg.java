package com.song.example.jni;

import android.view.Surface;
import android.view.SurfaceHolder;

/**
 * Created by wangsongtao on 2017/8/5.
 */

public class FFmpeg {

    static {
        System.loadLibrary("ffmpeg-lib");
    }

    public native int play(String file,Surface  surface);

}
