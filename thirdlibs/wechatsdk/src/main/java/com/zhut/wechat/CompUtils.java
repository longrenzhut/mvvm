package com.zhut.wechat;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class CompUtils {

    public static  Bitmap compressMatrix(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        InputStream isBm = new ByteArrayInputStream(baos .toByteArray());

        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        Bitmap mSrcBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        bm = null;

        return mSrcBitmap;
    }

}
