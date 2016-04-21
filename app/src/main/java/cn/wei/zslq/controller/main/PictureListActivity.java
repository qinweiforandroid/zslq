package cn.wei.zslq.controller.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.PullToRefreshBase;

import java.util.ArrayList;

import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.library.utils.ImageDisplay;
import cn.wei.zslq.R;
import cn.wei.zslq.core.BaseGridViewListActivity;
import cn.wei.zslq.domain.Meizhi;
import cn.wei.zslq.utils.GankIoCallback;
import http.AppException;
import http.Request;
import http.RequestManager;

/**
 * Created by qinwei on 2016/4/21 15:51
 * email:qinwei_it@163.com
 */
public class PictureListActivity extends BaseGridViewListActivity {
    private int pageNum = 1;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_picture_meizhi_list);
    }

    @Override
    protected void initializeData() {
        setTitle("妹紙圖");
        mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        loadDataFromServer(false);
    }

    @Override
    public void onRefresh(PullToRefreshBase<GridView> refreshView) {
        if (refreshView.getMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
            loadDataFromServer(false);
        } else {
            loadDataFromServer(true);
        }
    }

    public void loadDataFromServer(final boolean loadMore) {
        Request request = new Request("http://gank.io/api/data/福利/20/" + pageNum);
        request.setCallback(new GankIoCallback<ArrayList<Meizhi>>() {
            @Override
            public void onSuccess(ArrayList<Meizhi> result) {
                if (loadMore) {
                } else {
                    modules.clear();
                }
                pageNum++;
                modules.addAll(result);
                mPullToRefreshGridView.onRefreshComplete();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(AppException e) {
                mPullToRefreshGridView.onRefreshComplete();
            }
        });
        RequestManager.getInstance().execute(this.toString(), request);
    }

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this).inflate(R.layout.activity_picture_meizhi_list_item, null);
            holder.initializeView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.initializeData(position);
        return convertView;
    }

    public class ViewHolder extends QBaseViewHolder {
        private ImageView mPictureItemIconImg;
        private TextView mPictureItemTitleLabel;

        @Override
        public void initializeView(View view) {
            mPictureItemIconImg = (ImageView) view.findViewById(R.id.mPictureItemIconImg);
            mPictureItemTitleLabel = (TextView) view.findViewById(R.id.mPictureItemTitleLabel);
        }


        @Override
        public void initializeData(int position) {
            Meizhi icon = (Meizhi) modules.get(position);
            ImageDisplay.getInstance().displayImage(icon.getUrl(), mPictureItemIconImg);
            mPictureItemTitleLabel.setText(icon.getPublishedAt());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getInstance().cancelRequest(this.toString());
    }
}
