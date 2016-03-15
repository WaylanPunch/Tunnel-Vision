package com.way.tunnelvision.util;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.way.tunnelvision.entity.model.NewsDetailModel;
import com.way.tunnelvision.entity.model.NewsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/2/28.
 */
public class NewsJsonUtil {
    private final static String TAG = NewsJsonUtil.class.getName();

    /**
     * 将获取到的json转换为新闻列表对象
     * @param res
     * @param value
     * @return
     */
    public static List<NewsModel> readJsonNewsModels(String res, String value) {
        List<NewsModel> newsModels = new ArrayList<NewsModel>();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(value);
            if(jsonElement == null) {
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                if (jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) {
                    continue;
                }
                if (jo.has("TAGS") && !jo.has("TAG")) {
                    continue;
                }

                if (!jo.has("imgextra")) {
                    NewsModel news = JsonUtil.deserialize(jo, NewsModel.class);
                    newsModels.add(news);
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "readJsonNewsModels error" , e);
        }
        return newsModels;
    }

    public static NewsDetailModel readJsonNewsDetailModels(String res, String docId) {
        NewsDetailModel newsDetail = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if(jsonElement == null) {
                return null;
            }
            newsDetail = JsonUtil.deserialize(jsonElement.getAsJsonObject(), NewsDetailModel.class);
        } catch (Exception e) {
            LogUtil.e(TAG, "readJsonNewsDetailModels error" , e);
        }
        return newsDetail;
    }
}
