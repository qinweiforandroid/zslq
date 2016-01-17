package cn.wei.zslq;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

import cn.bmob.v3.Bmob;
import http.Trace;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public static final int APP_STATE_STARTED = 1;//app 开启
    public static final int APP_STATE_LOGINED = 2;//app 登陆
    private static MyApplication mInstance;
    private int app_state;
    public static boolean isDevelop = true;
    private boolean isForeground;//应用是否在前台 true代表前台，false代表后台
    private int activityCount;

    @Override
    public void onCreate() {
        super.onCreate();
        app_state = -1;//-1 app未开启 或者 被强杀导致
        mInstance = this;
        initializeImageloader();
        initializeBmobConfig();
        initializeDataTask();
        registerActivityLifecycleCallbacks(this);
    }



    private void initializeBmobConfig() {
        Bmob.initialize(this, "23cef32eb5b6f7cf7c6dfb88d7def740");
    }

    private void initializeDataTask() {

    }

    public void initializeImageloader() {
        String cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ZSLQ" + File.separator + "cache";
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCache(new UnlimitedDiscCache(new File(cacheDir)));
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
    public static MyApplication getInstance() {
        return mInstance;
    }
    public int getAppState() {
        return app_state;
    }

    public boolean isAppKilled() {
        return app_state == -1;
    }

    public void setAppState(int state) {
        this.app_state = state;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        activityCount++;
        Trace.d(activity.getClass().getSimpleName() + ":onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        isForeground = true;
        Trace.d(activity.getClass().getSimpleName() + ":onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Trace.d(activity.getClass().getSimpleName() + ":onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;
        Trace.d(activity.getClass().getSimpleName() + ":onActivityStopped，" + activityCount);
        if (activityCount == 0) {
            isForeground = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public boolean isForeground() {
        return isForeground;
    }
}
