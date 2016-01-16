package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.NewsAdapter;
import com.way.tunnelvision.model.NewsModel;
import com.way.tunnelvision.util.TimeUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pc on 2016/1/6.
 */
public class NewsFragment extends Fragment {

    private XRecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 5;
    private int loadingTimes = 0;

    private List<NewsModel> mContentItems = new ArrayList<>();

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_news_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.ic_arrow_down_gray);

        initNewsListData();

        mAdapter = new RecyclerViewMaterialAdapter(new NewsAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                loadingTimes = 0;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mContentItems.clear();

                        NewsModel newsHeader = new NewsModel();
                        newsHeader.setNewsId("1111111111111");
                        newsHeader.setNewsType(0);
                        newsHeader.setNewsIcon(R.drawable.ic_drawer_header_background + "");
                        newsHeader.setNewsTitle("aaaaaaaaaaaaaaaaaa");
                        newsHeader.setNewsDescription("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                        newsHeader.setNewsTime(new Date());
                        mContentItems.add(newsHeader);

                        NewsModel newsCell = new NewsModel();
                        newsCell.setNewsId("2222222222222");
                        newsCell.setNewsType(1);
                        newsCell.setNewsIcon(R.drawable.ic_drawer_header_background + "");
                        newsCell.setNewsTitle("fffffffffffffffff");
                        newsCell.setNewsDescription("ggggggggggggggggggggggggggggggggggg");
                        Date date = TimeUtil.standardDateTime("2016-01-08 12:12:12");
                        newsCell.setNewsTime(date);
                        mContentItems.add(newsCell);

                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                if(loadingTimes < 5) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.loadMoreComplete();

                            NewsModel newsCell = new NewsModel();
                            newsCell.setNewsId("2222222222222");
                            newsCell.setNewsType(1);
                            newsCell.setNewsIcon(R.drawable.ic_drawer_header_background + "");
                            newsCell.setNewsTitle("fffffffffffffffff");
                            newsCell.setNewsDescription("ggggggggggggggggggggggggggggggggggg");
                            Date date = TimeUtil.standardDateTime("2016-01-08 12:12:12");
                            newsCell.setNewsTime(date);
                            mContentItems.add(newsCell);

                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete();
                        }
                    }, 1000);
                }else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.loadMoreComplete();
                        }
                    }, 1000);
                }
                loadingTimes++;
            }
        });
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    private void initNewsListData() {
        NewsModel newsHeader = new NewsModel();
        newsHeader.setNewsId("1111111111111");
        newsHeader.setNewsType(0);
        newsHeader.setNewsIcon(R.drawable.ic_drawer_header_background + "");
        newsHeader.setNewsTitle("aaaaaaaaaaaaaaaaaa");
        newsHeader.setNewsDescription("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        newsHeader.setNewsTime(new Date());
        mContentItems.add(newsHeader);
        for (int i = 0; i < ITEM_COUNT; ++i) {
            NewsModel newsCell = new NewsModel();
            newsCell.setNewsId("2222222222222");
            newsCell.setNewsType(1);
            newsCell.setNewsIcon(R.drawable.ic_drawer_header_background + "");
            newsCell.setNewsTitle("fffffffffffffffff");
            newsCell.setNewsDescription("ggggggggggggggggggggggggggggggggggg");
            Date date = TimeUtil.standardDateTime("2016-01-08 12:12:12");
            newsCell.setNewsTime(date);
            mContentItems.add(newsCell);
        }
    }
}

