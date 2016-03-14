package com.way.tunnelvision.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.way.tunnelvision.entity.dao.DaoMaster;
import com.way.tunnelvision.entity.dao.DaoSession;
import com.way.tunnelvision.util.CrashHandler;

import java.io.File;

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

    private static String packageName = null;
    private static String externalStoragePath = null;
    private static String externalStorageFolder = null;
    private static String externalStoragePicFolder = null;

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

            packageName = getPackageName();
            externalStoragePath = getSDPath();
            externalStorageFolder = externalStoragePath + File.separator + packageName;
            externalStoragePicFolder = externalStorageFolder + File.separator + "Pictures";
            if (null != externalStoragePath) {
                createPackagePath(externalStorageFolder, externalStoragePicFolder);
            }
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

    /**
     * 在SD卡创建APP路径
     *
     * @return
     */
    private static String getSDPath() {
        String absolutePath = null;
        try {
            boolean sdCardExist = Environment.getExternalStorageState()
                    .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
            if (sdCardExist) {
                File sdDir = Environment.getExternalStorageDirectory();//获取跟目录
                absolutePath = sdDir.getAbsolutePath();
                Log.d(TAG, "getSDPath debug, Absolute Path = " + absolutePath);
            }
        } catch (Exception e) {
            Log.e(TAG, "getSDPath error", e);
            return null;
        }
        return absolutePath;
    }

    public static String getExternalStoragePath() {
        if (null == externalStoragePath) {
            externalStoragePath = getSDPath();
        }
        return externalStoragePath;
    }

    private static void createPackagePath(String packpath, String packPicPath) {
        try {
            File file = new File(packpath);
            if (!file.exists()) {
                file.mkdir();
                Log.d(TAG, "getSDPath debug, Path Not Exists");
            }

            File filePic = new File(packPicPath);
            if (!filePic.exists()) {
                filePic.mkdir();
                Log.d(TAG, "getSDPath debug, Pictures Path Not Exists");
            }
            Log.d(TAG, "getSDPath debug, Package Path = " + packpath);
            Log.d(TAG, "getSDPath debug, Package Pictures Path = " + packPicPath);
        } catch (Exception e) {
            Log.e(TAG, "createPackagePath error", e);
        }
    }

    public static String getExternalStorageFolder() {
        if (null == externalStorageFolder) {
            if (null == externalStoragePath) {
                externalStoragePath = getSDPath();
            }
            externalStorageFolder = externalStoragePath + File.separator + packageName;
        }
        return externalStorageFolder;
    }

    public static String getExternalStoragePicFolder() {
        if (null == externalStoragePicFolder) {
            if (null == externalStorageFolder) {
                externalStorageFolder = getExternalStorageFolder();
            }
            externalStoragePicFolder = externalStorageFolder + File.separator + "Pictures";
        }
        return externalStoragePicFolder;
    }
}
