package cn.wei.library.utils;

import android.widget.ImageView;

/**
 * @author qinwei email:qinwei_it@163.com
 * @created createTime: 2016-2-22 下午1:48:28
 * @version 1.0
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
		displayImage(uri,imageView,-1,-1);
	}

	public void displayImage(String uri, ImageView imageView,int placeId,int errorId) {
		if (display == null) {
			throw new IllegalArgumentException("IImageDisplay can't null");
		}
		if(placeId==-1&&errorId==-1){
			display.displayImage(uri, imageView);
		}else{
			display.displayImage(uri, imageView,placeId,errorId);
		}
	}

	public void displayImage(int id, ImageView imageView) {
		if (display == null) {
			throw new IllegalArgumentException("IImageDisplay can't null");
		}
		display.displayImage(id, imageView);
	}

	public void clearMemoryCache() {
		display.clearMemoryCache();
	}
}
