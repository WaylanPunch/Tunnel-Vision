package com.way.tunnelvision.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desmond.ripple.RippleCompat;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.ChannelViewPagerAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.model.ChannelModel;
import com.way.tunnelvision.model.dao.ChannelDao;
import com.way.tunnelvision.model.dao.DaoMaster;
import com.way.tunnelvision.model.dao.DaoSession;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getName();

    private int requestCode;

    //About the Database
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ChannelDao channelDao;
    private Cursor cursor;

    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private View leftDrawerMenu;
    //private List<MenuModel> mMenuItems = new ArrayList<>();
    //private MenuAdapter mAdapter;

    private List<ChannelModel> channelModels;
    private ChannelViewPagerAdapter channelViewPagerAdapter;

//    NewsFragment newsFragment;
//    TopicFragment topicFragment;
//    RecommendFragment recommendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RippleCompat.init(this);

        //view
        setTitle("");

        mViewPager = (MaterialViewPager) findViewById(R.id.mvp_main_view_pager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.dl_main_drawer_layout);

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

        initChannelData();
        /*
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 3) {
                    case 0:
                        Log.d(TAG, "onCreate debug, New NewsFragment Instance");
                        newsFragment = NewsFragment.newInstance();
                        return newsFragment;
                    case 1:
                        Log.d(TAG, "onCreate debug, New TopicFragment Instance");
                        topicFragment = TopicFragment.newInstance();
                        return topicFragment;
                    default:
                        Log.d(TAG, "onCreate debug, New RecommendFragment Instance");
                        recommendFragment = RecommendFragment.newInstance();
                        return recommendFragment;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 3) {
                    case 0:
                        //getResources().getString()
                        return getString(R.string.fragment_news_title);
                    case 1:
                        return getString(R.string.fragment_topic_title);
                    default:
                        return getString(R.string.fragment_recommend_title);
                }
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.colorPrimary,
                                "http://i4.tietuku.com/3590bb53f5752fe9.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.colorPrimary,
                                "http://i11.tietuku.com/e2718fdc58c721f2.jpg");
                    default:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.colorPrimary,
                                "http://i11.tietuku.com/d308b9a56f1c91b8.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                //return null;
            }
        });
        */

        mViewPager.getViewPager().setAdapter(channelViewPagerAdapter);
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
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

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.iv_fragment_header_logo);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
        }

        initDrawerMenu();
    }

    private void initChannelData() {
        Log.d(TAG, "initChannelData debug, start");
        channelModels = new ArrayList<>();
        ///*
        try {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
            db = helper.getReadableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            channelDao = daoSession.getChannelDao();

            String orderColumn = ChannelDao.Properties.ChannelChosen.columnName;
            String orderBy = orderColumn + " COLLATE LOCALIZED ASC";
            cursor = db.query(channelDao.getTablename(), channelDao.getAllColumns(), null, null, null, null, orderBy);
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
        channelViewPagerAdapter = new ChannelViewPagerAdapter(getSupportFragmentManager(), channelModels);
        Log.d(TAG, "initChannelData debug, end");
    }

    private void initDrawerMenu() {
        Log.d(TAG, "initDrawerMenu debug, start");
        leftDrawerMenu = findViewById(R.id.left_drawer);
        CircularImageView drawer_user_avatar = (CircularImageView) leftDrawerMenu.findViewById(R.id.civ_drawer_header_user_avatar);
        TextView drawer_user_signature = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_header_user_signature);
        TextView drawer_add_feeds_click = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_header_add_feeds_click);
        TextView drawer_user_display_name = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_header_user_display_name);
        TextView drawer_user_email = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_header_user_email);
        TextView drawer_settings_click = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_bottom_settings_click);
        TextView drawer_exit_click = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_bottom_exit_click);
        //ListView drawer_menu_list = (ListView) leftDrawerMenu.findViewById(R.id.rv_menu_list);
        drawer_user_avatar.setOnClickListener(viewOnClickListener);
        drawer_add_feeds_click.setOnClickListener(viewOnClickListener);
        drawer_settings_click.setOnClickListener(viewOnClickListener);
        drawer_exit_click.setOnClickListener(viewOnClickListener);

        //initDrawerMenuData();
        /*
        mAdapter = new MenuAdapter(MainActivity.this, mMenuItems);
        drawer_menu_list.setAdapter(mAdapter);
        drawer_menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Click Position = " + position + ", MenuId = " + mMenuItems.get(position).getMenuGUID(), Toast.LENGTH_SHORT).show();
            }
        });
        */
        //mAdapter.notifyDataSetChanged();
        Log.d(TAG, "initDrawerMenu debug, end");
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
                    int columnIndex_chosen = cursor1.getColumnIndex(ChannelDao.COLUMNNAME_CHOSEN);
                    int chosen = cursor1.getInt(columnIndex_chosen);
                    ChannelModel channelModel = new ChannelModel(id, guid, title, name, link, chosen);
                    channelModels.add(channelModel);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "getChannelDataFromCursor error", e);
        }
        Log.d(TAG, "getChannelDataFromCursor debug, end");
    }

    public void refreshMenu() {
        //cursor.requery();
        //getDataFromCursor(cursor);
        //mAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener viewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.civ_drawer_header_user_avatar) {
                Toast.makeText(MainActivity.this, "To My Home.", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.tv_drawer_header_add_feeds_click) {
                Toast.makeText(MainActivity.this, "To Add feeds.", Toast.LENGTH_SHORT).show();
                openActivity(ChannelLibraryActivity.class);
//                // 请求码的值随便设置，但必须>=0
//                requestCode = 0;
//                openActivityForResult(ChannelLibraryActivity.class, requestCode);
                //startActivityForResult(mIntent, requestCode);
            } else if (id == R.id.tv_drawer_bottom_settings_click) {
                Toast.makeText(MainActivity.this, "To App Settings.", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.tv_drawer_bottom_exit_click) {
                Toast.makeText(MainActivity.this, "To Exit App.", Toast.LENGTH_SHORT).show();
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
            } else {

            }
        }
    };

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
//            ToastUtil.show(MainActivity.this, "Settings");
//            Intent intent = new Intent(MainActivity.this, TestActivity.class);
//            startActivity(intent);
//            CrashReport.testJavaCrash();
            // 请求码的值随便设置，但必须>=0
            requestCode = 0;
            openActivityForResult(ChannelLibraryActivity.class, requestCode);
            //openActivity(TestActivity.class);
            return true;
        } else if (id == R.id.action_share) {
//            ToastUtil.show(MainActivity.this, "Messages");
//            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
//            startActivity(intent);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle("AppCompatDialog");
            builder.setMessage("Lorem ipsum dolor...");
            builder.setPositiveButton("OK", null);
            builder.setNegativeButton("Cancel", null);
            builder.show();
            return true;
        }
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String change01 = data.getStringExtra("change01");
//        String change02 = data.getStringExtra("change02");
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 0:
                refreshMenu();
                break;
            case 2:
                //mText02.setText(change02);
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {

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

}
