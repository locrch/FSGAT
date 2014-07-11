package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;

public class ListReturnDownStation {
	ArrayList<ReturnDownStation> ReturnDownStationList;
	Boolean success;
	String msg;
	
	public ArrayList<ReturnDownStation> getReturnDownStationList() {
		return ReturnDownStationList;
	}
	public void setReturnDownStationList(
			ArrayList<ReturnDownStation> returnDownStationList) {
		ReturnDownStationList = returnDownStationList;
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
	
}
