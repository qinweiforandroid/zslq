package com.qinwei.photoselector;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 一个目录的相册对象
 * 
 * @author Administrator
 * 
 */
public class PhotoAlbum implements Serializable {
	private static final long serialVersionUID = 1L;
	public int count = 0;
	public String bucketName;
	public ArrayList<PhotoEntity> imageList;

}
