package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.PostAdapter;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/6.
 */
public class FindFragment extends Fragment {
    private final static String TAG = FindFragment.class.getName();

    private XRecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private ArrayList<String> listData;
    private int refreshTime = 0;
    private int times = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.content_find, container, false);
            mRecyclerView = (XRecyclerView) rootView.findViewById(R.id.xrv_post_list);
            View header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_post_list_header, container, false);
            mRecyclerView.addHeaderView(header);
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
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
            mRecyclerView.setArrowImageView(R.drawable.ic_arrow_down_gray);

//            View header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_post_list_header, (ViewGroup) getActivity().findViewById(R.id.xrv_post_list), false);
//            mRecyclerView.addHeaderView(header);

            //View myHeader = LayoutInflater.from(getActivity()).inflate(R.layout.layout_post_list_header, null);
            //mRecyclerView.addHeaderView(myHeader);

            mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    refreshTime++;
                    times = 0;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            listData.clear();
                            for (int i = 0; i < 15; i++) {
                                listData.add("item" + i + "after " + refreshTime + " times of refresh");
                            }
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete();
                        }

                    }, 1000);            //refresh data here
                }

                @Override
                public void onLoadMore() {
                    if (times < 2) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                mRecyclerView.loadMoreComplete();
                                for (int i = 0; i < 15; i++) {
                                    listData.add("item" + (i + listData.size()));
                                }
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.refreshComplete();
                            }
                        }, 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {

                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.loadMoreComplete();
                            }
                        }, 1000);
                    }
                    times++;
                }
            });

            listData = new ArrayList<String>();
            for (int i = 0; i < 15; i++) {
                listData.add("item" + (i + listData.size()));
            }
            mAdapter = new PostAdapter(listData);

            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }
}
