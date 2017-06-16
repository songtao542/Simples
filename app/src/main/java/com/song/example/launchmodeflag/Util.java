package com.song.example.launchmodeflag;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by le on 6/16/17.
 */

public class Util {

    public static void initVisibility(Activity activity, int rootId) {
        try {
            int[] shows = activity.getIntent().getIntArrayExtra("SHOW");
            if (shows != null && shows.length > 0) {
                ViewGroup parent = (ViewGroup) activity.findViewById(rootId);
                if (parent != null) {
                    int childCount = parent.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        parent.getChildAt(i).setVisibility(View.GONE);
                    }

                    for (int id : shows) {
                        parent.findViewById(id).setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
