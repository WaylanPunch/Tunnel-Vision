package com.way.tunnelvision.entity.impl;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.HeaderImageModel;
import com.way.tunnelvision.util.JsoupUtil;
import com.way.tunnelvision.util.OkHttpUtil;

import java.util.List;

/**
 * Created by pc on 2016/3/12.
 */
public class HeaderImageModelImpl {

    /**
     * 获取图片列表
     *
     * @param listener
     */
    public void loadHeaderImageList(final OnLoadHeaderImageListListener listener) {
        String url = Constants.NEWS.HEADER_IMAGE_SOURCE;
        OkHttpUtil.ResultCallback<String> loadHeaderImageCallback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<HeaderImageModel> headerImageModels = JsoupUtil.getHeaderImageItems(response);
                listener.onSuccess(headerImageModels);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load image list failure.", e);
            }
        };
        OkHttpUtil.get(url, loadHeaderImageCallback);
    }

    public interface OnLoadHeaderImageListListener {
        void onSuccess(List<HeaderImageModel> list);

        void onFailure(String msg, Exception e);
    }
}
