package cn.wei.zslq.controller.seller;

import android.widget.ImageView;
import android.widget.TextView;

import cn.wei.library.utils.ImageUtils;
import cn.wei.zslq.R;
import cn.wei.zslq.domain.SellerDomain;
import cn.wei.zslq.support.BaseActivity;

/**
 * Created by qinwei on 2016/1/24 21:48
 * email:qinwei_it@163.com
 */
public class SellerDetailActivity extends BaseActivity{
    public static final String KEY_SELLER_DOMAIN="key_seller_domain";
    private SellerDomain seller;
    private ImageView mSellerIconImg;
    private TextView mSellerTitleLabel;
    private TextView mSellerQQLabel;
    private TextView mSellerDescriptorLabel;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_seller_detail_info);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        mSellerIconImg=(ImageView) findViewById(R.id.mSellerIconImg);
        mSellerTitleLabel=(TextView)findViewById(R.id.mSellerTitleLabel);
        mSellerDescriptorLabel=(TextView)findViewById(R.id.mSellerDescriptorLabel);
        mSellerQQLabel=(TextView)findViewById(R.id.mSellerQQLabel);
    }

    @Override
    protected void initializeData() {
        seller=( SellerDomain)getIntent().getSerializableExtra(KEY_SELLER_DOMAIN);
        setTitle(seller.getSellerName());
        mSellerTitleLabel.setText(seller.getTitle());
        mSellerDescriptorLabel.setText("\t\t\t"+seller.getDescriptor());
        mSellerQQLabel.setText(seller.qq);
        ImageUtils.displayImage(seller.image,mSellerIconImg,ImageUtils.getUserIconOptions());
    }
}
