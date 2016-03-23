package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.service.ImageDaoHelper;
import com.way.tunnelvision.entity.service.NewsDaoHelper;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.ui.fragment.CollectionFragment;
import com.way.tunnelvision.util.LogUtil;
import com.way.tunnelvision.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseActivity {
    private static final String TAG = CollectionActivity.class.getName();

    private LinearLayout mToolbarContainer;
    private int mToolbarHeight;
    private Toolbar mToolbar;
    private FloatingActionButton mFabButton;
    private RecyclerView recyclerView;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.title_activity_collection));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbarHeight = SystemUtil.getToolbarHeight(this);

        mFabButton = (FloatingActionButton) findViewById(R.id.fabButton);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(CollectionFragment.newInstance(1), "News");
        pagerAdapter.addFragment(CollectionFragment.newInstance(2), "Image");
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    static class PagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_collection_clear_news) {
            try {
                NewsDaoHelper newsDaoHelper = NewsDaoHelper.getInstance();
                newsDaoHelper.deleteAll();
                Toast.makeText(CollectionActivity.this,"清除所有收藏新闻",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                LogUtil.e(TAG, "onOptionsItemSelected error, action_collection_clear_news", e);
                return false;
            }
        } else if(id==R.id.action_collection_clear_image){
            try {
                ImageDaoHelper imageDaoHelper = ImageDaoHelper.getInstance();
                imageDaoHelper.deleteAll();
                Toast.makeText(CollectionActivity.this,"清除所有收藏图片",Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                LogUtil.e(TAG, "onOptionsItemSelected error, action_collection_clear_image", e);
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
