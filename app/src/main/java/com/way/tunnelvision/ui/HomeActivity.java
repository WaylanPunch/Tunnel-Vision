package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.weather.LocalDayWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.way.tunnelvision.R;
import com.way.tunnelvision.util.ToastUtil;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private final static String TAG = HomeActivity.class.getName();

    private WeatherSearchQuery mquery;
    private WeatherSearch mweathersearch;
    private LocalWeatherLive weatherlive;
    private LocalWeatherForecast weatherforecast;
    private List<LocalDayWeatherForecast> forecastlist = null;
    private String cityname = "北京市";//天气搜索的城市，可以写名称或adcode；
    private StringBuffer fullInfo = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate debug, start");
        initView();
        searchliveweather();
        searchforcastsweather();
        Log.d(TAG, "onCreate debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        final TextView tv_home_large_text = (TextView) findViewById(R.id.tv_home_large_text);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(fullInfo != null && !fullInfo.toString().equals("")){
                    tv_home_large_text.setText(fullInfo.toString());
                }
            }
        });
        Log.d(TAG, "initView debug, end");
    }

    private void searchforcastsweather() {
        Log.d(TAG, "searchforcastsweather debug, start");
        try {
            mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_FORECAST);//检索参数为城市和天气类型，实时天气为1、天气预报为2
            mweathersearch = new WeatherSearch(this);
            mweathersearch.setOnWeatherSearchListener(onWeatherSearchListener);
            mweathersearch.setQuery(mquery);
            mweathersearch.searchWeatherAsyn(); //异步搜索
        } catch (Exception e) {
            Log.e(TAG, "searchforcastsweather error", e);
        }
        Log.d(TAG, "searchforcastsweather debug, end");
    }

    private void searchliveweather() {
        Log.d(TAG, "searchliveweather debug, start");
        try {
            mquery = new WeatherSearchQuery(cityname, WeatherSearchQuery.WEATHER_TYPE_LIVE);//检索参数为城市和天气类型，实时天气为1、天气预报为2
            mweathersearch = new WeatherSearch(this);
            mweathersearch.setOnWeatherSearchListener(onWeatherSearchListener);
            mweathersearch.setQuery(mquery);
            mweathersearch.searchWeatherAsyn(); //异步搜索
        } catch (Exception e) {
            Log.e(TAG, "searchliveweather error", e);
        }
        Log.d(TAG, "searchliveweather debug, end");
    }

    WeatherSearch.OnWeatherSearchListener onWeatherSearchListener = new WeatherSearch.OnWeatherSearchListener() {
        /**
         * 实时天气查询回调
         */
        @Override
        public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
            Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherLiveSearched debug, start");
            Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherLiveSearched debug, Return Code = " + rCode);
            if (rCode == 0) {
                if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                    Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherLiveSearched debug, LocalWeatherLiveResult != null");
                    weatherlive = weatherLiveResult.getLiveResult();
                    fullInfo.append(weatherlive.getReportTime() + "发布");
                    fullInfo.append(weatherlive.getWeather());
                    fullInfo.append(weatherlive.getTemperature() + "°");
                    fullInfo.append(weatherlive.getWindDirection() + "风     " + weatherlive.getWindPower() + "级");
                    fullInfo.append("湿度         " + weatherlive.getHumidity() + "%");
                } else {
                    Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherLiveSearched debug, LocalWeatherLiveResult == null");
                    ToastUtil.show(HomeActivity.this, R.string.no_result);
                }
            } else {
                ToastUtil.showerror(HomeActivity.this, rCode);
            }
            Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherLiveSearched debug, end");
        }

        /**
         * 天气预报查询结果回调
         * */
        @Override
        public void onWeatherForecastSearched(LocalWeatherForecastResult weatherForecastResult, int rCode) {
            Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherForecastSearched debug, start");
            Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherForecastSearched debug, Return Code = " + rCode);
            if (rCode == 0) {
                if (weatherForecastResult != null && weatherForecastResult.getForecastResult() != null
                        && weatherForecastResult.getForecastResult().getWeatherForecast() != null
                        && weatherForecastResult.getForecastResult().getWeatherForecast().size() > 0) {
                    Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherForecastSearched debug, LocalWeatherForecastResult != null");
                    weatherforecast = weatherForecastResult.getForecastResult();
                    forecastlist = weatherforecast.getWeatherForecast();
                    fillforecast();

                } else {
                    Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherForecastSearched debug, LocalWeatherForecastResult == null");
                    ToastUtil.show(HomeActivity.this, R.string.no_result);
                }
            } else {
                ToastUtil.showerror(HomeActivity.this, rCode);
            }
            Log.d(TAG, "onWeatherSearchListener OnWeatherSearchListener onWeatherForecastSearched debug, end");
        }
    };

    private void fillforecast() {
        fullInfo.append(weatherforecast.getReportTime() + "发布");
        String forecast = "";
        for (int i = 0; i < forecastlist.size(); i++) {
            LocalDayWeatherForecast localdayweatherforecast = forecastlist.get(i);
            String week = null;
            switch (Integer.valueOf(localdayweatherforecast.getWeek())) {
                case 1:
                    week = "周一";
                    break;
                case 2:
                    week = "周二";
                    break;
                case 3:
                    week = "周三";
                    break;
                case 4:
                    week = "周四";
                    break;
                case 5:
                    week = "周五";
                    break;
                case 6:
                    week = "周六";
                    break;
                case 7:
                    week = "周日";
                    break;
                default:
                    break;
            }
            String temp = String.format("%-3s/%3s",
                    localdayweatherforecast.getDayTemp() + "°",
                    localdayweatherforecast.getNightTemp() + "°");
            String date = localdayweatherforecast.getDate();
            forecast += date + "  " + week + "                       " + temp + "\n\n";
        }
        fullInfo.append(forecast);
    }
}
