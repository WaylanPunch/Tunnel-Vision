package com.way.tunnelvision.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.way.tunnelvision.R;

/**
 * Created by pc on 2016/3/1.
 */
public class ImageLoaderUtil {
    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(error)
                .crossFade()
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail)
                .crossFade()
                .into(imageView);
    }
}
