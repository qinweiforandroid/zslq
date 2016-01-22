package cn.wei.zslq.entity;

import cn.bmob.v3.BmobUser;

/**
 * Created by qinwei on 2015/12/22 19:05
 * email:qinwei_it@163.com
 */
public class User  extends BmobUser {
    private String icon;
    private String nick;
    public void setIcon(String icon) {
        this.icon = icon;
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
}
