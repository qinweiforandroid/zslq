package cn.wei.zslq.controller.seller;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.wei.library.utils.ImageDisplay;
import cn.wei.zslq.R;
import cn.wei.zslq.domain.SellerDomain;
import cn.wei.zslq.core.BaseActivity;

/**
 * Created by qinwei on 2016/1/24 21:48
 * email:qinwei_it@163.com
 */
public class SellerDetailActivity extends BaseActivity {
    public static final String KEY_SELLER_DOMAIN = "key_seller_domain";
    private SellerDomain seller;
    private ImageView mSellerIconImg;
    private TextView mSellerQQLabel;
    private TextView mSellerDescriptorLabel;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_seller_detail_info);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mSellerIconImg = (ImageView) findViewById(R.id.mSellerIconImg);
        mSellerDescriptorLabel = (TextView) findViewById(R.id.mSellerDescriptorLabel);
        mSellerQQLabel = (TextView) findViewById(R.id.mSellerQQLabel);
    }

    @Override
    protected void initializeData() {
        seller = (SellerDomain) getIntent().getSerializableExtra(KEY_SELLER_DOMAIN);
        setTitle(seller.getSellerName());
        mSellerDescriptorLabel.setText("\t\t\t" + seller.getDescriptor());
        mSellerQQLabel.setText(seller.qq);
        ImageDisplay.getInstance().displayImage(seller.image, mSellerIconImg);
    }

    public void goSellerShops(View view) {
        Intent intent = new Intent(this, SellerShopGridViewActivity.class);
        startActivity(intent);
    }

    public void goSellerComments(View view) {

    }
}
