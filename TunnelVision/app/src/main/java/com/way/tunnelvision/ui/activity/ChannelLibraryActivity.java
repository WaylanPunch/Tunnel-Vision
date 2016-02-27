package com.way.tunnelvision.ui.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.way.tunnelvision.R;
import com.way.tunnelvision.adapter.ChannelUnchosenAdapter;
import com.way.tunnelvision.base.Constants;
import com.way.tunnelvision.model.ChannelModel;
import com.way.tunnelvision.model.dao.ChannelDao;
import com.way.tunnelvision.model.dao.DaoMaster;
import com.way.tunnelvision.model.dao.DaoSession;
import com.way.tunnelvision.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

public class ChannelLibraryActivity extends BaseActivity {
    private final static String TAG = ChannelLibraryActivity.class.getName();

    private int resultCode = 0;

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ChannelDao channelDao;
    private Cursor cursor;
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

        initDataBase();
        initView();
    }

    private void initDataBase() {
        Log.d(TAG, "initDataBase debug, start");
        try{
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE_NAME, null);
            db = helper.getWritableDatabase();
            daoMaster = new DaoMaster(db);
            daoSession = daoMaster.newSession();
            channelDao = daoSession.getChannelDao();
        }catch (Exception e){
            Log.e(TAG, "initDataBase error", e);
        }
        Log.d(TAG, "initDataBase debug, end");
    }

    private void initView() {
        Log.d(TAG, "initView debug, start");
        try{
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


            initListData();
            Log.d(TAG,"initView debug, Channel Items Count = " + channelModelsUnchosen.size());
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
                            return true;
                        }

                        @Override
                        public void onDismiss(View view) {
                            int id = rv_unchosenList.getChildPosition(view);
                            channelUnchosenAdapter.getContents().remove(id);
                            channelUnchosenAdapter.notifyDataSetChanged();

                            Toast.makeText(getBaseContext(), String.format("Delete item %d", id), Toast.LENGTH_LONG).show();
                        }
                    })
                    .setIsVertical(false)
                    .setItemTouchCallback(
                            new SwipeDismissRecyclerViewTouchListener.OnItemTouchCallBack() {
                                @Override
                                public void onTouch(int index) {
                                    //showDialog(String.format("Click item %d", index));
                                }
                            })
                    .create();

            rv_unchosenList.setOnTouchListener(listener);
        }catch (Exception e){
            Log.e(TAG, "initView error", e);
        }
        Log.d(TAG, "initView debug, end");
    }

    private void initListData() {
        Log.d(TAG, "initListData debug, start");
        try {
            String orderColumn = ChannelDao.Properties.ChannelChosen.columnName;
            String orderBy = orderColumn + " COLLATE LOCALIZED ASC";
            cursor = db.query(channelDao.getTablename(), channelDao.getAllColumns(), null, null, null, null, orderBy);
            if (null != cursor && cursor.getCount() > 0) {
                Log.d(TAG, "initListData debug, Cursor Row Count = " + cursor.getCount());
                while (cursor.moveToNext()) {
                    int columnIndex_id = cursor.getColumnIndex(ChannelDao.COLUMNNAME_ID);
                    Long id = cursor.getLong(columnIndex_id);
                    int columnIndex_guid = cursor.getColumnIndex(ChannelDao.COLUMNNAME_GUID);
                    String guid = cursor.getString(columnIndex_guid);
                    int columnIndex_title = cursor.getColumnIndex(ChannelDao.COLUMNNAME_TITLE);
                    String title = cursor.getString(columnIndex_title);
                    int columnIndex_name = cursor.getColumnIndex(ChannelDao.COLUMNNAME_NAME);
                    String name = cursor.getString(columnIndex_name);
                    int columnIndex_link = cursor.getColumnIndex(ChannelDao.COLUMNNAME_LINK);
                    String link = cursor.getString(columnIndex_link);
                    int columnIndex_chosen = cursor.getColumnIndex(ChannelDao.COLUMNNAME_CHOSEN);
                    int chosen = cursor.getInt(columnIndex_chosen);
                    ChannelModel channelModel = new ChannelModel(id, guid, title, name, link, chosen);
                    //if (0 == chosen || 1 == chosen) {
                    //channelModelsChosen.add(channelModel);
                    //} else {
                    channelModelsUnchosen.add(channelModel);
                    //}
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "initListData error", e);
        }
        Log.d(TAG, "initListData debug, end");
    }

    private void initDataTableChannel() {
        Log.d(TAG, "initDataTableChannel debug, start");
        try {
//            String orderColumn = ChannelDao.Properties.ChannelChosen.columnName;
//            String orderBy = orderColumn + " COLLATE LOCALIZED ASC";
//            cursor = db.query(channelDao.getTablename(), channelDao.getAllColumns(), null, null, null, null, orderBy);
            channelDao.deleteAll();
            String headline_guid = Constants.NEWS.TOP_ID;
            String headline_title = Constants.NEWS.TOP_TITLE;
            String headline_name = Constants.NEWS.TOP_NAME;
            String headline_link = Constants.NEWS.TOP_URL;
            int headline_chosen = 0;
            ChannelModel headline_channel = new ChannelModel(null, headline_guid, headline_title, headline_name, headline_link, headline_chosen);
            channelDao.insert(headline_channel);

            String nba_guid = Constants.NEWS.NBA_ID;
            String nba_title = Constants.NEWS.NBA_TITLE;
            String nba_name = Constants.NEWS.NBA_NAME;
            String nba_link = Constants.NEWS.NBA_ID;
            int nba_chosen = 1;
            ChannelModel nba_channel = new ChannelModel(null, nba_guid, nba_title, nba_name, nba_link, nba_chosen);
            channelDao.insert(nba_channel);

            String car_guid = Constants.NEWS.CAR_ID;
            String car_title = Constants.NEWS.CAR_TITLE;
            String car_name = Constants.NEWS.CAR_NAME;
            String car_link = Constants.NEWS.CAR_ID;
            int car_chosen = 1;
            ChannelModel car_channel = new ChannelModel(null, car_guid, car_title, car_name, car_link, car_chosen);
            channelDao.insert(car_channel);

            String joke_guid = Constants.NEWS.JOKE_ID;
            String joke_title = Constants.NEWS.JOKE_TITLE;
            String joke_name = Constants.NEWS.JOKE_NAME;
            String joke_link = Constants.NEWS.JOKE_ID;
            int joke_chosen = 1;
            ChannelModel joke_channel = new ChannelModel(null, joke_guid, joke_title, joke_name, joke_link, joke_chosen);
            channelDao.insert(joke_channel);
            //MainActivity.refreshMenu();

//                    Intent mIntent = new Intent();
//                    mIntent.putExtra("change01", "1000");
//                    mIntent.putExtra("change02", "2000");
//                    // 设置结果，并进行传送
//                    setResult(resultCode, mIntent);
            setResult(resultCode);
        } catch (Exception e) {
            Log.e(TAG, "initDataTableChannel error", e);
        }
        Log.d(TAG, "initDataTableChannel debug, end");
    }

    /***
     * 动态设置listview的高度
     *
     * @param listView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {
        Log.d(TAG, "setListViewHeightBasedOnChildren debug, start");
        try {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);  //在还没有构建View 之前无法取得View的度宽,在此之前我们必须选 measure 一下
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            // params.height += 5;// if without this statement,the listview will be a little short
            // listView.getDividerHeight()获取子项间分隔符占用的高度
            // params.height最后得到整个ListView完整显示需要的高度
            listView.setLayoutParams(params);
        } catch (Exception e) {
            Log.e(TAG, "setListViewHeightBasedOnChildren error", e);
        }
        Log.d(TAG, "setListViewHeightBasedOnChildren debug, end");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != cursor) {
            cursor.close();
        }
        if (null != daoSession) {
            daoSession.clear();
        }
        if (null != db) {
            db.close();
        }
    }
}
