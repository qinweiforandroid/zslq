package cn.wei.library.utils;

import android.widget.ImageView;

import cn.wei.library.R;


/**
 * @author qinwei email:qinwei_it@163.com
 * @version 1.0
 * @created createTime: 2016-2-22 下午1:48:28
 */

public class ImageDisplay {
    private ImageDisplay() {
    }

    private static ImageDisplay mInstance;
    private IImageDisplay display;

    public static ImageDisplay getInstance() {
        if (mInstance == null) {
            synchronized (ImageDisplay.class) {
                mInstance = new ImageDisplay();
            }
        }
        return mInstance;
    }

    public void init(IImageDisplay display) {
        this.display = display;
    }

    public void displayImage(String uri, ImageView imageView) {
        displayImage(uri, imageView, getDefaultOptions());
    }

    public void displayImage(String uri, ImageView imageView, DisplayOptions options) {
        if (display == null) {
            throw new IllegalArgumentException("IImageDisplay can't null");
        }
        display.displayImage(uri, imageView, options);
    }

    public void displayImage(int id, ImageView imageView) {
        if (display == null) {
            throw new IllegalArgumentException("IImageDisplay can't null");
        }
        display.displayImage(id, imageView);
    }

    public static class DisplayOptions {
        public int ingId;//加载中
        public int errorId;//加载失败
    }

    public DisplayOptions getDefaultOptions() {
        DisplayOptions options = new DisplayOptions();
        options.ingId = R.drawable.nim_image_default;
        options.errorId = R.drawable.default_img_failed;
        return options;
    }

    public void clearMemoryCache() {
        display.clearMemoryCache();
    }
}
