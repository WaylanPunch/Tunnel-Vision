package com.way.tunnelvision.entity.impl;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.ImageModel;
import com.way.tunnelvision.util.ImageJsonUtil;
import com.way.tunnelvision.util.OkHttpUtil;

import java.util.List;

/**
 * Created by pc on 2016/3/12.
 */
public class ImageModelImpl {

    /**
     * 获取图片列表
     *
     * @param listener
     */
    public void loadImageList(final OnLoadImageListListener listener) {
        String url = Constants.NEWS.IMAGES_URL;
        OkHttpUtil.ResultCallback<String> loadNewsCallback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<ImageModel> imageModels = ImageJsonUtil.readJsonImageModels(response);
                listener.onSuccess(imageModels);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load image list failure.", e);
            }
        };
        OkHttpUtil.get(url, loadNewsCallback);
    }

    public interface OnLoadImageListListener {
        void onSuccess(List<ImageModel> list);

        void onFailure(String msg, Exception e);
    }
}
