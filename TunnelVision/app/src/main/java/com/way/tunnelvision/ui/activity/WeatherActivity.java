package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.impl.WeatherModelImpl;
import com.way.tunnelvision.entity.model.WeatherModel;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.LogUtil;
import com.way.tunnelvision.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends BaseActivity {
    private final static String TAG = WeatherActivity.class.getName();

    private TextView mTodayTV;
    private ImageView mTodayWeatherImage;
    private TextView mTodayTemperatureTV;
    private TextView mTodayWindTV;
    private TextView mTodayWeatherTV;
    private TextView mCityTV;
    private ProgressBar mProgressBar;
    private LinearLayout mWeatherLayout;
    private LinearLayout mWeatherContentLayout;
    private FrameLayout mRootLayout;

    private WeatherModelImpl weatherModelImpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_weather_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mTodayTV = (TextView) findViewById(R.id.tv_weather_today);
        mTodayWeatherImage = (ImageView) findViewById(R.id.iv_weather_image);
        mTodayTemperatureTV = (TextView) findViewById(R.id.tv_weather_temp);
        mTodayWindTV = (TextView) findViewById(R.id.tv_weather_wind);
        mTodayWeatherTV = (TextView) findViewById(R.id.tv_weather_weather);
        mCityTV = (TextView) findViewById(R.id.tv_weather_city);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_weather_progress);
        mWeatherLayout = (LinearLayout) findViewById(R.id.ll_weather_layout);
        mWeatherContentLayout = (LinearLayout) findViewById(R.id.ll_weather_content);
        mRootLayout = (FrameLayout) findViewById(R.id.fl_weather_root);

        mRootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mRootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, "initView debug, getSystemUiVisibility = " + mRootLayout.getSystemUiVisibility());
                if (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION == mRootLayout.getSystemUiVisibility()) {
                    LogUtil.d(TAG, "initView debug, getSystemUiVisibility == View.SYSTEM_UI_FLAG_HIDE_NAVIGATION");
                } else {
                    LogUtil.d(TAG, "initView debug, getSystemUiVisibility != View.SYSTEM_UI_FLAG_HIDE_NAVIGATION");
                    mRootLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                }
            }
        });
    }

    private void initData() {
        try {
            if (null == weatherModelImpl) {
                weatherModelImpl = new WeatherModelImpl();
            }
            mProgressBar.setVisibility(View.VISIBLE);
            if (SystemUtil.isNetworkAvailable(WeatherActivity.this)) {
                weatherModelImpl.loadLocation(WeatherActivity.this, new WeatherModelImpl.LoadLocationListener() {
                    @Override
                    public void onSuccess(String cityName) {
                        mCityTV.setText(cityName);
                        weatherModelImpl.loadWeatherData(cityName, new WeatherModelImpl.LoadWeatherListener() {
                            @Override
                            public void onSuccess(List<WeatherModel> list) {
                                setWeatherData(list);
                                mProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(String msg, Exception e) {
                                LogUtil.e(TAG, "initData error, LoadLocationListener LoadWeatherListener, " + msg, e);
                                Toast.makeText(WeatherActivity.this, "获取天气数据失败", Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onFailure(String msg, Exception e) {
                        LogUtil.e(TAG, "initData error, LoadLocationListener , " + msg, e);
                        Toast.makeText(WeatherActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                        mCityTV.setText("杭州");
                        weatherModelImpl.loadWeatherData("杭州", new WeatherModelImpl.LoadWeatherListener() {
                            @Override
                            public void onSuccess(List<WeatherModel> list) {
                                setWeatherData(list);
                                mProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(String msg, Exception e) {
                                LogUtil.e(TAG, "initData error, LoadLocationListener LoadWeatherListener, " + msg, e);
                                Toast.makeText(WeatherActivity.this, "获取天气数据失败", Toast.LENGTH_SHORT).show();
                                mProgressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                });
            } else {
                Toast.makeText(WeatherActivity.this, "无网络连接", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "initData error", e);
        }
    }

    private void setWeatherData(List<WeatherModel> list) {
        if (list != null && list.size() > 0) {
            LogUtil.d(TAG, "setWeatherData debug, WeatherModel COUNT = " + list.size());
            WeatherModel todayWeather = list.remove(0);
            mTodayTV.setText(todayWeather.getDate());
            mTodayTemperatureTV.setText(todayWeather.getTemperature());
            mTodayWeatherTV.setText(todayWeather.getWeather());
            mTodayWindTV.setText(todayWeather.getWind());
            mTodayWeatherImage.setImageResource(todayWeather.getImageRes());
            mRootLayout.setBackgroundResource(todayWeather.getBackgroundImageRes());
        }
        if (mWeatherContentLayout.getChildCount() > 0) {
            //mWeatherContentLayout.removeAllViews();
            mWeatherContentLayout.removeAllViewsInLayout();
        }
        List<View> adapterList = new ArrayList<View>();
        for (WeatherModel weatherModel : list) {
            View view = LayoutInflater.from(this).inflate(R.layout.weather_item_card, null, false);
            LinearLayout rootLayout = (LinearLayout) view.findViewById(R.id.ll_weather_item_root);
            TextView dateTV = (TextView) view.findViewById(R.id.tv_weather_item_date);
            ImageView todayWeatherImage = (ImageView) view.findViewById(R.id.iv_weather_item_image);
            TextView todayTemperatureTV = (TextView) view.findViewById(R.id.tv_weather_item_temp);
            TextView todayWindTV = (TextView) view.findViewById(R.id.tv_weather_item_wind);
            TextView todayWeatherTV = (TextView) view.findViewById(R.id.tv_weather_item_weather);

            //rootLayout.setBackgroundResource(weatherModel.getBackgroundImageRes());
            dateTV.setText(weatherModel.getWeek());
            todayTemperatureTV.setText(weatherModel.getTemperature());
            todayWindTV.setText(weatherModel.getWind());
            todayWeatherTV.setText(weatherModel.getWeather());
            todayWeatherImage.setImageResource(weatherModel.getImageRes());
            mWeatherContentLayout.addView(view);
            adapterList.add(view);
        }
        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_weather_refresh) {
            try {
                initData();
            } catch (Exception e) {
                LogUtil.e(TAG, "onOptionsItemSelected error, action_weather_refresh", e);
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
