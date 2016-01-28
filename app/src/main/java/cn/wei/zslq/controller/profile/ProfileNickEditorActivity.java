package cn.wei.zslq.controller.profile;

import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.wei.library.utils.CommonUtil;
import cn.wei.library.utils.DensityUtil;
import cn.wei.library.widget.input.AccountView;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.model.impl.ProfileModel;
import cn.wei.zslq.support.BaseActivity;

/**
 * Created by qinwei on 2016/1/26 11:48
 * email:qinwei_it@163.com
 */
public class ProfileNickEditorActivity extends BaseActivity implements AccountView.OnTextChangedListener, Controller, View.OnClickListener {
    public static final String KEY_NICK = "key_nick";
    private String nick;
    private AccountView mProfileNickEditor;
    private TextView save;
    private ProfileModel model;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_profile_user_nick_edtior);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mProfileNickEditor = (AccountView) findViewById(R.id.mProfileNickEditor);
        mProfileNickEditor.setOnTextChangedListener(this);
    }

    @Override
    protected void initializeData() {
        setTitle("昵称");
        nick = getIntent().getStringExtra(KEY_NICK);
        mProfileNickEditor.setText(nick);
        model = new ProfileModel(this);
        model.setController(this);
    }


    @Override
    public void onClick(View v) {
        String nick = mProfileNickEditor.getText().toString().trim();
        doUpdateNick(nick);
    }

    private void doUpdateNick(final String nick) {
        CommonUtil.hideInput(this);
        model.doUpdateNick(nick);
        model.showProgress("提交中...");
    }


    @Override
    public void onSuccess(String action) {
        model.closeProgress();
        Toast.makeText(ProfileNickEditorActivity.this, "修改成功!!", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        MyApplication.getLoginUser().setNick(mProfileNickEditor.getText().toString().trim());
        finish();
    }

    @Override
    public void onFailure(String action, int errorCode, String errorMsg) {
        model.closeProgress();
    }

    @Override
    public void onTextChanged(CharSequence c) {
        if (save != null) {
            if (TextUtils.isEmpty(c)) {
                save.setEnabled(false);
            } else {
                save.setEnabled(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_nick_editor_menu, menu);
        save = new TextView(this);
        save.setText("完成");
        save.setBackgroundDrawable(null);
        int padding = DensityUtil.dip2px(this, 8);
        save.setPadding(padding, padding, padding, padding);
        save.setTextColor(getResources().getColorStateList(R.color.im_talk_send_font_selector));
        save.setOnClickListener(this);
        menu.findItem(R.id.profile_nick_action_save).setActionView(save);
        return super.onCreateOptionsMenu(menu);
    }

}
