package cn.wei.zslq.domain;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by qinwei on 2016/1/24 20:20
 * email:qinwei_it@163.com
 */
public class SellerDomain extends BmobObject {
    public String title;
    public String image;
    public ArrayList<String> images;
    public String sellerName;
    public String qq;
    public String phone;
    public String descriptor;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }
}
