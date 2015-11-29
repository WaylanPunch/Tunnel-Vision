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

        Log.d(TAG, "initView debug, HomeFragment Transaction start");
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_container, homeFragment);
        fragmentTransaction.commit();
        //fragmentTransaction.show(homeFragment);
        Log.d(TAG, "initView debug, HomeFragment Transaction start");
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
//        if(item.isChecked()) {
//            item.setChecked(false);
//        }
//        else {
//            item.setChecked(true);
//        }
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
            Log.d(TAG, "onNavigationItemSelected debug, HomeFragment Transaction start");
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, homeFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, HomeFragment Transaction end");
        } else if (id == R.id.nav_camera) {
            // Handle the camera action
            Log.d(TAG, "onNavigationItemSelected debug, CameraFragment Transaction start");
            Log.d(TAG, "onNavigationItemSelected debug, CameraFragment Transaction end");
        } else if (id == R.id.nav_gallery) {
            Log.d(TAG, "onNavigationItemSelected debug, GalleryFragment Transaction start");
            GalleryFragment galleryFragment = new GalleryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_container, galleryFragment);
            fragmentTransaction.commit();
            Log.d(TAG, "onNavigationItemSelected debug, GalleryFragment Transaction end");
        } else if (id == R.id.nav_slideshow) {
            Log.d(TAG, "onNavigationItemSelected debug, SlideShowFragment Transaction start");
            Log.d(TAG, "onNavigationItemSelected debug, SlideShowFragment Transaction end");
        } else if (id == R.id.nav_manage) {
            Log.d(TAG, "onNavigationItemSelected debug, ManageFragment Transaction start");
            Log.d(TAG, "onNavigationItemSelected debug, ManageFragment Transaction end");
        } else if (id == R.id.nav_share) {
            Log.d(TAG, "onNavigationItemSelected debug, ShareFragment Transaction start");
            Log.d(TAG, "onNavigationItemSelected debug, ShareFragment Transaction end");
        } else if (id == R.id.nav_send) {
            Log.d(TAG, "onNavigationItemSelected debug, SendFragment Transaction start");
            Log.d(TAG, "onNavigationItemSelected debug, SendFragment Transaction end");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Log.d(TAG, "onNavigationItemSelected debug, end");
        return true;
    }

}
