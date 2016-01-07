package com.way.tunnelvision.ui;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.way.tunnelvision.R;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;

public class MainActivity extends BaseActivity {

    private MaterialViewPager mViewPager;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //view
        setTitle("Tunnel Vision");

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        toolbar = mViewPager.getToolbar();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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
                switch (position % 4) {
                    case 0:
                        return NewsFragment.newInstance();
                    case 1:
                        return TopicFragment.newInstance();
                    case 2:
                        return RecommendFragment.newInstance();
//                    case 3:
//                        return AdvertisementFragment.newInstance();
                    default:
                        return NewsFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 4) {
                    case 0:
                        return "NEWS";
                    case 1:
                        return "TOPIC";
                    case 2:
                        return "RECOMMEND";
                    case 3:
                        return "ADVERTISEMENT";
                    default:
                        return "";
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
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.colorPrimary,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.colorPrimary,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        View logo = findViewById(R.id.logo_white);
        if (logo != null)
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                }
            });
    }

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
//            Intent intent = new Intent(MainActivity.this, TestActivity.class);
//            startActivity(intent);
            return true;
        } else if(id == R.id.action_shortcut) {
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
        } else if(id == R.id.action_share) {
//            ToastUtil.show(MainActivity.this, "Messages");
//            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
//            startActivity(intent);
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
