package cn.wei.zslq.fragment;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.PullToRefreshBase;

import java.util.ArrayList;

import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.library.utils.ImageDisplay;
import cn.wei.library.widget.card.CardClickListener;
import cn.wei.library.widget.card.CardDescriptor;
import cn.wei.library.widget.card.CardGroupDescriptor;
import cn.wei.library.widget.card.CardGroupView;
import cn.wei.library.widget.viewpager.AutoScrollViewPager;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.controller.seller.SellerDetailActivity;
import cn.wei.zslq.domain.SellerDomain;
import cn.wei.zslq.model.impl.SellerModel;
import cn.wei.zslq.support.BaseListFragment;

/**
 * Created by qinwei on 2016/3/18 11:35
 * email:qinwei_it@163.com
 */
public class InformationServiceFragment extends BaseListFragment implements Controller {
    ArrayList<String> imageIdList = null;
    private AutoScrollViewPager viewPager;
    private SellerModel model;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_information_service;
    }


    @Override
    protected void addRefreshHeaderView(ListView refreshableView) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_information_service_header, null);
        AutoScrollViewPager viewPager = (AutoScrollViewPager) v.findViewById(R.id.mServiceAutoScrollViewPager);
        initializeViewPager(viewPager);
        CardGroupView cardGroupView = (CardGroupView) v.findViewById(R.id.mServiceCardGroupView);
        cardGroupView.configColumnSize(3);
        ArrayList<CardDescriptor> cards = new ArrayList<>();
        cards.add(new CardDescriptor("1", "http://img3.imgtn.bdimg.com/it/u=1070621953,136357622&fm=21&gp=0.jpg", "找需求"));
        cards.add(new CardDescriptor("2", "http://img1.imgtn.bdimg.com/it/u=1326180773,2606853188&fm=21&gp=0.jpg", "找工作"));
        cards.add(new CardDescriptor("3", "http://img1.imgtn.bdimg.com/it/u=72424572,436883779&fm=21&gp=0.jpg", "找对象"));
        cards.add(new CardDescriptor("4", "http://img5.imgtn.bdimg.com/it/u=2864778312,3166905915&fm=21&gp=0.jpg", "超市"));
        cards.add(new CardDescriptor("6S", "http://img0.imgtn.bdimg.com/it/u=2043496843,2949780039&fm=21&gp=0.jpg", "聚会"));
        cards.add(new CardDescriptor("7", "http://img1.imgtn.bdimg.com/it/u=3357422860,149963283&fm=21&gp=0.jpg", "食谱"));
        CardGroupDescriptor descriptor = new CardGroupDescriptor(cards, "", "", 0);
        cardGroupView.initializeData(descriptor, new CardClickListener() {
            @Override
            public void onCardClickListener(String cardId) {

            }
        });
        cardGroupView.notifyDataChanged();
        refreshableView.addHeaderView(v);
    }

    private void initializeViewPager(AutoScrollViewPager viewPager) {
        this.viewPager = viewPager;
        imageIdList = new ArrayList<>();
        imageIdList.add("http://c.hiphotos.baidu.com/image/pic/item/3b292df5e0fe99257602967631a85edf8db1718e.jpg");
        imageIdList.add("http://b.hiphotos.baidu.com/image/pic/item/377adab44aed2e73ca14790a8201a18b87d6fa13.jpg");
        imageIdList.add("http://e.hiphotos.baidu.com/image/pic/item/aec379310a55b319c7427d5346a98226cffc1758.jpg");
        imageIdList.add("http://g.hiphotos.baidu.com/image/pic/item/0823dd54564e9258370a0ac89982d158ccbf4ede.jpg");
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyAdapter());
        viewPager.setInterval(3000);
        viewPager.startAutoScroll();
//        indicator.setViewPager(viewPager);
//        indicator.setCurrentItem(2);
    }

    @Override
    protected void initializeView(View v) {
        super.initializeView(v);
        model = new SellerModel(getActivity());
        model.setController(this);
        model.loadSellerInfoList(true);
        mPullToRefreshLsv.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    @Override
    public void onSuccess(String action) {
        modules.addAll(model.sellers);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String action, int errorCode, String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_serivce_hot_simple_info_item, null);
            holder.initializeView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.initializeData(position);
        return convertView;
    }


    public class ViewHolder extends QBaseViewHolder {
        private TextView mSellerItemTitleLabel;
        private TextView mSellerItemDescriptorLabel;
        private ImageView mSellerItemIconImg;
        private TextView mSellerItemNameLabel;
        private SellerDomain seller;

        @Override
        public void initializeView(View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SellerDetailActivity.class);
                    intent.putExtra(SellerDetailActivity.KEY_SELLER_DOMAIN, seller);
                    startActivity(intent);
                }
            });
            mSellerItemTitleLabel = (TextView) view.findViewById(R.id.mSellerItemTitleLabel);
            mSellerItemDescriptorLabel = (TextView) view.findViewById(R.id.mSellerItemDescriptorLabel);
            mSellerItemNameLabel = (TextView) view.findViewById(R.id.mSellerItemNameLabel);
            mSellerItemIconImg = (ImageView) view.findViewById(R.id.mSellerItemIconImg);
        }

        @Override
        public void initializeData(int position) {
            seller = (SellerDomain) modules.get(position);
            mSellerItemTitleLabel.setText(seller.getTitle());
            mSellerItemDescriptorLabel.setText(seller.descriptor);
            mSellerItemNameLabel.setText(seller.getSellerName());
            ImageDisplay.getInstance().displayImage(seller.image, mSellerItemIconImg, R.drawable.ic_launcher, R.drawable.ic_launcher);
        }
    }


    private class MyAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        /**
         * 销毁当前page的相隔2个及2个以上的item时调用
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("test", "销毁view位置:" + position);
            container.removeView((View) object); // 删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView photoView = new ImageView(container.getContext());
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            ImageDisplay.getInstance().displayImage(imageIdList.get(position), photoView,
                    R.drawable.ic_launcher, R.drawable.ic_launcher);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            Log.i("test", "实例化view位置pos:" + position);
            return photoView; // 返回该view对象，作为key
        }

        @Override
        public int getCount() {
            return imageIdList.size();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        viewPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.startAutoScroll();
    }
}
