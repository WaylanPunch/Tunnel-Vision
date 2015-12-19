package com.way.tunnelvision.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.way.tunnelvision.R;

/**
 * Created by pc on 2015/12/6.
 */
public class CollectionFragment extends Fragment {

    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        try{
            rootView = inflater.inflate(R.layout.content_collection, container, false);
            imageView = (ImageView) rootView.findViewById(R.id.iv_collection_tip1);
        }catch (Exception e){

        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        TextDrawable drawable = TextDrawable.builder().buildRect("A", Color.RED);
        imageView.setImageDrawable(drawable);
    }
}
