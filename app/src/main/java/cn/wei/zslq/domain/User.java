package cn.wei.zslq.domain;

import cn.bmob.v3.BmobUser;

/**
 * Created by qinwei on 2015/12/22 19:05
 * email:qinwei_it@163.com
 */
public class User extends BmobUser {
    private String icon;
    private String nick;
    private String signature;
    private boolean canPublish;

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public User() {
        super();
        canPublish = true;
    }

    public String getIcon() {
        return icon;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean isCanPublish() {
        return canPublish;
    }

    public void setCanPublish(boolean canPublish) {
        this.canPublish = canPublish;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
