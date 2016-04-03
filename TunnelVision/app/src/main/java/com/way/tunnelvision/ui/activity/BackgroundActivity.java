package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.BackgroundAdapter;
import com.way.tunnelvision.entity.model.HeaderImageModel;
import com.way.tunnelvision.entity.service.HeaderImageDaoHelper;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class BackgroundActivity extends BaseActivity {
    private final static String TAG = BackgroundActivity.class.getName();

    private HeaderImageDaoHelper headerImageDaoHelper;
    private BackgroundAdapter backgroundAdapter;
    private List<HeaderImageModel> headerImages = new ArrayList<>();

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private int CHECK_INDEX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);

        toolbar = (Toolbar) findViewById(R.id.tb_background_toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_background));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        recyclerView = (RecyclerView) findViewById(R.id.rv_background_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        initData();

        if (headerImages == null) {
            headerImages = new ArrayList<>();
        }
        backgroundAdapter = new BackgroundAdapter(BackgroundActivity.this, headerImages);
        recyclerView.setAdapter(backgroundAdapter);
        backgroundAdapter.notifyDataSetChanged();

        backgroundAdapter.setOnItemClickListener(new BackgroundAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HeaderImageModel item = headerImages.get(position);
                if (item.getChosen() == 0) {
                    Toast.makeText(BackgroundActivity.this, "默认背景", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backgroundAdapter.setOnItemCheckedChangeListener(new BackgroundAdapter.OnItemCheckedChangeListener() {
            @Override
            public void onItemCheckedChange(View view, int position) {
                LogUtil.d(TAG, "onCreate debug, onItemCheckedChange, Position = " + position);
                try {
                    CheckBox cb = (CheckBox) view;
                    HeaderImageModel item = (HeaderImageModel) cb.getTag();
                    if (cb.isChecked()) {
                        item.setChosen(1);
                    }
                    if (cb.isChecked()) {
                        if (CHECK_INDEX != 0) {
                            HeaderImageModel oldItem = headerImages.get(CHECK_INDEX);
                            oldItem.setChosen(2);
                            headerImageDaoHelper.updateData(oldItem);
                        }
                        item.setChosen(1);
                        headerImageDaoHelper.updateData(item);
                        CHECK_INDEX = position;
                    } else {
                        item.setChosen(2);
                        headerImageDaoHelper.updateData(item);
                        CHECK_INDEX = 0;
                    }
                    if (headerImages == null) {
                        headerImages = new ArrayList<>();
                    } else {
                        headerImages.clear();
                    }
                    headerImages = headerImageDaoHelper.getAllDataByNumber(10);
                    backgroundAdapter.setContents(headerImages);
                    backgroundAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    LogUtil.e(TAG, "onCreate error, onItemCheckedChange", e);
                }
            }
        });
    }

    private void initData() {
        if (headerImages == null) {
            headerImages = new ArrayList<>();
        } else {
            headerImages.clear();
        }
        if (headerImageDaoHelper == null) {
            headerImageDaoHelper = HeaderImageDaoHelper.getInstance();
        }
        headerImages = headerImageDaoHelper.getAllDataByNumber(10);
        if (headerImages != null && headerImages.size() > 0) {
            LogUtil.d(TAG, "initData debug, HeaderImages COUNT = " + headerImages.size());
        } else {
            LogUtil.d(TAG, "initData debug, HeaderImages == NULL");
        }
    }

}
