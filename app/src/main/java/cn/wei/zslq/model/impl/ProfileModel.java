package cn.wei.zslq.model.impl;

import android.content.Context;

import cn.bmob.v3.listener.UpdateListener;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.domain.User;
import cn.wei.zslq.model.IProfileModel;
import cn.wei.zslq.model.ViewModel;
import http.Trace;

/**
 * Created by qinwei on 2016/1/28 10:51
 * email:qinwei_it@163.com
 */
public class ProfileModel extends ViewModel implements IProfileModel {
    public ProfileModel(Context context) {
        super(context);
    }

    @Override
    public void doUpdateNick(final String nick) {
        User user = new User();
        user.setValue("nick", nick);
        user.update(context, MyApplication.getLoginUser().getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                onResponseSuccess(ACTION_UPDATE_NICK);
            }

            @Override
            public void onFailure(int i, String s) {
                Trace.e(s);
                onResponseError(ACTION_UPDATE_NICK, i, s);
            }
        });
    }

    @Override
    public void doUpdateSignature(String signature) {
        User user = new User();
        user.setValue("signature", signature);
        user.update(context, MyApplication.getLoginUser().getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                onResponseSuccess(ACTION_UPDATE_SIGNATURE);
            }

            @Override
            public void onFailure(int i, String s) {
                Trace.e(s);
                onResponseError(ACTION_UPDATE_SIGNATURE, i, s);
            }
        });
    }
}
