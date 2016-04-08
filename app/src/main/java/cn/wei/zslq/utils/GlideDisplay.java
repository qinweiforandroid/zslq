package cn.wei.zslq.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.wei.library.utils.IImageDisplay;
import cn.wei.library.utils.ImageDisplay;


/**
 * Created by qinwei on 2016/2/26 13:21
 * email:qinwei_it@163.com
 */
public class GlideDisplay implements IImageDisplay {
    private final Context context;

    public GlideDisplay(Context context) {
        this.context = context;
    }

    @Override
    public void displayImage(String uri, ImageView imageView) {
        Glide.with(context).load(uri).into(imageView);
    }

    @Override
    public void displayImage(String uri, ImageView imageView, ImageDisplay.DisplayOptions options) {
        Glide.with(context).load(uri)
                .centerCrop()
                .placeholder(options.ingId)
                .error(options.errorId)
                .crossFade()
                .into(imageView);
    }

    @Override
    public void displayImage(int id, ImageView imageView) {
        imageView.setImageResource(id);
    }

    @Override
    public void clearMemoryCache() {
    }
}
