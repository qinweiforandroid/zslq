package cn.wei.zslq.model;

/**
 * Created by qinwei on 2016/1/28 10:07
 * email:qinwei_it@163.com
 */
public interface ITalkModel {
    String ACTION_LOAD_TALK_LIST = "action_load_talk_list";

    void loadTalkList(int pageNum);
}
