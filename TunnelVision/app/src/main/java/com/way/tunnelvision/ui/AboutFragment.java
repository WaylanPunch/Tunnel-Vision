package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.way.tunnelvision.R;

/**
 * Created by pc on 2015/11/29.
 */
public class AboutFragment extends Fragment {
    private final static String TAG = AboutFragment.class.getName();

    //private TextView tv_about_tip;
    private ProgressBar pb_about_progress;
    private WebView wv_about_content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        Log.d(TAG, "onCreateView debug, end");
        return inflater.inflate(R.layout.content_about, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated debug, start");
        findView();
        initView();
        Log.d(TAG, "onActivityCreated debug, end");
    }

    private void findView() {
        Log.d(TAG, "findView debug, start");
        try {
            //tv_about_tip = (TextView) getView().findViewById(R.id.tv_about_tip);
            pb_about_progress = (ProgressBar) getView().findViewById(R.id.pb_about_progress);
            wv_about_content = (WebView) getView().findViewById(R.id.wv_about_content);
        } catch (Exception e) {
            Log.e(TAG, "findView error", e);
        }
        Log.d(TAG, "findView debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try {
            //允许JavaScript执行
            wv_about_content.getSettings().setJavaScriptEnabled(true);

            //pb是进度条ProgressBar
            wv_about_content.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    pb_about_progress.setProgress(newProgress);
                    if (newProgress == 100) {
                        pb_about_progress.setVisibility(View.GONE);
                    } else {
                        if (View.INVISIBLE == pb_about_progress.getVisibility()) {
                            pb_about_progress.setVisibility(View.VISIBLE);
                        }
                        pb_about_progress.setProgress(newProgress);
                    }
                    super.onProgressChanged(view, newProgress);
                }
            });

            //找到Html文件，也可以用网络上的文件
            wv_about_content.loadUrl("file:///android_asset/about.html");
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }
}
