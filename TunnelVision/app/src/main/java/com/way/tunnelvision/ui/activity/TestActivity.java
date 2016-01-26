package com.way.tunnelvision.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.way.tunnelvision.R;
import com.way.tunnelvision.model.FeedEntity.Feed36kr;
import com.way.tunnelvision.ui.base.BaseActivity;
import com.way.tunnelvision.util.http.Http36krService;
import com.way.tunnelvision.util.http.HttpCallbackListener;
import com.way.tunnelvision.util.http.Parser36krXMLUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends BaseActivity {
    private static final String TAG = TestActivity.class.getName();

    Button feed_click;
    TextView feed_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        feed_click = (Button)findViewById(R.id.btn_test_getfeed);
        feed_show = (TextView)findViewById(R.id.tv_test_showfeed);

        feed_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "button click debug");
//                Toast.makeText(TestActivity.this, "button click debug", Toast.LENGTH_SHORT).show();
                ///*
                Http36krService.sendHttpRequest("http://www.huxiu.com/rss/0.xml", new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        //Feed36kr feed36kr = Parser36krXMLUtil.parseXMLWithSAX(response);
                        Feed36kr feed36kr = Parser36krXMLUtil.parseXMLWithRSSLibJ(response);
                        if(null != feed36kr) {
                            //List<Feed36krItem> feed36krItems = feed36kr.getFeed36krItems();
                            Message msg = new Message();
                            msg.what = 0;
                            Bundle b = new Bundle();// 存放数据
                            b.putParcelable("Param_FeedXML",feed36kr);
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
                    Feed36kr feed36kr = b.getParcelable("Param_FeedXML");
                    feed_show.setText(feed36kr.getTitle()  + "," + feed36kr.getDescription()  + "," + feed36kr.getLink()  + "," + feed36kr.getGenerator()  + "," + feed36kr.getFeed36krItems().size());
                    break;
                default:
                    break;
            }
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
