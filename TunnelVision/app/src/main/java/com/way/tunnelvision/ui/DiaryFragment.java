package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.DiaryAdapter;
import com.way.tunnelvision.adapter.interf.OnRecyclerViewListener;
import com.way.tunnelvision.animator.RecyclerViewItemClickAnimator;
import com.way.tunnelvision.entity.Post;
import com.way.tunnelvision.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/6.
 */
public class DiaryFragment extends Fragment {
    private final static String TAG = DiaryFragment.class.getName();

    private FloatingActionButton fab;
    private XRecyclerView mRecyclerView;
    private DiaryAdapter mAdapter;
    private ArrayList<Post> listData;
    private int refreshTime = 0;
    private int times = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.content_diary, container, false);
            fab = (FloatingActionButton) rootView.findViewById(R.id.fab_diary_button);
            mRecyclerView = (XRecyclerView) rootView.findViewById(R.id.xrv_diary_list);
//            View header = LayoutInflater.from(getActivity()).inflate(R.layout.layout_post_list_header, container, false);
//            mRecyclerView.addHeaderView(header);
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
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action, Diary.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new RecyclerViewItemClickAnimator());
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
                            for (int i = 0; i < 1; i++) {
                                Post post1 = new Post();
                                post1.setContent("content " + i);
                                post1.setCreatedDate("2015-12-19");
                                post1.setIconResourceId("http://img0.ph.126.net/EnGLTAd9XWpTk4Q0LSzCOw==/6631364633840836611.jpg");
                                Post post2 = new Post();
                                post2.setContent("content " + i);
                                post2.setCreatedDate("2015-12-20");
                                post2.setIconResourceId("http://img1.ph.126.net/Q_duX-c5BQc65K8IeoOXyQ==/6631249185119739310.jpg");
                                listData.add(post1);
                                listData.add(post2);
                            }
                            Log.d(TAG, "initView debug, listData Size 1 = " + listData.size());
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
                                for (int i = 0; i < 1; i++) {
                                    Post post1 = new Post();
                                    post1.setContent("content " + i);
                                    post1.setCreatedDate("2015-12-21");
                                    post1.setIconResourceId("http://img0.ph.126.net/EnGLTAd9XWpTk4Q0LSzCOw==/6631364633840836611.jpg");
                                    Post post2 = new Post();
                                    post2.setContent("content " + i);
                                    post2.setCreatedDate("2015-12-22");
                                    post2.setIconResourceId("http://img1.ph.126.net/Q_duX-c5BQc65K8IeoOXyQ==/6631249185119739310.jpg");
                                    listData.add(post1);
                                    listData.add(post2);
                                }
                                Log.d(TAG, "initView debug, listData Size 2 = " + listData.size());
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

            listData = new ArrayList<Post>();
            for (int i = 0; i < 1; i++) {
                Post post1 = new Post();
                post1.setContent("content " + i);
                post1.setCreatedDate("2015-12-17");
                post1.setIconResourceId("http://img0.ph.126.net/EnGLTAd9XWpTk4Q0LSzCOw==/6631364633840836611.jpg");
                Post post2 = new Post();
                post2.setContent("content " + i);
                post2.setCreatedDate("2015-12-18");
                post2.setIconResourceId("http://img1.ph.126.net/Q_duX-c5BQc65K8IeoOXyQ==/6631249185119739310.jpg");
                listData.add(post1);
                listData.add(post2);
            }
            Log.d(TAG, "initView debug, listData Size 0 = " + listData.size());
            mAdapter = new DiaryAdapter(listData, getContext());
            mAdapter.setOnRecyclerViewListener(onRecyclerViewListener);
            mRecyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }

    OnRecyclerViewListener onRecyclerViewListener = new OnRecyclerViewListener() {
        @Override
        public void onItemClick(int position) {
            ToastUtil.show(getContext(), "Item Click " + position);
        }

        @Override
        public boolean onItemLongClick(int position) {
            ToastUtil.show(getContext(), "Item Long Click " + position);
            return false;
        }
    };
}
