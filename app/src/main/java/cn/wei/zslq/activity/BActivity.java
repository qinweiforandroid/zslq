package cn.wei.zslq.activity;

import android.content.Intent;
import android.view.View;

import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseActivity;

/**
 * Created by qinwei on 2016/3/3 17:44
 * email:qinwei_it@163.com
 */
public class BActivity extends BaseActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.a1);
    }

    @Override
    protected void initializeData() {

    }
    public void goC(View v){
        Intent intent=new Intent(this,CActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();
    }
}
