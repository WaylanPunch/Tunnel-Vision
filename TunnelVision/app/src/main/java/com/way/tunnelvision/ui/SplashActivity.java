package com.way.tunnelvision.ui;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.way.tunnelvision.R;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;
import com.way.tunnelvision.util.Constants;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

public class SplashActivity extends BaseActivity {
    private final static String TAG = SplashActivity.class.getName();

    SplashView youmi_SplashView;
    Context context;
    View youmi_AD_Splash;
    RelativeLayout youmi_AD_Container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate debug, start");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = this;

        initYouMi();
        setContentView(R.layout.activity_splash);
        initView();
        Log.d(TAG, "onCreate debug, end");
    }

    private void initYouMi() {
        Log.d(TAG, "initYouMi debug, start");
        try {
            // 初始化接口，应用启动的时候调用
            // 参数：appId, appSecret, 调试模式
            AdManager.getInstance(context).init(Constants.YouMi_AppId, Constants.YouMi_AppSecret, true);

            // 第二个参数传入目标activity，或者传入null，改为setIntent传入跳转的intent
            youmi_SplashView = new SplashView(context, null);
            // 设置是否显示倒数
            youmi_SplashView.setShowReciprocal(true);
            // 隐藏关闭按钮
            youmi_SplashView.hideCloseBtn(false);//设置为False

            Intent intent = new Intent(context, SigninActivity.class);
            youmi_SplashView.setIntent(intent);
            youmi_SplashView.setIsJumpTargetWhenFail(true);

            youmi_AD_Splash = youmi_SplashView.getSplashView();
            if(null == youmi_AD_Splash){
                Log.d(TAG, "initYouMi debug, SplashView == null");
            }
        } catch (Exception e) {
            Log.e(TAG, "initYouMi error", e);
        }
        Log.d(TAG, "initYouMi debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try {
            youmi_AD_Container = ((RelativeLayout) findViewById(R.id.rl_splash_ad_container));
            youmi_AD_Container.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -1);
            //params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            //params.addRule(RelativeLayout.ABOVE, R.id.v_splash_ad_bottom);
            youmi_AD_Container.addView(youmi_AD_Splash, params);

            SpotManager.getInstance(context).showSplashSpotAds(context, youmi_SplashView,
                    new SpotDialogListener() {

                        @Override
                        public void onShowSuccess() {
                            youmi_AD_Container.setVisibility(View.VISIBLE);
                            youmi_AD_Container.startAnimation(AnimationUtils.loadAnimation(context, R.anim.pic_enter_anim_alpha));
                            Log.d(TAG, "initView debug, YouMi AD 展示成功");
                        }

                        @Override
                        public void onShowFailed() {
                            Log.d(TAG, "initView debug, YouMi AD 展示失败");
                        }

                        @Override
                        public void onSpotClosed() {
                            Log.d(TAG, "initView debug, YouMi AD 展示关闭");
                        }

                        @Override
                        public void onSpotClick(boolean isWebPath) {
                            Log.d(TAG, "initView debug, YouMi AD 插屏点击");
                        }
                    });

            // 2.简单调用方式
            // 如果没有特殊要求，简单使用此句即可实现插屏的展示
            // SpotManager.getInstance(context).showSplashSpotAds(context, MainActivity.class);
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
    }

    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // land
        } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // port
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //listData = null;
        //ActivityCollector.finishAll();
    }
}
