package com.way.tunnelvision.ui.activity;

import android.os.Bundle;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.impl.WeatherModelImpl;
import com.way.tunnelvision.entity.model.WeatherModel;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.SystemUtil;

import java.util.List;

public class WeatherActivity extends BaseActivity {

    private WeatherModelImpl weatherModelImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initView();
        initData();
    }

    private void initView() {
    }

    private void initData() {
        if(!SystemUtil.isNetworkAvailable(WeatherActivity.this)) {

        } else {

        }
        weatherModelImpl.loadLocation(WeatherActivity.this, new WeatherModelImpl.LoadLocationListener() {
            @Override
            public void onSuccess(String cityName) {
                weatherModelImpl.loadWeatherData(cityName, new WeatherModelImpl.LoadWeatherListener() {
                    @Override
                    public void onSuccess(List<WeatherModel> list) {

                    }

                    @Override
                    public void onFailure(String msg, Exception e) {

                    }
                });
            }

            @Override
            public void onFailure(String msg, Exception e) {

            }
        });
    }
}
