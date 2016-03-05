package com.way.tunnelvision.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.NewsAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.impl.NewsModelImpl;
import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.entity.model.NewsModel;
import com.way.tunnelvision.ui.activity.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2016/1/6.
 */
public class NewsFragment extends Fragment {
    private final static String TAG = NewsFragment.class.getName();
    public static final String NEWS_ITEM_PARAMETER = "NEWS_DETAIL";

    private XRecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    private int loadingTimes = 0;

    private ChannelModel channelModel;
    private NewsAdapter mNewsAdapter;
    private List<NewsModel> mContentItems = new ArrayList<>();

    private int mType = Constants.NEWS.NEWS_TYPE_TOP;
    private NewsModelImpl newsModelImpl;
    private int pageIndex = 0;

    public static NewsFragment newInstance(ChannelModel channelItem) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.NEWSFRAGMENT_PARAMETER, channelItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate debug, start");
        try {
            newsModelImpl = new NewsModelImpl();
            Bundle bundle = getArguments();
            if (bundle != null) {
                channelModel = (ChannelModel) bundle.getParcelable(Constants.NEWSFRAGMENT_PARAMETER);
                Log.d(TAG, "onCreate debug, Channel GUID = " + channelModel.getChannelGUID());
                Log.d(TAG, "onCreate debug, Channel Title = " + channelModel.getChannelTitle());
                Log.d(TAG, "onCreate debug, Channel Name = " + channelModel.getChannelName());
                Log.d(TAG, "onCreate debug, Channel Type = " + channelModel.getChannelType());
                Log.d(TAG, "onCreate debug, Channel Link = " + channelModel.getChannelLink());
                mType = channelModel.getChannelType();
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate error", e);
        }
        Log.d(TAG, "onCreate debug, end");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (XRecyclerView) view.findViewById(R.id.rv_news_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.ic_arrow_down_gray);

        initNewsListData();
        mNewsAdapter = new NewsAdapter(getActivity(), mContentItems);
        mNewsAdapter.setOnItemClickListener(recyclerOnItemClickListener);
        mNewsAdapter.setOnItemLongClickListener(recyclerOnLongItemClickListener);

        mAdapter = new RecyclerViewMaterialAdapter(mNewsAdapter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                pageIndex = 0;
                mContentItems.clear();
                Log.d(TAG, "onViewCreated onLoadMore debug, Refresh, Begin");

                newsModelImpl.loadNews(mType, pageIndex, new NewsModelImpl.OnLoadNewsListListener() {
                    @Override
                    public void onSuccess(List<NewsModel> list) {
                        mContentItems.addAll(list);
                        Log.d(TAG, "onViewCreated onRefresh debug, Refresh, News Count = " + list.size());
                    }

                    @Override
                    public void onFailure(String msg, Exception e) {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.loadMoreComplete();
                Log.d(TAG, "onViewCreated onLoadMore debug, Load More, Begin");
                newsModelImpl.loadNews(mType, pageIndex + Constants.NEWS.PAGE_SIZE, new NewsModelImpl.OnLoadNewsListListener() {
                    @Override
                    public void onSuccess(List<NewsModel> list) {
                        mContentItems.addAll(list);
                        Log.d(TAG, "onViewCreated onLoadMore debug, Load More, News Count = " + list.size());
                        pageIndex += Constants.NEWS.PAGE_SIZE;
                    }

                    @Override
                    public void onFailure(String msg, Exception e) {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }
        });
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);


    }

    private void initNewsListData() {
        //mRecyclerView.noMoreLoading();
        //mRecyclerView.re
        pageIndex = 0;
        mContentItems.clear();
        Log.d(TAG, "initNewsListData debug, start");

        newsModelImpl.loadNews(mType, pageIndex, new NewsModelImpl.OnLoadNewsListListener() {
            @Override
            public void onSuccess(List<NewsModel> list) {
                mContentItems.addAll(list);
                Log.d(TAG, "initNewsListData debug, News Count = " + list.size());
            }

            @Override
            public void onFailure(String msg, Exception e) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "initNewsListData error, Message = " + msg, e);
            }
        });
        Log.d(TAG, "initNewsListData debug, end");
    }

    NewsAdapter.OnItemClickListener recyclerOnItemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            NewsModel newsItem = mNewsAdapter.getItem(position);
            int itemType = mNewsAdapter.getItemViewType(position);
            Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
            intent.putExtra(NEWS_ITEM_PARAMETER, newsItem);

            View transitionView = null;
            if (itemType == NewsAdapter.TYPE_TOP) {
                transitionView = view.findViewById(R.id.iv_news_item_top_img);
            } else {
                transitionView = view.findViewById(R.id.iv_news_item_normal_img);
            }
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    transitionView, getString(R.string.transition_news_img));
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };

    NewsAdapter.OnItemLongClickListener recyclerOnLongItemClickListener = new NewsAdapter.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(View view, int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
}

