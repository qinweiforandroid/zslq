package com.qinwei.photoselector;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

import cn.wei.zslq.R;
import cn.wei.zslq.support.BaseActivity;

/**
 * @author qinwei email:qinwei_it@163.com
 * @created createTime: 2016-3-28 下午3:47:38
 * @version 1.0
 */

public class PhotoSelectorActivity extends BaseActivity implements PhotoAlbumListFragment.OnPhotoAlbumItemClickListener, PhotoListFragment.OnPhotoSelectChangedListener {
	/**
	 * 选中的图片集合
	 */
	protected ArrayList<PhotoEntity> photos = new ArrayList<PhotoEntity>();
	private PhotoAlbumListFragment mPhotoAlbumListFragment;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_photo_selector);
	}

	@Override
	protected void initializeView() {
		super.initializeView();
		setTitle("图片选择");
		photos = (ArrayList<PhotoEntity>) getIntent().getSerializableExtra(Constants.KEY_PHOTOS);
		mPhotoAlbumListFragment = new PhotoAlbumListFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.mPhotoContainer, mPhotoAlbumListFragment);
		// ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	protected void initializeData() {

	}

	@Override
	public void onPhotoAlbumItemClick(PhotoAlbum album) {
		for (PhotoEntity entity : album.imageList) {
			entity.isSelected = false;
		}
		FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
		ft2.replace(R.id.mPhotoContainer, PhotoListFragment.getInstance(photos, album));
		// ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft2.addToBackStack(null);
		ft2.commit();
	}

	/**
	 * 描述
	 * 
	 * @param selectedCache
	 */
	public void done(ArrayList<PhotoEntity> selectedCache) {
		Intent data = new Intent();
		data.putExtra(Constants.KEY_PHOTOS, selectedCache);
		setResult(RESULT_OK, data);
		finish();
	}

	/**
	 * 
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
			finish();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void onPhotoSelectChanged(boolean isRemove, PhotoEntity photo) {
		if (isRemove) {
			photos.remove(photo);
		} else {
			photos.add(photo);
		}
	}
}
