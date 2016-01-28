package cn.wei.zslq.model.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.wei.zslq.domain.Talk;
import cn.wei.zslq.model.ITalkModel;
import cn.wei.zslq.model.ViewModel;
import http.Trace;

/**
 * Created by qinwei on 2016/1/28 10:08
 * email:qinwei_it@163.com
 */
public class TalkModel extends ViewModel implements ITalkModel {
    public static final int PAGE_SIZE=5;
    public ArrayList<Talk> talks;

    public TalkModel(Context context) {
        super(context);
        talks = new ArrayList<>();
    }

    @Override
    public void loadTalkList(int pageNum) {
        BmobQuery<Talk> query = new BmobQuery<>();
        query.setLimit(PAGE_SIZE);
        query.setSkip((pageNum - 1) * PAGE_SIZE);
        query.include("createUser");
        query.order("-createdAt");
        query.findObjects(context, new FindListener<Talk>() {
            @Override
            public void onSuccess(List<Talk> list) {
                talks = (ArrayList<Talk>) list;
                onResponseSuccess(ACTION_LOAD_TALK_LIST);
            }

            @Override
            public void onError(int i, String s) {
                Trace.e("onError:" + s);
                onResponseError(ACTION_LOAD_TALK_LIST, i, s);
            }
        });
    }
}
