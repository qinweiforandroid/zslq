package cn.wei.zslq.model.impl;

import android.content.Context;

import cn.bmob.v3.listener.SaveListener;
import cn.wei.zslq.model.IRegisterModel;
import cn.wei.zslq.model.ViewMode;
import cn.wei.zslq.domain.User;

/**
 * Created by qinwei on 2016/1/11 10:55
 * email:qinwei_it@163.com
 */
public class RegisterModel extends ViewMode implements IRegisterModel {
    public static final String ACTION_REGISTER = "action_register";

    public RegisterModel(Context context) {
        super(context);
    }

    @Override
    public void register(String account, String password) {
        User user = new User();
        user.setUsername(account);
        user.setNick(account);
        user.setPassword(password);
        user.setIcon("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1185115822,2221480095&fm=111&gp=0.jpg");
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                onRequestSuccess(ACTION_REGISTER);
            }

            @Override
            public void onFailure(int i, String s) {
                onRequestError(ACTION_REGISTER, i, s);
            }
        });
    }
}
