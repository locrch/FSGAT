package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;

public class ListSeatNumber {
	Boolean success;
	String msg;
	ArrayList<seatNumber> seatNumberList;
	Boolean isOptional;
	
	public Boolean getIsOptional() {
		return isOptional;
	}
	public void setIsOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}
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
	public ArrayList<seatNumber> getSeatNumberList() {
		return seatNumberList;
	}
	public void setSeatNumberList(ArrayList<seatNumber> seatNumberList) {
		this.seatNumberList = seatNumberList;
	}
	
}
