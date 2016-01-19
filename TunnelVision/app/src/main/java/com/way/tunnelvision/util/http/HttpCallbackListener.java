package com.way.tunnelvision.util.http;

/**
 * Created by pc on 2016/1/19.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
