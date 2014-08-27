package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;

public class ListupStation{
	ArrayList<Upstation> upStationList;
	Boolean success;
	String msg;
	public ArrayList<Upstation> getUpStationList()
	{
		return upStationList;
	}
	public void setUpStationList(ArrayList<Upstation> upStationList)
	{
		this.upStationList = upStationList;
	}
	public Boolean getSuccess()
	{
		return success;
	}
	public void setSuccess(Boolean success)
	{
		this.success = success;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	

	

	

	

	
}
