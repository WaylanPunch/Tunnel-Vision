package com.way.tunnelvision.entity.impl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.text.TextUtils;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.WeatherModel;
import com.way.tunnelvision.util.LogUtil;
import com.way.tunnelvision.util.OkHttpUtil;
import com.way.tunnelvision.util.WeatherJsonUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by pc on 2016/3/17.
 */
public class WeatherModelImpl {
    private final static String TAG = WeatherModelImpl.class.getName();


    public void loadWeatherData(String cityName, final LoadWeatherListener listener) {
        try {
            String url = Constants.NEWS.WEATHER + URLEncoder.encode(cityName, "utf-8");
            OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    List<WeatherModel> lists = WeatherJsonUtil.getWeatherInfo(response);
                    listener.onSuccess(lists);
                }

                @Override
                public void onFailure(Exception e) {
                    listener.onFailure("load weather data failure.", e);
                }
            };
            OkHttpUtil.get(url, callback);
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(TAG, "url encode error.", e);
        }
    }


    public void loadLocation(Context context, final LoadLocationListener listener) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                LogUtil.e(TAG, "location failure.");
                listener.onFailure("location failure.", null);
                return;
            }
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location == null) {
            LogUtil.e(TAG, "location failure.");
            listener.onFailure("location failure.", null);
            return;
        }
        double latitude = location.getLatitude();     //经度
        double longitude = location.getLongitude(); //纬度
        String url = getLocationURL(latitude, longitude);
        OkHttpUtil.ResultCallback<String> callback = new OkHttpUtil.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                String city = WeatherJsonUtil.getCity(response);
                if(TextUtils.isEmpty(city)) {
                    LogUtil.e(TAG, "load location info failure.");
                    listener.onFailure("load location info failure.", null);
                } else {
                    listener.onSuccess(city);
                }
            }

            @Override
            public void onFailure(Exception e) {
                LogUtil.e(TAG, "load location info failure.", e);
                listener.onFailure("load location info failure.", e);
            }
        };
        OkHttpUtil.get(url, callback);
    }

    private String getLocationURL(double latitude, double longitude) {
        StringBuffer sb = new StringBuffer(Constants.NEWS.INTERFACE_LOCATION);
        sb.append("?output=json").append("&referer=32D45CBEEC107315C553AD1131915D366EEF79B4");
        sb.append("&location=").append(latitude).append(",").append(longitude);
        LogUtil.d(TAG, sb.toString());
        return sb.toString();
    }


    public interface LoadWeatherListener {
        void onSuccess(List<WeatherModel> list);
        void onFailure(String msg, Exception e);
    }

    public interface LoadLocationListener {
        void onSuccess(String cityName);
        void onFailure(String msg, Exception e);
    }
}
