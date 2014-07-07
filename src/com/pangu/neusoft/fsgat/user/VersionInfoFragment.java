package com.pangu.neusoft.fsgat.user;



import com.fspangu.fsgat.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VersionInfoFragment extends  Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		this.getActivity().setTitle("版本信息");
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_version_info, container,false);
	}
}
