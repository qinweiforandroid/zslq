package cn.wei.zslq.utils;

/**
 * Created by qinwei on 2015/10/30 23:46
 * email:qinwei_it@163.com
 */
public class UrlHelpper {
    public static final String DOMAIN = "http://api.playsm.com/index.php";

    /**
     * 查看资讯列表
     */
    public static final String ACTION_FIND_INFORMATION_LIST = "?r=message/list";

    public static final String ACTION_GET_INFORMATION_DETAIL = "?r=message/detail";

    public static String getDomain() {
        return DOMAIN;
    }

    public static String loadInformationList() {
        return getDomain() + ACTION_FIND_INFORMATION_LIST + "&size=" + 10;
    }

    public static String loadInformationDetail(String informationId) {
        return getDomain() + ACTION_GET_INFORMATION_DETAIL + "&id=" + informationId;
    }

    public static String showImage(String uri) {
        return getDomain() + uri;
    }
}
