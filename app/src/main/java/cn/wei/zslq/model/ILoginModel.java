package cn.wei.zslq.model;

/**
 * 登录接口
 * Created by qinwei on 2015/12/22 18:59
 * email:qinwei_it@163.com
 */
public interface ILoginModel {
    /**
     * 请求登录
     *
     * @param user     用户名
     * @param password 密码
     */
    void login(String user, String password);
}
