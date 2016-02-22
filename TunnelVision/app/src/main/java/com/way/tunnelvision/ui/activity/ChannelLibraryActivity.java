package com.way.tunnelvision.ui.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.way.tunnelvision.R;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.model.ChannelModel;
import com.way.tunnelvision.model.dao.ChannelDao;
import com.way.tunnelvision.model.dao.DaoMaster;
import com.way.tunnelvision.model.dao.DaoSession;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;

public class ChannelLibraryActivity extends BaseActivity {
    private final static String TAG = ChannelLibraryActivity.class.getName();
    private int resultCode = 0;

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ChannelDao channelDao;
    private Cursor cursor;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_library);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_channel_library_toolbar);
        setSupportActionBar(toolbar);

        initDataBase();
        initView();
    }

    private void initDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        channelDao = daoSession.getChannelDao();
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab_channel_liabrary_initialize);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                initDataTableChannel();
            }


        });
    }

    private void initDataTableChannel() {
        Log.d(TAG, "initDataTableChannel debug, start");
        try {
//            String orderColumn = ChannelDao.Properties.ChannelChosen.columnName;
//            String orderBy = orderColumn + " COLLATE LOCALIZED ASC";
//            cursor = db.query(channelDao.getTablename(), channelDao.getAllColumns(), null, null, null, null, orderBy);
            channelDao.deleteAll();
            String headline_guid = Constants.NEWS.TOP_ID;
            String headline_title = Constants.NEWS.TOP_TITLE;
            String headline_name = Constants.NEWS.TOP_NAME;
            String headline_link = Constants.NEWS.TOP_URL;
            int headline_chosen = 0;
            ChannelModel headline_channel = new ChannelModel(null, headline_guid, headline_title, headline_name, headline_link, headline_chosen);
            channelDao.insert(headline_channel);

            String nba_guid = Constants.NEWS.NBA_ID;
            String nba_title = Constants.NEWS.NBA_TITLE;
            String nba_name = Constants.NEWS.NBA_NAME;
            String nba_link = Constants.NEWS.NBA_ID;
            int nba_chosen = 1;
            ChannelModel nba_channel = new ChannelModel(null, nba_guid, nba_title, nba_name, nba_link, nba_chosen);
            channelDao.insert(nba_channel);

            String car_guid = Constants.NEWS.CAR_ID;
            String car_title = Constants.NEWS.CAR_TITLE;
            String car_name = Constants.NEWS.CAR_NAME;
            String car_link = Constants.NEWS.CAR_ID;
            int car_chosen = 1;
            ChannelModel car_channel = new ChannelModel(null, car_guid, car_title, car_name, car_link, car_chosen);
            channelDao.insert(car_channel);

            String joke_guid = Constants.NEWS.JOKE_ID;
            String joke_title = Constants.NEWS.JOKE_TITLE;
            String joke_name = Constants.NEWS.JOKE_NAME;
            String joke_link = Constants.NEWS.JOKE_ID;
            int joke_chosen = 1;
            ChannelModel joke_channel = new ChannelModel(null, joke_guid, joke_title, joke_name, joke_link, joke_chosen);
            channelDao.insert(joke_channel);
            //MainActivity.refreshMenu();

//                    Intent mIntent = new Intent();
//                    mIntent.putExtra("change01", "1000");
//                    mIntent.putExtra("change02", "2000");
//                    // 设置结果，并进行传送
//                    setResult(resultCode, mIntent);
            setResult(resultCode);
        } catch (Exception e) {
            Log.e(TAG, "initDataTableChannel error", e);
        }
        Log.d(TAG, "initDataTableChannel debug, end");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(ChannelLibraryActivity.this);
        db.close();
        daoSession.clear();
    }
}
