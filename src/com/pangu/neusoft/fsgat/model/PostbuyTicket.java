package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;

public class PostbuyTicket {
	String username,receiver,address,postcode,contacter,contactNumber,goTicketDate,returnTicketDate;
	int goTicketLineID,returnTicketLineID,goDepartureTimeID,returnDepartureTimeID,ticketCount;
	Boolean isExpress,isDoubleWay;
	ArrayList<Integer> goSeatNumbers;
	ArrayList<Integer> returnSeatNumbers;
	
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getReceiver()
	{
		return receiver;
	}
	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getPostcode()
	{
		return postcode;
	}
	public void setPostcode(String postcode)
	{
		this.postcode = postcode;
	}
	public String getContacter()
	{
		return contacter;
	}
	public void setContacter(String contacter)
	{
		this.contacter = contacter;
	}
	public String getContactNumber()
	{
		return contactNumber;
	}
	public void setContactNumber(String contactNumber)
	{
		this.contactNumber = contactNumber;
	}
	public String getGoTicketDate()
	{
		return goTicketDate;
	}
	public void setGoTicketDate(String goTicketDate)
	{
		this.goTicketDate = goTicketDate;
	}
	public String getReturnTicketDate()
	{
		return returnTicketDate;
	}
	public void setReturnTicketDate(String returnTicketDate)
	{
		this.returnTicketDate = returnTicketDate;
	}
	public int getGoTicketLineID()
	{
		return goTicketLineID;
	}
	public void setGoTicketLineID(int goTicketLineID)
	{
		this.goTicketLineID = goTicketLineID;
	}
	public int getReturnTicketLineID()
	{
		return returnTicketLineID;
	}
	public void setReturnTicketLineID(int returnTicketLineID)
	{
		this.returnTicketLineID = returnTicketLineID;
	}
	public int getGoDepartureTimeID()
	{
		return goDepartureTimeID;
	}
	public void setGoDepartureTimeID(int goDepartureTimeID)
	{
		this.goDepartureTimeID = goDepartureTimeID;
	}
	public int getReturnDepartureTimeID()
	{
		return returnDepartureTimeID;
	}
	public void setReturnDepartureTimeID(int returnDepartureTimeID)
	{
		this.returnDepartureTimeID = returnDepartureTimeID;
	}
	public int getTicketCount()
	{
		return ticketCount;
	}
	public void setTicketCount(int ticketCount)
	{
		this.ticketCount = ticketCount;
	}
	public Boolean getIsExpress()
	{
		return isExpress;
	}
	public void setIsExpress(Boolean isExpress)
	{
		this.isExpress = isExpress;
	}
	public Boolean getIsDoubleWay()
	{
		return isDoubleWay;
	}
	public void setIsDoubleWay(Boolean isDoubleWay)
	{
		this.isDoubleWay = isDoubleWay;
	}
	public ArrayList<Integer> getGoSeatNumbers()
	{
		return goSeatNumbers;
	}
	public void setGoSeatNumbers(ArrayList<Integer> goSeatNumbers)
	{
		this.goSeatNumbers = goSeatNumbers;
	}
	public ArrayList<Integer> getReturnSeatNumbers()
	{
		return returnSeatNumbers;
	}
	public void setReturnSeatNumbers(ArrayList<Integer> returnSeatNumbers)
	{
		this.returnSeatNumbers = returnSeatNumbers;
	}
	
	
	
}
