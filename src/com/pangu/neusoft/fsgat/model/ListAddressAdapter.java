package com.pangu.neusoft.fsgat.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View.OnClickListener;



import com.fspangu.fsgat.R;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.user.ChangeAddressDialog;
import com.pangu.neusoft.fsgat.user.LoginFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("CommitTransaction")
public class ListAddressAdapter extends SimpleAdapter
{
	Activity activity1;
	Context context1;
	SharedPreferences sp;
	Editor editor;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	TextView listaddress_content_receiver, istaddress_content_address,listaddress_content_postcode;
	Button listadress_change_btn, listaddress_delete_btn;
	View myView;
	ListAddress listAddress;
	
	
	public ListAddressAdapter(Activity activity,Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to)
	{
		super(context, data, resource, from, to);
		context1 = context;		
		activity1 = activity;	
		sp = context1.getSharedPreferences("sp", Context.MODE_PRIVATE);
		editor = sp.edit();
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();
		listAddress = new ListAddress();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		myView = super.getView(position, convertView, parent);
		LayoutInflater inflater = ((Activity) context1).getLayoutInflater();
		if (myView == null)
		{
			inflater.inflate(R.layout.listaddress_content, parent, false);

		} else
		{

		}
		listaddress_content_receiver = (TextView) myView
				.findViewById(R.id.listaddress_content_receiver);
		listaddress_content_postcode = (TextView) myView
				.findViewById(R.id.listaddress_content_postcode);
		
//		Button listadress_change_btn = (Button) myView
//				.findViewById(R.id.listadress_change_btn);
//		Button listaddress_delete_btn = (Button) myView
//				.findViewById(R.id.listaddress_delete_btn);
//
//		final int p = position;
//
//		listaddress_delete_btn.setOnClickListener(new OnClickListener()
//		{
//
//			@Override
//			public void onClick(View v)
//			{
//				// TODO Auto-generated method stub
//				//deleteAddress(p);
//
//			}
//		});
//
//		listadress_change_btn.setOnClickListener(new OnClickListener()
//		{
//
//			@Override
//			public void onClick(View v)
//			{
//				// TODO Auto-generated method stub
//				ListAddress lA = (ListAddress) listAddress.getListAddresses();
//				Address address = lA.get(p);
//				new ChangeAddressDialog(address, activity1, context1, true, null).show();
//			}
//		});
		
		
		return myView;
	}
	
	
	
	
	
}
