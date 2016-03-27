package com.way.tunnelvision.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.model.ImageModel;

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


    public static void downloadImageToStorage(ImageModel imageModel, final Context context){
        if (null != imageModel) {
            String imageUrl = imageModel.getSourceurl();
            String imageThumbUrl = imageModel.getThumburl();
            LogUtil.d(TAG, "downloadImageToStorage debug, Image Source Url = " + imageUrl);
            LogUtil.d(TAG, "downloadImageToStorage debug, Image Thumb Url = " + imageThumbUrl);
            int lastIndexSlash = 0;
            if (imageThumbUrl.contains("/")) {
                lastIndexSlash = imageThumbUrl.lastIndexOf("/");
            } else {
                if (imageThumbUrl.contains("\\")) {
                    lastIndexSlash = imageThumbUrl.lastIndexOf("\\");
                }
            }
            final String fileName = imageThumbUrl.substring(lastIndexSlash + 1, imageThumbUrl.length());
            int lastIndexDot = 0;
            lastIndexDot = fileName.lastIndexOf(".");
            String fileExtension = fileName.substring(lastIndexDot, fileName.length());
            LogUtil.d(TAG, "downloadImageToStorage debug, Image Name = " + fileName);
            LogUtil.d(TAG, "downloadImageToStorage debug, Image Format = " + fileExtension);

            ImageLoaderUtil.downloadImage(imageUrl, new ImageLoaderUtil.OnDownloadImageListener() {

                @Override
                public void onSuccess(Bitmap bitmap) {
                    if (null != bitmap) {
                        LogUtil.d(TAG, "downloadImageToStorage debug, bitmap != NULL");
                        String imageFullPath = ImageUtil.saveBitmapToExternalStorage(bitmap, fileName);
                        //saveBitmap(bitmap);
                        Toast.makeText(context, "下载成功，保存到" + imageFullPath, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(String msg, Exception e) {
                    LogUtil.e(TAG, "downloadImageToStorage error", e);
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
