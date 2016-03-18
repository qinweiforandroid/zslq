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
    public static final String CONFIG_NAME = "appConfig.xml";
    public static final String KEY_PUSH = "KEY_PUSH";
    public static final String KEY_CURRENT_VERSION_CODE = "KEY_CURRENT_VERSION_CODE";
    public static final String ROOT_TEST_SERVER = "http://www.baidu.com/test";
    public static final String ROOT_SERVER = "http://www.baidu.com/";
    public static final String ROOT_IMAGE = "http://www.baidu.com/image";
    public static final String ROOT_TEST_IMAGE = "http://www.baidu.com/image";

    public static AppConfig mInstance;
    public boolean isDevelopment;
    private Context context;


    public static AppConfig getInstance() {
        if (mInstance == null) {
            synchronized (AppConfig.class) {
                mInstance = new AppConfig();
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public boolean isFirstOpen() {
        if (getSharedPreferences().getBoolean("key_is_first_open", true) || canNewShow()) {
            put("key_is_first_open", false);
            return true;
        }
        return false;
    }

    public void saveCurrentVersionCode() {
        put(KEY_CURRENT_VERSION_CODE, getPackageInfo().versionCode);
    }

    /**
     * 判断版本更新后显示更新轮播图
     *
     * @return
     */
    private boolean canNewShow() {
        int code = get(KEY_CURRENT_VERSION_CODE);
        if (code == 0) {
            return true;
        } else {
            return code < getPackageInfo().versionCode;
        }

    }

    public void put(String key, boolean value) {
        getEditor().putBoolean(key, value).commit();
    }

    public void put(String key, int value) {
        getEditor().putInt(key, value).commit();
    }

    public int get(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

    public boolean get(String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    public boolean isCanPush() {
        return get(KEY_PUSH, false);
    }

    public void pushEnable(boolean push) {
        put(KEY_PUSH, push);
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
