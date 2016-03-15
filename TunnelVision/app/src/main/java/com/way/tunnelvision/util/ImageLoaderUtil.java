package com.way.tunnelvision.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.way.tunnelvision.R;

/**
 * Created by pc on 2016/3/1.
 */
public class ImageLoaderUtil {
    private final static String TAG = ImageLoaderUtil.class.getName();

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
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
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail)
                .crossFade()
                .into(imageView);
    }

    public static void downloadImage(final String imageUrl, final OnDownloadImageListener listener) {
        //String url = Constants.NEWS.IMAGES_URL;

        OkHttpUtil.ResultCallback<byte[]> downloadCallback = new OkHttpUtil.ResultCallback<byte[]>() {
            @Override
            public void onSuccess(byte[] response) {
//                byte[] image = new byte[0];
//                image = response;
                LogUtil.d(TAG, "downloadImage debug, Result Length = " + response.length);
                Bitmap bitmap = BitmapFactory.decodeByteArray(response, 0, response.length);
                listener.onSuccess(bitmap);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("download image failure.", e);
            }
        };
        OkHttpUtil.getDownload(imageUrl, downloadCallback);
    }

    public interface OnDownloadImageListener {
        void onSuccess(Bitmap bitmap);

        void onFailure(String msg, Exception e);
    }
}
