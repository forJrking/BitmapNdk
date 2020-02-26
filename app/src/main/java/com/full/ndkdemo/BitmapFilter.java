package com.full.ndkdemo;

import android.graphics.Bitmap;

/**
 * @description:
 * @author: 岛主
 * @date: 2020/2/26 20:30
 * @version: 1.0.0
 */
public class BitmapFilter {

    public static native Bitmap gray(Bitmap bitmap);

    static {
        System.loadLibrary("native-lib");
    }

}
