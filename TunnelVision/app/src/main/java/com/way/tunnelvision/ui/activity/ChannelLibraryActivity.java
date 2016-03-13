package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.ChannelUnchosenAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.entity.model.ChannelModel;
import com.way.tunnelvision.entity.service.ChannelDaoHelper;
import com.way.tunnelvision.ui.base.BaseActivity;

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

    private FloatingActionButton fab;
    //private ListView lv_chosenList;
    private RecyclerView rv_unchosenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_library);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_channel_library_toolbar);
        setSupportActionBar(toolbar);

        initListData();
        initView();
    }


    private void initView() {
        Log.d(TAG, "initView debug, start");
        try {
            fab = (FloatingActionButton) findViewById(R.id.fab_channel_liabrary_initialize);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    initDataTableChannel();
                }


            });

            //lv_chosenList = (ListView) findViewById(R.id.lv_channel_library_chosen);
            rv_unchosenList = (RecyclerView) findViewById(R.id.rv_channel_library_unchosen);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv_unchosenList.setLayoutManager(layoutManager);



            Log.d(TAG, "initView debug, Channel Items Count = " + channelModelsUnchosen.size());
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
                            if (0 == channelItem.getChannelChosen()){
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
                                    Toast.makeText(ChannelLibraryActivity.this,"Swipe to Subscribe Channel!",Toast.LENGTH_SHORT).show();
                                }
                            })
                    .create();
            rv_unchosenList.setOnTouchListener(listener);
        } catch (Exception e) {
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }



    private void initListData() {
        Log.d(TAG, "initListData debug, start");
        try {
            channelDaoHelper = ChannelDaoHelper.getInstance();
            Long rowCount = channelDaoHelper.getTotalCount();
            if(rowCount>0){
                channelModelsUnchosen = channelDaoHelper.getAllData();
                Log.d(TAG, "initListData debug, ChannelModels COUNT = " + rowCount + "," + channelModelsUnchosen.size());
            }
        } catch (Exception e) {
            Log.e(TAG, "initListData error", e);
        }
        Log.d(TAG, "initListData debug, end");
    }

    private void initDataTableChannel() {
        Log.d(TAG, "initDataTableChannel debug, start");
        try {
            //channelDao.deleteAll();
            channelDaoHelper.deleteAll();
            String headline_guid = Constants.NEWS.TOP_ID;
            String headline_title = Constants.NEWS.TOP_TITLE;
            String headline_name = Constants.NEWS.TOP_NAME;
            String headline_link = Constants.NEWS.TOP_URL;
            int headline_type = Constants.NEWS.NEWS_TYPE_TOP;
            int headline_chosen = 0;
            ChannelModel headline_channel = new ChannelModel(null, headline_guid, headline_title, headline_name, headline_link, headline_type, headline_chosen);
            //channelDao.insert(headline_channel);
            channelDaoHelper.addData(headline_channel);

            String nba_guid = Constants.NEWS.NBA_ID;
            String nba_title = Constants.NEWS.NBA_TITLE;
            String nba_name = Constants.NEWS.NBA_NAME;
            String nba_link = Constants.NEWS.NBA_ID;
            int nba_type = Constants.NEWS.NEWS_TYPE_NBA;
            int nba_chosen = 1;
            ChannelModel nba_channel = new ChannelModel(null, nba_guid, nba_title, nba_name, nba_link, nba_type, nba_chosen);
            //channelDao.insert(nba_channel);
            channelDaoHelper.addData(nba_channel);

            String car_guid = Constants.NEWS.CAR_ID;
            String car_title = Constants.NEWS.CAR_TITLE;
            String car_name = Constants.NEWS.CAR_NAME;
            String car_link = Constants.NEWS.CAR_ID;
            int car_type = Constants.NEWS.NEWS_TYPE_CARS;
            int car_chosen = 1;
            ChannelModel car_channel = new ChannelModel(null, car_guid, car_title, car_name, car_link, car_type, car_chosen);
            //channelDao.insert(car_channel);
            channelDaoHelper.addData(car_channel);

            String joke_guid = Constants.NEWS.JOKE_ID;
            String joke_title = Constants.NEWS.JOKE_TITLE;
            String joke_name = Constants.NEWS.JOKE_NAME;
            String joke_link = Constants.NEWS.JOKE_ID;
            int joke_type = Constants.NEWS.NEWS_TYPE_JOKES;
            int joke_chosen = 1;
            ChannelModel joke_channel = new ChannelModel(null, joke_guid, joke_title, joke_name, joke_link, joke_type, joke_chosen);
            //channelDao.insert(joke_channel);
            channelDaoHelper.addData(joke_channel);

            setResult(resultCode);
        } catch (Exception e) {
            Log.e(TAG, "initDataTableChannel error", e);
        }
        Log.d(TAG, "initDataTableChannel debug, end");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(resultCode);
        finish();
    }
}
