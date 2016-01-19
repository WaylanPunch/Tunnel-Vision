package com.way.tunnelvision.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.way.tunnelvision.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends Activity {
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
                Log.d(TAG, "button click debug");
                Toast.makeText(TestActivity.this, "button click debug", Toast.LENGTH_SHORT).show();
                /*
                Http36krService.sendHttpRequest("https://36kr.com/feed", new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Message msg = new Message();
                        msg.what = 0;
                        Bundle b = new Bundle();// 存放数据
                        b.putString("36kr", response);
                        msg.setData(b);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "feed_show.setOnClickListener error", e);
                    }
                });
                */
                gethttpExcute();
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
                    String feed36krxml = b.getString("36kr");
                    feed_show.setText(feed36krxml);
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
}
