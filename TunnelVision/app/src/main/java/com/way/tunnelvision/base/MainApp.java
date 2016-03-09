package com.way.tunnelvision.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.way.tunnelvision.entity.dao.DaoMaster;
import com.way.tunnelvision.entity.dao.DaoSession;
import com.way.tunnelvision.util.CrashHandler;

import kll.dod.rtk.AdManager;

/**
 * Created by pc on 2016/1/13.
 */
public class MainApp extends Application {
    private final static String TAG = MainApp.class.getName();

    private static Context mContext;
    private static DaoMaster.DevOpenHelper helper;
    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

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

    public static Context getContext() {
        return mContext;
    }

    public static void initGreenDao() {
        helper = new DaoMaster.DevOpenHelper(MainApp.getContext(), Constants.DATABASE_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        if (null == daoSession) {
            Log.d(TAG, "getDaoSession debug, Execute initGreenDao()");
            initGreenDao();
        }
        return daoSession;
    }

    public static DaoMaster.DevOpenHelper getDevOpenHelper() {
        if (null == helper) {
            Log.d(TAG, "getDevOpenHelper debug, Execute initGreenDao()");
            initGreenDao();
        }
        return helper;
    }
    public static SQLiteDatabase getSQLiteDatabase() {
        if (null == db) {
            Log.d(TAG, "getSQLiteDatabase debug, Execute initGreenDao()");
            initGreenDao();
        }
        return db;
    }

    public static DaoMaster getDaoMaster() {
        if (null == daoMaster) {
            Log.d(TAG, "getDaoMaster debug, Execute initGreenDao()");
            initGreenDao();
        }
        return daoMaster;
    }
}
