package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;

public class ListTicketOrder
{
	Boolean success;
	String msg;
	ArrayList<ticketOrder> ticketOrderList;
	
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
	public ArrayList<ticketOrder> getTicketOrderList()
	{
		return ticketOrderList;
	}
	public void setTicketOrderList(ArrayList<ticketOrder> ticketOrderList)
	{
		this.ticketOrderList = ticketOrderList;
	}
	
}
