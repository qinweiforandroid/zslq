package cn.wei.zslq.activity;

import android.view.View;
import android.widget.EditText;

import cn.bmob.v3.listener.SaveListener;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.R;
import cn.wei.zslq.domain.Talk;
import cn.wei.zslq.support.BaseActivity;
import http.Trace;

/**
 * Created by qinwei on 2016/1/27 11:09
 * email:qinwei_it@163.com
 */
public class TalkPublishActivity extends BaseActivity implements View.OnClickListener {
    private EditText editText;

    @Override
    protected void setContentView() {
        setContentView(R.layout.im_activity_talk_publish);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        editText = (EditText) findViewById(R.id.editText);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    protected void initializeData() {

    }

    @Override
    public void onClick(View v) {
        Talk talk = new Talk();
        talk.setContent(editText.getText().toString().trim());
        talk.setCreateUser(MyApplication.getLoginUser());
        talk.setTimestamp(System.currentTimeMillis());
        talk.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Trace.e("onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Trace.e(s);
            }
        });
    }
}
