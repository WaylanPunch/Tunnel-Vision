package com.way.tunnelvision.entity.service;

import android.database.sqlite.SQLiteDatabase;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.entity.dao.DaoMaster;
import com.way.tunnelvision.entity.dao.DaoSession;

/**
 * Created by pc on 2016/3/7.
 */
public class THDatabaseLoader {
    //private static final String DATABASE_NAME = "PacPhotos-db";
    private static DaoSession daoSession;
    // 虚方法，可以无实体内容
    public static void init() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MainApp.getContext(), Constants.DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        if(null == daoSession) {
            init();
        }
        return daoSession;
    }
}
