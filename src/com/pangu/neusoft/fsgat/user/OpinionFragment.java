package com.pangu.neusoft.fsgat.user;

import com.fspangu.fsgat.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OpinionFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		this.getActivity().setTitle(this.getActivity().getResources().getString(R.string.app_name));
		return inflater.inflate(R.layout.fragment_opinion, container,false);
	}
}
