package cn.wei.zslq.domain;

import java.io.Serializable;

/**
 * Created by qinwei on 2016/3/7 10:19
 * email:qinwei_it@163.com
 */
public class JokeBean implements Serializable {
    private String ct;//创建时间

    private String text;//笑话内容

    private String title;//标题

    private int type;//类型

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getCt() {
        return this.ct;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
