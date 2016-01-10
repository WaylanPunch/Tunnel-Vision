package com.way.tunnelvision.ui;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.MenuAdapter;
import com.way.tunnelvision.model.MenuModel;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getName();

    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private View leftDrawerMenu;
    private List<MenuModel> mMenuItems = new ArrayList<>();
    private MenuAdapter mAdapter;

    NewsFragment newsFragment;
    TopicFragment topicFragment;
    RecommendFragment recommendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 3) {
                    case 0:
                        newsFragment = NewsFragment.newInstance();
                        return newsFragment;
                    case 1:
                        topicFragment = TopicFragment.newInstance();
                        return topicFragment;
                    default:
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

    private void initDrawerMenu() {
        Log.d(TAG, "initDrawerMenu debug, start");
        leftDrawerMenu = findViewById(R.id.left_drawer);
        CircularImageView drawer_user_avatar = (CircularImageView) leftDrawerMenu.findViewById(R.id.civ_drawer_header_user_avatar);
        TextView drawer_user_signature = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_header_user_signature);
        TextView drawer_add_feeds_click = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_header_add_feeds_click);
        TextView drawer_user_display_name = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_header_user_display_name);
        TextView drawer_collection_amount = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_header_collection_amount);
        TextView drawer_user_email = (TextView) leftDrawerMenu.findViewById(R.id.tv_drawer_header_user_email);
        Button drawer_settings_click = (Button) leftDrawerMenu.findViewById(R.id.btn_drawer_bottom_settings_click);
        Button drawer_exit_click = (Button) leftDrawerMenu.findViewById(R.id.btn_drawer_bottom_exit_click);
        ListView drawer_menu_list = (ListView) leftDrawerMenu.findViewById(R.id.rv_menu_list);
        drawer_user_avatar.setOnClickListener(viewOnClickListener);
        drawer_add_feeds_click.setOnClickListener(viewOnClickListener);
        drawer_settings_click.setOnClickListener(viewOnClickListener);
        drawer_exit_click.setOnClickListener(viewOnClickListener);

        initDrawerMenuData();
        mAdapter = new MenuAdapter(MainActivity.this, mMenuItems);
        drawer_menu_list.setAdapter(mAdapter);
        drawer_menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Click Position = " + position + ", MenuId = " + mMenuItems.get(position).getMenuId(), Toast.LENGTH_SHORT).show();
            }
        });
        //mAdapter.notifyDataSetChanged();
        Log.d(TAG, "initDrawerMenu debug, end");
    }

    private void initDrawerMenuData() {
        for (int i = 0; i < 12; ++i) {
            MenuModel menuModel1 = new MenuModel();
            menuModel1.setMenuId("0000" + i);
            menuModel1.setMenuTitle("36Ke " + i);
            menuModel1.setMenuInitial("" + i);
            mMenuItems.add(menuModel1);
        }
    }

    private View.OnClickListener viewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.civ_drawer_header_user_avatar) {
                Toast.makeText(MainActivity.this, "To My Home.", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.tv_drawer_header_add_feeds_click) {
                Toast.makeText(MainActivity.this, "To Add feeds.", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.btn_drawer_bottom_settings_click) {
                Toast.makeText(MainActivity.this, "To App Settings.", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.btn_drawer_bottom_exit_click) {
                Toast.makeText(MainActivity.this, "To Exit App.", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                builder.setTitle("警告");
                builder.setMessage("确定退出程序吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCollector.finishAll();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
        if (id == R.id.action_messages) {
//            ToastUtil.show(MainActivity.this, "Settings");
//            Intent intent = new Intent(MainActivity.this, PulldownViewActivity.class);
//            startActivity(intent);
            return true;
        } else if (id == R.id.action_shortcut) {
            // 设置关联程序
//            Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
//            launcherIntent.setClass(MainActivity.this, MainActivity.class);
//            launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//            boolean shortcutAdded = Utils.addShortcut(MainActivity.this, launcherIntent, false);
//            if (shortcutAdded){
//                ToastUtil.show(MainActivity.this, "Succeed to Add Shortcut!");
//            }else {
//                ToastUtil.show(MainActivity.this, "Failed to Add Shortcut!");
//            }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //listData = null;
        ActivityCollector.finishAll();
    }
}
