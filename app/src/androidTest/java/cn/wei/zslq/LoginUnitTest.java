package cn.wei.zslq;

import android.test.AndroidTestCase;

import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.model.impl.LoginModel;

/**
 * Created by qinwei on 2016/1/13 11:57
 * email:qinwei_it@163.com
 */
public class LoginUnitTest extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testLogin() {
        LoginModel model = new LoginModel(getContext());
        model.login("user", "password");
        model.setController(new Controller() {
            @Override
            public void onSuccess(String tag) {

            }

            @Override
            public void onFailure(String tag, int errorCode, String errorMsg) {

            }
        });
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
