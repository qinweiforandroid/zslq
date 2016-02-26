package cn.wei.zslq.controller.seller;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.PullToRefreshBase;

import java.util.ArrayList;

import cn.bmob.v3.listener.SaveListener;
import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.library.utils.ImageDisplay;
import cn.wei.library.utils.Trace;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.domain.SellerDomain;
import cn.wei.zslq.model.impl.SellerModel;
import cn.wei.zslq.support.BaseListActivity;

/**
 * Created by qinwei on 2016/1/24 20:58
 * email:qinwei_it@163.com
 */
public class SellerSimpleInfoListActivity extends BaseListActivity implements Controller {
    private SellerModel model;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_seller_simple_info_list);
    }

    @Override
    protected void initializeView() {
        super.initializeView();
        setTitle("商家列表");
        mPullToRefreshLsv.setMode(PullToRefreshBase.Mode.DISABLED);
    }


    @Override
    protected void initializeData() {
        model = new SellerModel(this);
        model.setController(this);
        model.loadSellerInfoList();
    }

    @Override
    public void onSuccess(String action) {
        modules.addAll(model.sellers);
        adapter.notifyDataSetChanged();
        showContent();
    }

    @Override
    public void onFailure(String action, int errorCode, String errorMsg) {
        Toast.makeText(SellerSimpleInfoListActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SellerDomain domain = (SellerDomain) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this, SellerDetailActivity.class);
        intent.putExtra(SellerDetailActivity.KEY_SELLER_DOMAIN, domain);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seller_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.seller_action_add:
                editor();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editor() {
        SellerDomain seller = new SellerDomain();
        seller.descriptor = "万达广场即将落户咱们刘圩镇啦，大家尽请期待吧！";
        seller.image = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1693809549,2938090561&fm=21&gp=0.jpg";
        seller.title = "完达山奶粉你的信任，我的责任……";
        seller.sellerName = "完达山世纪贝贝店";
        ArrayList<String> is = new ArrayList<>();
        is.add("http://b374.photo.store.qq.com/psb?/V1169V182QreBu/FZMuk0TnCPUiUpBIiioRFNG*JbrMAa0DMav9tj2suoY!/b/dHYBAAAAAAAA&bo=7gL6AQAAAAAFADQ!&rf=viewer_311");
        is.add("http://a1.qpic.cn/psb?/V1169V182QreBu/6ZtPMvQFx58SevFj2T6yi6z5jsaY2zTdhAdmiptXlmw!/b/dHQBAAAAAAAA&ek=1&kp=1&pt=0&bo=EQPGAQAAAAAFAPY!&sce=50-1-1&rf=viewer_311");
        seller.images = is;
        seller.qq = "1239175655";
        seller.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Trace.d("onSuccess");
            }

            @Override
            public void onFailure(int i, String s) {
                Trace.e(i + " onFailure " + s);
            }
        });
    }

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this).inflate(R.layout.list_seller_simple_info_item, null);
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
        private TextView mSellerItemTimeLabel;

        @Override
        public void initializeView(View view) {
            mSellerItemTitleLabel = (TextView) view.findViewById(R.id.mSellerItemTitleLabel);
            mSellerItemDescriptorLabel = (TextView) view.findViewById(R.id.mSellerItemDescriptorLabel);
            mSellerItemTimeLabel = (TextView) view.findViewById(R.id.mSellerItemTimeLabel);
            mSellerItemIconImg = (ImageView) view.findViewById(R.id.mSellerItemIconImg);
        }

        @Override
        public void initializeData(int position) {
            SellerDomain seller = (SellerDomain) modules.get(position);
            mSellerItemTitleLabel.setText(seller.getTitle());
            mSellerItemDescriptorLabel.setText(seller.descriptor);
            mSellerItemTimeLabel.setText(seller.getCreatedAt());
            ImageDisplay.getInstance().displayImage(seller.image, mSellerItemIconImg, R.drawable.ic_launcher, R.drawable.ic_launcher);
        }
    }

}
