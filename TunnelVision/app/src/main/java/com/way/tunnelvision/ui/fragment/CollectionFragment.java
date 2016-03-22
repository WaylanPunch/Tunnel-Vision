package com.way.tunnelvision.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.ImageAdapter;
import com.way.tunnelvision.adapter.NewsCollectionAdapter;
import com.way.tunnelvision.entity.model.ImageModel;
import com.way.tunnelvision.entity.model.NewsModel;
import com.way.tunnelvision.entity.service.ImageDaoHelper;
import com.way.tunnelvision.entity.service.NewsDaoHelper;
import com.way.tunnelvision.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link CollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionFragment extends Fragment {
    private final static String TAG = CollectionFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "CollectionType";

    // TODO: Rename and change types of parameters
    private int mType;

    private RecyclerView recyclerView;

    public CollectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param colltype Parameter.
     * @return A new instance of fragment CollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectionFragment newInstance(int colltype) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, colltype);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(ARG_PARAM);
            LogUtil.d(TAG, "onCreate debug, " + ARG_PARAM + " = " + mType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_collection, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        setupRecyclerView();
        return rootView;
    }

    private void setupRecyclerView() {
        try {
            if (1 == mType) {//News
                LogUtil.d(TAG, "setupRecyclerView debug, News");
                NewsCollectionAdapter newsCollectionAdapter = new NewsCollectionAdapter(getActivity(), getAllNews());
                recyclerView.setAdapter(newsCollectionAdapter);
            } else {//Image
                LogUtil.d(TAG, "setupRecyclerView debug, Image");
                ImageAdapter imageAdapter = new ImageAdapter(getActivity());
                imageAdapter.setContent(getAllImage());
                recyclerView.setAdapter(imageAdapter);
            }

        } catch (Exception e) {
            LogUtil.e(TAG, "setupRecyclerView error", e);
        }
    }

    private List<NewsModel> getAllNews() {
        List<NewsModel> newsModels = new ArrayList<>();
        try {
            NewsDaoHelper newsDaoHelper = NewsDaoHelper.getInstance();
            newsModels = newsDaoHelper.getAllData();
            if (null != newsModels) {
                LogUtil.d(TAG, "getAllNews debug, NewsModels COUNT = " + newsModels.size());
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getAllNews error", e);
        }
        return newsModels;
    }

    private List<ImageModel> getAllImage() {
        List<ImageModel> imageModels = new ArrayList<>();
        try {
            ImageDaoHelper imageDaoHelper = ImageDaoHelper.getInstance();
            imageModels = imageDaoHelper.getAllData();
            if (null != imageModels) {
                LogUtil.d(TAG, "getAllImage debug, ImageModels COUNT = " + imageModels.size());
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getAllImage error", e);
        }
        return imageModels;
    }
}
