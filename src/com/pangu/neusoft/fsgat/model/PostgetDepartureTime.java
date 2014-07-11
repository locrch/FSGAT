package com.pangu.neusoft.fsgat.model;

public class PostgetDepartureTime {
	int busCompanyID,ticketDirectionID,upStationID,downStationID,ticketLineID;
	
	public int getTicketLineID() {
		return ticketLineID;
	}

	public void setTicketLineID(int ticketLineID) {
		this.ticketLineID = ticketLineID;
	}

	public int getBusCompanyID() {
		return busCompanyID;
	}

	public void setBusCompanyID(int busCompanyID) {
		this.busCompanyID = busCompanyID;
	}

	public int getTicketDirectionID() {
		return ticketDirectionID;
	}

	public void setTicketDirectionID(int ticketDirectionID) {
		this.ticketDirectionID = ticketDirectionID;
	}	

	public int getUpStationID() {
		return upStationID;
	}

	public void setUpStationID(int upStationID) {
		this.upStationID = upStationID;
	}

	public int getDownStationID() {
		return downStationID;
	}

	public void setDownStationID(int downStationID) {
		this.downStationID = downStationID;
	}
	
}
