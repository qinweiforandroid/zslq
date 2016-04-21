package com.qinwei.photoselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.PullToRefreshBase;

import java.util.List;

import cn.wei.library.utils.ImageDisplay;
import cn.wei.zslq.R;
import cn.wei.zslq.core.BaseGridViewFragment;

public class PhotoAlbumListFragment extends BaseGridViewFragment {
    public interface OnPhotoAlbumItemClickListener {
        void onPhotoAlbumItemClick(PhotoAlbum album);
    }

    private OnPhotoAlbumItemClickListener listener;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_album_list;
    }

    @Override
    public void initializeView(View v) {
        super.initializeView(v);
        mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.DISABLED);
        List<PhotoAlbum> imagesBucketList = AlbumHelper.getInstance(getActivity()).getImagesBucketList(false);
        modules.addAll(imagesBucketList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPhotoAlbumItemClickListener) {
            listener = (OnPhotoAlbumItemClickListener) context;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listener != null) {
            listener.onPhotoAlbumItemClick((PhotoAlbum) modules.get(position));
        }
    }

    @Override
    public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_album_list_item, null);
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

        private ImageView mPhotoAlbumItemIconImg;
        private TextView mPhotoAlbumItemNameLabel;
        private TextView mPhotoAlbumItemNumLabel;

        /**
         * 描述
         *
         * @param convertView
         */
        public void initializeView(View convertView) {
            mPhotoAlbumItemIconImg = (ImageView) convertView.findViewById(R.id.mPhotoAlbumItemIconImg);
            mPhotoAlbumItemNameLabel = (TextView) convertView.findViewById(R.id.mPhotoAlbumItemNameLabel);
            mPhotoAlbumItemNumLabel = (TextView) convertView.findViewById(R.id.mPhotoAlbumItemNumLabel);
        }

        /**
         * 描述
         */
        public void initializeData(int position) {
            PhotoAlbum album = (PhotoAlbum) modules.get(position);
            final String uri = "file:///" + album.imageList.get(0).imagePath;
            ImageDisplay.getInstance().displayImage(uri, mPhotoAlbumItemIconImg);
            mPhotoAlbumItemNameLabel.setText(album.bucketName);
            mPhotoAlbumItemNumLabel.setText(album.count + "");
        }
    }

}
