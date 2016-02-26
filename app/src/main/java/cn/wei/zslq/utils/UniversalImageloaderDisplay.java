package cn.wei.zslq.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

import cn.wei.library.utils.IImageDisplay;

/**
 * Created by qinwei on 2016/2/26 11:35
 * email:qinwei_it@163.com
 */
public class UniversalImageloaderDisplay implements IImageDisplay {
    public UniversalImageloaderDisplay(Context context) {
        String cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ZSLQ" + File.separator + "cache";
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCache(new UnlimitedDiscCache(new File(cacheDir)));
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public void displayImage(String uri, ImageView imageView) {

    }

    @Override
    public void displayImage(String uri, ImageView imageView, int placeId, int errorId) {
        ImageLoader.getInstance().displayImage(uri, imageView, getDisplayOptions(placeId, errorId));
    }

    @Override
    public void displayImage(int id, ImageView imageView) {
//        ImageLoader.getInstance().displayImage("drawable://" + id, imageView);
        imageView.setImageResource(id);
    }

    @Override
    public void clearMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    public DisplayImageOptions getDisplayOptions(int placeId, int errorId) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.showImageOnLoading(placeId);
        builder.showImageForEmptyUri(errorId);
        builder.showImageOnFail(errorId);
        builder.cacheInMemory(true);
        builder.cacheOnDisk(true);
        builder.considerExifParams(true);
        return builder.build();
    }
}
