package cn.wei.zslq.utils;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by qinwei on 2016/1/4 14:57
 * email:qinwei_it@163.com
 */
public class ActionClickUtils {
    /**
     * 登录
     */
    public static final java.lang.String ACTION_LOGIN = "100001";
    public static final java.lang.String ACTION_LOOK_INFORMATION = "1000001";

    public static void onEvent(Context context, String eventId) {
        MobclickAgent.onEvent(context, eventId);
    }

    public static void onEvent(Context context, String eventId, HashMap map) {
        MobclickAgent.onEvent(context, eventId, map);
    }
}
