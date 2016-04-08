package com.qinwei.photoselector;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import cn.wei.library.utils.ImageDisplay;
import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseGridViewFragment;

/**
 * @author qinwei email:qinwei_it@163.com
 * @created createTime: 2015-9-11 下午10:35:44
 * @version 1.0
 */

public class PhotoListFragment extends BaseGridViewFragment implements OnClickListener {
	private Button mPhotoDoChoiceBtn;
	private ArrayList<PhotoEntity> selectedCache = new ArrayList<PhotoEntity>();
	private PhotoAlbum album;

	public interface OnPhotoSelectChangedListener {
		void onPhotoSelectChanged(boolean isRemove, PhotoEntity photo);
	}

	private OnPhotoSelectChangedListener listener;

	public static PhotoListFragment getInstance(ArrayList<PhotoEntity> photos, PhotoAlbum album) {
		PhotoListFragment fragment = new PhotoListFragment();
		Bundle args = new Bundle();
		args.putSerializable(Constants.KEY_PHOTOS, photos);
		args.putSerializable(Constants.KEY_ALBUM_ENTITIES, album);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	protected int getFragmentLayoutId() {
		return R.layout.fragment_photo_list;
	}

	@Override
	protected void initializeArguments(Bundle args) {
		super.initializeArguments(args);
		album = (PhotoAlbum) args.getSerializable(Constants.KEY_ALBUM_ENTITIES);
		Serializable serializable = args.getSerializable(Constants.KEY_PHOTOS);
		selectedCache.addAll((ArrayList<PhotoEntity>) serializable);
		for (PhotoEntity photo : selectedCache) {
			if (album.imageList.contains(photo)) {
				int i = album.imageList.indexOf(photo);
				album.imageList.get(i).isSelected = true;
			}
		}
	}

	@Override
	public void initializeView(View v) {
		super.initializeView(v);
		mPhotoDoChoiceBtn = (Button) v.findViewById(R.id.mPhotoDoChoiceBtn);
		mPhotoDoChoiceBtn.setOnClickListener(this);
		mPhotoDoChoiceBtn.setText("确定(" + selectedCache.size() + ")");
		// 获取选中图片进行回显
		modules.addAll(album.imageList);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * @param v
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		PhotoSelectorActivity activity = (PhotoSelectorActivity) getActivity();
		activity.done(selectedCache);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnPhotoSelectChangedListener) {
			this.listener = (OnPhotoSelectChangedListener) activity;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		PhotoEntity photo = (PhotoEntity) modules.get(position);
		listener.onPhotoSelectChanged(photo.isSelected, photo);
		if (photo.isSelected) {
			photo.isSelected = false;
			selectedCache.remove(photo);
		} else {
			if (selectedCache.size() == Constants.MAX_SELECT_SIZE) {
				Toast.makeText(getActivity(), "最多只能上传" + Constants.MAX_SELECT_SIZE + "张图", Toast.LENGTH_SHORT).show();
				return;
			}
			photo.isSelected = true;
			selectedCache.add(photo);
		}
		mPhotoDoChoiceBtn.setText("确定(" + selectedCache.size() + ")");
		mAdapter.notifyDataSetChanged();

	}

	@Override
	public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_photo_list_item, null);
			holder = new Holder();
			holder.initializeView(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.initializeData(position);
		return convertView;
	}

	class Holder {
		private ImageView mPhotoItemIconImg;
		private ImageView mPhotoItemCheckedStateImg;

		public void initializeView(View convertView) {
			mPhotoItemIconImg = (ImageView) convertView.findViewById(R.id.mPhotoItemIconImg);
			mPhotoItemCheckedStateImg = (ImageView) convertView.findViewById(R.id.mPhotoItemCheckedStateImg);
		}

		public void initializeData(int position) {
			PhotoEntity photo = (PhotoEntity) modules.get(position);
			String tempUri = "";
			if (photo.thumbnailPath != null && new File(photo.thumbnailPath).exists()) {
				tempUri = "file:///" + photo.thumbnailPath;
			} else {
				tempUri = "file:///" + photo.imagePath;
			}
			ImageDisplay.getInstance().displayImage(tempUri, mPhotoItemIconImg);
			if (photo.isSelected) {
				mPhotoItemCheckedStateImg.setVisibility(View.VISIBLE);
			} else {
				mPhotoItemCheckedStateImg.setVisibility(View.GONE);
			}
		}
	}
}
