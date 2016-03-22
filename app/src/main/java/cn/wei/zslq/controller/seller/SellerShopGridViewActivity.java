package cn.wei.zslq.controller.seller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.PullToRefreshBase;

import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.library.utils.ImageDisplay;
import cn.wei.zslq.R;
import cn.wei.zslq.domain.Shop;
import cn.wei.zslq.support.BaseGridViewListActivity;

/**
 * Created by qinwei on 2016/3/22 11:42
 * email:qinwei_it@163.com
 */
public class SellerShopGridViewActivity extends BaseGridViewListActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_seller_shop_list);
    }

    @Override
    protected void initializeData() {
        setTitle("店内商品");
        mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.DISABLED);
        modules.addAll(Shop.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this).inflate(R.layout.list_shop_item, null);
            holder.initializeView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.initializeData(position);
        return convertView;
    }

    public class ViewHolder extends QBaseViewHolder {

        private ImageView mShopItemIconImg;
        private TextView mShopItemTitleLabel;
        private TextView mShopItemPriceLabel;

        @Override
        public void initializeView(View view) {
            mShopItemIconImg = (ImageView) view.findViewById(R.id.mShopItemIconImg);
            mShopItemTitleLabel = (TextView) view.findViewById(R.id.mShopItemTitleLabel);
            mShopItemPriceLabel = (TextView) view.findViewById(R.id.mShopItemPriceLabel);
        }

        @Override
        public void initializeData(int position) {
            Shop shop = (Shop) modules.get(position);
            ImageDisplay.getInstance().displayImage(shop.getIcon(), mShopItemIconImg, R.drawable.ic_launcher, R.drawable.ic_launcher);
            mShopItemPriceLabel.setText("￥" + shop.getPrice());
            mShopItemTitleLabel.setText(shop.getTitle());
        }
    }
}
