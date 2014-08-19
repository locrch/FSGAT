package com.pangu.neusoft.fsgat.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.view.View.OnClickListener;
import com.fspangu.fsgat.R;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.user.ListPassFragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListPassAdapter extends SimpleAdapter {
	Activity activity;
	Context context1;
	String countString;
	SharedPreferences sp;
	Editor editor;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	TextView passNumber;
	View myView;
	ListPass listPass;
	public ListPassAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);

		context1 = context;

		sp = context1.getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();
		listPass = new ListPass();
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		myView = super.getView(position, convertView, parent);
		LayoutInflater inflater = ((Activity) context1).getLayoutInflater();
		if (myView == null) {
			inflater.inflate(R.layout.listpass_content, parent, false);

		} else {
			
		}
		passNumber = (TextView) myView
				.findViewById(R.id.listpass_content_passNumber);
		
		

		return myView;
	}

}
