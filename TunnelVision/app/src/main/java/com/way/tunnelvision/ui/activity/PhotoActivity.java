package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.PhotoAdapter;
import com.way.tunnelvision.entity.impl.ImageModelImpl;
import com.way.tunnelvision.entity.model.ImageModel;
import com.way.tunnelvision.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends BaseActivity {
    private final static String TAG = PhotoActivity.class.getName();

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private PhotoAdapter mPhotoAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ImageModel> imageModels = new ArrayList<>();
    private ImageModelImpl imageModelImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try {
            imageModelImpl = new ImageModelImpl();


            mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
            mRecyclerView = (RecyclerView) findViewById(R.id.xrv_photo_list);

            mSwipeRefreshWidget.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark, R.color.colorPrimaryLight, R.color.colorAccent);
            mSwipeRefreshWidget.setOnRefreshListener(onRefreshListener);


            //列数为两列
//            int spanCount = 2;
//            mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
//            mRecyclerView.setLayoutManager(mLayoutManager);

            LinearLayoutManager layoutManager = new LinearLayoutManager(PhotoActivity.this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);

            //设置layoutManager
            //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


            mPhotoAdapter = new PhotoAdapter(PhotoActivity.this);
            mPhotoAdapter.setContent(imageModels);
            mPhotoAdapter.setOnItemClickListener(onImageItemClickListener);
            mRecyclerView.setAdapter(mPhotoAdapter);
            //设置item之间的间隔
//            SpacesItemDecoration decoration=new SpacesItemDecoration(16);
//            mRecyclerView.addItemDecoration(decoration);

            //mRecyclerView.setOnScrollListener(mOnScrollListener);

            refreshData();
            Log.d(TAG, "initView debug,ImageModels COUNT = " + imageModels.size());
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }

    /*
    private void initData() {
        for(int i=0;i<20;i++){
            ImageModel im = new ImageModel();
            im.setTitle("ddd");
            imageModels.add(im);
        }
    }
    */

    PhotoAdapter.OnItemClickListener onImageItemClickListener = new PhotoAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(PhotoActivity.this,"Item Position = "+position,Toast.LENGTH_SHORT).show();
        }
    };

    private void refreshData() {
        mSwipeRefreshWidget.setRefreshing(true);
        imageModelImpl.loadImageList(onLoadImageListListener);
    }

    ImageModelImpl.OnLoadImageListListener onLoadImageListListener = new ImageModelImpl.OnLoadImageListListener() {
        @Override
        public void onSuccess(List<ImageModel> list) {
            Log.d(TAG, "onLoadImageListListener debug, onSuccess, ImageModels COUNT = " + list.size());
            imageModels = list;
            mPhotoAdapter.setContent(imageModels);
            mPhotoAdapter.notifyDataSetChanged();
            mSwipeRefreshWidget.setRefreshing(false);
        }

        @Override
        public void onFailure(String msg, Exception e) {
            Toast.makeText(PhotoActivity.this, msg, Toast.LENGTH_SHORT).show();
            mSwipeRefreshWidget.setRefreshing(false);
        }
    };

    ///*
    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (imageModels != null) {
                imageModels.clear();
            }
            refreshData();
        }
    };
    //*/

    /*
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //lastVisibleItem = mLayoutManager.las();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mPhotoAdapter.getItemCount()) {
                //加载更多
                //Snackbar.make(findViewById(R.id.drawer_layout), getString(R.string.image_hit), Snackbar.LENGTH_SHORT).show();
            }
        }
    };
    */
}
