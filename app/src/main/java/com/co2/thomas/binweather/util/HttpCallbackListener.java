package com.co2.thomas.binweather.util;

/**
 * Created by Thomas on 2017/4/6.
 */
public interface HttpCallbackListener {
    void onFinish (String response);

    void onError (Exception e);
}
