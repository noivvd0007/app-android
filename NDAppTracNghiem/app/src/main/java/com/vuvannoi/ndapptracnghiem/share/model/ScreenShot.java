package com.vuvannoi.ndapptracnghiem.share.model;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Admin on 9/7/2017.
 */

public class ScreenShot {
    public static Bitmap takesscreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return b;

    }

    public static Bitmap takecreenshotOfRootView(View v){
        return takesscreenShot(v.getRootView());
    }

}
