package com.way.tunnelvision.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.way.tunnelvision.model.ChannelModel;
import com.way.tunnelvision.ui.fragment.NewsFragment;

import java.util.List;

/**
 * Created by pc on 2016/2/21.
 */
public class ChannelViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<ChannelModel> channelModels;

    public ChannelViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ChannelViewPagerAdapter(FragmentManager fm,List<ChannelModel> channelModels) {
        super(fm);
        this.channelModels = channelModels;
    }

    @Override
    public Fragment getItem(int position) {
        NewsFragment newsFragment = NewsFragment.newInstance();
        ChannelModel channelModel = channelModels.get(position);
        Bundle args = new Bundle();
        args.putParcelable("ChannelModel", channelModel);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    public int getCount() {
        return channelModels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ChannelModel channelModel = channelModels.get(position);
        String title = channelModel.getChannelTitle();
        return title;
    }
}
