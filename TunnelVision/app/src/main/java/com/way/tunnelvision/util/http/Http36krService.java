package com.way.tunnelvision.util.http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pc on 2016/1/18.
 */
public class Http36krService {


    public static void sendHttpRequest(final String url, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                String result = "";
                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                    if (listener != null) {
                        // 回调onFinish()方法
                        listener.onFinish(result);
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        // 回调onError()方法
                        listener.onError(e);
                    }
                } finally {

                }

            }
        }).start();

    }
}
