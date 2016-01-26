package cn.wei.zslq.domain;

import cn.bmob.v3.BmobObject;

/**
 * Created by qinwei on 2016/1/6 13:32
 * email:qinwei_it@163.com
 */
public class ThunderAccount extends BmobObject {
    public static final int account_type_xunlei = 0;//迅雷
    public static final int account_type_leshi = 1;//乐视
    public static final int account_type_iqiyi = 2;//爱奇艺
    private String account;
    private String password;
    private int accountType;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}
