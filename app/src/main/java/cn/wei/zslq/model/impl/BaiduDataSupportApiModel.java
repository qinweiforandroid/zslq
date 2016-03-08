package cn.wei.zslq.model.impl;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.wei.library.utils.Trace;
import cn.wei.zslq.domain.JokeBean;
import cn.wei.zslq.model.IBaiduDataSupportApiModel;
import cn.wei.zslq.model.ViewModel;
import cn.wei.zslq.utils.UrlHelpper;
import http.AppException;
import http.JsonParser;
import http.Request;
import http.RequestManager;
import http.StringCallback;

/**
 * Created by qinwei on 2016/3/7 9:42
 * email:qinwei_it@163.com
 */
public class BaiduDataSupportApiModel extends ViewModel implements IBaiduDataSupportApiModel {
    public String jokeJson;
    public ArrayList<JokeBean> jokes;

    public BaiduDataSupportApiModel(Context context) {
        super(context);
    }

    @Override
    public void loadJokeData(int page) {
        Request request = new Request(UrlHelpper.loadJokeData(page), Request.RequestMethod.GET);
        request.addHeader("apikey", UrlHelpper.BAIDU_APIKEY);
        request.setCallback(new StringCallback() {
            @Override
            public void onSuccess(String result) {
                jokeJson = result;
                Trace.d(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    int responseCode = obj.getInt("showapi_res_code");
                    if (responseCode == 0) {
                        String data = obj.getJSONObject("showapi_res_body").getJSONArray("contentlist").toString();
                        jokes = JsonParser.deserializeFromJson(data, new TypeToken<ArrayList<JokeBean>>() {
                        }.getType());
                        onResponseSuccess(ACTION_LOAD_JOKE_DATA);
                    } else {
                        onResponseError(ACTION_LOAD_JOKE_DATA, 300, "");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    onResponseError(ACTION_LOAD_JOKE_DATA, 300, e.getMessage());
                }
            }

            @Override
            public void onFailure(AppException e) {
                e.printStackTrace();
                onResponseError(ACTION_LOAD_JOKE_DATA, e.responseCode, e.responseMessage);
            }
        });
        RequestManager.getInstance().execute(ACTION_LOAD_JOKE_DATA, request);
    }
}
