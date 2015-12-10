package com.way.tunnelvision.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.way.tunnelvision.R;
import com.way.tunnelvision.util.ActivityCollector;
import com.way.tunnelvision.util.ToastUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = MainActivity.class.getName();
    private static int MENU_ITEM_INDEX = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate debug, start");
        initView();
        Log.d(TAG, "onCreate debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navigationHeaderView = navigationView.getHeaderView(0);
        navigationHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show(MainActivity.this, "Go Home");
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        setFirstFramentVisible();
        Log.d(TAG, "initView debug, end");
    }

    private void setFirstFramentVisible() {
        Log.d(TAG, "setFirstFramentVisible debug, FindFragment Transaction start");
        FindFragment findFragment = new FindFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_container, findFragment);
        fragmentTransaction.commit();
        MENU_ITEM_INDEX = 1;
        //setFloatingActionButtonVisible(MENU_ITEM_INDEX);
        Log.d(TAG, "setFirstFramentVisible debug, FindFragment Transaction end");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            ToastUtil.show(MainActivity.this, "Settings");
            return true;
        } else if(id == R.id.action_messages) {
            ToastUtil.show(MainActivity.this, "Messages");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_find) {
            // Handle the find action
            Log.d(TAG, "onNavigationItemSelected debug, FindFragment Transaction start");
            FindFragment findFragment = new FindFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, findFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 1;
            Log.d(TAG, "onNavigationItemSelected debug, FindFragment Transaction end");
        } else if (id == R.id.nav_chat) {
            Log.d(TAG, "onNavigationItemSelected debug, ChatFragment Transaction start");
            ChatFragment chatFragment = new ChatFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, chatFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 2;
            Log.d(TAG, "onNavigationItemSelected debug, ChatFragment Transaction end");
        } else if (id == R.id.nav_friend) {
            Log.d(TAG, "onNavigationItemSelected debug, FriendFragment Transaction start");
            FriendFragment friendFragment = new FriendFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, friendFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 3;
            Log.d(TAG, "onNavigationItemSelected debug, FriendFragment Transaction end");
        } else if (id == R.id.nav_diary) {
            Log.d(TAG, "onNavigationItemSelected debug, DiaryFragment Transaction start");
            DiaryFragment diaryFragment = new DiaryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, diaryFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 4;
            Log.d(TAG, "onNavigationItemSelected debug, DiaryFragment Transaction end");
        } else if (id == R.id.nav_collection) {
            Log.d(TAG, "onNavigationItemSelected debug, CollectionFragment Transaction start");
            CollectionFragment collectionFragment = new CollectionFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, collectionFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 5;
            Log.d(TAG, "onNavigationItemSelected debug, CollectionFragment Transaction end");
        } else if (id == R.id.nav_manage) {
            Log.d(TAG, "onNavigationItemSelected debug, ManageFragment Transaction start");
            ManageFragment manageFragment = new ManageFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, manageFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 6;
            Log.d(TAG, "onNavigationItemSelected debug, ManageFragment Transaction end");
        } else if (id == R.id.nav_scan) {
            Log.d(TAG, "onNavigationItemSelected debug, ScanFragment Transaction start");
            ScanFragment scanFragment = new ScanFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, scanFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 7;
            Log.d(TAG, "onNavigationItemSelected debug, ScanFragment Transaction end");
        } else if (id == R.id.nav_share) {
            Log.d(TAG, "onNavigationItemSelected debug, ShareFragment Transaction start");
            ShareFragment shareFragment = new ShareFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, shareFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 8;
            Log.d(TAG, "onNavigationItemSelected debug, ShareFragment Transaction end");
        } else if (id == R.id.nav_about) {
            Log.d(TAG, "onNavigationItemSelected debug, AboutFragment Transaction start");
            AboutFragment aboutFragment = new AboutFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, aboutFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 9;
            Log.d(TAG, "onNavigationItemSelected debug, AboutFragment Transaction end");
        } else if (id == R.id.nav_exit) {
            Log.d(TAG, "onNavigationItemSelected debug, Exit Action start");
            MENU_ITEM_INDEX = 10;
            ActivityCollector.finishAll();
            Log.d(TAG, "onNavigationItemSelected debug, Exit Action end");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
