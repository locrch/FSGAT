package com.pangu.neusoft.fsgat.model;

public class seatNumber {
	int seatNumberID,rowNumber,columnNumber;
	String seatNumberName;
	Boolean isOptional;
	
	public int getSeatNumberID() {
		return seatNumberID;
	}
	public void setSeatNumberID(int seatNumberID) {
		this.seatNumberID = seatNumberID;
	}
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public int getColumnNumber() {
		return columnNumber;
	}
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	public String getSeatNumberName() {
		return seatNumberName;
	}
	public void setSeatNumberName(String seatNumberName) {
		this.seatNumberName = seatNumberName;
	}
	public Boolean getIsOptional() {
		return isOptional;
	}
	public void setIsOptional(Boolean isOptional) {
		this.isOptional = isOptional;
	}
	
}
