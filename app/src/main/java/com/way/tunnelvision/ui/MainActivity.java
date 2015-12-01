package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.way.tunnelvision.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = MainActivity.class.getName();

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
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
                Toast.makeText(MainActivity.this, "fdfd", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "initView debug, FindFragment Transaction start");
        FindFragment findFragment = new FindFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_container, findFragment);
        fragmentTransaction.commit();
        //fragmentTransaction.show(findFragment);
        Log.d(TAG, "initView debug, FindFragment Transaction start");
        Log.d(TAG, "initView debug, end");
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
            Log.d(TAG, "onNavigationItemSelected debug, FindFragment Transaction end");
        } else if (id == R.id.nav_message) {
            // Handle the message action
            Log.d(TAG, "onNavigationItemSelected debug, MessageFragment Transaction start");
            MessageFragment messageFragment = new MessageFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, messageFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, MessageFragment Transaction end");
        } else if (id == R.id.nav_chat) {
            Log.d(TAG, "onNavigationItemSelected debug, ChatFragment Transaction start");
            ChatFragment chatFragment = new ChatFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, chatFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, ChatFragment Transaction end");
        } else if (id == R.id.nav_friend) {
            Log.d(TAG, "onNavigationItemSelected debug, FriendFragment Transaction start");
            FriendFragment friendFragment = new FriendFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, friendFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, FriendFragment Transaction end");
        } else if (id == R.id.nav_diary) {
            Log.d(TAG, "onNavigationItemSelected debug, DiaryFragment Transaction start");
            DiaryFragment diaryFragment = new DiaryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, diaryFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, DiaryFragment Transaction end");
        } else if (id == R.id.nav_collection) {
            Log.d(TAG, "onNavigationItemSelected debug, CollectionFragment Transaction start");
            CollectionFragment collectionFragment = new CollectionFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, collectionFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, CollectionFragment Transaction end");
        } else if (id == R.id.nav_manage) {
            Log.d(TAG, "onNavigationItemSelected debug, ManageFragment Transaction start");
            ManageFragment manageFragment = new ManageFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, manageFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, ManageFragment Transaction end");
        } else if (id == R.id.nav_scan) {
            Log.d(TAG, "onNavigationItemSelected debug, ScanFragment Transaction start");
            ScanFragment scanFragment = new ScanFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, scanFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, ScanFragment Transaction end");
        } else if (id == R.id.nav_share) {
            Log.d(TAG, "onNavigationItemSelected debug, ShareFragment Transaction start");
            ShareFragment shareFragment = new ShareFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, shareFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, ShareFragment Transaction end");
        } else if (id == R.id.nav_about) {
            Log.d(TAG, "onNavigationItemSelected debug, AboutFragment Transaction start");
            AboutFragment aboutFragment = new AboutFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, aboutFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, AboutFragment Transaction end");
        } else if (id == R.id.nav_exit) {
            Log.d(TAG, "onNavigationItemSelected debug, Exit Action start");

            Log.d(TAG, "onNavigationItemSelected debug, Exit Action end");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Log.d(TAG, "onNavigationItemSelected debug, end");
        return true;
    }

}
