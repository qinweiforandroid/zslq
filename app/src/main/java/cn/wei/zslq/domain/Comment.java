package cn.wei.zslq.domain;

public class Comment {
	public static final int comment=0;
	public static final int reply=1;
	private String _id;
	private String fromNick;
	private String toNick;
	private String content;
	private int replyType;//0代表 评论说说 1 代表互相 回复
	public Comment(String fromNick, String toNick, String content) {
		this.fromNick = fromNick;
		this.toNick = toNick;
		this.content = content;
		this.replyType=reply;
	}
	
	public Comment(String fromNick, String content) {
		this.fromNick = fromNick;
		this.content = content;
		this.replyType=comment;
	}

	public int getReplyType() {
		return replyType;
	}

	public void setReplyType(int replyType) {
		this.replyType = replyType;
	}

	public String getFromNick() {
		return fromNick;
	}

	public void setFromNick(String fromNick) {
		this.fromNick = fromNick;
	}

	public String getToNick() {
		return toNick;
	}

	public void setToNick(String toNick) {
		this.toNick = toNick;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
