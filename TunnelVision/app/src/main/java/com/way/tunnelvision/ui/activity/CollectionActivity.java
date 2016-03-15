package com.way.tunnelvision.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.NewsCollectionAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.NewsModel;
import com.way.tunnelvision.entity.service.NewsDaoHelper;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseActivity {
    private final static String TAG = CollectionActivity.class.getName();
    private List<NewsModel> newsModels = new ArrayList<>();
    private NewsDaoHelper newsDaoHelper;

    //private LinearLayout mToolbarContainer;
    //private int mToolbarHeight;

    private Toolbar mToolbar;
    private FloatingActionButton mFabButton;
    ;
    private RecyclerView xRecyclerView;
    private NewsCollectionAdapter newsCollectionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        mToolbar = (Toolbar) findViewById(R.id.tb_collection_toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.title_activity_collection));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //mToolbarHeight = SystemUtil.getToolbarHeight(this);

        //mToolbarContainer = (LinearLayout) findViewById(R.id.ll_collection_toolbarContainer);
        mFabButton = (FloatingActionButton) findViewById(R.id.fab_collection_refresh);
        xRecyclerView = (RecyclerView) findViewById(R.id.xrv_collection_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CollectionActivity.this);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setHasFixedSize(true);

        refreshData();
        initView();
    }

    private void initView() {
        LogUtil.d(TAG, "initView debug, start");
        try {
            mFabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshData();
                    newsCollectionAdapter.notifyDataSetChanged();
                }
            });

            newsCollectionAdapter = new NewsCollectionAdapter(CollectionActivity.this, newsModels);
            newsCollectionAdapter.setOnItemClickListener(recyclerOnItemClickListener);
            newsCollectionAdapter.setOnItemLongClickListener(recyclerOnLongItemClickListener);

            LogUtil.d(TAG, "initView debug, NewsModels COUNT = " + newsModels.size());
            xRecyclerView.setAdapter(newsCollectionAdapter);

            xRecyclerView.setOnScrollListener(new HidingScrollListener() {
                @Override
                public void onHide() {
                    hideViews();
                }

                @Override
                public void onShow() {
                    showViews();
                }
            });
        } catch (Exception e) {
            LogUtil.e(TAG, "initView error", e);
        }
        LogUtil.d(TAG, "initView debug, end");
    }

    private void refreshData() {
        LogUtil.d(TAG, "refreshData debug, start");
        try {
            newsModels.clear();
            newsDaoHelper = NewsDaoHelper.getInstance();
            newsModels = newsDaoHelper.getAllData();
            if (null != newsModels) {
                LogUtil.d(TAG, "refreshData debug, NewsModels COUNT = " + newsModels.size());
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "refreshData error", e);
        }
        LogUtil.d(TAG, "refreshData debug, end");
    }

    NewsCollectionAdapter.OnItemClickListener recyclerOnItemClickListener = new NewsCollectionAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            NewsModel newsItem = newsCollectionAdapter.getItem(position);
            Intent intent = new Intent(CollectionActivity.this, NewsDetailActivity.class);
            intent.putExtra(Constants.NEWSDETAILACTIVITY_PARAMETER, newsItem);

            View transitionView = view.findViewById(R.id.iv_news_item_normal_img);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CollectionActivity.this,
                    transitionView, getString(R.string.transition_news_img));
            ActivityCompat.startActivity(CollectionActivity.this, intent, options.toBundle());
        }
    };

    NewsCollectionAdapter.OnItemLongClickListener recyclerOnLongItemClickListener = new NewsCollectionAdapter.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(View view, int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);
            //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle(getString(R.string.text_dialog_title));
            builder.setMessage(getString(R.string.text_dialog_collect_info));
            builder.setPositiveButton(getString(R.string.text_dialog_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            builder.setNegativeButton(getString(R.string.text_dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_collection_edit) {

            try {
                refreshData();
                newsCollectionAdapter.notifyDataSetChanged();
                Toast.makeText(CollectionActivity.this, "sdsdssssssssssss", Toast.LENGTH_SHORT).show();
                LogUtil.d(TAG, "onOptionsItemSelected error, action_collection_refresh ");
            } catch (Exception e) {
                LogUtil.e(TAG, "onOptionsItemSelected error, action_collection_refresh", e);
                return false;
            }
        }
        return true;
    }

    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFabButton.animate().translationY(mFabButton.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
        private static final int HIDE_THRESHOLD = 20;
        private int scrolledDistance = 0;
        private boolean controlsVisible = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                onHide();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                onShow();
                controlsVisible = true;
                scrolledDistance = 0;
            }
            if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                scrolledDistance += dy;
            }
        }

        public abstract void onHide();

        public abstract void onShow();

    }

}
