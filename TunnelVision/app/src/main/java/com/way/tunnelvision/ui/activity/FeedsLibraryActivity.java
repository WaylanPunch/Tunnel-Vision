package com.way.tunnelvision.ui.activity;

import android.os.Bundle;

import com.way.tunnelvision.R;
import com.way.tunnelvision.ui.base.BaseActivity;

public class FeedsLibraryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds_library);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
