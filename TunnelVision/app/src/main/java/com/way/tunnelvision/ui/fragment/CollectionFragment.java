package com.way.tunnelvision.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.ImageAdapter;
import com.way.tunnelvision.adapter.NewsCollectionAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.ImageModel;
import com.way.tunnelvision.entity.model.NewsModel;
import com.way.tunnelvision.entity.service.ImageDaoHelper;
import com.way.tunnelvision.entity.service.NewsDaoHelper;
import com.way.tunnelvision.ui.activity.ImageExpandActivity;
import com.way.tunnelvision.ui.activity.NewsDetailActivity;
import com.way.tunnelvision.util.ImageLoaderUtil;
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

    NewsCollectionAdapter newsCollectionAdapter;
    ImageAdapter imageAdapter;
    List<NewsModel> newsModels;
    List<ImageModel> imageModels;

    private void setupRecyclerView() {
        try {
            if (1 == mType) {//News
                LogUtil.d(TAG, "setupRecyclerView debug, News");
                newsCollectionAdapter = new NewsCollectionAdapter(getActivity(), getAllNews());
                newsCollectionAdapter.setOnItemClickListener(new NewsCollectionAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        NewsModel newsItem = newsModels.get(position);
                        //int itemType = mNewsAdapter.getItemViewType(position);
                        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                        intent.putExtra(Constants.NEWSDETAILACTIVITY_PARAMETER, newsItem);

                        View transitionView = null;
//                        if (itemType == NewsAdapter.TYPE_TOP) {
//                            transitionView = view.findViewById(R.id.iv_news_item_top_img);
//                        } else {
                            transitionView = view.findViewById(R.id.iv_news_item_normal_img);
//                        }
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                transitionView, getString(R.string.transition_news_img));
                        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                    }
                });
                newsCollectionAdapter.setOnItemLongClickListener(new NewsCollectionAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position) {
                        final NewsModel newsItem = newsModels.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                        builder.setTitle(getString(R.string.text_dialog_title));
                        builder.setMessage("点击确定即可取消收藏");
                        builder.setPositiveButton(getString(R.string.text_dialog_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    NewsDaoHelper newsDaoHelper = NewsDaoHelper.getInstance();
                                    newsDaoHelper.deleteData(newsItem);
                                    Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                                    newsModels.remove(newsItem);
                                    newsCollectionAdapter.notifyDataSetChanged();
                                }catch (Exception e){
                                    LogUtil.e(TAG, "newsCollectionAdapter.setOnItemLongClickListener error", e);
                                    Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT).show();
                                }
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
                        return false;
                    }
                });
                recyclerView.setAdapter(newsCollectionAdapter);
            } else {//Image
                LogUtil.d(TAG, "setupRecyclerView debug, Image");
                imageAdapter = new ImageAdapter(getActivity());
                imageAdapter.setContent(getAllImage());
                imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ImageModel imageModel = imageModels.get(position);
                        //getActivity().openActivityWithParcelable(ImageExpandActivity.class, imageModel);
                        Intent intent = new Intent(getActivity(), ImageExpandActivity.class);
                        intent.putExtra(Constants.ACTIVITY_PARAMETER, imageModel);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.create_zoomin, R.anim.create_zoomout);
                    }
                });
                imageAdapter.setOnItemLongClickListener(new ImageAdapter.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position) {
                        final ImageModel imageItem = imageModels.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        //AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                        builder.setTitle(getString(R.string.text_dialog_title));
                        builder.setMessage("点击确定即可取消收藏");
                        builder.setPositiveButton(getString(R.string.text_dialog_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    ImageDaoHelper imageDaoHelper = ImageDaoHelper.getInstance();
                                    imageDaoHelper.deleteData(imageItem);
                                    Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                                    imageModels.remove(imageItem);
                                    imageAdapter.notifyDataSetChanged();
                                }catch (Exception e){
                                    LogUtil.e(TAG, "imageAdapter.setOnItemLongClickListener error", e);
                                    Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT).show();
                                }
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
                        return false;
                    }
                });
                imageAdapter.setOnDownloadClickListener(new ImageAdapter.OnDownloadClickListener() {
                    @Override
                    public void onDownloadClick(View view, int position) {
                        ImageModel imageModel = imageModels.get(position);
                        ImageLoaderUtil.downloadImageToStorage(imageModel, getActivity());
                    }
                });
                recyclerView.setAdapter(imageAdapter);
            }

        } catch (Exception e) {
            LogUtil.e(TAG, "setupRecyclerView error", e);
        }
    }

    private List<NewsModel> getAllNews() {
        newsModels = new ArrayList<>();
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
        imageModels = new ArrayList<>();
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
