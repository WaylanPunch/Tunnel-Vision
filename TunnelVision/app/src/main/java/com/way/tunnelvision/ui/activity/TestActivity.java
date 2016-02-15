package com.way.tunnelvision.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.model.feedentity.RSSFeed;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.http.RSSFeedService;
import com.way.tunnelvision.util.http.HttpCallbackListener;
import com.way.tunnelvision.util.http.RSSFeedXMLParser;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends BaseActivity {
    private static final String TAG = TestActivity.class.getName();

    private ProgressDialog progressDialog = null;

    private Button feed_click;
    private TextView feed_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        feed_click = (Button)findViewById(R.id.btn_test_getfeed);
        feed_show = (TextView)findViewById(R.id.tv_test_showfeed);



        feed_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(TestActivity.this, "下载", "正在下载,请稍候！");
//                Log.d(TAG, "button click debug");
//                Toast.makeText(TestActivity.this, "button click debug", Toast.LENGTH_SHORT).show();
                ///*
                RSSFeedService.sendHttpRequest("http://www.huxiu.com/rss/0.xml", new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        //RSSFeed RSSFeed = RSSFeedXMLParser.parseXMLWithSAX(response);
                        //RSSFeed RSSFeed = RSSFeedXMLParser.parseXMLWithRSSLibJ(response);
                        RSSFeed RSSFeed = RSSFeedXMLParser.parseXMLWithJSoup(response);
                        if(null != RSSFeed) {
                            //List<RSSFeedItem> feed36krItems = RSSFeed.getRSSFeedItems();
                            Message msg = new Message();
                            msg.what = 1;
                            Bundle b = new Bundle();// 存放数据
                            b.putParcelable("Param_FeedXML", RSSFeed);
                            //b.putString("Param_FeedXML", response);
                            msg.setData(b);
                            handler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "feed_show.setOnClickListener error", e);
                    }
                });
                //*/
                //gethttpExcute();
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.d(TAG, "handler debug");
                    // 此处可以更新UI
                    Bundle b = msg.getData();
                    RSSFeed RSSFeed = b.getParcelable("Param_FeedXML");
                    feed_show.setText(RSSFeed.getTitle()  + "," + RSSFeed.getDescription()  + "," + RSSFeed.getLink()  + "," + RSSFeed.getGenerator()  + "," + RSSFeed.getRSSFeedItems().size());

                    break;
                default:
                    break;
            }
            //刷新UI，显示数据，并关闭进度条
            progressDialog.dismiss(); //关闭进度条
        }
    };

    OkHttpClient okHttpClient = new OkHttpClient();
    public void gethttpExcute() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url("http://www.huxiu.com/rss/0.xml").build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    final String string = response.body().string();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            feed_show.setText(string);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
