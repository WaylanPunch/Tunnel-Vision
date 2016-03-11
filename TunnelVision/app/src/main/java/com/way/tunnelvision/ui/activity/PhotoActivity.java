package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.ItemAdapter;
import com.way.tunnelvision.ui.base.BaseActivity;

import java.util.ArrayList;

public class PhotoActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.xrv_photo_list);
        //列数为两列
        int spanCount = 2;
        mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //构建一个临时数据源
        for (int i = 0; i < 100; i++) {
            items.add("i:" + i);
        }
        mAdapter = new ItemAdapter(items);
        mRecyclerView.setAdapter(mAdapter);
    }

}
