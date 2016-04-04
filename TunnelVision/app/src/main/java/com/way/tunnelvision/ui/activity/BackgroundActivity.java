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
    private static int CHECK_ITEM_ID = 0;

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
                LogUtil.d(TAG, "onCreate debug, onItemCheckedChange, Old CHECK_ITEM_ID = " + CHECK_ITEM_ID);
                try {
                    CheckBox cb = (CheckBox) view;
                    HeaderImageModel newItem = (HeaderImageModel) cb.getTag();
                    if (cb.isChecked()) {
                        LogUtil.d(TAG, "onCreate debug, onItemCheckedChange, CheckBox Is Checked.");
                        if (CHECK_ITEM_ID != 0) {
                            HeaderImageModel oldItem = headerImages.get(CHECK_ITEM_ID);//getItemByIdFromData(CHECK_ITEM_ID);
                            if(oldItem != null) {
                                oldItem.setChosen(2);
                                headerImageDaoHelper.updateData(oldItem);
                                headerImages.set(CHECK_ITEM_ID, oldItem);
                            }
                        }
                        newItem.setChosen(1);
                        headerImageDaoHelper.updateData(newItem);
                        CHECK_ITEM_ID = position;//newItem.getId();
                    } else {
                        LogUtil.d(TAG, "onCreate debug, onItemCheckedChange, CheckBox Is Unchecked.");
                        newItem.setChosen(2);
                        headerImageDaoHelper.updateData(newItem);
                        CHECK_ITEM_ID = 0;
                    }
                    headerImages.set(position, newItem);
                    /*
                    if (headerImages == null) {
                        headerImages = new ArrayList<>();
                    } else {
                        headerImages.clear();
                    }
                    headerImages = headerImageDaoHelper.getAllDataByNumber(10);
                    backgroundAdapter.setContents(headerImages);
                    */
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
            for(int i=0; i<headerImages.size(); i++){
                if(headerImages.get(i).getChosen() == 1) {
                    CHECK_ITEM_ID = i;//headerImages.get(i).getId();
                }
            }
            LogUtil.d(TAG, "initData debug, CHECK_ITEM_ID = " + CHECK_ITEM_ID);
        } else {
            LogUtil.d(TAG, "initData debug, HeaderImages == NULL");
        }
    }

    private HeaderImageModel getItemByIdFromData(long id){
        //HeaderImageModel item = null;
        try {
            for(HeaderImageModel item1:headerImages){
                if(item1.getId() == id){
                    LogUtil.d(TAG, "getItemByIdFromData debug, return Item ID = " + id);
                    return item1;
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getItemByIdFromData error", e);
        }
        LogUtil.d(TAG, "getItemByIdFromData debug, return Item = NULL");
        return null;
    }

}
