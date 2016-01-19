package com.way.tunnelvision.base;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.way.tunnelvision.util.CrashHandler;

/**
 * Created by pc on 2016/1/13.
 */
public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        CrashReport.initCrashReport(getApplicationContext(), Constants.BUGLY_APPID, false);
    }

}
