package com.way.tunnelvision.util;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.way.tunnelvision.entity.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/3/12.
 */
public class ImageJsonUtil {
    private final static String TAG = ImageJsonUtil.class.getName();

    /**
     * 将获取到的json转换为图片列表对象
     *
     * @param res
     * @return
     */
    public static List<ImageModel> readJsonImageModels(String res) {
        List<ImageModel> imageModels = new ArrayList<ImageModel>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(res).getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                ImageModel news = JsonUtil.deserialize(jo, ImageModel.class);
                imageModels.add(news);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "readJsonImageModels error", e);
        }
        return imageModels;
    }

}
