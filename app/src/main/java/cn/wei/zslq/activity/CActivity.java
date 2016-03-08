package cn.wei.zslq.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseActivity;

/**
 * Created by qinwei on 2016/3/3 17:44
 * email:qinwei_it@163.com
 */
public class CActivity extends BaseActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.a2);
    }

    @Override
    protected void initializeData() {

    }

    public void goA(View v){
        Intent intent=new Intent();
        EditText editText = (EditText) findViewById(R.id.editText);
        intent.putExtra("data",editText.getText().toString().trim());
        setResult(RESULT_OK,intent);
        finish();
    }
}
