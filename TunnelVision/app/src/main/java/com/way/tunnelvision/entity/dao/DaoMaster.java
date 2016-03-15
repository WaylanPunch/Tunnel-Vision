package com.way.tunnelvision.entity.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.util.LogUtil;
import com.way.tunnelvision.util.PreferencesUtil;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

/**
 * Created by pc on 2016/2/15.
 * Master of DAO (schema version 1000): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    private final static String TAG = DaoMaster.class.getName();

    public DaoMaster(SQLiteDatabase db) {
        super(db, Constants.SCHEMA_VERSION);
        registerDaoClass(MenuDao.class);
        registerDaoClass(ChannelDao.class);
        registerDaoClass(NewsDao.class);
        registerDaoClass(NewsDetailDao.class);
    }

    /**
     * Creates underlying database table using DAOs.
     */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        MenuDao.createTable(db, ifNotExists);
        ChannelDao.createTable(db, ifNotExists);
        NewsDao.createTable(db, ifNotExists);
        NewsDetailDao.createTable(db, ifNotExists);
    }

    /**
     * Drops underlying database table using DAOs.
     */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        MenuDao.dropTable(db, ifExists);
        ChannelDao.dropTable(db, ifExists);
        NewsDao.dropTable(db, ifExists);
        NewsDetailDao.dropTable(db, ifExists);
    }

    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, Constants.SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            LogUtil.i(TAG, "Creating tables for schema version " + Constants.SCHEMA_VERSION);
            createAllTables(db, false);
            PreferencesUtil.putInt(MainApp.getContext(), Constants.PREFERENCE_KEY_DATABASE_VERSION_CURRENT, Constants.SCHEMA_VERSION);
        }
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion) {
                LogUtil.i(TAG, "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
                dropAllTables(db, true);
                onCreate(db);
                PreferencesUtil.putInt(MainApp.getContext(), Constants.PREFERENCE_KEY_DATABASE_VERSION_CURRENT, newVersion);
            }
        }
    }

    @Override
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    @Override
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
}
