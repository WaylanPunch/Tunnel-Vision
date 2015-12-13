package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.way.tunnelvision.R;

public class HomeActivity extends BaseActivity {
    private final static String TAG = HomeActivity.class.getName();
    private boolean showHomeAsUp = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "onCreate debug, start");
        initView();
        Log.d(TAG, "onCreate debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try{
            final TextView tv_home_large_text = (TextView) findViewById(R.id.tv_home_large_text);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
            if (null != toolbar) {
                //toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

                //toolbar.setTitle(R.string.title_activity_settings);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       finish();
                    }
                });

                // Inflate a menu to be displayed in the toolbar
                toolbar.inflateMenu(R.menu.menu_home);
            }

            //getSupportActionBar().

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_home_button);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } catch (Exception e){
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Log.d(TAG, "action bar clicked");
        } else if (id == R.id.action_home_settings) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
