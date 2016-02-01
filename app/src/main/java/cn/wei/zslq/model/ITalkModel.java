package cn.wei.zslq.model;

import cn.wei.zslq.domain.User;

/**
 * Created by qinwei on 2016/1/28 10:07
 * email:qinwei_it@163.com
 */
public interface ITalkModel {
    String ACTION_LOAD_TALK_LIST = "action_load_talk_list";
    String ACTION_DO_PUBLISH_TALK="action_do_publish_talk";
    void loadTalkList(int pageNum);

    void doPublishTalk(String text,User user);
}
