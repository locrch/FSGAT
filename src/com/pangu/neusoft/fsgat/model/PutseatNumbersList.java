package com.pangu.neusoft.fsgat.model;

import java.util.ArrayList;

public class PutseatNumbersList {
	static ArrayList<Integer> seatNumbersList;

	public static ArrayList<Integer> getSeatNumbersList() {
		return seatNumbersList;
	}

	public static void setSeatNumbersList(ArrayList<Integer> seatNumbersList) {
		PutseatNumbersList.seatNumbersList = seatNumbersList;
	}
}
