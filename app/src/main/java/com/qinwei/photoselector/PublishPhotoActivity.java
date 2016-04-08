package com.qinwei.photoselector;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import cn.wei.zslq.R;

/**
 * @author qinwei email:qinwei_it@163.com
 * @created createTime: 2015-9-11 下午11:27:53
 * @version 1.0
 */

public class PublishPhotoActivity extends BasePublishPhotoActivity implements OnClickListener {
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
			Toast.makeText(PublishPhotoActivity.this, "图片压缩完成!", Toast.LENGTH_SHORT).show();
			super.handleMessage(msg);

		}
	};
	private ProgressDialog progressDialog;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_publish);
	}

	@Override
	public void initializeView() {
		super.initializeView();
		findViewById(R.id.mPublishCommitBtn).setOnClickListener(this);
		setTheme(R.style.ActionSheetStyleIOS7);
	}

	@Override
	protected void initializeData() {
		modules.add("");
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		progressDialog = new ProgressDialog(this);
		progressDialog.show();
		new Thread(new UploadTask()).start();
	}

	class UploadTask implements Runnable {
		@Override
		public void run() {
			if (photos != null && photos.size() > 0) {
				try {
					uploads.clear();
					for (PhotoEntity photo : photos) {
						File file = new File(photo.imagePath);
						String temp = "temp_" + file.getName();
						if (!new File(temp).exists() && file.length() > 100 * 1024) {
							temp = ImageSelectorFileUtils.saveBitmap(photo.imagePath, temp);
						} else {
							temp = photo.imagePath;
						}
						uploads.add(temp);
					}
					for (int i = 0; i < uploads.size(); i++) {
						String upload = uploads.get(i);
						Log.i("上传的本地文件路径为:", upload);
					}
					mHandler.sendEmptyMessage(0);
				} catch (IOException e) {
					e.printStackTrace();
					Log.i(this.getClass().getName(), "图片压缩失败");
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc();
	}
}