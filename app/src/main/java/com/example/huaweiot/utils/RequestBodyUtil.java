package com.example.huaweiot.utils;

import android.util.Log;

import com.example.huaweiot.api.Beans.CommandBody;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class RequestBodyUtil {
    private static final String TAG = "RequestBodyUtil";

    private static String bodyInfo(String method, Map<String, String> paras) {
        Gson gson = new Gson();
        CommandBody body = new CommandBody();
        body.setMethod(method);
        body.setParas(paras);
        String jsonStr = gson.toJson(body);
        Log.d(TAG, "bodyInfo:tojson: " + jsonStr);
        return jsonStr;
    }


    public static RequestBody getRequestBody(String method, Map<String, String> paras) {

        String json = bodyInfo(method, paras);
        return RequestBody.create(MediaType.parse("application/json"), json);
    }
}
