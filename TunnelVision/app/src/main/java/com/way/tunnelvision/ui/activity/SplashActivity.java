package com.way.tunnelvision.ui.activity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.SplashViewPagerAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.model.ChannelModel;
import com.way.tunnelvision.model.dao.ChannelDao;
import com.way.tunnelvision.model.dao.DaoMaster;
import com.way.tunnelvision.model.dao.DaoSession;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;

public class SplashActivity extends BaseActivity {
    private final static String TAG = SplashActivity.class.getName();

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ChannelDao channelDao;
    private Cursor cursor;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    private SplashViewPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SplashViewPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.vp_splash_viewpagercontainer);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        initDataBase();
    }

    private void initDataBase() {
        Log.d(TAG, "initDataBase debug, start");
        try {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            channelDao = daoSession.getChannelDao();

            initDataTableChannel();
        } catch (Exception e) {
            Log.e(TAG, "initDataBase error", e);
        }
        Log.d(TAG, "initDataBase debug, end");
    }

    private void initDataTableChannel() {
        Log.d(TAG, "initDataTableChannel debug, start");
        try {
            String orderColumn = ChannelDao.Properties.ChannelChosen.columnName;
            String orderBy = orderColumn + " COLLATE LOCALIZED ASC";
            cursor = db.query(channelDao.getTablename(), channelDao.getAllColumns(), null, null, null, null, orderBy);
            if (null != cursor) {
                Log.d(TAG, "initDataTableChannel debug, Cursor Row Count = " + cursor.getCount());
            }

            if (null == cursor || 0 == cursor.getCount()) {
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
            }
        } catch (Exception e) {
            Log.e(TAG, "initDataTableChannel error", e);
        }
        Log.d(TAG, "initDataTableChannel debug, end");
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
    }
}
