package com.way.tunnelvision.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.way.tunnelvision.R;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;

import kll.dod.rtk.st.SplashView;
import kll.dod.rtk.st.SpotDialogListener;
import kll.dod.rtk.st.SpotManager;

public class AdDisplayActivity extends BaseActivity {
    private final static String TAG = AdDisplayActivity.class.getName();

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_display);
        mContext = this;

        //设置开屏广告
        setupSplashAd();
    }

    /**
     * 设置开屏广告
     */
    private void setupSplashAd() {
        /**
         * 自定义模式
         */
        SplashView splashView = new SplashView(this, null);
        // 设置是否显示倒计时，默认显示
        splashView.setShowReciprocal(true);
        // 设置是否显示关闭按钮，默认不显示
        splashView.hideCloseBtn(true);
        //传入跳转的intent，若传入intent，初始化时目标activity应传入null
        Intent intent = new Intent(this, MainActivity.class);
        splashView.setIntent(intent);
        //展示失败后是否直接跳转，默认直接跳转
        splashView.setIsJumpTargetWhenFail(true);
        //获取开屏视图
        View splash = splashView.getSplashView();

        final RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.rl_ad_display_layout);
        splashLayout.setVisibility(View.GONE);
        //添加开屏视图到布局中
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE, R.id.v_ad_display_logodivider);
        splashLayout.addView(splash, params);
        //显示开屏
        SpotManager.getInstance(mContext)
                .showSplashSpotAds(mContext, splashView, new SpotDialogListener() {

                    @Override
                    public void onShowSuccess() {
                        Log.i(TAG, "开屏展示成功");
                        splashLayout.setVisibility(View.VISIBLE);
                        splashLayout.startAnimation(AnimationUtils.loadAnimation(AdDisplayActivity.this, R.anim.ad_display_enter));
                    }

                    @Override
                    public void onShowFailed() {
                        Log.i(TAG, "开屏展示失败");
                    }

                    @Override
                    public void onSpotClosed() {
                        Log.i(TAG, "开屏被关闭");
                    }

                    @Override
                    public void onSpotClick(boolean isWebPath) {
                        Log.i(TAG, "开屏被点击");
                    }
                });

        /**
         * 默认模式
         */
        // SpotManager.getInstance(this).showSplashSpotAds(this,
        // MainActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
    }
}
