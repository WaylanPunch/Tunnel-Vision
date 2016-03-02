package com.way.tunnelvision.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.way.tunnelvision.R;
import com.way.tunnelvision.base.MainApp;
import com.way.tunnelvision.ui.fragment.CollectionFragment;

/**
 * Created by pc on 2016/2/21.
 */
public class CollectionViewPagerAdapter extends FragmentStatePagerAdapter {

    //private List<ChannelModel> channelModels;

    public CollectionViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

//    public CollectionViewPagerAdapter(FragmentManager fm, List<ChannelModel> channelModels) {
//        super(fm);
//        this.channelModels = channelModels;
//    }

    @Override
    public Fragment getItem(int position) {
        CollectionFragment collectionFragment = CollectionFragment.newInstance("1", "2");
        return collectionFragment;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainApp.getContext().getResources().getString(R.string.menu_title_collection);
    }
}
