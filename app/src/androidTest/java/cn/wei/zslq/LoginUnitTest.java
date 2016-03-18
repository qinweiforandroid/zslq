package cn.wei.zslq;

import android.test.AndroidTestCase;

import cn.wei.library.utils.Trace;
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
        final LoginModel model = new LoginModel(getContext());
        model.setController(new Controller() {
            @Override
            public void onSuccess(String tag) {
                if(tag.equals(LoginModel.ACTION_LOGIN)){
                    String user=model.user;
                    Trace.d("login success userInfo:" + user);
                }
            }
            @Override
            public void onFailure(String tag, int errorCode, String errorMsg) {
                Trace.d("login err:"+errorMsg);
            }
        });
        model.login("user", "password");
        Trace.e("login Start");
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
