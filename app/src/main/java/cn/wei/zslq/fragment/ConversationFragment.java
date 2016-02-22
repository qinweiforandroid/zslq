package cn.wei.zslq.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.PullToRefreshBase;

import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.zslq.R;
import cn.wei.zslq.controller.im.ChatActivity;
import cn.wei.zslq.support.BaseListFragment;

/**
 */
public class ConversationFragment extends BaseListFragment implements OnItemClickListener {

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_tab_conversation;
    }

    @Override
    protected void initializeView(View v) {
        super.initializeView(v);
        mPullToRefreshLsv.setMode(PullToRefreshBase.Mode.DISABLED);
        for (int i = 0; i < 10; i++) {
            if(i>2&&i<5){
                modules.add("b"+i);
            }else if(i>=5){
                modules.add("c"+i);
            }else if(i<=2){
                modules.add("a"+i);
            }
        }
        adapter.notifyDataSetChanged();
    }


    static class ViewHolder extends QBaseViewHolder {
        TextView mConversationStatusLabel;
        TextView mConversationContentLabel;
        TextView mConversationUsernameLabel;
        TextView mConversationTimestampLabel;
        TextView mConversationNumTip;
        ImageView mConversationAvatarImg;

        @Override
        public void initializeView(View view) {
            mConversationAvatarImg = (ImageView) view.findViewById(R.id.mConversationAvatarImg);
            mConversationNumTip = (TextView) view.findViewById(R.id.mConversationNumTip);
            mConversationTimestampLabel = (TextView) view.findViewById(R.id.mConversationTimestampLabel);
            mConversationUsernameLabel = (TextView) view.findViewById(R.id.mConversationUsernameLabel);
            mConversationContentLabel = (TextView) view.findViewById(R.id.mConversationContentLabel);

        }

        @Override
        public void initializeData(int position) {
//			Conversation conversation = mConversationList.get(position);
//			if (conversation.getUnreadNum() == 0) {
//				mConversationNumTip.setVisibility(View.INVISIBLE);
//			}else {
//				mConversationNumTip.setVisibility(View.VISIBLE);
//				mConversationNumTip.setText(String.valueOf(conversation.getUnreadNum()));
//			}
//			mConversationUsernameLabel.setText(conversation.getTargetName());
//			if (conversation.getType() == MessageType.txt) {
//				mConversationContentLabel.setText(EmoParser.parseEmo(getActivity(), conversation.getContent()));
//			}else if(conversation.getType() == MessageType.emo){
//				mConversationContentLabel.setText(R.string.mChatEmoMessageLabel);
//			}else {
//				mConversationContentLabel.setText(R.string.mChatImageMessageLabel);
//			}
//			mConversationTimestampLabel.setText(TimeHelper.getTimeRule3(conversation.getTimestamp()));

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
//		Conversation conversation = mConversationList.get(position);
//		intent.putExtra(Constants.KEY_TARGETID, conversation.getTargetId());
//		intent.putExtra(Constants.KEY_TARGETNAME, conversation.getTargetName());
        startActivity(intent);
    }

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null || convertView.getTag() == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_conversation_item, null);
            mViewHolder.initializeView(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.initializeData(position);
        return convertView;
    }

}
