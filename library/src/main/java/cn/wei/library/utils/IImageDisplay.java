package cn.wei.library.utils;

import android.widget.ImageView;

/**
 * @author qinwei email:qinwei_it@163.com
 * @created createTime: 2016-2-22 下午1:48:57
 * @version 1.0
 */

public interface IImageDisplay {
	void displayImage(String uri, ImageView imageView);

	void displayImage(int id, ImageView imageView);

	void clearMemoryCache();
}
