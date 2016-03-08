package cn.wei.zslq.model;

/**
 * Created by qinwei on 2016/3/5 21:28
 * email:qinwei_it@163.com
 */
public interface IBaiduDataSupportApiModel {
    String ACTION_LOAD_JOKE_DATA="action_load_joke_data";
    /**
     * 中国笑话数据接口
     */
    void loadJokeData(int page);
}
