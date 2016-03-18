package cn.wei.zslq.controller.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

import cn.wei.zslq.MyApplication;
import cn.wei.zslq.R;
import cn.wei.zslq.config.AppConfig;

public class SplashActivity extends Activity {
    public static final int STATE_LOGINED = 100;
    public static final int STATE_NO_LOGIN = 200;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_LOGINED:
//                    if(System.currentTimeMillis()%2==0){
                        startActivity(new Intent(SplashActivity.this, HomeBottomActivity.class));
//                    }else{
//                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
//                    }
                    break;
                case STATE_NO_LOGIN:
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    break;
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MyApplication.getInstance().setAppState(MyApplication.APP_STATE_STARTED);
        if (AppConfig.getInstance().isFirstOpen()) {
            Log.e("wei", "app is first start");
            AppConfig.getInstance().saveCurrentVersionCode();
        } else {
            Log.e("wei", "app started");
        }
//		TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
//		Toast.makeText(this, tm.getDeviceId(), 1000).show();
        if (MyApplication.getLoginUser() == null) {
            mHandler.sendEmptyMessageDelayed(STATE_NO_LOGIN, 2000);

        } else {
            mHandler.sendEmptyMessageDelayed(STATE_LOGINED, 2000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
