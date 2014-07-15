package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;

public class PutReturnseatNumbersList {
	static ArrayList<Integer> seatNumbersList;

	public static ArrayList<Integer> getSeatNumbersList() {
		return seatNumbersList;
	}

	public static void setSeatNumbersList(ArrayList<Integer> seatNumbersList) {
		PutReturnseatNumbersList.seatNumbersList = seatNumbersList;
	}
}
