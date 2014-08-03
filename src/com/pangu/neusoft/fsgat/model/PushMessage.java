package com.pangu.neusoft.fsgat.model;

public class PushMessage {
	private int pushMessageID;
	private String messageTitle;
	private String messageContent;
	private String createTime;
	private int pushMessageStatusID;
	public int getPushMessageID() {
		return pushMessageID;
	}
	public void setPushMessageID(int pushMessageID) {
		this.pushMessageID = pushMessageID;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getPushMessageStatusID() {
		return pushMessageStatusID;
	}
	public void setPushMessageStatusID(int pushMessageStatusID) {
		this.pushMessageStatusID = pushMessageStatusID;
	}
	
}
