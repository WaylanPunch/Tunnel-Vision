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
import com.way.tunnelvision.entity.dao.ChannelDao;
import com.way.tunnelvision.entity.dao.DaoMaster;
import com.way.tunnelvision.entity.dao.DaoSession;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;
import com.way.tunnelvision.util.DatabaseUtil;
import com.way.tunnelvision.util.PreferencesUtil;

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

        Log.d(TAG, "onCreate debug, start");
        try {
            DatabaseUtil.initDataBase(this);
            int first_enter_valuue = PreferencesUtil.getInt(getApplicationContext(), Constants.PREFERENCE_KEY_FIRST_ENTER);
            if (-1 == first_enter_valuue) {
                PreferencesUtil.putInt(getApplicationContext(), Constants.PREFERENCE_KEY_FIRST_ENTER, 1);
                // Create the adapter that will return a fragment for each of the three
                // primary sections of the activity.
                mSectionsPagerAdapter = new SplashViewPagerAdapter(getSupportFragmentManager());

                // Set up the ViewPager with the sections adapter.
                mViewPager = (ViewPager) findViewById(R.id.vp_splash_viewpagercontainer);
                mViewPager.setAdapter(mSectionsPagerAdapter);
            } else {
                openActivity(AdDisplayActivity.class);
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate error", e);
        }
        Log.d(TAG, "onCreate debug, end");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
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
}
