package com.pangu.neusoft.fsgat.user;

import com.fspangu.fsgat.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class OpinionFragment extends Fragment
{
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("意见反馈");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		// TODO Auto-generated method stub
//		android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText(this.getActivity().getResources().getString(R.string.app_name));
		this.getActivity().setTitle(this.getActivity().getResources().getString(R.string.app_name));
		return inflater.inflate(R.layout.fragment_opinion, container,false);
	}
}
