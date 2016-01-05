package cn.wei.zslq.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.wei.zslq.support.BaseActivity;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.model.impl.LoginModel;
import cn.wei.zslq.utils.CommonUtil;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements Controller {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private Button mEmailSignInButton;
    private LoginModel viewMode;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mLoginFormView = findViewById(R.id.login_form);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.account);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
    }


    @Override
    protected void initializeData() {
        setTitle("login");
        viewMode = new LoginModel(this);
        viewMode.setController(this);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.hideInput(LoginActivity.this);
                attemptLogin();
            }
        });
        List<String> acounts = new ArrayList<>();
        acounts.add("qinwei_it@163.com");
        acounts.add("qinmi@163.com");
        addEmailsToAutoComplete(acounts);
    }


    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !viewMode.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!viewMode.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            focusView.requestFocus();
        } else {
//            goHome();
            doLogin(email, password);
        }
    }

    private void doLogin(String email, String password) {
        viewMode.login(email, password);
    }

    private void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
        mEmailView.setAdapter(adapter);
    }


    @Override
    public void onSuccess(String tag) {
        if (tag.equals(LoginModel.ACTION_LOGIN)) {
//TODO: Replace this with your own logic
            goHome();
            Toast.makeText(this, viewMode.user, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(String tag, int errorCode, String errorMsg) {
        if (tag.equals(LoginModel.ACTION_LOGIN)) {
            Toast.makeText(this, "errorCode-----" + errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onProgressUpdated(String tag, long curPos, long contentLength) {

    }
}

