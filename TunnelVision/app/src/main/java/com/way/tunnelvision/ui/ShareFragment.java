package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.way.tunnelvision.R;

/**
 * Created by pc on 2015/11/29.
 */
public class ShareFragment extends Fragment {
    private final static String TAG = ShareFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        Log.d(TAG, "onCreateView debug, end");
        return inflater.inflate(R.layout.content_share, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated debug, start");
        Log.d(TAG, "onActivityCreated debug, end");
    }
}
