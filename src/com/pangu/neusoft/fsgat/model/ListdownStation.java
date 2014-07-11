package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;
import java.util.Map;

public class ListdownStation{
	
	ArrayList<downStation> downStationList;
	Boolean success;
	String msg;
	
	public ArrayList<downStation> getDownStationList() {
		return downStationList;
	}

	public void setDownStationList(ArrayList<downStation> downStationList) {
		this.downStationList = downStationList;
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
