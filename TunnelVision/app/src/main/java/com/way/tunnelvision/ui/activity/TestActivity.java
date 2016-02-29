package com.way.tunnelvision.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.ui.base.BaseActivity;

public class TestActivity extends BaseActivity {
    private static final String TAG = TestActivity.class.getName();

    private ProgressDialog progressDialog = null;

    private Button feed_click;
    private TextView feed_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        feed_click = (Button)findViewById(R.id.btn_test_getfeed);
        feed_show = (TextView)findViewById(R.id.tv_test_showfeed);


    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
