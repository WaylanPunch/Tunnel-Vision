package com.way.tunnelvision.ui.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.ImageAdapter;
import com.way.tunnelvision.entity.impl.ImageModelImpl;
import com.way.tunnelvision.entity.model.ImageModel;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.ImageLoaderUtil;
import com.way.tunnelvision.util.ImageUtil;
import com.way.tunnelvision.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends BaseActivity {
    private final static String TAG = ImageActivity.class.getName();

    //private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    //private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private ImageAdapter mImageAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ImageModel> imageModels = new ArrayList<>();
    private ImageModelImpl imageModelImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        initView();
    }

    private void initSystemBar() {
        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set a custom tint color for all system bars
        tintManager.setTintResource(R.color.colorPrimary);
        //tintManager.setTintColor(Color.parseColor("#99000FF"));
        // set a custom navigation bar resource
        //tintManager.setNavigationBarTintResource(R.drawable.my_tint);
        // set a custom status bar drawable
        //tintManager.setStatusBarTintDrawable(MyDrawable);
    }

    private void initView() {
        LogUtil.d(TAG, "initView debug, start");
        try {
            appBarLayout = (AppBarLayout) findViewById(R.id.abl_image_appbarlayout);
            Toolbar toolbar = (Toolbar) findViewById(R.id.tb_image_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

            imageModelImpl = new ImageModelImpl();

            //mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.srl_image_refresh_layout);
            mRecyclerView = (RecyclerView) findViewById(R.id.xrv_image_list);
            mProgressBar = (ProgressBar) findViewById(R.id.pb_image_progress);

//            mSwipeRefreshWidget.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorPrimaryLight, R.color.colorAccent);
//            mSwipeRefreshWidget.setOnRefreshListener(onRefreshListener);


            //列数为两列
//            int spanCount = 2;
//            mLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
//            mRecyclerView.setLayoutManager(mLayoutManager);

            LinearLayoutManager layoutManager = new LinearLayoutManager(ImageActivity.this);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);

            //设置layoutManager
            //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


            mImageAdapter = new ImageAdapter(ImageActivity.this);
            mImageAdapter.setContent(imageModels);
            mImageAdapter.setOnItemClickListener(onImageItemClickListener);
            mImageAdapter.setOnDownloadClickListener(onImageDownloadClickListener);
            mRecyclerView.setAdapter(mImageAdapter);
            //设置item之间的间隔
//            SpacesItemDecoration decoration=new SpacesItemDecoration(16);
//            mRecyclerView.addItemDecoration(decoration);


            refreshData();
            LogUtil.d(TAG, "initView debug,ImageModels COUNT = " + imageModels.size());
        } catch (Exception e) {
            LogUtil.e(TAG, "initView error", e);
        }
        LogUtil.d(TAG, "initView debug, end");
    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
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

    ImageAdapter.OnItemClickListener onImageItemClickListener = new ImageAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            LogUtil.d(TAG, "onImageItemClickListener debug, Click Item = " + position);
            ImageModel imageModel = imageModels.get(position);
            openActivityWithParcelable(ImageExpandActivity.class, imageModel);
        }
    };
    ImageAdapter.OnDownloadClickListener onImageDownloadClickListener = new ImageAdapter.OnDownloadClickListener() {

        @Override
        public void onDownloadClick(View view, int position) {
            ImageModel imageModel = imageModels.get(position);
            if (null != imageModel) {
                String imageUrl = imageModel.getSourceurl();
                String imageThumbUrl = imageModel.getThumburl();
                LogUtil.d(TAG, "onImageDownloadClickListener debug, Image Source Url = " + imageUrl);
                LogUtil.d(TAG, "onImageDownloadClickListener debug, Image Thumb Url = " + imageThumbUrl);
                int lastIndexSlash = 0;
                if (imageThumbUrl.contains("/")) {
                    lastIndexSlash = imageThumbUrl.lastIndexOf("/");
                } else {
                    if (imageThumbUrl.contains("\\")) {
                        lastIndexSlash = imageThumbUrl.lastIndexOf("\\");
                    }
                }
                final String fileName = imageThumbUrl.substring(lastIndexSlash + 1, imageThumbUrl.length());
                int lastIndexDot = 0;
                lastIndexDot = fileName.lastIndexOf(".");
                String fileExtension = fileName.substring(lastIndexDot, fileName.length());
                LogUtil.d(TAG, "onImageDownloadClickListener debug, Image Name = " + fileName);
                LogUtil.d(TAG, "onImageDownloadClickListener debug, Image Format = " + fileExtension);

                ImageLoaderUtil.downloadImage(imageUrl, new ImageLoaderUtil.OnDownloadImageListener() {

                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        if (null != bitmap) {
                            LogUtil.d(TAG, "onImageDownloadClickListener debug, bitmap != NULL");
                            ImageUtil.saveBitmapToExternalStorage(bitmap, fileName);
                            //saveBitmap(bitmap);
                            Toast.makeText(ImageActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ImageActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String msg, Exception e) {
                        LogUtil.e(TAG, "onImageDownloadClickListener error", e);
                        Toast.makeText(ImageActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    private void refreshData() {
        mProgressBar.setVisibility(View.VISIBLE);
        imageModelImpl.loadImageList(onLoadImageListListener);
    }

    ImageModelImpl.OnLoadImageListListener onLoadImageListListener = new ImageModelImpl.OnLoadImageListListener() {
        @Override
        public void onSuccess(List<ImageModel> list) {
            LogUtil.d(TAG, "onLoadImageListListener debug, onSuccess, ImageModels COUNT = " + list.size());
            imageModels = list;
            mImageAdapter.setContent(imageModels);
            mImageAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(String msg, Exception e) {
            Toast.makeText(ImageActivity.this, msg, Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.GONE);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_image_refresh) {
            try {
                if (imageModels != null) {
                    imageModels.clear();
                }
                refreshData();
            } catch (Exception e) {
                LogUtil.e(TAG, "onOptionsItemSelected error, action_image_refresh", e);
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
