package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.NewsAdapter;
import com.way.tunnelvision.model.NewsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pc on 2016/1/6.
 */
public class NewsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private static final int ITEM_COUNT = 100;

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_recommend_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new NewsAdapter(mContentItems));
        mRecyclerView.setAdapter(mAdapter);

        {
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
                String dateStr = "2016-01-08 12:12:12";
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = format.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    date = new Date();
                }
                newsCell.setNewsTime(date);
                mContentItems.add(newsCell);
            }
            mAdapter.notifyDataSetChanged();
        }

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}

