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
import com.way.tunnelvision.adapter.LatestMessageAdapter;
import com.way.tunnelvision.adapter.interf.OnRecyclerViewListener;
import com.way.tunnelvision.animator.RecyclerViewItemClickAnimator;
import com.way.tunnelvision.entity.LatestMessage;
import com.way.tunnelvision.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by pc on 2015/12/6.
 */
public class ChatFragment extends Fragment {
    private final static String TAG = ChatFragment.class.getName();

    private FloatingActionButton fab;
    private XRecyclerView mRecyclerView;
    private LatestMessageAdapter mAdapter;
    private static ArrayList<LatestMessage> listData;
    private int refreshTime = 0;
    private int times = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView debug, start");
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.content_chat, container, false);
            fab = (FloatingActionButton) rootView.findViewById(R.id.fab_chat_button);
            mRecyclerView = (XRecyclerView) rootView.findViewById(R.id.xrv_chat_list);
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
                    Snackbar.make(view, "Replace with your own action, chat.", Snackbar.LENGTH_LONG)
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

            refreshTime = 0;
            times = 0;
            mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    if (refreshTime < 2) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //mRecyclerView.refreshComplete();
                                //listData.clear();
                                for (int i = 0; i < 1; i++) {
                                    LatestMessage latestMessage = new LatestMessage();
                                    latestMessage.setUserName("username 111");
                                    latestMessage.setLatestContent("content 111");
                                    latestMessage.setUserID("userid 111");
                                    latestMessage.setLatestDateTime("2015-12-11");
                                    latestMessage.setAvatarResourceId("http://i12.tietuku.com/f45f8b32289d8797.jpg");
                                    latestMessage.setDisplayName("displayname 111");
                                    listData.add(latestMessage);
                                }
                                Log.d(TAG, "initView debug, listData Size 1 = " + listData.size());
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.refreshComplete();
                            }

                        }, 1000);            //refresh data here
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.refreshComplete();
                            }
                        }, 1000);
                    }
                    refreshTime++;
                }

                @Override
                public void onLoadMore() {
                    if (times < 2) {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                mRecyclerView.loadMoreComplete();
                                for (int i = 0; i < 1; i++) {
                                    LatestMessage latestMessage = new LatestMessage();
                                    latestMessage.setUserName("username 222");
                                    latestMessage.setLatestContent("content 222");
                                    latestMessage.setUserID("userid 222");
                                    latestMessage.setLatestDateTime("2015-12-12");
                                    latestMessage.setAvatarResourceId("http://i12.tietuku.com/f45f8b32289d8797.jpg");
                                    latestMessage.setDisplayName("displayname 222");
                                    listData.add(latestMessage);
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

            listData = new ArrayList<LatestMessage>();
            for (int i = 0; i < 1; i++) {
                LatestMessage latestMessage = new LatestMessage();
                latestMessage.setUserName("username 000");
                latestMessage.setLatestContent("content 000");
                latestMessage.setUserID("userid 000");
                latestMessage.setLatestDateTime("2015-12-14");
                latestMessage.setAvatarResourceId("http://i12.tietuku.com/f45f8b32289d8797.jpg");
                latestMessage.setDisplayName("displayname 000");
                listData.add(latestMessage);
            }
            Log.d(TAG, "initView debug, listData Size 0 = " + listData.size());
            mAdapter = new LatestMessageAdapter(listData, getContext());
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
            ToastUtil.show(getActivity(), "Item Click");
        }

        @Override
        public boolean onItemLongClick(int position) {
            ToastUtil.show(getActivity(), "Item Long Click");
            return false;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        listData = null;
    }
}