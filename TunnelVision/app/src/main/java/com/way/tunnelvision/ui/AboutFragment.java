package com.way.tunnelvision.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.way.tunnelvision.R;

/**
 * Created by pc on 2015/12/6.
 */
public class AboutFragment extends Fragment {
    private final static String TAG = AboutFragment.class.getName();

    //private TextView tv_about_tip;
    private ProgressBar pb_about_progress;
    private WebView wv_about_content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.content_about, container, false);
            //tv_about_tip = (TextView) rootView.findViewById(R.id.tv_about_tip);
            pb_about_progress = (ProgressBar) rootView.findViewById(R.id.pb_about_progress);
            wv_about_content = (WebView) rootView.findViewById(R.id.wv_about_content);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView error", e);
        }
        Log.d(TAG, "onCreateView debug, end");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated debug, start");
        initView();
        Log.d(TAG, "onActivityCreated debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try {
            if (haveNetworkConnection()) {
                //startWebView("http://codetic.net/demo/webview-app/");
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

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
                    progressDialog = new ProgressDialog(getContext());
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
}
