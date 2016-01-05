package cn.wei.zslq.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by qinwei on 2015/12/23 10:46
 * email:qinwei_it@163.com
 */
public class AppConfig {
    public static final String CONFIG_NAME = "appConfig";
    public static final String KEY_PUSH = "KEY_PUSH";
    public static final String ROOT_TEST_SERVER = "http://www.baidu.com/test";
    public static final String ROOT_SERVER = "http://www.baidu.com/";
    public static AppConfig mInstance;
    private Context context;


    private AppConfig(Context context) {
        this.context = context;
    }

    public static AppConfig getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppConfig(context);
        }
        return mInstance;
    }

    public boolean isFirstOpen() {
        SharedPreferences sp = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        boolean isFirstOpen = sp.getBoolean("is_first_open", true);
        if (isFirstOpen) {
            sp.edit().putBoolean("is_first_open", false);
            return true;
        }
        return false;
    }

    public boolean isOpenPush() {
        return get(KEY_PUSH, false);
    }

    public void setPush(boolean push) {
        put(KEY_PUSH, push);
    }

    public void put(String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value);
    }

    public boolean get(String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    public PackageInfo getPackageInfo() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
