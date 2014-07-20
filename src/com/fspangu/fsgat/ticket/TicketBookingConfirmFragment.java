package com.fspangu.fsgat.ticket;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.fspangu.fsgat.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.PostJSONfromGson;
import com.pangu.neusoft.fsgat.model.ConfirmInfo;
import com.pangu.neusoft.fsgat.model.ListDepartureTime;
import com.pangu.neusoft.fsgat.model.ListbuyTicket;
import com.pangu.neusoft.fsgat.model.PostbuyTicket;
import com.pangu.neusoft.fsgat.model.PostgetDepartureTime;
import com.pangu.neusoft.fsgat.model.departureTime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class TicketBookingConfirmFragment extends Fragment{
	TextView contant,tellphone,address,upplace,downplace,updatetime,company,ticketcount,price;
	Button confirm_btn;
	PostbuyTicket postbuyTicket;
	String msg;
	FragmentManager fragmentManager;
	FragmentTransaction transaction;
	private void init() {
		// TODO Auto-generated method stub
		contant = (TextView)getActivity().findViewById(R.id.booking_confirm_contant);
		tellphone = (TextView)getActivity().findViewById(R.id.booking_confirm_tellphone);
		address = (TextView)getActivity().findViewById(R.id.booking_confirm_address);
		upplace = (TextView)getActivity().findViewById(R.id.booking_confirm_upplace);
		downplace = (TextView)getActivity().findViewById(R.id.booking_confirm_downplace);
		updatetime = (TextView)getActivity().findViewById(R.id.booking_confirm_updatetime);
		company = (TextView)getActivity().findViewById(R.id.booking_confirm_company);
		ticketcount = (TextView)getActivity().findViewById(R.id.booking_confirm_ticketcount);
		price = (TextView)getActivity().findViewById(R.id.booking_confirm_price);
		confirm_btn = (Button)getActivity().findViewById(R.id.booking_confirm_btn);
		
		postbuyTicket = TicketMainFragment.postbuyTicket;
		
		fragmentManager = getFragmentManager();
		
		transaction = fragmentManager.beginTransaction();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_ticket_booking_confirm, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
		
			contant.setText(ConfirmInfo.getContant());
			tellphone.setText(ConfirmInfo.getTellphone());
			address.setText(ConfirmInfo.getAddress());
			upplace.setText(ConfirmInfo.getUpplace());
			downplace.setText(ConfirmInfo.getDownplace());
			updatetime.setText(ConfirmInfo.getUpdatetime());
			company.setText(ConfirmInfo.getCompany());
			
			int ticketcountnum = ConfirmInfo.getTicketcount();
			float pricenum = ConfirmInfo.getPrice();
			ticketcount.setText(String.valueOf(ticketcountnum));
			price.setText(String.valueOf(pricenum));
		
		confirm_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new CustomAsynTask(getActivity())
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						PostJSONfromGson postGson = new PostJSONfromGson();
						
						String result = (String) postGson.GsonPost(postbuyTicket, "buyTicket");
						
						ListbuyTicket listbuyTicket = new ListbuyTicket();
						
						Type listType=new TypeToken<ListbuyTicket>(){}.getType();
						
						Gson gson = new Gson();
						
						listbuyTicket = gson.fromJson(result,listType);
						
						Boolean success = listbuyTicket.getSuccess();
						
						msg = listbuyTicket.getMsg();
						
						return success;
						
					}
					
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						
						Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
						
						if (result)
						{
							TicketHistoryFragment thf = new TicketHistoryFragment();
							transaction.replace(R.id.content, thf); 
							transaction.addToBackStack(null);
				            transaction.commit();
							
						}
					}
					
				}.execute();
				
				
			}
		});
	}
}
