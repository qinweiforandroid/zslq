package cn.wei.zslq.controller.zone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.PullToRefreshBase;

import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.library.utils.CommonUtil;
import cn.wei.library.utils.ImageUtils;
import cn.wei.library.utils.TimeHelper;
import cn.wei.library.widget.EmptyView;
import cn.wei.library.widget.FooterView;
import cn.wei.zslq.MyApplication;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.Controller;
import cn.wei.zslq.domain.Talk;
import cn.wei.zslq.domain.TalkComment;
import cn.wei.zslq.model.impl.TalkCommentModel;
import cn.wei.zslq.support.BaseListActivity;
import cn.wei.zslq.utils.Constants;

public class TalkCommentActivity extends BaseListActivity implements View.OnClickListener, Controller {
    public static final String KEY_TALK_ENTITIES = "key_talk_entities";
    public static final String KEY_START_LOCATION = "key_start_location";
    private EditText mCommentContentEdt;
    private Talk talk;
    private int startLocation;
    private int pageNum = 1;
    public int load_data_state = 1;
    public boolean isFirstLoad = true;
    private TalkCommentModel model;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_zone_talk_comment);
    }

    @Override
    protected void initializeView() {
        talk = (Talk) getIntent().getSerializableExtra(KEY_TALK_ENTITIES);
        startLocation = getIntent().getIntExtra(KEY_START_LOCATION, 0);
        super.initializeView();
        mCommentContentEdt = (EditText) findViewById(R.id.mCommentContentEdt);
        findViewById(R.id.mCommentCommitBtn).setOnClickListener(this);
    }

    @Override
    protected void initializeData() {
        setTitle("说说详情");
        mPullToRefreshLsv.setMode(PullToRefreshBase.Mode.DISABLED);
        model = new TalkCommentModel(this);
        model.setController(this);
        findViewById(R.id.mTalkCommentLayout).setTranslationY(100);
        startIntroAnimation();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                loadDataFromServer();
            }
        }, 500);
    }

    private void loadDataFromServer() {
        footerView.notifyDataChanged(FooterView.State.ing);
        model.loadTalkCommentList(talk, pageNum);
    }

    private void startIntroAnimation() {
        mEmptyView.setScaleY(0.1f);
        mEmptyView.setPivotY(startLocation);
        mEmptyView.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .start();
    }

    private void animateContent() {
        findViewById(R.id.mTalkCommentLayout).animate().translationY(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(300)
                .start();
    }


    @Override
    public void onClick(View v) {
        final String content = mCommentContentEdt.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "评论内容不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        CommonUtil.hideInput(this);
        model.showProgress("提交中...");
       CommonUtil.postDelayed(new Runnable() {
           public void run() {
               model.doTalkComment(talk, MyApplication.getLoginUser(), content, TalkComment.comment);
           }
       }, 500);
    }

    @Override
    protected void addRefreshHeaderView(ListView refreshableView) {
        View view = LayoutInflater.from(this).inflate(R.layout.list_zone_talk_content_header, null);
        ImageView mTalkItemUserIconImg = (ImageView) view.findViewById(R.id.mTalkItemUserIconImg);
        TextView mTalkItemUserNickLabel = (TextView) view.findViewById(R.id.mTalkItemUserNickLabel);
        TextView mTalkItemCreateTimeLabel = (TextView) view.findViewById(R.id.mTalkItemCreateTimeLabel);
        TextView mTalkContentLabel = (TextView) view.findViewById(R.id.mTalkContentLabel);
        TextView mTalkItemLookNumLabel = (TextView) view.findViewById(R.id.mTalkItemLookNumLabel);
        TextView mTalkItemCommentNumLabel = (TextView) view.findViewById(R.id.mTalkItemCommentNumLabel);

        ImageUtils.displayImage(talk.getCreateUser().getIcon(), mTalkItemUserIconImg, ImageUtils.getUserIconOptions());
        mTalkItemUserNickLabel.setText(talk.getCreateUser().getNick());
        mTalkItemCreateTimeLabel.setText(TimeHelper.getTimeRule2(talk.getCreatedAt()));
        mTalkContentLabel.setText(talk.getContent());
        mTalkItemLookNumLabel.setText(talk.getLookNum() + "");
        mTalkItemCommentNumLabel.setText(talk.getCommentNum() + "");
        refreshableView.addHeaderView(view);
        super.addRefreshHeaderView(refreshableView);
    }

    @Override
    public void loadMore() {
        loadDataFromServer();
    }

    @Override
    public void onSuccess(String action) {
        switch (action) {
            case TalkCommentModel.ACTION_LOAD_TALK_COMMENT_LIST:
                if (model.comments.size() < TalkCommentModel.PAGE_SIZE) {
                    footerView.notifyDataChanged(FooterView.State.no_data);
                } else {
                    footerView.notifyDataChanged(FooterView.State.done);
                }
                modules.addAll(model.comments);
                pageNum++;
                adapter.notifyDataSetChanged();
                if (isFirstLoad) {
                    findViewById(R.id.content).setVisibility(View.VISIBLE);
                    mEmptyView.notifyDataChanged(EmptyView.State.done);
                    animateContent();
                    isFirstLoad = false;
                }
                break;
            case TalkCommentModel.ACTION_DO_TALK_COMMENT:
                mCommentContentEdt.setText("");
                modules.add(0, model.comment);
                adapter.notifyDataSetChanged();
                mPullToRefreshLsv.getRefreshableView().setSelection(0);
                model.closeProgress();
                Toast.makeText(TalkCommentActivity.this, "评论成功!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(String action, int errorCode, String errorMsg) {
        switch (action) {
            case TalkCommentModel.ACTION_LOAD_TALK_COMMENT_LIST:
                if (load_data_state == Constants.LOAD_DATA_STATE_LOAD_MORE) {
                    footerView.notifyDataChanged(FooterView.State.error);
                } else if (load_data_state == Constants.LOAD_DATA_STATE_LOAD_REFRESH) {
                    mPullToRefreshLsv.onRefreshComplete();
                }
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
            convertView = LayoutInflater.from(this).inflate(R.layout.list_zone_talk_comment_item, null);
            holder.initializeView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.initializeData(position);
        return convertView;
    }


    public class ViewHolder extends QBaseViewHolder {
        private TextView mTalkContentLabel;
        private ImageView mTalkItemUserIconImg;
        private TextView mTalkItemUserNickLabel;
        private TextView mTalkItemCreateTimeLabel;
        private TalkComment comment;

        @Override
        public void initializeView(View view) {
            mTalkItemUserIconImg = (ImageView) view.findViewById(R.id.mTalkItemUserIconImg);
            mTalkItemUserNickLabel = (TextView) view.findViewById(R.id.mTalkItemUserNickLabel);
            mTalkItemCreateTimeLabel = (TextView) view.findViewById(R.id.mTalkItemCreateTimeLabel);
            mTalkContentLabel = (TextView) view.findViewById(R.id.mTalkContentLabel);
        }

        @Override
        public void initializeData(int position) {
            comment = (TalkComment) modules.get(position);
            ImageUtils.displayImage(comment.getFromUser().getIcon(), mTalkItemUserIconImg, ImageUtils.getUserIconOptions());
            mTalkItemUserNickLabel.setText(comment.getFromUser().getNick());
            mTalkItemCreateTimeLabel.setText(TimeHelper.getTimeRule2(comment.getCreatedAt()));
            mTalkContentLabel.setText(comment.getContent());
        }
    }

    @Override
    public boolean isCanLoadMore() {
        return true;
    }

    @Override
    protected boolean isCanFinish() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.content).animate()
                .translationY(mEmptyView.getHeight())
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        TalkCommentActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }
}
