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
import com.way.tunnelvision.adapter.CollectionAdapter;
import com.way.tunnelvision.adapter.interf.OnRecyclerViewListener;
import com.way.tunnelvision.animator.RecyclerViewItemClickAnimator;
import com.way.tunnelvision.entity.Post;
import com.way.tunnelvision.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/6.
 */
public class CollectionFragment extends Fragment {
    private final static String TAG = CollectionFragment.class.getName();

    private XRecyclerView mRecyclerView;
    private CollectionAdapter mAdapter;
    private ArrayList<Post> listData;
    private int refreshTime = 0;
    private int times = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        View rootView = null;
        try{
            rootView = inflater.inflate(R.layout.content_collection, container, false);
            mRecyclerView = (XRecyclerView) rootView.findViewById(R.id.xrv_collection_list);
        }catch (Exception e){
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
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new RecyclerViewItemClickAnimator());
            mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
            mRecyclerView.setArrowImageView(R.drawable.ic_arrow_down_gray);

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
                                post1.setUserId("111111");
                                post1.setUsername("dfdfdf");
                                post1.setDisplayName("sdfsdfsdf");
                                post1.setGender(1);
                                post1.setAvatarResourceId("http://i4.tietuku.com/8a9db62c02200200.png");
                                post1.setContent("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
                                post1.setCreatedDate("2015-12-12");
                                post1.setRepeatsCount(i);
                                post1.setCommentsCount(i);
                                post1.setLikesCount(i);
                                post1.setIconResourceId("http://img0.ph.126.net/EnGLTAd9XWpTk4Q0LSzCOw==/6631364633840836611.jpg");
                                Post post2 = new Post();
                                post2.setUserId("22222");
                                post2.setUsername("gtgtgtg");
                                post2.setDisplayName("hyhyhyhy");
                                post2.setGender(0);
                                post2.setAvatarResourceId("http://i4.tietuku.com/c8ddf60965321181.png");
                                post2.setContent("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                                post2.setCreatedDate("2015-12-13");
                                post2.setRepeatsCount(i);
                                post2.setCommentsCount(i);
                                post2.setLikesCount(i);
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
                                    post1.setUserId("333333");
                                    post1.setUsername("hyhyhyhy");
                                    post1.setDisplayName("hyhyhyhy");
                                    post1.setGender(1);
                                    post1.setAvatarResourceId("http://i4.tietuku.com/8a9db62c02200200.png");
                                    post1.setContent("ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
                                    post1.setCreatedDate("2015-12-14");
                                    post1.setRepeatsCount(i);
                                    post1.setCommentsCount(i);
                                    post1.setLikesCount(i);
                                    post1.setIconResourceId("http://img0.ph.126.net/EnGLTAd9XWpTk4Q0LSzCOw==/6631364633840836611.jpg");
                                    Post post2 = new Post();
                                    post2.setUserId("444444");
                                    post2.setUsername("jujujuju");
                                    post2.setDisplayName("jujujuu");
                                    post2.setGender(0);
                                    post2.setAvatarResourceId("http://i4.tietuku.com/c8ddf60965321181.png");
                                    post2.setContent("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
                                    post2.setCreatedDate("2015-12-15");
                                    post2.setRepeatsCount(i);
                                    post2.setCommentsCount(i);
                                    post2.setLikesCount(i);
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
                post1.setUserId("555555");
                post1.setUsername("dwdwdwdwdw");
                post1.setDisplayName("dwdwdwdw");
                post1.setGender(1);
                post1.setAvatarResourceId("http://i4.tietuku.com/8a9db62c02200200.png");
                post1.setContent("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                post1.setCreatedDate("2015-12-14");
                post1.setRepeatsCount(i);
                post1.setCommentsCount(i);
                post1.setLikesCount(i);
                post1.setIconResourceId("http://img0.ph.126.net/EnGLTAd9XWpTk4Q0LSzCOw==/6631364633840836611.jpg");
                Post post2 = new Post();
                post2.setUserId("666666");
                post2.setUsername("kukukuku");
                post2.setDisplayName("kukukuku");
                post2.setGender(0);
                post2.setAvatarResourceId("http://i4.tietuku.com/c8ddf60965321181.png");
                post2.setContent("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
                post2.setCreatedDate("2015-12-15");
                post2.setRepeatsCount(i);
                post2.setCommentsCount(i);
                post2.setLikesCount(i);
                post2.setIconResourceId("http://img1.ph.126.net/Q_duX-c5BQc65K8IeoOXyQ==/6631249185119739310.jpg");
                listData.add(post1);
                listData.add(post2);
            }
            Log.d(TAG, "initView debug, listData Size 0 = " + listData.size());
            mAdapter = new CollectionAdapter(listData, getContext());
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
            ToastUtil.show(getActivity(), "Item Click : " + position);
        }

        @Override
        public boolean onItemLongClick(int position) {
            ToastUtil.show(getActivity(), "Item Long Click : " + position);
            return false;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        listData = null;
    }
}
