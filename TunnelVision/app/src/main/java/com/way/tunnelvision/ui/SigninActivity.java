package com.way.tunnelvision.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.way.tunnelvision.R;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ActivityCollector;

public class SigninActivity extends BaseActivity {
    private final String TAG = SigninActivity.class.getName();
    //private boolean showHomeAsUp = true;
    private EditText et_signin_username, et_signin_password;
    private CheckBox cb_signin_show_password;
    private Button btn_signin_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Log.d(TAG, "onCreate debug, start");
        initView();
        Log.d(TAG, "onCreate debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try {
            //TextView tv_signin_large_text = (TextView) findViewById(R.id.tv_signin_large_text);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_signin);
            setSupportActionBar(toolbar);
            /*
            getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeAsUp);
            if (null != toolbar) {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                //toolbar.inflateMenu(R.menu.menu_signin);
            }
            */
            /*
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_signin_button);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Replace with your own action, signin.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            */
            et_signin_username = (EditText) findViewById(R.id.et_signin_username);
            et_signin_password = (EditText) findViewById(R.id.et_signin_password);
            cb_signin_show_password = (CheckBox) findViewById(R.id.cb_signin_show_password);
            btn_signin_login = (Button) findViewById(R.id.btn_signin_login);
            btn_signin_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCollector.finishAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_signin_signup){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ActivityCollector.finishAll();
    }
}
