package cn.wei.zslq.model.impl;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;

import java.util.HashMap;

import cn.bmob.v3.listener.SaveListener;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.entity.User;
import cn.wei.zslq.model.ILoginModel;
import cn.wei.zslq.model.ViewMode;
import cn.wei.zslq.utils.PrefsAccessor;
import http.OnGlobalExceptionListener;

/**
 * Created by qinwei on 2015/12/18 15:36
 * email:qinwei_it@163.com
 */
public class LoginModel extends ViewMode implements ILoginModel, OnGlobalExceptionListener {
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
        PrefsAccessor.getInstance(context).saveString("token", Base64.encodeToString(token.getBytes(), Base64.DEFAULT));
    }

    public String getToken() {
        String token = PrefsAccessor.getInstance(context).getString("token");
        if (token == null) {
            return null;
        }
        token = new String(Base64.decode(token, Base64.DEFAULT));
        return token;
    }


    @Override
    public void login(String username, String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", username);
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
//        User.loginByAccount(context, username, password, new LogInListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//
//            }
//        });
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                MyApplication.setLoginUser(User.getCurrentUser(context));
                onRequestSuccess(ACTION_LOGIN);
            }

            @Override
            public void onFailure(int i, String s) {
                onRequestError(ACTION_LOGIN, i, s);
            }
        });
//        ActionClickUtils.onEvent(context, ActionClickUtils.ACTION_LOGIN, data);
////        Bmob.
//        Request request = new Request(DataServiceAPI.getDomain(), Request.RequestMethod.GET);
//        request.addHeader("token", "123456789");
//        request.put("username", username);
//        request.put("password", password);
//        request.setCallback(new StringCallback() {
//            @Override
//            public void onSuccess(String result) {
//                LoginModel.this.user = result;
//                onRequestSuccess(ACTION_LOGIN);
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
//                onRequestError(ACTION_LOGIN, e.responseCode, e.responseMessage);
//            }
//        });
//        request.setOnGlobalExceptionListener(this);
//        RequestManager.getInstance().execute(ACTION_LOGIN, request);
    }
}