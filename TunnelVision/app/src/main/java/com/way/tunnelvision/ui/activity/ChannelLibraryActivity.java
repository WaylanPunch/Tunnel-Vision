package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.ChannelUnchosenAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.entity.service.ChannelDaoHelper;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

public class ChannelLibraryActivity extends BaseActivity {
    private final static String TAG = ChannelLibraryActivity.class.getName();

    private int resultCode = 0;

    //    private SQLiteDatabase db;
//    private DaoMaster daoMaster;
//    private DaoSession daoSession;
//    private ChannelDao channelDao;
//    private Cursor cursor;
    private ChannelDaoHelper channelDaoHelper;
    //private List<ChannelModel> channelModelsChosen = new ArrayList<>();
    private List<ChannelModel> channelModelsUnchosen = new ArrayList<>();
    //private ChannelChosenAdapter channelChosenAdapter;
    private ChannelUnchosenAdapter channelUnchosenAdapter;

    //private FloatingActionButton fab;
    //private ListView lv_chosenList;
    private RecyclerView rv_unchosenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_library);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_channel_library_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initListData();
        initView();
    }


    private void initView() {
        LogUtil.d(TAG, "initView debug, start");
        try {
            /*
            fab = (FloatingActionButton) findViewById(R.id.fab_channel_liabrary_initialize);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    initDataTableChannel();
                }


            });
            */

            //lv_chosenList = (ListView) findViewById(R.id.lv_channel_library_chosen);
            rv_unchosenList = (RecyclerView) findViewById(R.id.rv_channel_library_unchosen);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv_unchosenList.setLayoutManager(layoutManager);


            LogUtil.d(TAG, "initView debug, Channel Items Count = " + channelModelsUnchosen.size());
            channelUnchosenAdapter = new ChannelUnchosenAdapter(ChannelLibraryActivity.this, channelModelsUnchosen);
            //channelUnchosenAdapter = new ChannelChosenAdapter(ChannelLibraryActivity.this, channelModelsUnchosen);
            //lv_chosenList.setAdapter(channelUnchosenAdapter);
            rv_unchosenList.setAdapter(channelUnchosenAdapter);
            //setListViewHeightBasedOnChildren(lv_chosenList);
            //setListViewHeightBasedOnChildren(lv_unchosenList);


            SwipeDismissRecyclerViewTouchListener listener = new SwipeDismissRecyclerViewTouchListener.Builder(
                    rv_unchosenList,
                    new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                        @Override
                        public boolean canDismiss(int position) {
                            ChannelModel channelItem = channelModelsUnchosen.get(position);
                            if (0 == channelItem.getChannelChosen()) {
                                return false;
                            }
                            return true;
                        }

                        @Override
                        public void onDismiss(View view) {
                            int position = rv_unchosenList.getChildPosition(view);
                            ChannelModel channelItem = channelModelsUnchosen.get(position);
                            if (1 == channelItem.getChannelChosen()) {
                                channelItem.setChannelChosen(2);
                                channelModelsUnchosen.set(position, channelItem);
                                channelUnchosenAdapter.notifyDataSetChanged();
                                //channelDao.update(channelItem);
                                channelDaoHelper.updateData(channelItem);
                                setResult(resultCode);
                                Snackbar.make(view, "Cancel Channel", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            } else if (2 == channelItem.getChannelChosen()) {
                                channelItem.setChannelChosen(1);
                                channelModelsUnchosen.set(position, channelItem);
                                channelUnchosenAdapter.notifyDataSetChanged();
                                //channelDao.update(channelItem);
                                channelDaoHelper.updateData(channelItem);
                                setResult(resultCode);
                                Snackbar.make(view, "Subscribe Channel", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            } else {
                                Snackbar.make(view, "Default Channel", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }
                            //channelUnchosenAdapter.getContents().remove(position);


                            //Toast.makeText(getBaseContext(), String.format("Delete item %d", position), Toast.LENGTH_LONG).show();
                        }
                    })
                    .setIsVertical(false)
                    .setItemTouchCallback(
                            new SwipeDismissRecyclerViewTouchListener.OnItemTouchCallBack() {
                                @Override
                                public void onTouch(int index) {
                                    //showDialog(String.format("Click item %d", index));
                                    //Toast.makeText(ChannelLibraryActivity.this, "Swipe to Subscribe Channel!", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .create();
            rv_unchosenList.setOnTouchListener(listener);
        } catch (Exception e) {
            LogUtil.e(TAG, "initView error", e);
        }
        LogUtil.d(TAG, "initView debug, end");
    }


    private void initListData() {
        LogUtil.d(TAG, "initListData debug, start");
        try {
            channelDaoHelper = ChannelDaoHelper.getInstance();
            Long rowCount = channelDaoHelper.getTotalCount();
            if (rowCount > 0) {
                channelModelsUnchosen = channelDaoHelper.getAllData();
                LogUtil.d(TAG, "initListData debug, ChannelModels COUNT = " + rowCount + "," + channelModelsUnchosen.size());
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "initListData error", e);
        }
        LogUtil.d(TAG, "initListData debug, end");
    }

    private void initDataTableChannel() {
        LogUtil.d(TAG, "initDataTableChannel debug, start");
        try {
            //channelDao.deleteAll();
            channelDaoHelper.deleteAll();
            ChannelModel channel;
            channel = new ChannelModel(null,
                    Constants.NEWS.TOP_ID,
                    Constants.NEWS.TOP_TITLE,
                    Constants.NEWS.TOP_NAME,
                    Constants.NEWS.TOP_URL,
                    Constants.NEWS.NEWS_TYPE_TOP,
                    0);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.NBA_ID,
                    Constants.NEWS.NBA_TITLE,
                    Constants.NEWS.NBA_NAME,
                    Constants.NEWS.NBA_ID,
                    Constants.NEWS.NEWS_TYPE_NBA,
                    1);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.CAR_ID,
                    Constants.NEWS.CAR_TITLE,
                    Constants.NEWS.CAR_NAME,
                    Constants.NEWS.CAR_ID,
                    Constants.NEWS.NEWS_TYPE_CARS,
                    1);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.JOKE_ID,
                    Constants.NEWS.JOKE_TITLE,
                    Constants.NEWS.JOKE_NAME,
                    Constants.NEWS.JOKE_ID,
                    Constants.NEWS.NEWS_TYPE_JOKES,
                    1);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.FOOTBALL_ID,
                    Constants.NEWS.FOOTBALL_TITLE,
                    Constants.NEWS.FOOTBALL_NAME,
                    Constants.NEWS.FOOTBALL_ID,
                    Constants.NEWS.NEWS_TYPE_FOOTBALL,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.ENTERTAINMENT_ID,
                    Constants.NEWS.ENTERTAINMENT_TITLE,
                    Constants.NEWS.ENTERTAINMENT_NAME,
                    Constants.NEWS.ENTERTAINMENT_ID,
                    Constants.NEWS.NEWS_TYPE_ENTERTAINMENT,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.SPORTS_ID,
                    Constants.NEWS.SPORTS_TITLE,
                    Constants.NEWS.SPORTS_NAME,
                    Constants.NEWS.SPORTS_ID,
                    Constants.NEWS.NEWS_TYPE_SPORTS,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.FINANCE_ID,
                    Constants.NEWS.FINANCE_TITLE,
                    Constants.NEWS.FINANCE_NAME,
                    Constants.NEWS.FINANCE_ID,
                    Constants.NEWS.NEWS_TYPE_FINANCE,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.SCIENCE_ID,
                    Constants.NEWS.SCIENCE_TITLE,
                    Constants.NEWS.SCIENCE_NAME,
                    Constants.NEWS.SCIENCE_ID,
                    Constants.NEWS.NEWS_TYPE_SCIENCE,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.MOVIE_ID,
                    Constants.NEWS.MOVIE_TITLE,
                    Constants.NEWS.MOVIE_NAME,
                    Constants.NEWS.MOVIE_ID,
                    Constants.NEWS.NEWS_TYPE_MOVIE,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.GAME_ID,
                    Constants.NEWS.GAME_TITLE,
                    Constants.NEWS.GAME_NAME,
                    Constants.NEWS.GAME_ID,
                    Constants.NEWS.NEWS_TYPE_GAME,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.FASHION_ID,
                    Constants.NEWS.FASHION_TITLE,
                    Constants.NEWS.FASHION_NAME,
                    Constants.NEWS.FASHION_ID,
                    Constants.NEWS.NEWS_TYPE_FASHION,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.EMOTION_ID,
                    Constants.NEWS.EMOTION_TITLE,
                    Constants.NEWS.EMOTION_NAME,
                    Constants.NEWS.EMOTION_ID,
                    Constants.NEWS.NEWS_TYPE_EMOTION,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.HITS_ID,
                    Constants.NEWS.HITS_TITLE,
                    Constants.NEWS.HITS_NAME,
                    Constants.NEWS.HITS_ID,
                    Constants.NEWS.NEWS_TYPE_HITS,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.RADIO_ID,
                    Constants.NEWS.RADIO_TITLE,
                    Constants.NEWS.RADIO_NAME,
                    Constants.NEWS.RADIO_ID,
                    Constants.NEWS.NEWS_TYPE_RADIO,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.DIGITAL_ID,
                    Constants.NEWS.DIGITAL_TITLE,
                    Constants.NEWS.DIGITAL_NAME,
                    Constants.NEWS.DIGITAL_ID,
                    Constants.NEWS.NEWS_TYPE_DIGITAL,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.MOBILE_ID,
                    Constants.NEWS.MOBILE_TITLE,
                    Constants.NEWS.MOBILE_NAME,
                    Constants.NEWS.MOBILE_ID,
                    Constants.NEWS.NEWS_TYPE_MOBILE,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.LOTTERY_ID,
                    Constants.NEWS.LOTTERY_TITLE,
                    Constants.NEWS.LOTTERY_NAME,
                    Constants.NEWS.LOTTERY_ID,
                    Constants.NEWS.NEWS_TYPE_LOTTERY,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.EDUCATION_ID,
                    Constants.NEWS.EDUCATION_TITLE,
                    Constants.NEWS.EDUCATION_NAME,
                    Constants.NEWS.EDUCATION_ID,
                    Constants.NEWS.NEWS_TYPE_EDUCATION,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.FORUM_ID,
                    Constants.NEWS.FORUM_TITLE,
                    Constants.NEWS.FORUM_NAME,
                    Constants.NEWS.FORUM_ID,
                    Constants.NEWS.NEWS_TYPE_FORUM,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.TRAVEL_ID,
                    Constants.NEWS.TRAVEL_TITLE,
                    Constants.NEWS.TRAVEL_NAME,
                    Constants.NEWS.TRAVEL_ID,
                    Constants.NEWS.NEWS_TYPE_TRAVEL,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.PHONE_ID,
                    Constants.NEWS.PHONE_TITLE,
                    Constants.NEWS.PHONE_NAME,
                    Constants.NEWS.PHONE_ID,
                    Constants.NEWS.NEWS_TYPE_PHONE,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.BLOG_ID,
                    Constants.NEWS.BLOG_TITLE,
                    Constants.NEWS.BLOG_NAME,
                    Constants.NEWS.BLOG_ID,
                    Constants.NEWS.NEWS_TYPE_BLOG,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.SOCIETY_ID,
                    Constants.NEWS.SOCIETY_TITLE,
                    Constants.NEWS.SOCIETY_NAME,
                    Constants.NEWS.SOCIETY_ID,
                    Constants.NEWS.NEWS_TYPE_SOCIETY,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.HOUSE_ID,
                    Constants.NEWS.HOUSE_TITLE,
                    Constants.NEWS.HOUSE_NAME,
                    Constants.NEWS.HOUSE_ID,
                    Constants.NEWS.NEWS_TYPE_HOUSE,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.BABY_ID,
                    Constants.NEWS.BABY_TITLE,
                    Constants.NEWS.BABY_NAME,
                    Constants.NEWS.BABY_ID,
                    Constants.NEWS.NEWS_TYPE_BABY,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.CBA_ID,
                    Constants.NEWS.CBA_TITLE,
                    Constants.NEWS.CBA_NAME,
                    Constants.NEWS.CBA_ID,
                    Constants.NEWS.NEWS_TYPE_CBA,
                    2);
            channelDaoHelper.addData(channel);
            channel = new ChannelModel(null,
                    Constants.NEWS.MILITARY_ID,
                    Constants.NEWS.MILITARY_TITLE,
                    Constants.NEWS.MILITARY_NAME,
                    Constants.NEWS.MILITARY_ID,
                    Constants.NEWS.NEWS_TYPE_MILITARY,
                    2);
            channelDaoHelper.addData(channel);

            setResult(resultCode);
        } catch (Exception e) {
            LogUtil.e(TAG, "initDataTableChannel error", e);
        }
        LogUtil.d(TAG, "initDataTableChannel debug, end");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_channel_library, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_channel_library_initial) {
            try {
                initDataTableChannel();
                initListData();
                channelUnchosenAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                LogUtil.e(TAG, "onOptionsItemSelected error, action_channel_library_initial", e);
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(resultCode);
        finish();
    }
}
