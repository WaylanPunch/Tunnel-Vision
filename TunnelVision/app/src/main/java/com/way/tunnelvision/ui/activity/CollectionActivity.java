package com.way.tunnelvision.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.NewsCollectionAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.NewsModel;
import com.way.tunnelvision.entity.service.NewsDaoHelper;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class CollectionActivity extends BaseActivity {
    private final static String TAG = CollectionActivity.class.getName();
    private List<NewsModel> newsModels = new ArrayList<>();
    private NewsDaoHelper newsDaoHelper;

    private LinearLayout mToolbarContainer;
    private int mToolbarHeight;

    private RecyclerView xRecyclerView;
    private NewsCollectionAdapter newsCollectionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_collection_toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_collection));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mToolbarHeight = SystemUtil.getToolbarHeight(this);

        mToolbarContainer = (LinearLayout) findViewById(R.id.ll_collection_toolbarContainer);
        xRecyclerView = (RecyclerView) findViewById(R.id.xrv_collection_list);

        refreshData();
        initView();
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try {
            LinearLayoutManager layoutManager = new LinearLayoutManager(CollectionActivity.this);
            xRecyclerView.setLayoutManager(layoutManager);
            xRecyclerView.setHasFixedSize(true);


            newsCollectionAdapter = new NewsCollectionAdapter(CollectionActivity.this, newsModels);
            newsCollectionAdapter.setOnItemClickListener(recyclerOnItemClickListener);
            newsCollectionAdapter.setOnItemLongClickListener(recyclerOnLongItemClickListener);

            Log.d(TAG, "initView debug, NewsModels COUNT = " + newsModels.size());
            xRecyclerView.setAdapter(newsCollectionAdapter);

            xRecyclerView.setOnScrollListener(new HidingScrollListener(this) {

                @Override
                public void onMoved(int distance) {
                    mToolbarContainer.setTranslationY(-distance);
                }

                @Override
                public void onShow() {
                    mToolbarContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                }

                @Override
                public void onHide() {
                    mToolbarContainer.animate().translationY(-mToolbarHeight).setInterpolator(new AccelerateInterpolator(2)).start();
                }

            });
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }

    private void refreshData() {
        Log.d(TAG, "refreshData debug, start");
        try {
            newsModels.clear();
            newsDaoHelper = NewsDaoHelper.getInstance();
            newsModels = newsDaoHelper.getAllData();
            if (null != newsModels) {
                Log.d(TAG, "refreshData debug, NewsModels COUNT = " + newsModels.size());
            }
        } catch (Exception e) {
            Log.e(TAG, "refreshData error", e);
        }
        Log.d(TAG, "refreshData debug, end");
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
        MenuItem item = menu.getItem(0);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(CollectionActivity.this, "sdsdssssssssssss", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_collection_refresh) {

            try {
                refreshData();
                newsCollectionAdapter.notifyDataSetChanged();
                Toast.makeText(CollectionActivity.this, "sdsdssssssssssss", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onOptionsItemSelected error, action_collection_refresh ");
            } catch (Exception e) {
                Log.e(TAG, "onOptionsItemSelected error, action_collection_refresh", e);
                return false;
            }
        }
        return true;
    }


    public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

        private static final float HIDE_THRESHOLD = 10;
        private static final float SHOW_THRESHOLD = 70;

        private int mToolbarOffset = 0;
        private boolean mControlsVisible = true;
        private int mToolbarHeight;

        public HidingScrollListener(Context context) {
            mToolbarHeight = SystemUtil.getToolbarHeight(context);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mControlsVisible) {
                    if (mToolbarOffset > HIDE_THRESHOLD) {
                        setInvisible();
                    } else {
                        setVisible();
                    }
                } else {
                    if ((mToolbarHeight - mToolbarOffset) > SHOW_THRESHOLD) {
                        setVisible();
                    } else {
                        setInvisible();
                    }
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            clipToolbarOffset();
            onMoved(mToolbarOffset);

            if ((mToolbarOffset < mToolbarHeight && dy > 0) || (mToolbarOffset > 0 && dy < 0)) {
                mToolbarOffset += dy;
            }

        }

        private void clipToolbarOffset() {
            if (mToolbarOffset > mToolbarHeight) {
                mToolbarOffset = mToolbarHeight;
            } else if (mToolbarOffset < 0) {
                mToolbarOffset = 0;
            }
        }

        private void setVisible() {
            if (mToolbarOffset > 0) {
                onShow();
                mToolbarOffset = 0;
            }
            mControlsVisible = true;
        }

        private void setInvisible() {
            if (mToolbarOffset < mToolbarHeight) {
                onHide();
                mToolbarOffset = mToolbarHeight;
            }
            mControlsVisible = false;
        }

        public abstract void onMoved(int distance);

        public abstract void onShow();

        public abstract void onHide();
    }
}
