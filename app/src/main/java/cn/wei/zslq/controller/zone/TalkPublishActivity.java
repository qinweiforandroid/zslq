package cn.wei.zslq.controller.zone;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qinwei.photoselector.BasePublishPhotoActivity;

import java.util.ArrayList;

import cn.wei.library.utils.DensityUtil;
import cn.wei.library.utils.Trace;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.model.impl.TalkModel;

/**
 * Created by qinwei on 2016/1/27 11:09
 * email:qinwei_it@163.com
 */
public class TalkPublishActivity extends BasePublishPhotoActivity implements View.OnClickListener, TextWatcher, Controller {
    public static final String KEY_TALK_ENTITIES = "key_talk_entities";
    private EditText mTalkContentEdt;
    private TextView publish;
    private TalkModel model;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_zone_talk_publish);
    }

    @Override
    public void initializeView() {
        super.initializeView();
        setTheme(R.style.ActionSheetStyleIOS7);
        mTalkContentEdt = (EditText) findViewById(R.id.mTalkContentEdt);
        mTalkContentEdt.addTextChangedListener(this);
    }

    @Override
    protected void initializeData() {
        setTitle(getString(R.string.title_talk_add));
        model = new TalkModel(this);
        model.setController(this);
        modules.add("");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.talk_publish_menu, menu);
        publish = new TextView(this);
        publish.setText("发表");
        int padding = DensityUtil.dip2px(this, 8);
        publish.setTextSize(16);
        publish.setPadding(padding, padding, padding, padding);
        publish.setTextColor(getResources().getColorStateList(R.color.im_talk_send_font_selector));
        publish.setOnClickListener(this);
        publish.setEnabled(false);
        menu.findItem(R.id.talk_action_add).setActionView(publish);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        model.showProgress("提交中...");
        ArrayList<String> uploadPhotos = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            uploadPhotos.add(photos.get(i).imagePath);
        }
        model.doPublishTalk(mTalkContentEdt.getText().toString().trim(), uploadPhotos, MyApplication.getLoginUser());
    }

    @Override
    public void onSuccess(String action) {
        model.closeProgress();
        Intent data = new Intent();
        data.putExtra(KEY_TALK_ENTITIES, model.talk);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onFailure(String action, int errorCode, String errorMsg) {
        Trace.e(errorMsg);
        model.closeProgress();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            publish.setEnabled(false);
        } else {
            publish.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
