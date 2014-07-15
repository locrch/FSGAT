package com.fspangu.fsgat;

import com.fspangu.fsgat.ticket.TicketHistoryFragment;
import com.fspangu.fsgat.ticket.TicketMainFragment;
import com.pangu.neusoft.fsgat.user.LoginFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public  class BmfwFragment extends Fragment {
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	View view = inflater.inflate(R.layout.bmfw_fragment, null);  
        this.getActivity().setTitle("便民服务");
        Button ticket_booking_btn = (Button)view.findViewById(R.id.ticket_booking_btn);
        Button ticket_history_btn = (Button)view.findViewById(R.id.ticket_history_btn);
        
        
    	ticket_booking_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction =getFragmentManager().beginTransaction(); 
				TicketMainFragment tm = new TicketMainFragment();
				transaction.replace(R.id.content, tm); 
				transaction.addToBackStack(null);
	            transaction.commit();
	        }
		});
    	
    	ticket_history_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction =getFragmentManager().beginTransaction(); 
				TicketHistoryFragment thf = new TicketHistoryFragment();
				transaction.replace(R.id.content, thf); 
				transaction.addToBackStack(null);
	            transaction.commit();
	            
			}
		});
        
        return view;  
    }  
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    	
    	
    	
    	
    	
    }
}  