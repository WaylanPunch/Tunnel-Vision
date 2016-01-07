package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.way.tunnelvision.R;

/**
 * Created by pc on 2016/1/6.
 */
public class AdvertisementFragment extends Fragment {

    private ObservableWebView mWebView;

    public static AdvertisementFragment newInstance() {
        return new AdvertisementFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advertisement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView = (ObservableWebView) view.findViewById(R.id.wv_advertisement_show);

        //must be called before loadUrl()
        MaterialViewPagerHelper.preLoadInjectHeader(mWebView);

        //have to inject header when WebView page loaded
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                MaterialViewPagerHelper.injectHeader(mWebView, true);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.loadUrl("http://mobile.francetvinfo.fr/");

        MaterialViewPagerHelper.registerWebView(getActivity(), mWebView, null);
    }
}
