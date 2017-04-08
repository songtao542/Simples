package com.song.example.service;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by wangsongtao on 2017/1/30.
 */

public class RemoteKeyListener extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_BUTTON);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("remoteaaa","broadcat");
            }
        },filter);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        MediaSession session = new MediaSession(this, "Remote");
        session.setCallback(new MediaSession.Callback() {
            @Override
            public boolean onMediaButtonEvent(@NonNull Intent mediaButtonIntent) {

                return super.onMediaButtonEvent(mediaButtonIntent);
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                Log.d("RemoteService", "skip to next");
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                Log.d("RemoteService", "skip to previous");
            }
        });
//        session.setMediaButtonReceiver(PendingIntent.getBroadcast(this,123,new Intent("com.aperise.remote"),PendingIntent.FLAG_UPDATE_CURRENT));
        session.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        session.setActive(true);

        PlaybackState state = new PlaybackState.Builder()
                .setActions(
                        PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PLAY_PAUSE |
                                PlaybackState.ACTION_PLAY_FROM_MEDIA_ID | PlaybackState.ACTION_PAUSE |
                                PlaybackState.ACTION_SKIP_TO_NEXT | PlaybackState.ACTION_SKIP_TO_PREVIOUS)
                .setState(PlaybackState.STATE_NONE, PlaybackState.PLAYBACK_POSITION_UNKNOWN, 1, SystemClock.elapsedRealtime())
                .build();

        session.setPlaybackState(state);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        int key = event.getKeyCode();
        switch (key) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.d("RemoteKeyListener", "key volume down");
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.d("RemoteKeyListener", "key volume up");
                break;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                Log.d("RemoteKeyListener", "key volume next");
                break;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                Log.d("RemoteKeyListener", "key volume previous");
                break;
        }
        return super.onKeyEvent(event);
    }
}
