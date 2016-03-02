package com.way.tunnelvision.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.desmond.ripple.RippleCompat;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.CollectionViewPagerAdapter;
import com.way.tunnelvision.adapter.NewsViewPagerAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.dao.ChannelDao;
import com.way.tunnelvision.entity.dao.DaoMaster;
import com.way.tunnelvision.entity.dao.DaoSession;
import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.ui.fragment.CollectionFragment;
import com.way.tunnelvision.util.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, CollectionFragment.OnFragmentInteractionListener {
    private final String TAG = MainActivity.class.getName();

    private int requestCode;

    //About the Database
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ChannelDao channelDao;
    private Cursor cursor;

    private MaterialViewPager mMaterialViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    //private View leftDrawerMenu;
    //private List<MenuModel> mMenuItems = new ArrayList<>();
    //private MenuAdapter mAdapter;

    private List<ChannelModel> channelModels;
    private NewsViewPagerAdapter newsViewPagerAdapter;
    private CollectionViewPagerAdapter collectionViewPagerAdapter;
    private int FIRST_TIME_NEWS = 0;
    private int FIRST_TIME_COLLECTION = 0;
    private int FIRST_TIME_PHOTO = 0;
    private int FIRST_TIME_WEATHER = 0;
    private int FIRST_TIME_SETTINGS = 0;
    private int MENU_ITEM_CHOSEN_INDEX = 0;

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
        newsViewPagerAdapter = new NewsViewPagerAdapter(fragmentManager, channelModels);
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
        channelModels = new ArrayList<>();
        ///*
        try {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
            db = helper.getReadableDatabase();//db.up
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            channelDao = daoSession.getChannelDao();

            String orderColumn = ChannelDao.Properties.ChannelChosen.columnName;
            String orderBy = orderColumn + " COLLATE LOCALIZED ASC";
            String selection = orderColumn + " != 2";
            cursor = db.query(channelDao.getTablename(), channelDao.getAllColumns(), selection, null, null, null, orderBy);
            getChannelDataFromCursor(cursor);
        } catch (Exception e) {
            Log.e(TAG, "initChannelData error", e);
        }
        //*/
        /*
        for (int i = 0; i < 3; i++) {
            ChannelModel channelModel = new ChannelModel(1L,"aaaaaa","AAAAAA","a_a_a_a_a_a","www.baidu.com",1);
            channelModels.add(channelModel);
        }
        */
        Log.d(TAG, "initChannelData debug, end");
    }

    public void getChannelDataFromCursor(Cursor cursor1) {
        Log.d(TAG, "getChannelDataFromCursor debug, start");
        try {
            if (null != cursor1 && cursor1.getCount() > 0) {
                channelModels.clear();
                while (cursor1.moveToNext()) {
                    int columnIndex_id = cursor1.getColumnIndex(ChannelDao.COLUMNNAME_ID);
                    Long id = cursor1.getLong(columnIndex_id);
                    int columnIndex_guid = cursor1.getColumnIndex(ChannelDao.COLUMNNAME_GUID);
                    String guid = cursor1.getString(columnIndex_guid);
                    int columnIndex_title = cursor1.getColumnIndex(ChannelDao.COLUMNNAME_TITLE);
                    String title = cursor1.getString(columnIndex_title);
                    int columnIndex_name = cursor1.getColumnIndex(ChannelDao.COLUMNNAME_NAME);
                    String name = cursor1.getString(columnIndex_name);
                    int columnIndex_link = cursor1.getColumnIndex(ChannelDao.COLUMNNAME_LINK);
                    String link = cursor1.getString(columnIndex_link);
                    int columnIndex_type = cursor.getColumnIndex(ChannelDao.COLUMNNAME_TYPE);
                    int type = cursor.getInt(columnIndex_type);
                    int columnIndex_chosen = cursor1.getColumnIndex(ChannelDao.COLUMNNAME_CHOSEN);
                    int chosen = cursor1.getInt(columnIndex_chosen);
                    ChannelModel channelModel = new ChannelModel(id, guid, title, name, link, type, chosen);
                    channelModels.add(channelModel);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getChannelDataFromCursor error", e);
        }
        Log.d(TAG, "getChannelDataFromCursor debug, end");
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
        if (0 == MENU_ITEM_CHOSEN_INDEX) {
            cursor.requery();
            getChannelDataFromCursor(cursor);
            newsViewPagerAdapter.notifyDataSetChanged();
        }
    }

    public void switchViewPagerAdapter(FragmentStatePagerAdapter pagerAdapter) {
        Log.d(TAG, "switchViewPagerAdapter debug, start");
        try {
            if (mMaterialViewPager.getViewPager().getAdapter() != null) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle bundle = new Bundle();
                int index = mMaterialViewPager.getViewPager().getAdapter().getCount();
                String key = "index";
                while (index >= 0) {
                    bundle.putInt(key, index);
                    ft.remove(fm.getFragment(bundle, key));
                    index--;
                }
                ft.commit();
            }
            mMaterialViewPager.getViewPager().setAdapter(pagerAdapter);
            mMaterialViewPager.getViewPager().setOffscreenPageLimit(mMaterialViewPager.getViewPager().getAdapter().getCount());
            mMaterialViewPager.getPagerTitleStrip().setViewPager(mMaterialViewPager.getViewPager());
        } catch (Exception e) {
            Log.e(TAG, "switchViewPagerAdapter error", e);
        }
        Log.d(TAG, "switchViewPagerAdapter debug, end");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_menu_item_news) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_news index = " + 0);
            MENU_ITEM_CHOSEN_INDEX = 0;
            Toast.makeText(MainActivity.this, "nav_camera", Toast.LENGTH_SHORT).show();
            if (0 == FIRST_TIME_NEWS) {
                FIRST_TIME_NEWS++;
            }
            if (0 < FIRST_TIME_NEWS) {
                if (null != newsViewPagerAdapter && null != channelModels) {
                    switchViewPagerAdapter(newsViewPagerAdapter);
                }
            }
        } else if (id == R.id.nav_menu_item_collection) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_collection index = " + 1);
            MENU_ITEM_CHOSEN_INDEX = 1;
            if (0 == FIRST_TIME_COLLECTION) {
                FIRST_TIME_COLLECTION++;
                collectionViewPagerAdapter = new CollectionViewPagerAdapter(fragmentManager);
                switchViewPagerAdapter(collectionViewPagerAdapter);
            }
            if (0 < FIRST_TIME_COLLECTION) {
                if (null != collectionViewPagerAdapter) {
                    switchViewPagerAdapter(collectionViewPagerAdapter);
                }
            }
        } else if (id == R.id.nav_menu_item_photo) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_photo index = " + 2);
            MENU_ITEM_CHOSEN_INDEX = 2;
            Toast.makeText(MainActivity.this, "nav_slideshow", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_menu_item_weather) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_weather index = " + 3);
            MENU_ITEM_CHOSEN_INDEX = 3;
            Toast.makeText(MainActivity.this, "nav_manage", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_menu_item_settings) {
            Log.d(TAG, "onNavigationItemSelected debug, nav_menu_item_settings index = " + 4);
            MENU_ITEM_CHOSEN_INDEX = 4;
            Toast.makeText(MainActivity.this, "nav_share", Toast.LENGTH_SHORT).show();
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
        if (null != cursor) {
            cursor.close();
        }
        if (null != daoSession) {
            daoSession.clear();
        }
        if (null != db) {
            db.close();
        }
        ActivityCollector.finishAll();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
