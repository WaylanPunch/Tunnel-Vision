package com.way.tunnelvision.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.way.tunnelvision.R;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ToastUtil;

public class AboutActivity extends BaseActivity {
    private final static String TAG = AboutActivity.class.getName();
    
    private WebView wv_about_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Log.d(TAG, "onCreate debug, start");
        initView();
        Log.d(TAG, "onCreate debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

            wv_about_content = (WebView) findViewById(R.id.wv_about_content);

            if (haveNetworkConnection()) {
                //startWebView("https://github.com/WaylanPunch/Tunnel-Vision/blob/master/TunnelVision/app/src/main/java/com/way/tunnelvision/ui/AboutFragment.java");
                startWebView("file:///android_asset/about.html");
            } else {
                wv_about_content.loadUrl("file:///android_asset/error.html");
            }
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }

    private boolean haveNetworkConnection() {
        Log.d(TAG, "haveNetworkConnection debug, start");
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        Log.d(TAG, "haveNetworkConnection debug, end");
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void startWebView(String url) {
        Log.d(TAG, "startWebView debug, start");
        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        wv_about_content.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //If url has "tel:245678" , on clicking the number it will directly call to inbuilt calling feature of phone
            /*
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                } else {

                    view.loadUrl(url);

                }
            }
            */

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(AboutActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        // Javascript inabled on webview
        wv_about_content.getSettings().setJavaScriptEnabled(true);

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        //Additional Webview Properties
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setDatabaseEnabled(true);
		webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
		webView.getSettings().setAppCacheEnabled(true);
		webView.getSettings().setLayoutAlgorithm(webView.getSettings().getLayoutAlgorithm().NORMAL);
		 webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(false);
		webView.setSoundEffectsEnabled(true);
		webView.setHorizontalFadingEdgeEnabled(false);
		webView.setKeepScreenOn(true);
		webView.setScrollbarFadingEnabled(true);
		webView.setVerticalFadingEdgeEnabled(false);






        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        wv_about_content.loadUrl(url);
        Log.d(TAG, "startWebView debug, end");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about_settings) {
            ToastUtil.show(AboutActivity.this, "About Settings");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //listData = null;
    }

}
