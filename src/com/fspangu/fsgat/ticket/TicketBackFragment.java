package com.fspangu.fsgat.ticket;

import com.fspangu.fsgat.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

public class TicketBackFragment extends Fragment {

	Spinner upplace_spinner, downplace_spinner;
	EditText uptime_date, uptime_time;

	private void init() {
		// TODO Auto-generated method stub
		upplace_spinner = (Spinner) getActivity().findViewById(
				R.id.ticket_main_back_upplace_spinner);
		downplace_spinner = (Spinner) getActivity().findViewById(
				R.id.ticket_main_back_downplace_spinner);
		uptime_date = (EditText) getActivity().findViewById(
				R.id.ticket_main_back_uptime_date);
		uptime_time = (EditText) getActivity().findViewById(
				R.id.ticket_main_back_uptime_time);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_ticket_main_back, container);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
	}
}
