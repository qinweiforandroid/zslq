package cn.wei.zslq.entity;

import java.io.Serializable;

public class InformationBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String userID;
	private String status;
	private String createTime;
	private String createTimeValue;
	private String modifyTime;
	private String author;
	private String title;
	private String content;
	private String contentValue;

	private String images;

	private String viewCount;

	private String commentCount;

	private String praiseCount;
	// userPraiseExists 否 int 0没有赞过，1已赞过

	private int userPraiseExists;


	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserID() {
		return this.userID;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getImages() {
		return this.images;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getViewCount() {
		return this.viewCount;
	}

	public String getCreateTimeValue() {
		return createTimeValue;
	}

	public void setCreateTimeValue(String createTimeValue) {
		this.createTimeValue = createTimeValue;
	}



	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	public String getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(String praiseCount) {
		this.praiseCount = praiseCount;
	}

	public int getUserPraiseExists() {
		return userPraiseExists;
	}

	public void setUserPraiseExists(int userPraiseExists) {
		this.userPraiseExists = userPraiseExists;
	}

	public String getContentValue() {
		return contentValue;
	}

	public void setContentValue(String contentValue) {
		this.contentValue = contentValue;
	}



}
