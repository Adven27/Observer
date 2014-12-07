package com.tools;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.urban.data.Image;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by MetallFoX on 07.12.2014.
 */
public class ImageHelper {

    public static Drawable getDrawableFromImage(Image image) {
        if (image == null) {
            return null;
        }
        InputStream is = image.getAsStream();

        return getDrawableFromStream(is);
    }

    private static Drawable getDrawableFromStream(InputStream is) {
        try {
            return Drawable.createFromStream(is, "name");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.e("ImageHelper", "An error during close() operation on InputStream");
                }
            }
        }
    }

}
