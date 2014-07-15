package com.pangu.neusoft.fsgat.user;

import android.R.string;

public class ConfirmInfo {
	static String contant,tellphone,address,upplace,downplace,updatetime,company;
	static int ticketcount;
	static float price;
	
	public static float getPrice() {
		return price;
	}

	public static void setPrice(float price) {
		ConfirmInfo.price = price;
	}

	public static int getTicketcount() {
		return ticketcount;
	}

	public static void setTicketcount(int ticketcount) {
		ConfirmInfo.ticketcount = ticketcount;
	}

	

	public static String getContant() {
		return contant;
	}

	public static void setContant(String contant) {
		ConfirmInfo.contant = contant;
	}

	public static String getTellphone() {
		return tellphone;
	}

	public static void setTellphone(String tellphone) {
		ConfirmInfo.tellphone = tellphone;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		ConfirmInfo.address = address;
	}

	public static String getUpplace() {
		return upplace;
	}

	public static void setUpplace(String upplace) {
		ConfirmInfo.upplace = upplace;
	}

	public static String getDownplace() {
		return downplace;
	}

	public static void setDownplace(String downplace) {
		ConfirmInfo.downplace = downplace;
	}

	public static String getUpdatetime() {
		return updatetime;
	}

	public static void setUpdatetime(String updatetime) {
		ConfirmInfo.updatetime = updatetime;
	}

	public static String getCompany() {
		return company;
	}

	public static void setCompany(String company) {
		ConfirmInfo.company = company;
	}

	
}
