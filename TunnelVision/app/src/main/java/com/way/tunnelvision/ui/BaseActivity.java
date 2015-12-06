package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.way.tunnelvision.util.ActivityCollector;

/**
 * Created by pc on 2015/12/6.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
