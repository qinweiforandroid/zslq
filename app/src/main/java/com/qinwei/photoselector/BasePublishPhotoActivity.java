package com.qinwei.photoselector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.handmark.pulltorefresh.PullToRefreshBase;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import cn.wei.library.utils.ImageDisplay;
import cn.wei.zslq.R;
import cn.wei.zslq.core.BaseGridViewListActivity;

/**
 * 图片选择器入口基类
 *
 * @author qinwei email:qinwei_it@163.com
 * @version 1.0
 * @created createTime: 2015-9-11 下午11:27:53
 */
public abstract class BasePublishPhotoActivity extends BaseGridViewListActivity {

    /**
     * 选中的图片集合
     */
    protected ArrayList<PhotoEntity> photos = new ArrayList<PhotoEntity>();
    /**
     * 压缩后图片路径
     */
    protected ArrayList<String> uploads = new ArrayList<String>();
    private Uri imageUri;

    public static final int REQUEST_OPEN_CAMERA = 10;
    private static final int REQUEST_OPEN_PIC = 11;

    @Override
    protected void initializeView() {
        super.initializeView();
        mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    /**
     * 更新当前UI
     *
     * @param photos
     */
    public void notifyDataChanged(ArrayList<PhotoEntity> photos) {
        this.photos = photos;
        modules.clear();
        uploads.clear();
        modules.add("");
        modules.addAll(0, photos);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 打开图片选择器
     */
    public void openPictureSelector() {
        Intent intent = new Intent(this, PhotoSelectorActivity.class);
        intent.putExtra(Constants.KEY_PHOTOS, photos);
        startActivityForResult(intent, REQUEST_OPEN_PIC);
    }

    /**
     * 打开系统相机拍照
     */
    public void openSystemCamera() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getOpenCameraTempUri());
        startActivityForResult(openCameraIntent, REQUEST_OPEN_CAMERA);
    }

    /**
     * @return 拍照路径
     */
    public Uri getOpenCameraTempUri() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageUri = Uri.fromFile(file);
        return imageUri;
    }

    /**
     * 添加照片菜单
     */
    public void openMenu() {
        new ActionSheet().createBuilder(this, getFragmentManager()).setCancelButtonTitle("取消").setOtherButtonTitles("从手机选择", "拍照")
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        switch (index) {
                            case 0:
                                openPictureSelector();
                                break;
                            case 1:
                                openSystemCamera();
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
                    }
                }).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (modules.size() - 1 == position) {
            openMenu();
        } else {
            PhotoEntity photo = (PhotoEntity) modules.get(position);
            Toast.makeText(this, "open:" + photo.imagePath, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_OPEN_CAMERA:
                    Log.d("tom", "拍照照片已保存:" + imageUri.getPath());
                    PhotoEntity photo = new PhotoEntity();
                    photo.imageId = UUID.randomUUID().toString();
                    photo.imagePath = imageUri.getPath();
                    photo.isSelected = true;
                    photos.add(photo);
                    notifyDataChanged(photos);
                    break;
                case REQUEST_OPEN_PIC:
                    photos = (ArrayList<PhotoEntity>) data.getSerializableExtra(Constants.KEY_PHOTOS);
                    if (photos == null) {
                        photos = new ArrayList<PhotoEntity>();
                    }
                    notifyDataChanged(photos);
                    break;
                default:
                    break;
            }
        } else {
            switch (requestCode) {
                case REQUEST_OPEN_CAMERA:
                    File file = new File(imageUri.getPath());
                    if (file.exists()) {
                        file.delete();
                    }
                    Log.d("tom", "resultCode:" + resultCode + ",删除缓存文件:" + file.getAbsolutePath());
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ;

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(this).inflate(R.layout.activity_publish_list_item, null);
            holder = new Holder();
            holder.initializeView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.initializeData(position);
        return convertView;
    }

    class Holder implements OnClickListener {

        private ImageView mPublishPhotoItemIconImg;
        private ImageView mPublishPhotoItemDeleteImg;
        private String tempUri;
        private int position;

        /**
         * 描述
         *
         * @param convertView
         */
        public void initializeView(View convertView) {
            mPublishPhotoItemIconImg = (ImageView) convertView.findViewById(R.id.mPublishPhotoItemIconImg);
            mPublishPhotoItemDeleteImg = (ImageView) convertView.findViewById(R.id.mPublishPhotoItemDeleteImg);
            mPublishPhotoItemDeleteImg.setOnClickListener(this);
        }

        /**
         * 描述
         */
        public void initializeData(int position) {
            this.position = position;
            if (position == modules.size() - 1) {
                mPublishPhotoItemDeleteImg.setVisibility(View.GONE);
                ImageDisplay.getInstance().displayImage(R.drawable.icon_addpic_unfocused, mPublishPhotoItemIconImg);
            } else {
                mPublishPhotoItemDeleteImg.setVisibility(View.VISIBLE);
                PhotoEntity photo = (PhotoEntity) modules.get(position);
                if (photo.thumbnailPath != null && new File(photo.thumbnailPath).exists()) {
                    tempUri = "file:///" + photo.thumbnailPath;
                } else {
                    tempUri = "file:///" + photo.imagePath;
                }
                ImageDisplay.getInstance().displayImage(tempUri, mPublishPhotoItemIconImg);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mPublishPhotoItemDeleteImg:
                    new AlertDialog.Builder(BasePublishPhotoActivity.this).setMessage("您要刪除此图片吗?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    photos.remove(position);
                                    modules.remove(position);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }).setNegativeButton("取消", null).create().show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void recoveryState(Bundle saveInstance) {
        photos = (ArrayList<PhotoEntity>) saveInstance.getSerializable(Constants.KEY_SELECT_PHOTOS);
        Log.e("wei", saveInstance + "," + photos.size());
        modules.add("");
        modules.addAll(0, photos);
        mAdapter.notifyDataSetChanged();
        super.recoveryState(saveInstance);
    }

    /**
     * @param outState
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.KEY_SELECT_PHOTOS, photos);
        super.onSaveInstanceState(outState);
    }

    /**
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
