package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.FriendAdapter;
import com.way.tunnelvision.animator.RecyclerViewItemClickAnimator;
import com.way.tunnelvision.entity.Friend;
import com.way.tunnelvision.util.ToastUtil;
import com.way.tunnelvision.view.IconCenterEditText;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/6.
 */
public class FriendFragment extends Fragment {
    private final String TAG = FriendFragment.class.getName();

    private XRecyclerView friendRecyclerView;
    private FloatingActionButton fab;
    private static ArrayList<Friend> friends;
    private FriendAdapter friendAdapter;
    private static int times = 0;
    private IconCenterEditText icet_search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.content_friend, container, false);
            friendRecyclerView = (XRecyclerView) rootView.findViewById(R.id.xrv_friend_list);
            fab = (FloatingActionButton) rootView.findViewById(R.id.fab_friend_button);
            icet_search = (IconCenterEditText) rootView.findViewById(R.id.icet_friend_search);
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
                public void onClick(View v) {
                    /*
                    if (null != friends && null != friendAdapter) {
                        for (int i = 0; i < 2; i++) {
                            Friend friend = new Friend();
                            friend.setDisplayName("fis1");
                            friend.setIconResourceId("http://img0.ph.126.net/EnGLTAd9XWpTk4Q0LSzCOw==/6631364633840836611.jpg");
                            friend.setSignature("sig1");
                            friend.setUserId("id1");
                            friend.setUsername("name1");
                            friends.add(friend);
                        }
                        Log.d(TAG, "initView debug, Friends Size from button = " + friends.size());
                        friendAdapter.notifyDataSetChanged();
                        friendRecyclerView.refreshComplete();
                    }
                    */
                }
            });

            // 实现TextWatcher监听即可
            icet_search.setOnSearchClickListener(new IconCenterEditText.OnSearchClickListener() {
                @Override
                public void onSearchClick(View view) {
                    ToastUtil.show(getContext(), "i'm going to seach");
                }
            });

            friendRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            friendRecyclerView.setLayoutManager(layoutManager);
            friendRecyclerView.setItemAnimator(new RecyclerViewItemClickAnimator());
            friendRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            friendRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
            friendRecyclerView.setArrowImageView(R.drawable.ic_arrow_down_gray);
            friendRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            times = 0;
                            for (int i = 0; i < 2; i++) {
                                Friend friend = new Friend();
                                friend.setDisplayName("dis1");
                                friend.setIconResourceId("http://img0.ph.126.net/EnGLTAd9XWpTk4Q0LSzCOw==/6631364633840836611.jpg");
                                friend.setSignature("sig1");
                                friend.setUserId("id1");
                                friend.setUsername("name1");
                                friend.setGender(0);
                                friends.add(friend);
                            }
                            Log.d(TAG, "initView debug, Friends Size 1 = " + friends.size());
                            friendAdapter.notifyDataSetChanged();
                            friendRecyclerView.refreshComplete();
                        }
                    }, 1000);
                }

                @Override
                public void onLoadMore() {
                    if (times < 2) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                friendRecyclerView.loadMoreComplete();
                                for (int i = 0; i < 2; i++) {
                                    Friend friend = new Friend();
                                    friend.setDisplayName("cis2");
                                    friend.setIconResourceId("http://img1.ph.126.net/Q_duX-c5BQc65K8IeoOXyQ==/6631249185119739310.jpg");
                                    friend.setSignature("sig2");
                                    friend.setUserId("id2");
                                    friend.setUsername("name2");
                                    friend.setGender(1);
                                    friends.add(friend);
                                }
                                Log.d(TAG, "initView debug, Friends Size 2 = " + friends.size());
                                friendAdapter.notifyDataSetChanged();
                                friendRecyclerView.refreshComplete();
                            }
                        }, 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                friendAdapter.notifyDataSetChanged();
                                friendRecyclerView.loadMoreComplete();
                            }
                        }, 1000);
                    }
                    times++;
                }
            });

            friends = new ArrayList<Friend>();
            for (int i = 0; i < 2; i++) {
                Friend friend = new Friend();
                friend.setDisplayName("ais0");
                friend.setIconResourceId("http://img0.ph.126.net/EnGLTAd9XWpTk4Q0LSzCOw==/6631364633840836611.jpg");
                friend.setSignature("sig0");
                friend.setUserId("id0");
                friend.setUsername("name0");
                friend.setGender(1);
                friends.add(friend);
            }
            Log.d(TAG, "initView debug, Friends Size 0 = " + friends.size());
            friendAdapter = new FriendAdapter(friends, getContext());
            friendAdapter.setOnRecyclerViewListener(onRecyclerViewListener);
            friendRecyclerView.setAdapter(friendAdapter);
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }

    FriendAdapter.OnRecyclerViewListener onRecyclerViewListener = new FriendAdapter.OnRecyclerViewListener() {
        @Override
        public void onItemClick(int position) {
            ToastUtil.show(getContext(), "Item Click : " + position);
        }

        @Override
        public boolean onItemLongClick(int position) {
            ToastUtil.show(getContext(), "Item Long Click : " + position);
            return false;
        }
    };


}
