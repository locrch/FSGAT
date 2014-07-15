package com.pangu.neusoft.fsgat.model;

public class ticketOrder
{
	int ticketOrderID,ticketCount;
	float totalCost;
	String ticketDirection,createTime,goUpStation,goDownStation,goTicketDate,goDepartureTime,returnUpStation,returnDownStation,returnTicketDate,returnDepartureTime;
	Boolean isDoubleWay;
	
	public int getTicketOrderID()
	{
		return ticketOrderID;
	}
	public void setTicketOrderID(int ticketOrderID)
	{
		this.ticketOrderID = ticketOrderID;
	}
	public int getTicketCount()
	{
		return ticketCount;
	}
	public void setTicketCount(int ticketCount)
	{
		this.ticketCount = ticketCount;
	}
	public float getTotalCost()
	{
		return totalCost;
	}
	public void setTotalCost(float totalCost)
	{
		this.totalCost = totalCost;
	}
	public String getTicketDirection()
	{
		return ticketDirection;
	}
	public void setTicketDirection(String ticketDirection)
	{
		this.ticketDirection = ticketDirection;
	}
	public String getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}
	public String getGoUpStation()
	{
		return goUpStation;
	}
	public void setGoUpStation(String goUpStation)
	{
		this.goUpStation = goUpStation;
	}
	public String getGoDownStation()
	{
		return goDownStation;
	}
	public void setGoDownStation(String goDownStation)
	{
		this.goDownStation = goDownStation;
	}
	public String getGoTicketDate()
	{
		return goTicketDate;
	}
	public void setGoTicketDate(String goTicketDate)
	{
		this.goTicketDate = goTicketDate;
	}
	public String getGoDepartureTime()
	{
		return goDepartureTime;
	}
	public void setGoDepartureTime(String goDepartureTime)
	{
		this.goDepartureTime = goDepartureTime;
	}
	public String getReturnUpStation()
	{
		return returnUpStation;
	}
	public void setReturnUpStation(String returnUpStation)
	{
		this.returnUpStation = returnUpStation;
	}
	public String getReturnDownStation()
	{
		return returnDownStation;
	}
	public void setReturnDownStation(String returnDownStation)
	{
		this.returnDownStation = returnDownStation;
	}
	public String getReturnTicketDate()
	{
		return returnTicketDate;
	}
	public void setReturnTicketDate(String returnTicketDate)
	{
		this.returnTicketDate = returnTicketDate;
	}
	public String getReturnDepartureTime()
	{
		return returnDepartureTime;
	}
	public void setReturnDepartureTime(String returnDepartureTime)
	{
		this.returnDepartureTime = returnDepartureTime;
	}
	public Boolean getIsDoubleWay()
	{
		return isDoubleWay;
	}
	public void setIsDoubleWay(Boolean isDoubleWay)
	{
		this.isDoubleWay = isDoubleWay;
	}
	
	
}
