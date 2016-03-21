package com.way.tunnelvision.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    private Toolbar toolbar;
    //private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
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

    private void initView() {
        LogUtil.d(TAG, "initView debug, start");
        try {
            toolbar = (Toolbar) findViewById(R.id.tb_image_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            imageModelImpl = new ImageModelImpl();

            //mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.srl_image_refresh_layout);
            mRecyclerView = (RecyclerView) findViewById(R.id.xrv_image_list);
            fab = (FloatingActionButton) findViewById(R.id.fab_image_refresh);
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

            mRecyclerView.setOnScrollListener(new HidingScrollListener() {
                @Override
                public void onHide() {
                    hideViews();
                }

                @Override
                public void onShow() {
                    showViews();
                }
            });

            refreshData();
            LogUtil.d(TAG, "initView debug,ImageModels COUNT = " + imageModels.size());
        } catch (Exception e) {
            LogUtil.e(TAG, "initView error", e);
        }
        LogUtil.d(TAG, "initView debug, end");
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

    /*
    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (imageModels != null) {
                imageModels.clear();
            }
            refreshData();
        }
    };
    */

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
                    && lastVisibleItem + 1 == mImageAdapter.getItemCount()) {
                //加载更多
                //Snackbar.make(findViewById(R.id.drawer_layout), getString(R.string.image_hit), Snackbar.LENGTH_SHORT).show();
            }
        }
    };
    */

    private void hideViews() {
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) fab.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        fab.animate().translationY(fab.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
        private static final int HIDE_THRESHOLD = 20;
        private int scrolledDistance = 0;
        private boolean controlsVisible = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            //show views if first item is first visible position and views are hidden
            if (firstVisibleItem == 0) {
                if (!controlsVisible) {
                    onShow();
                    controlsVisible = true;
                }
            } else {
                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    onHide();
                    controlsVisible = false;
                    scrolledDistance = 0;
                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                    onShow();
                    controlsVisible = true;
                    scrolledDistance = 0;
                }
            }
            if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                scrolledDistance += dy;
            }
        }

        public abstract void onHide();

        public abstract void onShow();

    }

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
