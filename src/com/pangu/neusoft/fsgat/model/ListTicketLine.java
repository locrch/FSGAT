package com.pangu.neusoft.fsgat.model;

public class ListTicketLine {
	Boolean success;
	String msg;
	int ticketLineID;
	float oneWayPrice,doubleWayPrice;
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getTicketLineID() {
		return ticketLineID;
	}
	public void setTicketLineID(int ticketLineID) {
		this.ticketLineID = ticketLineID;
	}
	public float getOneWayPrice() {
		return oneWayPrice;
	}
	public void setOneWayPrice(float oneWayPrice) {
		this.oneWayPrice = oneWayPrice;
	}
	public float getDoubleWayPrice() {
		return doubleWayPrice;
	}
	public void setDoubleWayPrice(float doubleWayPrice) {
		this.doubleWayPrice = doubleWayPrice;
	}
	
}
