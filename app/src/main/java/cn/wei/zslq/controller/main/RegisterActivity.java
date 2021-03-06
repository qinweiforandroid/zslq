package cn.wei.zslq.controller.main;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.wei.library.widget.input.AccountView;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.model.impl.RegisterModel;
import cn.wei.zslq.core.BaseActivity;

/**
 * Created by qinwei on 2016/1/11 10:18
 * email:qinwei_it@163.com
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener, Controller {

    private AccountView mRegisterAccount;
    private AccountView mRegisterPwd;
    private Button mRegisterCommitBtn;
    private RegisterModel model;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mRegisterAccount = (AccountView) findViewById(R.id.mRegisterAccount);
        mRegisterPwd = (AccountView) findViewById(R.id.mRegisterPwd);
        mRegisterCommitBtn = (Button) findViewById(R.id.mRegisterCommitBtn);
        mRegisterCommitBtn.setEnabled(false);
        mRegisterCommitBtn.setOnClickListener(this);
        setTitle("注册");
    }

    @Override
    protected void initializeData() {
        mRegisterAccount.setOnTextChangedListener(new AccountView.OnTextChangedListener() {
            @Override
            public void onTextChanged(CharSequence c) {
                validInput();
            }
        });
        mRegisterPwd.setOnTextChangedListener(new AccountView.OnTextChangedListener() {
            @Override
            public void onTextChanged(CharSequence c) {
                validInput();
            }
        });
        model = new RegisterModel(this);
        model.setController(this);
    }

    public void validInput() {
        if (mRegisterAccount.getText().toString().isEmpty() || mRegisterPwd.getText().toString().isEmpty()) {
            mRegisterCommitBtn.setEnabled(false);
        } else {
            mRegisterCommitBtn.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        String account = mRegisterAccount.getText().toString();
        String pwd = mRegisterPwd.getText().toString();
        if (validAccount(account) && validPwd(pwd)) {
            model.register(account, pwd);
        }else{
            model.showToast("用户名和密码长度不少于6");
        }
    }

    public boolean validAccount(String account) {
        return account.length() > 5;
    }

    public boolean validPwd(String pwd) {
        return pwd.length() > 5;
    }

    @Override
    public void onSuccess(String tag) {
        if(tag.equals(RegisterModel.ACTION_REGISTER)){
            model.showToast("注册成功!");
            finish();
        }
    }

    @Override
    public void onFailure(String tag, int errorCode, String errorMsg) {
        Toast.makeText(RegisterActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
    }

}
