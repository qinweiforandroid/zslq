package cn.wei.zslq.controller;

/**
 * Created by qinwei on 2016/1/13 13:53
 * email:qinwei_it@163.com
 */
public interface OnProgressUpdatedListener {
    void onProgressUpdated(String action, long curPos, long contentLength);
}
