package cn.wei.zslq.service;

/**
 * Created by qinwei on 2015/12/18 15:37
 * email:qinwei_it@163.com
 */
public class DataServiceAPI {
    private static String DOMAIN = "https://api.bmob.cn";
    private static final String ACTION_LOGIN = "/functions/login";

    public static String getDomain() {
        return DOMAIN + "/1";
    }

    public static String loadLogin() {
        return getDomain() + ACTION_LOGIN;
    }

    public static String loadBaiduWebContent() {
        return getDomain();
    }
}
