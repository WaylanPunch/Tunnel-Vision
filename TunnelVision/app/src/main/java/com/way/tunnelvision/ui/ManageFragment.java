package com.way.tunnelvision.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.way.tunnelvision.R;

/**
 * Created by pc on 2015/11/29.
 */
public class ManageFragment extends Fragment {
    private final static String TAG = ManageFragment.class.getName();

    private TextView tv_manage_home;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        Log.d(TAG, "onCreateView debug, end");
        return inflater.inflate(R.layout.content_manage, container, false);
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
        tv_manage_home = (TextView) getView().findViewById(R.id.tv_manage_home);
        Log.d(TAG, "findView debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        tv_manage_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "initView tv_manage_home OnClickListener onClick debug, start");
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                getActivity().startActivity(intent);
                Log.d(TAG, "initView tv_manage_home OnClickListener onClick debug, end");
            }
        });
        Log.d(TAG, "initView debug, end");
    }
}
