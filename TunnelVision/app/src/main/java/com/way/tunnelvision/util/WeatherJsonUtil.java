package com.way.tunnelvision.util;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.model.WeatherModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/3/18.
 */
public class WeatherJsonUtil {
    /**
     * 从定位的json字串中获取城市
     * @param json
     * @return
     */
    public static String getCity(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        JsonElement status = jsonObj.get("status");
        if(status != null && "OK".equals(status.getAsString())) {
            JsonObject result = jsonObj.getAsJsonObject("result");
            if(result != null) {
                JsonObject addressComponent = result.getAsJsonObject("addressComponent");
                if(addressComponent != null) {
                    JsonElement cityElement = addressComponent.get("city");
                    if(cityElement != null) {
                        return cityElement.getAsString().replace("市", "");
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取天气信息
     * @param json
     * @return
     */
    public static List<WeatherModel> getWeatherInfo(String json) {
        List<WeatherModel> list = new ArrayList<WeatherModel>();
        if (TextUtils.isEmpty(json)) {
            return list;
        }
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        String status = jsonObj.get("status").getAsString();
        if("1000".equals(status)) {
            JsonArray jsonArray = jsonObj.getAsJsonObject("data").getAsJsonArray("forecast");
            for (int i = 0; i < jsonArray.size(); i++) {
                WeatherModel weatherModel = getWeatherModelFromJson(jsonArray.get(i).getAsJsonObject());
                list.add(weatherModel);
            }
        }
        return list;
    }

    private static WeatherModel getWeatherModelFromJson(JsonObject jsonObject) {

        String temperature = jsonObject.get("high").getAsString() + " " + jsonObject.get("low").getAsString();
        String weather = jsonObject.get("type").getAsString();
        String wind = jsonObject.get("fengxiang").getAsString();
        String date = jsonObject.get("date").getAsString();

        WeatherModel weatherModel = new WeatherModel();

        weatherModel.setDate(date);
        weatherModel.setTemperature(temperature);
        weatherModel.setWeather(weather);
        weatherModel.setWeek(date.substring(date.length()-3));
        weatherModel.setWind(wind);
        weatherModel.setImageRes(getWeatherImage(weather));
        weatherModel.setBackgroundImageRes(getWeatherBackgroundImage(weather));
        return weatherModel;
    }

    public static int getWeatherImage(String weather) {
        if (weather.equals("多云") || weather.equals("多云转阴") || weather.equals("多云转晴")) {
            return R.drawable.biz_plugin_weather_duoyun;
        } else if (weather.equals("中雨") || weather.equals("中到大雨")) {
            return R.drawable.biz_plugin_weather_zhongyu;
        } else if (weather.equals("雷阵雨")) {
            return R.drawable.biz_plugin_weather_leizhenyu;
        } else if (weather.equals("阵雨") || weather.equals("阵雨转多云")) {
            return R.drawable.biz_plugin_weather_zhenyu;
        } else if (weather.equals("暴雪")) {
            return R.drawable.biz_plugin_weather_baoxue;
        } else if (weather.equals("暴雨")) {
            return R.drawable.biz_plugin_weather_baoyu;
        } else if (weather.equals("大暴雨")) {
            return R.drawable.biz_plugin_weather_dabaoyu;
        } else if (weather.equals("大雪")) {
            return R.drawable.biz_plugin_weather_daxue;
        } else if (weather.equals("大雨") || weather.equals("大雨转中雨")) {
            return R.drawable.biz_plugin_weather_dayu;
        } else if (weather.equals("雷阵雨冰雹")) {
            return R.drawable.biz_plugin_weather_leizhenyubingbao;
        } else if (weather.equals("晴")) {
            return R.drawable.biz_plugin_weather_qing;
        } else if (weather.equals("沙尘暴")) {
            return R.drawable.biz_plugin_weather_shachenbao;
        } else if (weather.equals("特大暴雨")) {
            return R.drawable.biz_plugin_weather_tedabaoyu;
        } else if (weather.equals("雾") || weather.equals("雾霾")) {
            return R.drawable.biz_plugin_weather_wu;
        } else if (weather.equals("小雪")) {
            return R.drawable.biz_plugin_weather_xiaoxue;
        } else if (weather.equals("小雨")) {
            return R.drawable.biz_plugin_weather_xiaoyu;
        } else if (weather.equals("阴")) {
            return R.drawable.biz_plugin_weather_yin;
        } else if (weather.equals("雨夹雪")) {
            return R.drawable.biz_plugin_weather_yujiaxue;
        } else if (weather.equals("阵雪")) {
            return R.drawable.biz_plugin_weather_zhenxue;
        } else if (weather.equals("中雪")) {
            return R.drawable.biz_plugin_weather_zhongxue;
        } else {
            return R.drawable.biz_plugin_weather_duoyun;
        }
    }

    public static int getWeatherBackgroundImage(String weather) {
        if (weather.equals("多云") || weather.equals("多云转阴") || weather.equals("多云转晴")) {
            return R.drawable.bg_weather_duoyun;
        } else if (weather.equals("中雨") || weather.equals("中到大雨")) {
            return R.drawable.bg_weather_zhongyu;
        } else if (weather.equals("雷阵雨")) {
            return R.drawable.bg_weather_leizhenyu;
        } else if (weather.equals("阵雨") || weather.equals("阵雨转多云")) {
            return R.drawable.bg_weather_zhenyu;
        } else if (weather.equals("暴雪")) {
            return R.drawable.bg_weather_baoxue;
        } else if (weather.equals("暴雨")) {
            return R.drawable.bg_weather_baoyu;
        } else if (weather.equals("大暴雨")) {
            return R.drawable.bg_weather_baoyu;
        } else if (weather.equals("大雪")) {
            return R.drawable.bg_weather_daxue;
        } else if (weather.equals("大雨") || weather.equals("大雨转中雨")) {
            return R.drawable.bg_weather_baoyu;
        } else if (weather.equals("雷阵雨冰雹")) {
            return R.drawable.bg_weather_yujiaxue;
        } else if (weather.equals("晴")) {
            return R.drawable.bg_weather_qing;
        } else if (weather.equals("沙尘暴")) {
            return R.drawable.bg_weather_shachenbao;
        } else if (weather.equals("特大暴雨")) {
            return R.drawable.bg_weather_baoyu;
        } else if (weather.equals("雾") || weather.equals("雾霾")) {
            return R.drawable.bg_weather_wu;
        } else if (weather.equals("小雪")) {
            return R.drawable.bg_weather_daxue;
        } else if (weather.equals("小雨")) {
            return R.drawable.bg_weather_xiaoyu;
        } else if (weather.equals("阴")) {
            return R.drawable.bg_weather_yin;
        } else if (weather.equals("雨夹雪")) {
            return R.drawable.bg_weather_yujiaxue;
        } else if (weather.equals("阵雪")) {
            return R.drawable.bg_weather_daxue;
        } else if (weather.equals("中雪")) {
            return R.drawable.bg_weather_baoxue;
        } else {
            return R.drawable.bg_weather_qing;
        }
    }
}
