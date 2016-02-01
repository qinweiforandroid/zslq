package cn.wei.zslq.model;

import cn.wei.zslq.domain.Talk;
import cn.wei.zslq.domain.User;

/**
 * 说说评论列表业务接口
 * Created by qinwei on 2016/1/28 17:43
 * email:qinwei_it@163.com
 */
public interface ITalkCommentModel {
    String ACTION_LOAD_TALK_COMMENT_LIST = "action_load_talk_comment_list";
    String ACTION_DO_TALK_COMMENT="action_do_talk_comment";
    void doTalkComment(Talk talk, User fromUser, String content, int type);

    /**
     * 加载说说评论内容 一页10条记录
     * @param talk
     * @param pageNum
     */
    void loadTalkCommentList(Talk talk, int pageNum);
}
