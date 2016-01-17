package cn.wei.zslq.model.impl;

import android.content.Context;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.wei.zslq.model.IRegisterModel;
import cn.wei.zslq.model.ViewMode;

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
        BmobUser user = new BmobUser();
        user.setUsername(account);
        user.setPassword(password);
        user.save(context, new SaveListener() {
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
