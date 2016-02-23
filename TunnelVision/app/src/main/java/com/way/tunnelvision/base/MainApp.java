package com.way.tunnelvision.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.way.tunnelvision.util.CrashHandler;

import kll.dod.rtk.AdManager;

/**
 * Created by pc on 2016/1/13.
 */
public class MainApp extends Application {
    private final static String TAG = MainApp.class.getName();

    private Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate debug, start");
        try {
            mContext = getApplicationContext();
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(mContext);

            //腾讯Tencent Bugly
            CrashReport.initCrashReport(mContext, Constants.BUGLY_APPID, false);
            //YouMi广告初始化SDK
            AdManager.getInstance(mContext).init(Constants.YouMi_ID, Constants.YouMi_KEY);
        } catch (Exception e) {
            Log.e(TAG, "onCreate error", e);
        }
        Log.d(TAG, "onCreate debug, end");
    }
}
