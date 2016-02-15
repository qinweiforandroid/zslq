package cn.wei.zslq.controller.zone;

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

import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.library.utils.ImageUtils;
import cn.wei.library.utils.TimeHelper;
import cn.wei.library.widget.EmptyView;
import cn.wei.library.widget.FooterView;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.domain.Talk;
import cn.wei.zslq.model.impl.TalkModel;
import cn.wei.zslq.support.BaseListActivity;
import cn.wei.zslq.utils.Constants;
import http.Trace;

/**
 * 类描述:
 *
 * @author 秦伟
 * @version 1.0
 * @created 创建时间: 2015-5-2 上午20:36:45
 */
public class TalkListActivity extends BaseListActivity implements Controller {
    private static final int REQUEST_TO_ADD_TALK = 100;
    private int pageNum = 1;
    private TalkModel model;

    public int load_data_state = 1;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_zone_talk_list);
    }

    @Override
    public void initializeView() {
        super.initializeView();
        setTitle("空间动态");
    }

    @Override
    public void initializeData() {
        model = new TalkModel(this);
        model.setController(this);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        load_data_state = Constants.LOAD_DATA_STATE_LOAD_FIRST;
        mEmptyView.notifyDataChanged(EmptyView.State.ing);
        model.loadTalkList(pageNum);
    }

    @Override
    public void onRefresh(boolean isRefresh) {
        super.onRefresh(isRefresh);
        pageNum = 1;
        load_data_state = Constants.LOAD_DATA_STATE_LOAD_REFRESH;
        model.loadTalkList(pageNum);
    }

    @Override
    public void loadMore() {
        load_data_state = Constants.LOAD_DATA_STATE_LOAD_MORE;
        model.loadTalkList(pageNum);
    }

    @Override
    public void onRetryLoadMore() {
        loadMore();
    }

    @Override
    public void onRetry() {
        loadDataFromServer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.talk__list_to_publish_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.talk_action_add:
                goTalkAdd();
                break;
            case R.id.talk_action_look:
                loadDataFromServer();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goTalkAdd() {
        Intent intent = new Intent(this, TalkPublishActivity.class);
        startActivityForResult(intent, REQUEST_TO_ADD_TALK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            if (requestCode == REQUEST_TO_ADD_TALK && data != null) {
                Talk talk = (Talk) data.getSerializableExtra(TalkPublishActivity.KEY_TALK_ENTITIES);
                modules.add(0, talk);
                adapter.notifyDataSetChanged();
                mPullToRefreshLsv.getRefreshableView().setSelection(0);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(String action) {
        Trace.e("onSuccess:" + model.talks.size());
        switch (load_data_state) {
            case Constants.LOAD_DATA_STATE_LOAD_FIRST:
                pageNum++;
                modules.addAll(model.talks);
                adapter.notifyDataSetChanged();
                showContent();
                break;
            case Constants.LOAD_DATA_STATE_LOAD_REFRESH:
                pageNum++;
                modules.clear();
                modules.addAll(model.talks);
                adapter.notifyDataSetChanged();
                mPullToRefreshLsv.onRefreshComplete();
                footerView.notifyDataChanged(FooterView.State.done);
                break;
            case Constants.LOAD_DATA_STATE_LOAD_MORE:
                if (model.talks.size() == 0) {
                    footerView.notifyDataChanged(FooterView.State.no_data);
                } else {
                    pageNum++;
                    modules.addAll(model.talks);
                    adapter.notifyDataSetChanged();
                    footerView.notifyDataChanged(FooterView.State.done);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isCanLoadMore() {
        return true;
    }

    @Override
    public void onFailure(String action, int errorCode, String errorMsg) {
        switch (load_data_state) {
            case Constants.LOAD_DATA_STATE_LOAD_FIRST:
                mEmptyView.notifyDataChanged(EmptyView.State.error);
                Trace.e("onFailure:" + errorMsg);
                break;
            case Constants.LOAD_DATA_STATE_LOAD_REFRESH:
                Toast.makeText(TalkListActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                break;
            case Constants.LOAD_DATA_STATE_LOAD_MORE:
                footerView.notifyDataChanged(FooterView.State.error);
                break;
            default:
                break;
        }
    }

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this).inflate(R.layout.list_zone_talk_item, null);
            holder.initializeView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.initializeData(position);
        return convertView;
    }


    public class ViewHolder extends QBaseViewHolder {
        private Talk talk;
        private TextView mTalkContentLabel;
        private ImageView mTalkItemUserIconImg;
        private TextView mTalkItemUserNickLabel;
        private TextView mTalkItemCreateTimeLabel;
        private TextView mTalkItemLookNumLabel;
        private TextView mTalkItemCommentNumLabel;

        @Override
        public void initializeView(View view) {
            mTalkItemUserIconImg = (ImageView) view.findViewById(R.id.mTalkItemUserIconImg);
            mTalkItemUserNickLabel = (TextView) view.findViewById(R.id.mTalkItemUserNickLabel);
            mTalkItemCreateTimeLabel = (TextView) view.findViewById(R.id.mTalkItemCreateTimeLabel);
            mTalkContentLabel = (TextView) view.findViewById(R.id.mTalkContentLabel);
            mTalkItemLookNumLabel = (TextView) view.findViewById(R.id.mTalkItemLookNumLabel);
            mTalkItemCommentNumLabel = (TextView) view.findViewById(R.id.mTalkItemCommentNumLabel);
        }


        @Override
        public void initializeData(int position) {
            talk = (Talk) modules.get(position);
            ImageUtils.displayImage(talk.getCreateUser().getIcon(), mTalkItemUserIconImg, ImageUtils.getUserIconOptions());
            mTalkItemUserNickLabel.setText(talk.getCreateUser().getNick());
            mTalkItemCreateTimeLabel.setText(TimeHelper.getTimeRule2(talk.getCreatedAt()));
            mTalkContentLabel.setText(talk.getContent());
            mTalkItemLookNumLabel.setText(talk.getLookNum() + "");
            mTalkItemCommentNumLabel.setText(talk.getCommentNum() + "");
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Talk talk = (Talk) parent.getAdapter().getItem(position);
        model.doTalkLookNumAdd(talk.getObjectId());
        Intent intent = new Intent(this, TalkCommentActivity.class);
        intent.putExtra(TalkCommentActivity.KEY_TALK_ENTITIES, talk);
        startActivity(intent);
    }
}
