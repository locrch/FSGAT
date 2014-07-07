package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;

public class ListAddress extends ArrayList<Address>{
	static ArrayList<Address> listAddresses;

	public ArrayList<Address> getListAddresses() {
		return listAddresses;
	}

	public void setListAddresses(ArrayList<Address> listAddresses) {
		ListAddress.listAddresses = listAddresses;
	}
}
