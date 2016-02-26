package cn.wei.zslq.model.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.wei.library.utils.Trace;
import cn.wei.zslq.domain.Talk;
import cn.wei.zslq.domain.TalkComment;
import cn.wei.zslq.domain.User;
import cn.wei.zslq.model.ITalkCommentModel;
import cn.wei.zslq.model.ViewModel;

/**
 * 业务实现类
 * Created by qinwei on 2016/1/28 17:43
 * email:qinwei_it@163.com
 */
public class TalkCommentModel extends ViewModel implements ITalkCommentModel {
    public static final int PAGE_SIZE = 10;
    public ArrayList<TalkComment> comments;
    public TalkComment comment;

    public TalkCommentModel(Context context) {
        super(context);
        comments = new ArrayList<>();
    }

    @Override
    public void doTalkComment(Talk talk, User fromUser, String content, int type) {
        comment = new TalkComment();
        comment.setReplyType(type);
        comment.setFromUser(fromUser);
        comment.setContent(content);
        comment.setTalk(talk);
        comment.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                Trace.e("onSuccess");
                onResponseSuccess(ACTION_DO_TALK_COMMENT);
            }

            @Override
            public void onFailure(int i, String s) {
                onResponseError(ACTION_DO_TALK_COMMENT, i, s);
            }
        });
    }

    @Override
    public void loadTalkCommentList(Talk talk, int pageNum) {
        BmobQuery<TalkComment> query = new BmobQuery<>();
        query.addWhereEqualTo("talk", new BmobPointer(talk));
        query.include("fromUser");
        query.setLimit(PAGE_SIZE);
        query.setSkip((pageNum - 1) * PAGE_SIZE);
        query.order("-createdAt");
        query.findObjects(context, new FindListener<TalkComment>() {
            @Override
            public void onSuccess(List<TalkComment> list) {
                comments = (ArrayList<TalkComment>) list;
                onResponseSuccess(ACTION_LOAD_TALK_COMMENT_LIST);
            }

            @Override
            public void onError(int i, String s) {
                Trace.e("onError:" + s);
                onResponseError(ACTION_LOAD_TALK_COMMENT_LIST, i, s);
            }
        });
    }
}
