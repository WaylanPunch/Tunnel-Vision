package com.way.tunnelvision.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.way.tunnelvision.R;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;
import com.way.tunnelvision.util.PermissionHelper;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

public class AdDisplayActivity extends BaseActivity {
    private final static String TAG = AdDisplayActivity.class.getName();

    private Context mContext;

    private PermissionHelper mPermissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //移除标题栏
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ad_display);
        mContext = this;

        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                Log.i(TAG, "All of requested permissions has been granted, so run app logic.");
                runApp();
            }
        });
        if (Build.VERSION.SDK_INT < 23) {
            //如果系统版本低于23，直接跑应用的逻辑
            Log.d(TAG, "The api level of system is lower than 23, so run app logic directly.");
            runApp();
        } else {
            //如果权限全部申请了，那就直接跑应用逻辑
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
                Log.d(TAG, "All of requested permissions has been granted, so run app logic directly.");
                runApp();
            } else {
                //如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
                Log.i(TAG, "Some of requested permissions hasn't been granted, so apply permissions first.");
                mPermissionHelper.applyPermissions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 跑应用的逻辑
     */
    private void runApp() {
        //初始化SDK
        AdManager.getInstance(mContext).init(Constants.YouMi_ID, Constants.YouMi_KEY);
        //设置开屏
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
        //		splashLayout.setVisibility(View.GONE);
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

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
