package com.song.example.camera;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.song.example.LogTag;
import com.song.example.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraTestActivity extends AppCompatActivity {

    @BindView(R.id.open1)
    Button open1;
    @BindView(R.id.open2)
    Button open2;

    Handler mHandler1;
    Handler mHandler2;
    CameraManager mCameraManager;
    String mCameraId;

    boolean mEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        ButterKnife.bind(this);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        String cameraId = null;
        try {
            cameraId = getCameraId();
        } catch (Throwable e) {
            Log.e(LogTag.TAG, "Couldn't initialize.", e);
            return;
        } finally {
            mCameraId = cameraId;
        }

        if (mCameraId != null) {
            ensureHandler();
            mCameraManager.registerTorchCallback(mTorchCallback1, null);
            mCameraManager.registerTorchCallback(mTorchCallback2, mHandler2);
        }

        open1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setTorchOn(!mEnabled);
                mEnabled = !mEnabled;
            }
        });
        open2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setTorchOn(!mEnabled);
                mEnabled = !mEnabled;
            }
        });
    }

    private void setTorchOn(boolean enabled) {
        try {
            Log.i(LogTag.TAG, "set torch " + enabled + " to mCameraId = " +
                    (mCameraId != null ? mCameraId : " null"));
            mCameraManager.setTorchMode(mCameraId, enabled);
        } catch (CameraAccessException e) {
            Log.e(LogTag.TAG, "Couldn't set torch mode", e);
        } catch (IllegalArgumentException e) { // add for debugging RUBY-19921
            // Check whether the cameraId has changed since we initialized it.
            // If it has changed, we may add a mechanism here to update the cameraId.
            String cameraId = null;
            try {
                cameraId = getCameraId();
            } catch (Throwable ex) {
                Log.e(LogTag.TAG, "Couldn't get cameraId. ", ex);
            }
            Log.e(LogTag.TAG, "error setting torch, enabled=" + enabled
                    + ", mCameraId=" + (mCameraId != null ? mCameraId : "null")
                    + ", new cameraId=" + (cameraId != null ? cameraId : "null"));
        }
    }

    private String getCameraId() throws CameraAccessException {
        String[] ids = mCameraManager.getCameraIdList();
        for (String id : ids) {
            CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
            Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
            if (flashAvailable != null && flashAvailable
                    && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                return id;
            }
        }
        return null;
    }

    private synchronized void ensureHandler() {
        if (mHandler1 == null) {
//            HandlerThread thread = new HandlerThread(LogTag.TAG + "1", Process.THREAD_PRIORITY_BACKGROUND);
//            thread.start();
//            mHandler1 = new Handler(thread.getLooper());
            mHandler1 = new Handler();
        }
        if (mHandler2 == null) {
            HandlerThread thread = new HandlerThread(LogTag.TAG + "2", Process.THREAD_PRIORITY_BACKGROUND);
            thread.start();
            mHandler2 = new Handler(thread.getLooper());
        }
    }

    private final CameraManager.TorchCallback mTorchCallback1 = new CameraManager.TorchCallback() {

        @Override
        public void onTorchModeUnavailable(String cameraId) {
            Log.d(LogTag.TAG, "1 onTorchModeUnavailable cameraId=" + cameraId);
        }

        @Override
        public void onTorchModeChanged(String cameraId, boolean enabled) {
            Log.d(LogTag.TAG, "1 onTorchModeChanged cameraId=" + cameraId + " enabled=" + enabled);
        }
    };

    private final CameraManager.TorchCallback mTorchCallback2 = new CameraManager.TorchCallback() {

        @Override
        public void onTorchModeUnavailable(String cameraId) {
            Log.d(LogTag.TAG, "2 onTorchModeUnavailable cameraId=" + cameraId);
        }

        @Override
        public void onTorchModeChanged(String cameraId, boolean enabled) {
            Log.d(LogTag.TAG, "2 onTorchModeChanged cameraId=" + cameraId + " enabled=" + enabled);
        }
    };
}
