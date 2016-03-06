package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.way.tunnelvision.R;
import com.way.tunnelvision.entity.impl.NewsModelImpl;
import com.way.tunnelvision.entity.model.NewsDetailModel;
import com.way.tunnelvision.entity.model.NewsModel;
import com.way.tunnelvision.ui.base.SwipeBackActivity;
import com.way.tunnelvision.ui.fragment.NewsFragment;
import com.way.tunnelvision.ui.widget.SwipeBackLayout;
import com.way.tunnelvision.util.ImageLoaderUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class NewsDetailActivity extends SwipeBackActivity {
    private final static String TAG = NewsDetailActivity.class.getName();

    private NewsModel mNews;
    private HtmlTextView mNewsDetailContent;
    private NewsModelImpl newsModelImpl;
    private ProgressBar mProgressBar;
    private SwipeBackLayout mSwipeBackLayout;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_news_detail_toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_news_detail_progressbar);
        mNewsDetailContent = (HtmlTextView) findViewById(R.id.htv_news_detail_content);
        fab = (FloatingActionButton) findViewById(R.id.fab_news_detail_refresh);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mSwipeBackLayout = getSwipeBackLayout();
        //mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        mNews = (NewsModel) getIntent().getParcelableExtra(NewsFragment.NEWS_ITEM_PARAMETER);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.ctl_news_detail_toolbarlayout);
        collapsingToolbar.setTitle(mNews.getTitle());

        newsModelImpl = new NewsModelImpl();
        refreshData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });
    }

    private void refreshData() {
        ImageLoaderUtil.display(getApplicationContext(), (ImageView) findViewById(R.id.iv_news_detail_toolbarbackgroud), mNews.getImgsrc());
        mProgressBar.setVisibility(View.VISIBLE);
        newsModelImpl.loadNewsDetail(mNews.getDocid(), onLoadNewsDetailListener);
    }

    NewsModelImpl.OnLoadNewsDetailListener onLoadNewsDetailListener = new NewsModelImpl.OnLoadNewsDetailListener() {
        @Override
        public void onSuccess(NewsDetailModel newsDetailModel) {
            String newsDetailContent = newsDetailModel.getBody();
            mNewsDetailContent.setHtmlFromString(newsDetailContent, new HtmlTextView.LocalImageGetter());
            mProgressBar.setVisibility(View.GONE);
            Snackbar.make(fab, "Refresh Finished", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

        @Override
        public void onFailure(String msg, Exception e) {
            mProgressBar.setVisibility(View.GONE);
            Snackbar.make(fab, "Refresh Failure", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
