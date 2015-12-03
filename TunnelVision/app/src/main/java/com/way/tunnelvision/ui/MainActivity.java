package com.way.tunnelvision.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

    private FloatingActionButton fab;
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

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action, action " + MENU_ITEM_INDEX, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        setFloatingActionButtonVisible(MENU_ITEM_INDEX);
        Log.d(TAG, "setFirstFramentVisible debug, FindFragment Transaction start");
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed debug, start");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Log.d(TAG, "onBackPressed debug, end");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, "onCreateOptionsMenu debug, start");
        getMenuInflater().inflate(R.menu.main, menu);
        Log.d(TAG, "onCreateOptionsMenu debug, end");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG, "onOptionsItemSelected debug, start");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        Log.d(TAG, "onOptionsItemSelected debug, end");
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.d(TAG, "onNavigationItemSelected debug, start");
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
        } else if (id == R.id.nav_message) {
            // Handle the message action
            Log.d(TAG, "onNavigationItemSelected debug, MessageFragment Transaction start");
            MessageFragment messageFragment = new MessageFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, messageFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 2;
            Log.d(TAG, "onNavigationItemSelected debug, MessageFragment Transaction end");
        } else if (id == R.id.nav_chat) {
            Log.d(TAG, "onNavigationItemSelected debug, ChatFragment Transaction start");
            ChatFragment chatFragment = new ChatFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, chatFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 3;
            Log.d(TAG, "onNavigationItemSelected debug, ChatFragment Transaction end");
        } else if (id == R.id.nav_friend) {
            Log.d(TAG, "onNavigationItemSelected debug, FriendFragment Transaction start");
            FriendFragment friendFragment = new FriendFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, friendFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 4;
            Log.d(TAG, "onNavigationItemSelected debug, FriendFragment Transaction end");
        } else if (id == R.id.nav_diary) {
            Log.d(TAG, "onNavigationItemSelected debug, DiaryFragment Transaction start");
            DiaryFragment diaryFragment = new DiaryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, diaryFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 5;
            Log.d(TAG, "onNavigationItemSelected debug, DiaryFragment Transaction end");
        } else if (id == R.id.nav_collection) {
            Log.d(TAG, "onNavigationItemSelected debug, CollectionFragment Transaction start");
            CollectionFragment collectionFragment = new CollectionFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, collectionFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 6;
            Log.d(TAG, "onNavigationItemSelected debug, CollectionFragment Transaction end");
        } else if (id == R.id.nav_manage) {
            Log.d(TAG, "onNavigationItemSelected debug, ManageFragment Transaction start");
            ManageFragment manageFragment = new ManageFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, manageFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 7;
            Log.d(TAG, "onNavigationItemSelected debug, ManageFragment Transaction end");
        } else if (id == R.id.nav_scan) {
            Log.d(TAG, "onNavigationItemSelected debug, ScanFragment Transaction start");
            ScanFragment scanFragment = new ScanFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, scanFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 8;
            Log.d(TAG, "onNavigationItemSelected debug, ScanFragment Transaction end");
        } else if (id == R.id.nav_share) {
            Log.d(TAG, "onNavigationItemSelected debug, ShareFragment Transaction start");
            ShareFragment shareFragment = new ShareFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, shareFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 9;
            Log.d(TAG, "onNavigationItemSelected debug, ShareFragment Transaction end");
        } else if (id == R.id.nav_about) {
            Log.d(TAG, "onNavigationItemSelected debug, AboutFragment Transaction start");
            AboutFragment aboutFragment = new AboutFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, aboutFragment);
            fragmentTransaction.commit();
            MENU_ITEM_INDEX = 10;
            Log.d(TAG, "onNavigationItemSelected debug, AboutFragment Transaction end");
        } else if (id == R.id.nav_exit) {
            Log.d(TAG, "onNavigationItemSelected debug, Exit Action start");
            MENU_ITEM_INDEX = 11;
            ActivityCollector.finishAll();
            Log.d(TAG, "onNavigationItemSelected debug, Exit Action end");
        }
        setFloatingActionButtonVisible(MENU_ITEM_INDEX);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Log.d(TAG, "onNavigationItemSelected debug, end");
        return true;
    }

    private void setFloatingActionButtonVisible(int i_menu_item_index) {
        if (i_menu_item_index == 1 || i_menu_item_index == 5 || i_menu_item_index == 10) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }
    }

}
