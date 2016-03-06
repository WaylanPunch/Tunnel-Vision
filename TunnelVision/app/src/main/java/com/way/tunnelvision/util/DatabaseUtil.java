package com.way.tunnelvision.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.entity.dao.ChannelDao;
import com.way.tunnelvision.entity.dao.DaoMaster;
import com.way.tunnelvision.entity.dao.DaoSession;

/**
 * Created by pc on 2016/2/25.
 */
public class DatabaseUtil {
    private final static String TAG = DatabaseUtil.class.getName();

    private DatabaseUtil() {
        throw new AssertionError();
    }


    public static void initDataBase(Context context) {
        Log.d(TAG, "initDataBase debug, start");
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        int currentVersion = PreferencesUtil.getInt(MainApp.getContext(), Constants.PREFERENCE_KEY_DATABASE_VERSION_CURRENT);
        if(currentVersion < DaoMaster.SCHEMA_VERSION_NEW) {
            helper.onUpgrade(db, DaoMaster.SCHEMA_VERSION, DaoMaster.SCHEMA_VERSION_NEW);
        }
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        ChannelDao channelDao = daoSession.getChannelDao();
        String orderColumn = ChannelDao.Properties.ChannelChosen.columnName;
        String orderBy = orderColumn + " COLLATE LOCALIZED ASC";
        Cursor cursor = db.query(channelDao.getTablename(), channelDao.getAllColumns(), null, null, null, null, orderBy);
        try {
            if (null != cursor) {
                Log.d(TAG, "initDataBase debug, Cursor Row Count = " + cursor.getCount());
            }

            if (null == cursor || 0 == cursor.getCount()) {
                channelDao.deleteAll();

                String headline_guid = Constants.NEWS.TOP_ID;
                String headline_title = Constants.NEWS.TOP_TITLE;
                String headline_name = Constants.NEWS.TOP_NAME;
                String headline_link = Constants.NEWS.TOP_URL;
                int headline_type = Constants.NEWS.NEWS_TYPE_TOP;
                int headline_chosen = 0;
                ChannelModel headline_channel = new ChannelModel(null, headline_guid, headline_title, headline_name, headline_link, headline_type, headline_chosen);
                channelDao.insert(headline_channel);

                String nba_guid = Constants.NEWS.NBA_ID;
                String nba_title = Constants.NEWS.NBA_TITLE;
                String nba_name = Constants.NEWS.NBA_NAME;
                String nba_link = Constants.NEWS.NBA_ID;
                int nba_type = Constants.NEWS.NEWS_TYPE_NBA;
                int nba_chosen = 1;
                ChannelModel nba_channel = new ChannelModel(null, nba_guid, nba_title, nba_name, nba_link, nba_type, nba_chosen);
                channelDao.insert(nba_channel);

                String car_guid = Constants.NEWS.CAR_ID;
                String car_title = Constants.NEWS.CAR_TITLE;
                String car_name = Constants.NEWS.CAR_NAME;
                String car_link = Constants.NEWS.CAR_ID;
                int car_type = Constants.NEWS.NEWS_TYPE_CARS;
                int car_chosen = 1;
                ChannelModel car_channel = new ChannelModel(null, car_guid, car_title, car_name, car_link, car_type, car_chosen);
                channelDao.insert(car_channel);

                String joke_guid = Constants.NEWS.JOKE_ID;
                String joke_title = Constants.NEWS.JOKE_TITLE;
                String joke_name = Constants.NEWS.JOKE_NAME;
                String joke_link = Constants.NEWS.JOKE_ID;
                int joke_type = Constants.NEWS.NEWS_TYPE_JOKES;
                int joke_chosen = 1;
                ChannelModel joke_channel = new ChannelModel(null, joke_guid, joke_title, joke_name, joke_link, joke_type, joke_chosen);
                channelDao.insert(joke_channel);
            }
        } catch (Exception e) {
            Log.e(TAG, "initDataBase error", e);
        } finally {
            if (null != cursor) {
                cursor.close();
            }
            if (null != daoSession) {
                daoSession.clear();
            }
            if (null != db) {
                db.close();
            }
        }
        Log.d(TAG, "initDataBase debug, end");
    }
}
