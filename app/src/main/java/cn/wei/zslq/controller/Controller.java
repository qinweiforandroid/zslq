package cn.wei.zslq.controller;

/**
 * Created by qinwei on 2015/12/18 15:53
 * email:qinwei_it@163.com
 */
public interface Controller {
    void onSuccess(String action);

    void onFailure(String action, int errorCode, String errorMsg);
}
