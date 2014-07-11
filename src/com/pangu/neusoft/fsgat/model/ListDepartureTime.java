package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;

public class ListDepartureTime {
	ArrayList<departureTime> departureTimeList;
	int ticketLineID;
	Boolean success;
	String msg;
	ArrayList<ReturnUpStation> upStationList;
	ArrayList<ReturnDownStation> downStationList;
	
	public ArrayList<ReturnUpStation> getUpStationList() {
		return upStationList;
	}
	public void setUpStationList(ArrayList<ReturnUpStation> upStationList) {
		this.upStationList = upStationList;
	}
	public ArrayList<ReturnDownStation> getDownStationList() {
		return downStationList;
	}
	public void setDownStationList(ArrayList<ReturnDownStation> downStationList) {
		this.downStationList = downStationList;
	}
	
	
	
	public ArrayList<departureTime> getDepartureTimeList() {
		return departureTimeList;
	}
	public void setDepartureTimeList(ArrayList<departureTime> departureTimeList) {
		this.departureTimeList = departureTimeList;
	}
	public int getTicketLineID() {
		return ticketLineID;
	}
	public void setTicketLineID(int ticketLineID) {
		this.ticketLineID = ticketLineID;
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
