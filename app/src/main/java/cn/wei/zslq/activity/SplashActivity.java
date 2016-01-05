package cn.wei.zslq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

import cn.wei.zslq.MyApplication;
import cn.wei.zslq.R;

public class SplashActivity extends Activity {
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MyApplication.getInstance().setAppState(MyApplication.APP_STATE_STARTED);
        Log.d("wei", "app started");
//		TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
//		Toast.makeText(this, tm.getDeviceId(), 1000).show();
        mHandler.sendEmptyMessageDelayed(0, 2000);
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
