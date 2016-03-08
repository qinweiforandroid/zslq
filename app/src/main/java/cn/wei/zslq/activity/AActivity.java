package cn.wei.zslq.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseActivity;

/**
 * Created by qinwei on 2016/3/3 17:44
 * email:qinwei_it@163.com
 */
public class AActivity extends BaseActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.a0);
    }

    @Override
    protected void initializeData() {

    }

    public void goB(View v){
        Intent intent=new Intent(this,BActivity.class);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            TextView mLabel = (TextView) findViewById(R.id.mLabel);
            mLabel.setText(data.getStringExtra("data"));
        }
    }
}
