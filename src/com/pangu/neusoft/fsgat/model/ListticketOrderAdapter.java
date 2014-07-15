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
public class ListticketOrderAdapter extends SimpleAdapter
{
	Activity activity1;
	Context context1;
	SharedPreferences sp;
	Editor editor;
	TextView ticket_history_id,ticket_history_way,ticket_history_count,ticket_history_date,ticket_history_price;
	View myView;
	ListTicketOrder listTicketOrder;
	
	
	public ListticketOrderAdapter(Activity activity,Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to)
	{
		super(context, data, resource, from, to);
		context1 = context;		
		activity1 = activity;	
		sp = context1.getSharedPreferences("sp", Context.MODE_PRIVATE);
		editor = sp.edit();
		listTicketOrder = new ListTicketOrder();
		
	
		

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		myView = super.getView(position, convertView, parent);
		LayoutInflater inflater = ((Activity) context1).getLayoutInflater();
		if (myView == null)
		{
			inflater.inflate(R.layout.ticket_history_content, parent, false);

		} else
		{

		}
		ticket_history_id = (TextView)myView.findViewById(R.id.ticket_history_id);
		ticket_history_way = (TextView)myView.findViewById(R.id.ticket_history_way);
		ticket_history_count = (TextView)myView.findViewById(R.id.ticket_history_count);
		ticket_history_date = (TextView)myView.findViewById(R.id.ticket_history_date);
		ticket_history_price = (TextView)myView.findViewById(R.id.ticket_history_price);
		
		
		
		return myView;
	}
	
	
	
	
	
}
