package cn.wei.zslq.controller;

/**
 * Created by qinwei on 2015/12/18 15:53
 * email:qinwei_it@163.com
 */
public interface Controller {
    void onSuccess(String tag);

    void onFailure(String tag, int errorCode, String errorMsg);

    void onProgressUpdated(String tag, long curPos, long contentLength);

}
