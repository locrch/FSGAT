package com.pangu.neusoft.fsgat.user;

import com.fspangu.fsgat.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.view.View.OnClickListener;

public class MysettingFragment extends Fragment
{
	Button switch1_btn,switch2_btn,switch3_btn,switch4_btn,switch5_btn;
	static String switchstatus1,switchstatus2,switchstatus3,switchstatus4,switchstatus5 ;
	SharedPreferences sp;
	Editor editor;
	
	private void init()
	{
		this.getActivity().setTitle("我的设置");
		// TODO Auto-generated method stub
		switch1_btn = (Button)getActivity().findViewById(R.id.switch1_btn);
		switch2_btn = (Button)getActivity().findViewById(R.id.switch2_btn);
		switch3_btn = (Button)getActivity().findViewById(R.id.switch3_btn);
		switch4_btn = (Button)getActivity().findViewById(R.id.switch4_btn);
		switch5_btn = (Button)getActivity().findViewById(R.id.switch5_btn);
		
		sp = getActivity()
				.getSharedPreferences(
						"sp",
						Context.MODE_PRIVATE);
		editor = sp.edit();
		
		switchstatus1 = sp.getString("switchstatus1", "off");
		switchstatus2 = sp.getString("switchstatus2", "off");
		switchstatus3 = sp.getString("switchstatus3", "off");
		switchstatus4 = sp.getString("switchstatus4", "off");
		switchstatus5 = sp.getString("switchstatus5", "off");
		
		if (switchstatus1.equals("on"))
		{
			switch1_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
			
		}
		if (switchstatus2.equals("on"))
		{
			switch2_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
			
		}
		if (switchstatus3.equals("on"))
		{
			switch3_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
			
		}
		if (switchstatus4.equals("on"))
		{
			switch4_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
			
		}
		if (switchstatus5.equals("on"))
		{
			switch5_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
			
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.fragment_mysetting, container,false);
		
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		init();
		
		
		switch1_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
		switch1_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (switchstatus1.equals("on"))
				{
					switch1_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
					
					switchstatus1 = "off";
					
					
				}
				else {
					switch1_btn.setTag("on");
					
					switchstatus1 = "on";
					switch1_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
					
					
				}
				editor.putString("switchstatus1", switchstatus1);
				editor.commit();
				
			}
		});
		
		switch2_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
		switch2_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (switchstatus2.equals("on"))
				{
					switch2_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
					
					switchstatus2 = "off";
				}
				else {
					switchstatus2 = "on";
					switch2_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
					
				}
				editor.putString("switchstatus2", switchstatus2);
				editor.commit();
			}
		});
		
		switch3_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
		switch3_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (switchstatus3.equals("on"))
				{
					switch3_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
					
					switchstatus3 = "off";
				}
				else {
					switchstatus3 = "on";
					switch3_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
					
				}
				editor.putString("switchstatus3", switchstatus3);
				editor.commit();
			}
		});
		
		switch4_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
		switch4_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (switchstatus4.equals("on"))
				{
					switch4_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
					
					switchstatus4 = "off";
				}
				else {
					switchstatus4 = "on";
					switch4_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
					
				}
				editor.putString("switchstatus4", switchstatus4);
				editor.commit();
			}
		});
		
		switch5_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
		switch5_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				if (switchstatus5.equals("on"))
				{
					switch5_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_off);
					
					switchstatus5 = "off";
				}
				else {
					switchstatus5 = "on";
					switch5_btn.setBackgroundResource(R.drawable.mysetting_switch_btn_on);
					
				}
				editor.putString("switchstatus1", switchstatus5);
				editor.commit();
			}
		});
		
		
	}
}
