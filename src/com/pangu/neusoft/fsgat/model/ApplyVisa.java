package com.pangu.neusoft.fsgat.model;

public class ApplyVisa {
	//"username", "passNumber", "phone", "express","receiver","address","postcode","qwd","HKGZJYXQ","MACZJYXQ"
	private String username;
	private String passNumber;
	private String phone;
	private boolean express;
	private String receiver;
	private String address;
	private String postcode;
	private String[] qwd;
	private String HKGZJYXQ;
	private String MACZJYXQ;
	private String datetime;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassNumber() {
		return passNumber;
	}
	public void setPassNumber(String passNumber) {
		this.passNumber = passNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isExpress() {
		return express;
	}
	public void setExpress(boolean express) {
		this.express = express;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String[] getQwd() {
		return qwd;
	}
	public void setQwd(String[] qwd) {
		this.qwd = qwd;
	}
	public String getHKGZJYXQ() {
		return HKGZJYXQ;
	}
	public void setHKGZJYXQ(String hKGZJYXQ) {
		HKGZJYXQ = hKGZJYXQ;
	}
	public String getMACZJYXQ() {
		return MACZJYXQ;
	}
	public void setMACZJYXQ(String mACZJYXQ) {
		MACZJYXQ = mACZJYXQ;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
	
}
