package cn.wei.zslq.domain;

import cn.bmob.v3.BmobObject;

public class TalkComment extends BmobObject{
	public static final int comment=0;
	public static final int reply=1;
	private String _id;
	private User fromUser;
	private User toUser;
	private Talk talk;
	private String content;
	private int replyType;//0代表 评论说说 1 代表互相 回复
	public TalkComment(String fromNick, String toNick, String content) {
		this.content = content;
		this.replyType=reply;
		this.fromUser=new User();
		fromUser.setNick(fromNick);
		this.toUser=new User();
		toUser.setNick(toNick);
	}
	
	public TalkComment(String fromNick, String content) {
		this.content = content;
		this.replyType=comment;
		this.fromUser=new User();
		fromUser.setNick(fromNick);
	}
	public TalkComment(){

	}

	public int getReplyType() {
		return replyType;
	}

	public void setReplyType(int replyType) {
		this.replyType = replyType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public Talk getTalk() {
		return talk;
	}

	public void setTalk(Talk talk) {
		this.talk = talk;
	}
}
