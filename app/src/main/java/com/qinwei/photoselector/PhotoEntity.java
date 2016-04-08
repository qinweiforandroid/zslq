package com.qinwei.photoselector;

import java.io.Serializable;

/**
 * 一个图片对象
 * 
 * @author Administrator
 * 
 */
public class PhotoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	public boolean isSelected = false;

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return imageId.hashCode();
	}

	/**
	 * @param o
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return o.hashCode() == this.hashCode();
	}
}
