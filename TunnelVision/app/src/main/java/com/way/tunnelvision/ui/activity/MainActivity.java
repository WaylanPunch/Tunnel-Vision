package com.way.tunnelvision.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.desmond.ripple.RippleCompat;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.NewsViewPagerAdapter;
import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.entity.service.ChannelDaoHelper;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = MainActivity.class.getName();

    private int requestCode;

    //About the Database
//    private SQLiteDatabase db;
//    private DaoMaster daoMaster;
//    private DaoSession daoSession;
//    private ChannelDao channelDao;
//    private Cursor cursor;
    private ChannelDaoHelper channelDaoHelper;

    private MaterialViewPager mMaterialViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    private List<ChannelModel> channelModels = new ArrayList<>();
    private NewsViewPagerAdapter newsViewPagerAdapter;
    private int FIRST_TIME_NEWS = 0;
    private int FIRST_TIME_COLLECTION = 0;
    private int FIRST_TIME_PHOTO = 0;
    private int FIRST_TIME_WEATHER = 0;
    private int FIRST_TIME_SETTINGS = 0;
    private static int MENU_ITEM_CHOSEN_INDEX = 0;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RippleCompat.init(this);

        //view
        setTitle("");

        mMaterialViewPager = (MaterialViewPager) findViewById(R.id.mvp_main_view_pager);

        toolbar = mMaterialViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.dl_main_drawer_layout);
        //mDrawer.
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                Drawable colorDrawable = new ColorDrawable(getResources().getColor(android.R.color.black));
                actionBar.setBackgroundDrawable(colorDrawable);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_main_menu);
        navigationView.setNavigationItemSelectedListener(this);

        FIRST_TIME_NEWS = 1;
        initChannelData();

        fragmentManager = getSupportFragmentManager();
        newsViewPagerAdapter = new NewsViewPagerAdapter(fragmentManager);
        newsViewPagerAdapter.setData(channelModels);
        mMaterialViewPager.getViewPager().setAdapter(newsViewPagerAdapter);
        mMaterialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int position) {
                ChannelModel channelModel = channelModels.get(position);
                return HeaderDesign.fromColorResAndUrl(
                        R.color.colorPrimary,
                        "http://i4.tietuku.com/3590bb53f5752fe9.jpg");
//                switch (position) {
//                    case 0:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.colorPrimary,
//                                "http://i4.tietuku.com/3590bb53f5752fe9.jpg");
//                    case 1:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.colorPrimary,
//                                "http://i11.tietuku.com/e2718fdc58c721f2.jpg");
//                    default:
//                        return HeaderDesign.fromColorResAndUrl(
//                                R.color.colorPrimary,
//                                "http://i11.tietuku.com/d308b9a56f1c91b8.jpg");
//                }

                //execute others actions if needed (ex : modify your header logo)

                //return null;
            }
        });

        mMaterialViewPager.getViewPager().setOffscreenPageLimit(mMaterialViewPager.getViewPager().getAdapter().getCount());
        mMaterialViewPager.getPagerTitleStrip().setViewPager(mMaterialViewPager.getViewPager());

        View logo = findViewById(R.id.iv_fragment_header_logo);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialViewPager.notifyHeaderChanged();
                    //Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initChannelData() {
        Log.d(TAG, "initChannelData debug, start");
        channelModels.clear();
        ///*
        try {
            if (null == channelDaoHelper) {
                channelDaoHelper = ChannelDaoHelper.getInstance();
            }
            long totalCount = channelDaoHelper.getTotalCount();
            ;
            Log.d(TAG, "initChannelData debug, Total Count = " + totalCount);

            channelModels = channelDaoHelper.getAllDataByChosen();
            Log.d(TAG, "initChannelData debug, Chosen Count = " + channelModels.size());
        } catch (Exception e) {
            Log.e(TAG, "initChannelData error", e);
        }
        Log.d(TAG, "initChannelData debug, end");
    }

    private View.OnClickListener viewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

        }
    };

    /**
     * Activity彻底运行起来之后的回调
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_subscribe) {
            requestCode = 0;
            openActivityForResult(ChannelLibraryActivity.class, requestCode);
            return true;
        } else if (id == R.id.action_share) {
            return true;
        }
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 0:
                refreshNewsViewPager();
                break;
            case 2:
                //mText02.setText(change02);
                break;
            default:
                break;
        }
    }

    public void refreshNewsViewPager() {
        Log.d(TAG, "refreshNewsViewPager debug, Get Data from Database Again.");
        initChannelData();
        newsViewPagerAdapter.setData(channelModels);
        newsViewPagerAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_menu_item_news) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_news index = " + 0);
            MENU_ITEM_CHOSEN_INDEX = 0;
        } else if (id == R.id.nav_menu_item_collection) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_collection index = " + 1);
            openActivity(CollectionActivity.class);
            MENU_ITEM_CHOSEN_INDEX = 1;
        } else if (id == R.id.nav_menu_item_photo) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_photo index = " + 2);
            openActivity(PhotoActivity.class);
            MENU_ITEM_CHOSEN_INDEX = 2;
        } else if (id == R.id.nav_menu_item_weather) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_weather index = " + 3);
            openActivity(WeatherActivity.class);
            MENU_ITEM_CHOSEN_INDEX = 3;
        } else if (id == R.id.nav_menu_item_settings) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_settings index = " + 4);
            openActivity(SettingsActivity.class);
            MENU_ITEM_CHOSEN_INDEX = 4;
        } else if (id == R.id.nav_menu_item_exit) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_exit index = " + 5);
            MENU_ITEM_CHOSEN_INDEX = 5;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle(getString(R.string.text_dialog_title));
            builder.setMessage(getString(R.string.text_dialog_exit_warning));
            builder.setPositiveButton(getString(R.string.text_dialog_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCollector.finishAll();
                }
            });
            builder.setNegativeButton(getString(R.string.text_dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle(getString(R.string.text_dialog_title));
            builder.setMessage(getString(R.string.text_dialog_exit_warning));
            builder.setPositiveButton(getString(R.string.text_dialog_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    ActivityCollector.finishAll();
                }
            });
            builder.setNegativeButton(getString(R.string.text_dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            //super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityCollector.finishAll();
    }

}
