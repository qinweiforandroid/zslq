package cn.wei.zslq.utils;

import cn.wei.zslq.config.AppConfig;

/**
 * Created by qinwei on 2015/10/30 23:46
 * email:qinwei_it@163.com
 */
public class UrlHelpper {
    public static final String DOMAIN = "http://api.playsm.com/index.php";

    public static final String BAIDU_APIKEY = "d21e61b696755d2ca3d1d3484bcc5331";
    /**
     * 查看资讯列表
     */
    public static final String ACTION_FIND_INFORMATION_LIST = "?r=message/list";

    public static final String ACTION_GET_INFORMATION_DETAIL = "?r=message/detail";

    public static String getDomain() {
        if (AppConfig.getInstance().isDevelopment) {
            return DOMAIN;
        }
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


    /**
     * 微信热门文章
     *
     * @return
     */
    public static String getWinXinHotArticle() {
        return "http://apis.baidu.com/txapi/weixin/wxhot";
    }

    public static String loadJokeData(int page) {
        return "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text?page="+page;
    }
}
