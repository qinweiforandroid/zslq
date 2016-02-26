package cn.wei.zslq;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.wei.library.utils.ImageDisplay;
import cn.wei.library.utils.Trace;
import cn.wei.zslq.config.AppConfig;
import cn.wei.zslq.domain.User;
import cn.wei.zslq.utils.GlideDisplay;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public static final int APP_STATE_STARTED = 1;//app 开启
    public static final int APP_STATE_LOGINED = 2;//app 登陆
    private static MyApplication mInstance;
    private static User currentUser;
    private int app_state;
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
        AppConfig config = AppConfig.getInstance();
        config.isDevelopment = true;
        config.init(getApplicationContext());
        Trace.model = config.isDevelopment;
    }

    private void initializeBmobConfig() {
        Bmob.initialize(this, "23cef32eb5b6f7cf7c6dfb88d7def740");
    }

    private void initializeDataTask() {
        if (User.getCurrentUser(getApplicationContext()) != null) {
            currentUser = BmobUser.getCurrentUser(getApplicationContext(), User.class);
            app_state = APP_STATE_LOGINED;
        }
    }

    public void initializeImageloader() {
        ImageDisplay.getInstance().init(new GlideDisplay(getApplicationContext()));
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

    public static void setLoginUser(User tmp) {
        currentUser = tmp;
    }

    public static User getLoginUser() {
        return currentUser;
    }

    public static void loginOut() {
        MyApplication.getLoginUser().logOut(mInstance.getApplicationContext());
        currentUser = null;
    }

    public static boolean isLogin() {
        return currentUser != null;
    }
}
