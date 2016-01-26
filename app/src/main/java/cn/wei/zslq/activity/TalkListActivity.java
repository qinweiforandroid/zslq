package cn.wei.zslq.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.PullToRefreshListView.DispatchTouchEventListener;

import java.util.ArrayList;
import java.util.LinkedList;

import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.library.utils.DensityUtil;
import cn.wei.zslq.R;
import cn.wei.zslq.domain.Comment;
import cn.wei.zslq.domain.Talk;
import cn.wei.zslq.support.BaseListActivity;
import cn.wei.zslq.widget.talk.SayTextView;
import cn.wei.zslq.widget.talk.TalkEditText;
import http.Trace;

/**
 * 类描述:
 *
 * @author 秦伟
 * @version 1.0
 * @created 创建时间: 2015-5-2 上午20:36:45
 */
public class TalkListActivity extends BaseListActivity {
    public static final String reply = "回复";
    public static final String word = ": ";
    private int height;
    private TalkEditText mTalkEditText;
    private RecyclerList views;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_say_list);
    }

    @Override
    public void initializeView() {
        super.initializeView();
        setTitle("空间动态");
        mTalkEditText = (TalkEditText) findViewById(R.id.mTalkEditText);
        mPullToRefreshLsv.setMode(Mode.DISABLED);
        mPullToRefreshLsv.setOnDispatchTouchEventListener(new DispatchTouchEventListener() {

            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mTalkEditText.getVisibility() == View.VISIBLE) {
                            mTalkEditText.hideInputSoft();
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void initializeData() {
        views = new RecyclerList(this, R.layout.layout_talk_comment_view);
//        views.initliazeData(10);
        loadDataFromServer();
    }

    public void loadDataFromServer() {
        modules.addAll(getTalks());
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Talk> getTalks() {
        ArrayList<Talk> talks = new ArrayList<Talk>();
        Talk talk = new Talk();
        talk.setTimestamp(System.currentTimeMillis());
        talk.setTalkContent("这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐,这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐");
        ArrayList<Comment> comments = new ArrayList<Comment>();
        comments.add(new Comment("小伟", "雨过晴天", "人之初,性本善.知之为知之,不知为不知,是知也!"));
        comments.add(new Comment("小明", "小伟", "呵呵,好着呢！"));
        comments.add(new Comment("小伟", "小明", "吃饭了没有?"));
        comments.add(new Comment("小明", "小伟", "吃了,你呢!"));
        comments.add(new Comment("李四", "哈哈!"));
        comments.add(new Comment("張三", "你好呀!"));
        talk.setComments(comments);
        ArrayList<String> imgUris = new ArrayList<String>();
        imgUris.add("imgUris");
        imgUris.add("imgUris");
        talk.setImgUris(imgUris);
        talks.add(talk);

        Talk talk1 = new Talk();
        talk1.setTimestamp(System.currentTimeMillis());
        talk1.setTalkContent("这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐,这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐 这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐,这次南京之旅很开心，谢谢姐姐专车接送，姐姐的大龙虾，火锅，还有午觉睡晚贴心的丰盛外卖，还有送我两套衣服，很开心，好想有个这样的姐姐");
        ArrayList<Comment> comments1 = new ArrayList<Comment>();
        comments1.add(new Comment("小伟", "雨过晴天", "人之初,性本善.知之为知之,不知为不知,是知也!"));
        comments1.add(new Comment("小明", "小伟", "呵呵,好着呢！"));
        comments1.add(new Comment("小伟", "小明", "吃饭了没有?"));
        comments1.add(new Comment("小明", "小伟", "吃了,你呢!"));
        comments1.add(new Comment("李四", "哈哈!"));
        talk1.setComments(comments1);
        ArrayList<String> imgUris1 = new ArrayList<String>();
        imgUris1.add("imgUris");
        imgUris1.add("imgUris");
        talk1.setImgUris(imgUris1);
        talks.add(talk1);
        talks.add(talk1);
        talks.add(talk1);
        talks.add(talk);
        talks.add(talk1);
        talks.add(talk);
        talks.add(talk1);
        talks.add(talk1);
        talks.add(talk1);
        talks.add(talk1);
        return talks;
    }

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this).inflate(R.layout.layout_talk_list_item, null);
            holder.initializeView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.initializeData(position);
        return convertView;
    }

    public class ViewHolder extends QBaseViewHolder implements SayTextView.OnLightTextListener, OnClickListener, OnLongClickListener {
        private Talk talk;
        private LinearLayout mTalkCommentContainer;
        private TextView mTalkContentLabel;
        private ImageView mTalkItemCommentImg;
        private TextView mTalkItemCommentLabel;
        private View itemView;
        private int itemViewHeight;
        private int position;
        // 延迟计算top值
        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mPullToRefreshLsv.getRefreshableView().setFocusableInTouchMode(false);
                mPullToRefreshLsv.getRefreshableView().setSelectionFromTop(position + 1, getTop());
            }

        };

        @Override
        public void initializeView(View view) {
            this.itemView = view;
            mTalkItemCommentLabel = (TextView) view.findViewById(R.id.mTalkItemCommentLabel);
            mTalkCommentContainer = (LinearLayout) view.findViewById(R.id.mTalkCommentContainer);
            mTalkContentLabel = (TextView) view.findViewById(R.id.mTalkContentLabel);
            mTalkItemCommentImg = (ImageView) view.findViewById(R.id.mTalkItemCommentImg);
            TalkCommentClickListener talkCommentClickListener = new TalkCommentClickListener();
            mTalkItemCommentImg.setOnClickListener(talkCommentClickListener);
            mTalkItemCommentLabel.setOnClickListener(talkCommentClickListener);
        }

        private class TalkCommentClickListener implements OnClickListener {
            @Override
            public void onClick(View v) {
                showSoftInput();
            }
        }

        public void showSoftInput() {
            height = mPullToRefreshLsv.getHeight();
            mTalkEditText.setVisibility(View.VISIBLE);
            mTalkEditText.showSoftInput();
            mHandler.sendEmptyMessageDelayed(0, 400);
        }

        public int getTop() {
            // TODO 编辑框距离底部距离-（listview高度-itemView高度）-编辑框高度
            int h = mTalkEditText.getBottom() - (height - itemViewHeight) - mTalkItemCommentLabel.getHeight() - DensityUtil.dip2px(TalkListActivity.this, 5);
            return h * -1;
        }

        @Override
        public void initializeData(int position) {
            this.position = position;
            talk = (Talk) modules.get(position);
            mTalkContentLabel.setText(talk.getTalkContent());
            initializeCommentView(talk.getComments());
            initializeCOmmentPictures(talk.getImgUris());
            itemViewHeight = itemView.getHeight();
        }

        private void initializeCOmmentPictures(ArrayList<String> imgUris) {

        }

        private void initializeCommentView(ArrayList<Comment> comments) {
            if (comments == null || comments.size() == 0) {
                mTalkItemCommentLabel.setVisibility(View.GONE);
            } else {
                mTalkItemCommentLabel.setVisibility(View.VISIBLE);
            }

            int childViewSize = mTalkCommentContainer.getChildCount();
            for (int i = 0; i < (childViewSize>comments.size()?comments.size():childViewSize); i++) {
                View view = mTalkCommentContainer.getChildAt(i);
                initializeCommentData(comments.get(i), view);
            }
            if (childViewSize == comments.size()) {
                return;
            } else if (comments.size() > childViewSize) {
                for (int i = childViewSize; i < comments.size(); i++) {
                    View view=views.get();
                    initializeCommentData(comments.get(i),view);
                    mTalkCommentContainer.addView(view, i);
                }
            } else {
                for (int i =childViewSize; i < childViewSize; i++) {
                    if(views.size()<20){
                        views.add(mTalkCommentContainer.getChildAt(i));
                    }
                    mTalkCommentContainer.removeViewAt(i);
                }
            }
        }

        public void initializeCommentData(Comment comment, View view) {
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            SayTextView sayTextView = (SayTextView) view.findViewById(R.id.mSayLabel);
            String fromNick = comment.getFromNick();
            ArrayList<SayTextView.SpanEntity> spans = new ArrayList<SayTextView.SpanEntity>();
            String content = null;
            if (comment.getReplyType() == Comment.reply) {
                String toNick = comment.getToNick();
                content = fromNick + reply + toNick + word + comment.getContent();
                int endIndex = fromNick.length() + reply.length() + toNick.length();
                spans.add(new SayTextView.SpanEntity(0, fromNick.length(), fromNick));
                spans.add(new SayTextView.SpanEntity(fromNick.length() + reply.length(), endIndex, toNick));
            } else {
                content = fromNick + word + comment.getContent();
                spans.add(new SayTextView.SpanEntity(0, fromNick.length(), fromNick));
            }
            sayTextView.initializeData(content, spans, this);
            view.setTag(comment);
        }

        @Override
        public void onLightTextClickListener(String id) {
            Toast.makeText(TalkListActivity.this, id, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View v) {
            Comment comment = (Comment) v.getTag();
            mTalkEditText.setHintText("回复" + comment.getFromNick() + ":");
            mTalkEditText.setText(null);
            showSoftInput();
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }

    public static class RecyclerList extends LinkedList<View> {
        private final Context context;
        private final int LayoutId;

        public RecyclerList(Context context, int layoutId) {
            this.context = context;
            this.LayoutId = layoutId;
        }

        public void initliazeData(int number) {
            for (int i = 0; i < number; i++) {
                addFirst(LayoutInflater.from(context).inflate(LayoutId, null));
            }
        }

        public View get() {
            View view = null;
            if (size() > 0) {
                view = getFirst();
                removeFirst();
            } else {
                view = LayoutInflater.from(context).inflate(LayoutId, null);
                Trace.e("创建新的view");
            }
            return view;
        }
    }
}
