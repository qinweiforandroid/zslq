package cn.wei.zslq.domain;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by qinwei on 2016/3/22 13:48
 * email:qinwei_it@163.com
 */
public class Shop extends BmobObject {
    private String title;
    private double price;
    private double marketPrice;
    private String icon;
    private int reserve;//库存
    private SellerDomain seller;
    private float score;
    private ArrayList<String> images;

    public static ArrayList<Shop> getData() {
        ArrayList<Shop> shops=new ArrayList<>();
        Shop shop=new Shop();
        shop.price=37.80;
        shop.title="耐拓休闲大容量小学生书包男女生双肩儿童减负书包1-3-6年级 001大号藏青色";
        shop.icon="http://m.360buyimg.com/mobilecms/s276x276_jfs/t2275/82/461722004/318819/fb0bd4b5/561100e1N3655ca98.jpg";
        shop.marketPrice=50.0;
        shop.score=5;
        shops.add(shop);

        shop=new Shop();
        shop.price=99.00;
        shop.title="爱国者（aigo）移动电源 聚合物10000mAh 充电宝N6超薄便携通用 白色金边";
        shop.icon="http://m.360buyimg.com/mobilecms/s276x276_jfs/t1189/199/1264659439/103484/67d0798c/558ba275Ndc525258.jpg";
        shop.marketPrice=100.0;
        shop.score=5;
        shops.add(shop);

        shop=new Shop();
        shop.price=29.00;
        shop.title="金婕妤 2016春夏新款欧洲站潮欧美英伦坡跟厚底鞋松糕鞋布洛克单";
        shop.icon="http://m.360buyimg.com/mobilecms/s276x276_jfs/t1642/359/1311360292/410775/c8b745ba/55c3833dNcce1e533.jpg";
        shop.marketPrice=50.0;
        shop.score=5;
        shops.add(shop);

        shop=new Shop();
        shop.price=18.80;
        shop.title="羽翩 2016修身小吊带背心女夏外穿紧身百搭棉短款工字女装打底衫韩版 工字款玫红 均码";
        shop.icon="http://m.360buyimg.com/n12/jfs/t2389/154/1727983919/327947/ef05963d/56d8f97cN55cf4eba.jpg!q70.jpg";
        shop.marketPrice=50.0;
        shop.score=5;
        shops.add(shop);
        return shops;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    public SellerDomain getSeller() {
        return seller;
    }

    public void setSeller(SellerDomain seller) {
        this.seller = seller;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
