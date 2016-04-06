package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.ui.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    private Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.tb_about_toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_about));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        webView = (WebView) findViewById(R.id.wv_about_content);
        webView.loadUrl("file:///android_asset/about.html");
    }

}
