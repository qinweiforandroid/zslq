package cn.wei.zslq.controller.im;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import cn.wei.library.adapter.QBaseViewHolder;
import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseListActivity;
import cn.wei.zslq.utils.FileUtil;
import http.User;

public class ChatActivity extends BaseListActivity  {
	public final int type_txt_in = 0;
	public final int type_txt_out = 1;
	public final int type_img_in = 2;
	public final int type_img_out = 3;
	public final int type_voice_in = 4;
	public final int type_voice_out = 5;
	public int playingPosition = -1;
	public boolean isPlaying = false;
	private TextView mChatNoticeMessageLabel;
	private User targetUser;// 对方
	private User sendUser;// 自己
	private String targetId;
	private String selfId;
	private String caseId;
	private long endTimestamp;
	private LinearLayout v;
	private Uri tempUri;

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_chat);
	}


	@Override
	public void initializeView() {
		super.initializeView();
	}

	public void initializeData() {
		setTitle("聊天详情");
		for (int i = 0; i < 20; i++) {
			modules.add("");
		}
		adapter.notifyDataSetChanged();
	}



	@Override
	public void onRefresh(boolean isRefresh) {
	}

	@Override
	public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
		QBaseViewHolder holder = null;
		if (convertView == null || convertView.getTag() == null) {
			LayoutInflater inflater = LayoutInflater.from(this);
			switch (getAdapterViewType(position)) {
			case type_txt_in:
				holder = new TxtInHolder();
				convertView = inflater.inflate(R.layout.layout_chat_txt_in_item, null);
				break;
			case type_txt_out:
				holder = new TxtOutHolder();
				convertView = inflater.inflate(R.layout.layout_chat_txt_out_item, null);
				break;
			case type_img_in:
				holder = new ImgInHolder();
				convertView = inflater.inflate(R.layout.layout_chat_img_in_item, null);
				break;
			case type_img_out:
				holder = new ImgOutHolder();
				convertView = inflater.inflate(R.layout.layout_chat_img_out_item, null);
				break;
			case type_voice_in:
				holder = new VoiceInHolder();
				convertView = inflater.inflate(R.layout.layout_chat_voice_in_item, null);
				break;
			case type_voice_out:
				holder = new VoiceOutHolder();
				convertView = inflater.inflate(R.layout.layout_chat_voice_out_item, null);
				break;
			default:
				break;
			}
			holder.initializeView(convertView);
			convertView.setTag(holder);
		} else {
			holder = (QBaseViewHolder) convertView.getTag();
		}
		holder.initializeData(position);
		return convertView;
	}

	class TxtInHolder extends QBaseViewHolder {

		private ImageView mChatItemUserIconImg;
		private TextView mChatItemMsgContent;
		private TextView mChatItemDateLabel;

		@Override
		public void initializeView(View view) {
			mChatItemUserIconImg = (ImageView) view.findViewById(R.id.mChatItemUserIconImg);
			mChatItemMsgContent = (TextView) view.findViewById(R.id.mChatItemMsgContent);
			mChatItemDateLabel = (TextView) view.findViewById(R.id.mChatItemDateLabel);
		}

		@Override
		public void initializeData(int position) {
//			Message message = (Message) modules.get(position);
//			imageLoader.displayImage(targetUser.getIcon(), mChatItemUserIconImg, options);
//			mChatItemDateLabel.setText(TimeHelper.getChatTime(message.getTimestamp()));
//			mChatItemMsgContent.setText("");
//			mChatItemMsgContent.setMovementMethod(LinkMovementMethod.getInstance());
		}

	}


	class TxtOutHolder extends QBaseViewHolder implements OnClickListener {

		private ImageView mChatItemUserIconImg;
		private TextView mChatItemMsgContent;
		private TextView mChatItemDateLabel;
		private ProgressBar mProgressBarStatu;
		private ImageView mChatItemSendErrImg;

		@Override
		public void initializeView(View view) {
			mChatItemUserIconImg = (ImageView) view.findViewById(R.id.mChatItemUserIconImg);
			mChatItemMsgContent = (TextView) view.findViewById(R.id.mChatItemMsgContent);
			mChatItemDateLabel = (TextView) view.findViewById(R.id.mChatItemDateLabel);
			mProgressBarStatu = (ProgressBar) view.findViewById(R.id.mProgressBarStatu);
			mChatItemSendErrImg = (ImageView) view.findViewById(R.id.mChatItemSendErrImg);
			mChatItemSendErrImg.setOnClickListener(this);
		}

		@Override
		public void initializeData(int position) {
//			imageLoader.displayImage(sendUser.getIcon(), mChatItemUserIconImg, options);
//			mChatItemMsgContent.setText(message.getContent());
//			mChatItemDateLabel.setText(TimeHelper.getChatTime(message.getTimestamp()));
//			switch (message.getStatus()) {
//			case ing:
//				mProgressBarStatu.setVisibility(View.VISIBLE);
//				mChatItemSendErrImg.setVisibility(View.GONE);
//				break;
//			case done:
//				mProgressBarStatu.setVisibility(View.GONE);
//				mChatItemSendErrImg.setVisibility(View.GONE);
//				break;
//			case err:
//				mChatItemSendErrImg.setVisibility(View.VISIBLE);
//				mProgressBarStatu.setVisibility(View.GONE);
//				break;
//			default:
//				break;
//			}
		}

		@Override
		public void onClick(View v) {
		}
	}

	class ImgInHolder extends QBaseViewHolder {
		private ImageView mChatItemUserIconImg;
		private TextView mChatItemDateLabel;
		private ImageView mChatItemPictureImg;

		@Override
		public void initializeView(View view) {
			mChatItemUserIconImg = (ImageView) view.findViewById(R.id.mChatItemUserIconImg);
			mChatItemPictureImg = (ImageView) view.findViewById(R.id.mChatItemPictureImg);
			mChatItemDateLabel = (TextView) view.findViewById(R.id.mChatItemDateLabel);
		}

		@Override
		public void initializeData(int position) {
//			imageLoader.displayImage(targetUser.getIcon(), mChatItemUserIconImg, options);
//			imageLoader.displayImage(message.getReceiver_image(), mChatItemPictureImg, options);
//			mChatItemDateLabel.setText(TimeHelper.getChatTime(message.getTimestamp()));
//			mChatItemPictureImg.setOnClickListener(new ImageClickListener(message.getReceiver_image(), null));
		}

	}

	private class ImageClickListener implements OnClickListener {

		private String imageUrl;
		private String path;

		public ImageClickListener(String imageUrl, String path) {
			this.imageUrl = imageUrl;
			this.path = path;
		}

		@Override
		public void onClick(View v) {
//			Intent intent = new Intent(getContext(), ChatPictureLookActivity.class);
//			intent.putExtra(Constants.KEY_IMAGE_URL, imageUrl);
//			intent.putExtra(Constants.KEY_IMAGE_PATH, path);
//			startActivity(intent);
//			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}

	}

	class ImgOutHolder extends QBaseViewHolder implements OnClickListener {

		private ImageView mChatItemUserIconImg;
		private TextView mChatItemDateLabel;
		private ProgressBar mProgressBarStatu;
		private ImageView mChatItemSendErrImg;
		private ImageView mChatItemPictureImg;

		@Override
		public void initializeView(View view) {
			mChatItemUserIconImg = (ImageView) view.findViewById(R.id.mChatItemUserIconImg);
			mChatItemDateLabel = (TextView) view.findViewById(R.id.mChatItemDateLabel);
			mProgressBarStatu = (ProgressBar) view.findViewById(R.id.mProgressBarStatu);
			mChatItemSendErrImg = (ImageView) view.findViewById(R.id.mChatItemSendErrImg);
			mChatItemPictureImg = (ImageView) view.findViewById(R.id.mChatItemPictureImg);
			mChatItemSendErrImg.setOnClickListener(this);
		}

		@Override
		public void initializeData(int position) {
//			mChatItemPictureImg.setOnClickListener(new ImageClickListener(message.getReceiver_image(), message.getAttachments().get(0)
//					.getFile_path()));
//			imageLoader.displayImage(sendUser.getIcon(), mChatItemUserIconImg, options);
//			String file_path = message.getAttachments().get(0).getFile_path();
//			if (TextUtil.isValidate(file_path)) {
//				File file = new File(file_path);
//				if (file.exists()) {
//					Trace.d(file_path);
//					imageLoader.displayImage(Uri.fromFile(file).toString(), mChatItemPictureImg, options);
//				}
//			}
//			mChatItemDateLabel.setText(TimeHelper.getChatTime(message.getTimestamp()));
//			switch (message.getStatus()) {
//			case ing:
//				mProgressBarStatu.setVisibility(View.VISIBLE);
//				mChatItemSendErrImg.setVisibility(View.GONE);
//				break;
//			case done:
//				mProgressBarStatu.setVisibility(View.GONE);
//				mChatItemSendErrImg.setVisibility(View.GONE);
//				break;
//			case err:
//				mChatItemSendErrImg.setVisibility(View.VISIBLE);
//				mProgressBarStatu.setVisibility(View.GONE);
//				break;
//			default:
//				break;
//			}
		}

		@Override
		public void onClick(View v) {
		}

	}

	class VoiceInHolder extends QBaseViewHolder {
		private ImageView mChatItemVoiceImg;
		private ImageView mChatItemUserIconImg;
		private TextView mChatItemDateLabel;
		private ProgressBar mProgressBarStatu;
		private ImageView mChatItemSendErrImg;
		private View mChatItemMsgContent;
		private TextView mChatItemVoiceLengthLabel;

		@Override
		public void initializeView(View view) {
			mChatItemMsgContent = view.findViewById(R.id.mChatItemMsgContent);
			mChatItemUserIconImg = (ImageView) view.findViewById(R.id.mChatItemUserIconImg);
			mChatItemDateLabel = (TextView) view.findViewById(R.id.mChatItemDateLabel);
			mProgressBarStatu = (ProgressBar) view.findViewById(R.id.mProgressBarStatu);
			mChatItemSendErrImg = (ImageView) view.findViewById(R.id.mChatItemSendErrImg);
			mChatItemVoiceImg = (ImageView) view.findViewById(R.id.mChatItemVoiceImg);
			mChatItemVoiceLengthLabel = (TextView) view.findViewById(R.id.mChatItemVoiceLengthLabel);
		}

		@Override
		public void initializeData(int position) {
//			LayoutParams layoutParams = mChatItemMsgContent.getLayoutParams();
//			double length = message.getVoice_length() / 1000.0 / 60.0;
//			if (length == 0) {
//				layoutParams.width = DensityUtils.dip2px(getContext(), 60);
//			} else {
//				layoutParams.width = DensityUtils.dip2px(getContext(), 40 + (float) (length * 240));
//			}
//			mChatItemMsgContent.setLayoutParams(layoutParams);
		}
	}

	class VoiceOutHolder extends QBaseViewHolder implements OnClickListener {

		private ImageView mChatItemVoiceImg;
		private ImageView mChatItemUserIconImg;
		private TextView mChatItemDateLabel;
		private ProgressBar mProgressBarStatu;
		private ImageView mChatItemSendErrImg;
		private View mChatItemMsgContent;
		private TextView mChatItemVoiceLengthLabel;
		private Message message;

		@Override
		public void initializeView(View view) {
			mChatItemMsgContent = view.findViewById(R.id.mChatItemMsgContent);
			mChatItemUserIconImg = (ImageView) view.findViewById(R.id.mChatItemUserIconImg);
			mChatItemDateLabel = (TextView) view.findViewById(R.id.mChatItemDateLabel);
			mProgressBarStatu = (ProgressBar) view.findViewById(R.id.mProgressBarStatu);
			mChatItemSendErrImg = (ImageView) view.findViewById(R.id.mChatItemSendErrImg);
			mChatItemVoiceImg = (ImageView) view.findViewById(R.id.mChatItemVoiceImg);
			mChatItemVoiceLengthLabel = (TextView) view.findViewById(R.id.mChatItemVoiceLengthLabel);
			mChatItemSendErrImg.setOnClickListener(this);
		}

		@Override
		public void initializeData(int position) {
//			LayoutParams layoutParams = mChatItemMsgContent.getLayoutParams();
//			double length = message.getVoice_length() / 1000.0 / 60.0;
//			if (length == 0) {
//				layoutParams.width = DensityUtils.dip2px(getContext(), 60);
//			} else {
//				layoutParams.width = DensityUtils.dip2px(getContext(), 40 + (float) (length * 240));
//			}
//			mChatItemMsgContent.setLayoutParams(layoutParams);
//			mChatItemVoiceLengthLabel.setText(message.getVoice_length() / 1000 + "");
//			mChatItemMsgContent.setOnClickListener(new VoiceClickListener(message));
//			imageLoader.displayImage(sendUser.getIcon(), mChatItemUserIconImg, options);
//			mChatItemVoiceImg.setImageResource(R.drawable.gotye_anim_voice_right);
//			AnimationDrawable voiceAnimation = (AnimationDrawable) mChatItemVoiceImg.getDrawable();
//			voiceAnimation.selectDrawable(0);
//			if (isPlaying && playingPosition == position) {
//				voiceAnimation.start();
//			} else {
//				voiceAnimation.stop();
//				voiceAnimation.selectDrawable(0);
//			}
//			mChatItemDateLabel.setText(TimeHelper.getChatTime(message.getTimestamp()));
//			switch (message.getStatus()) {
//			case ing:
//				mProgressBarStatu.setVisibility(View.VISIBLE);
//				mChatItemSendErrImg.setVisibility(View.GONE);
//				break;
//			case done:
//				mProgressBarStatu.setVisibility(View.GONE);
//				mChatItemSendErrImg.setVisibility(View.GONE);
//				break;
//			case err:
//				mChatItemSendErrImg.setVisibility(View.VISIBLE);
//				mProgressBarStatu.setVisibility(View.GONE);
//				break;
//			default:
//				break;
//			}
		}

		@Override
		public void onClick(View v) {
			retrySendMessage(message);
		}

	}


	@Override
	public int getAdapterViewType(int position) {
//		Message message = (Message) modules.get(position);
//		switch (message.getType()) {
//		case txt:
//			return message.getReceiverId().equals(selfId) ? type_txt_in : type_txt_out;
//		case img:
//			return message.getReceiverId().equals(selfId) ? type_img_in : type_img_out;
//		case voice:
//			return message.getReceiverId().equals(selfId) ? type_voice_in : type_voice_out;
//		default:
//			break;
//		}
		int num= (int) (Math.random()*10);
		if(num>5){
			num=num-5;
		}
		return num;
	}

	@Override
	public int getAdapterViewTypeCount() {
		return 8;
	}



	public void openCamera(){
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File file = new File(FileUtil.getTmpDir() + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg");
		tempUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
	}
	/**
	 * 打开系统相册
	 * 
	 * @param
	 */
	public Intent getOpenPicIntent() {
		Intent getPicAction = new Intent(Intent.ACTION_GET_CONTENT);
		getPicAction.setAction(Intent.ACTION_PICK);
		getPicAction.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		return getPicAction;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 100:
				handleImg(data.getData());
				break;
			case 200:
				handleImg(tempUri);
				break;
			default:
				break;
			}
		} else {
			switch (requestCode) {
			case 100:
				break;
			case 200:
				String path = tempUri.getPath();
				File file = new File(path);
				if (file.exists()) {
					file.delete();// 刪除緩存文件
				}
				break;
			default:
				break;
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private void handleImg(Uri uri) {
//		if (TextUtil.isValidate(type)) {
//			path = FileUtil.getFilePathByUri(this, uri);
//			attachment.setFile_type(type);
//		} else {
//			attachment.setFile_type("image/jpg");
//			path = uri.getPath();
//		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 更新listview
	 * 
	 * @param isScrollBottom
	 *            是否滚动到底部
	 */
	public void notifyDataSetChanged(boolean isScrollBottom) {
		adapter.notifyDataSetChanged();
		if (isScrollBottom && modules.size() > 0) {
		}
	}


	public void notifyDataSetChanged() {
		notifyDataSetChanged(false);
	}


	public void retrySendMessage(final Message message) {
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info).setMessage("是否重新发送!")
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).setPositiveButton("取消", null).create().show();
	}
}
