package com.way.tunnelvision.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.way.tunnelvision.R;

/**
 * Created by pc on 2016/1/6.
 */
public class TopicFragment extends Fragment {

    private ObservableScrollView mScrollView;

    public static TopicFragment newInstance() {
        return new TopicFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mScrollView = (ObservableScrollView) view.findViewById(R.id.sv_topic_container);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);
    }
}
