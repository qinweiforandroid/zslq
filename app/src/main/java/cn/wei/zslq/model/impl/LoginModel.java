package cn.wei.zslq.model.impl;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.domain.User;
import cn.wei.zslq.model.ILoginModel;
import cn.wei.zslq.model.ViewModel;
import cn.wei.zslq.service.DataServiceAPI;
import cn.wei.zslq.utils.ActionClickUtils;
import cn.wei.zslq.utils.PrefsAccessor;
import http.AppException;
import http.OnGlobalExceptionListener;
import http.Request;
import http.RequestManager;
import http.StringCallback;
import http.Trace;

/**
 * Created by qinwei on 2015/12/18 15:36
 * email:qinwei_it@163.com
 */
public class LoginModel extends ViewModel implements ILoginModel, OnGlobalExceptionListener {
    public static final String ACTION_LOGIN = "action_login";
    public String user;

    public LoginModel(Context context) {
        super(context);
    }

    public boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    public boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public User getUser() {
        String userJson = PrefsAccessor.getInstance(context).getString("user");
        return new Gson().fromJson(userJson, User.class);
    }

    public void setUser(User user) {
        String userJson = new Gson().toJson(user);
        PrefsAccessor.getInstance(context).saveString("user", userJson);
    }

    public void setToken(String token) {
        PrefsAccessor.getInstance(context).saveString("token", token);
    }

    public String getToken() {
        String token = PrefsAccessor.getInstance(context).getString("token");
        if (token == null) {
            return null;
        }
        return token;
    }


    @Override
    public void login(String username, String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", username);

        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        User.loginByAccount(context, username, password, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user != null) {
                    MyApplication.setLoginUser(user);
                    onResponseSuccess(ACTION_LOGIN);
                } else {
                    onResponseError(ACTION_LOGIN, e.getErrorCode(), e.getMessage());
                }
            }
        });
        ActionClickUtils.onEvent(context, ActionClickUtils.ACTION_LOGIN, data);
//        Request request = new Request(DataServiceAPI.getDomain(), Request.RequestMethod.GET);
//        request.addHeader("token", "123456789");
//        request.put("username", username);
//        request.put("password", password);
//        request.setCallback(new StringCallback() {
//            @Override
//            public void onSuccess(String result) {
//                LoginModel.this.user = result;
//                onResponseSuccess(ACTION_LOGIN);
//            }
//
//            @Override
//            public void onCompleted() {
//                RequestManager.getInstance().cancelRequest(ACTION_LOGIN);
//            }
//
//            @Override
//            public void onFailure(AppException e) {
//                e.printStackTrace();
//                onResponseError(ACTION_LOGIN, e.responseCode, e.responseMessage);
//            }
//        });
//        request.setOnGlobalExceptionListener(this);
//        RequestManager.getInstance().execute(ACTION_LOGIN, request);
    }

    public void loginREST_API(String username, String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", username);
        JSONObject loginParams=new JSONObject();
        try {
            loginParams.put("username",username);
            loginParams.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request=new Request(DataServiceAPI.loadLogin(), Request.RequestMethod.POST);
        request.addHeader("Content-Type","application/json");
        request.addHeader("X-Bmob-Application-Id","23cef32eb5b6f7cf7c6dfb88d7def740");
        request.addHeader("X-Bmob-REST-API-Key","c8ca055b8e8505f7dcbeb5482cc974d9");
        request.postContent=loginParams.toString();
        request.setCallback(new StringCallback() {
            @Override
            public void onSuccess(String result) {
                closeProgress();
                Trace.d(result);
            }

            @Override
            public void onFailure(AppException e) {
                closeProgress();
                Trace.e(e.toString());
            }
        });
        RequestManager.getInstance().execute(ACTION_LOGIN,request);
    }

    public void yuanchengdiao(String username,String password){
        JSONObject loginParams=new JSONObject();
        try {
            loginParams.put("username",username);
            loginParams.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        AsyncCustomEndpoints ace = new AsyncCustomEndpoints();
////第一个参数是上下文对象，第二个参数是云端代码的方法名称，第三个参数是上传到云端代码的参数列表（JSONObject cloudCodeParams），第四个参数是回调类
//
//        ace.callEndpoint(context, "learn", loginParams, new CloudCodeListener() {
//            @Override
//            public void onSuccess(Object object) {
//                // TODO Auto-generated method stub
//                Trace.d("云端usertest方法返回:" + object.toString());
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                // TODO Auto-generated method stub
//                Trace.d("访问云端usertest方法失败:" + msg);
//            }
//        });
    }
}