package cn.wei.zslq.controller.main;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.wei.library.utils.CommonUtil;
import cn.wei.library.utils.TextUtil;
import cn.wei.library.widget.input.AccountView;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.model.impl.LoginModel;
import cn.wei.zslq.support.BaseActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements Controller, OnClickListener {
    // UI references.
    private AccountView mEmailView;
    private AccountView mLoginPwd;
    private Button mEmailSignInButton;
    private LoginModel viewMode;
    private Button mLoginGoRegisterBtn;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mEmailView = (AccountView) findViewById(R.id.mLoginAccount);
        mLoginPwd = (AccountView) findViewById(R.id.mLoginPwd);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mLoginGoRegisterBtn = (Button) findViewById(R.id.mLoginGoRegisterBtn);
        mLoginGoRegisterBtn.setOnClickListener(this);
        mEmailSignInButton.setEnabled(false);
        mEmailView.setOnTextChangedListener(new AccountView.OnTextChangedListener() {
            @Override
            public void onTextChanged(CharSequence c) {
                changedCommitState();
            }
        });
        mLoginPwd.setOnTextChangedListener(new AccountView.OnTextChangedListener() {
            @Override
            public void onTextChanged(CharSequence c) {
                changedCommitState();
            }
        });
    }

    public void changedCommitState() {
        if (TextUtil.isValidate(mEmailView.getText().toString(), mLoginPwd.getText().toString())) {
            mEmailSignInButton.setEnabled(true);
        } else {
            mEmailSignInButton.setEnabled(false);
        }
    }

    @Override
    protected void initializeData() {
        setTitle("login");
        viewMode = new LoginModel(this);
        viewMode.setController(this);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }


    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mLoginPwd.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !viewMode.isPasswordValid(password)) {
            focusView = mLoginPwd;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            focusView = mEmailView;
            cancel = true;
        } else if (!viewMode.isEmailValid(email)) {
            focusView = mEmailView;
            cancel = true;
        }
        CommonUtil.hideInput(LoginActivity.this);
        if (cancel) {
            focusView.requestFocus();
        } else {
            doLogin(email, password);
        }
    }

    private void doLogin(String email, String password) {
        if (!viewMode.checkNetworkConnected()) {
            return;
        }
        viewMode.showProgress("");
        viewMode.login(email, password);
    }

    private void goHome() {
        Intent intent = new Intent(this, HomeBottomActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(String tag) {
        if (tag.equals(LoginModel.ACTION_LOGIN)) {
            viewMode.closeProgress();
            goHome();
            Toast.makeText(this, "欢迎，" + BmobUser.getCurrentUser(this).getUsername(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(String tag, int errorCode, String errorMsg) {
        if (tag.equals(LoginModel.ACTION_LOGIN)) {
            viewMode.closeProgress();
            Toast.makeText(this, "" + errorMsg, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mLoginGoRegisterBtn:
                goRegister();
                break;
        }
    }

    private void goRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}

