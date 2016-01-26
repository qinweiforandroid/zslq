package cn.wei.zslq.domain;

import java.util.ArrayList;

public class Talk {
	private String id;
	private String talkContent;
	private long timestamp;
	private ArrayList<String> imgUris;
	private ArrayList<Comment> comments;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTalkContent() {
		return talkContent;
	}
	public void setTalkContent(String talkContent) {
		this.talkContent = talkContent;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public ArrayList<String> getImgUris() {
		return imgUris;
	}
	public void setImgUris(ArrayList<String> imgUris) {
		this.imgUris = imgUris;
	}
	public ArrayList<Comment> getComments() {
		return comments;
	}
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
}
