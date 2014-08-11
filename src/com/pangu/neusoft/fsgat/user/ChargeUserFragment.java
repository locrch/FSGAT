package com.pangu.neusoft.fsgat.user;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.PostJSONfromGson;
import com.pangu.neusoft.fsgat.model.ListcDef;
import com.pangu.neusoft.fsgat.model.ListdownStation;
import com.pangu.neusoft.fsgat.model.PostcCharge;
import com.pangu.neusoft.fsgat.model.PostgetDownStation;
import com.pangu.neusoft.fsgat.model.downStation;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class ChargeUserFragment extends Fragment
{ 
	View rootview;
	EditText charge_user_username,charge_user_var;
	Button charge_get_var_btn,charge_user_charge_btn;
	SharedPreferences sp;
	Editor editor;
	String msg;
	MenuItem action_logout,action_changepassword,action_setting,action_pass,action_address,action_bookinghistory;
	
	private void init()
	{
		// TODO Auto-generated method stub
		charge_user_username = (EditText)rootview.findViewById(R.id.charge_user_username);
		charge_user_var = (EditText)rootview.findViewById(R.id.charge_user_var);
		charge_get_var_btn = (Button)rootview.findViewById(R.id.charge_get_var_btn);
		charge_user_charge_btn = (Button)rootview.findViewById(R.id.charge_user_charge_btn);
		
		sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		action_logout = menu.findItem(R.id.action_logout);
		action_changepassword = menu.findItem(R.id.action_changepassword);
		action_setting = menu.findItem(R.id.action_setting);
		action_pass = menu.findItem(R.id.action_pass);
		action_address = menu.findItem(R.id.action_address);
		action_bookinghistory = menu.findItem(R.id.action_bookinghistory);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				YwblFragment ywbl = new YwblFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, ywbl).commit();
			}
		});
		
		actionbar_back_btn.setVisibility(View.INVISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("会员升级");
	}
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			rootview = inflater.inflate(R.layout.fragment_charge_user, null);
			init();
			setHasOptionsMenu(true);
			return rootview;
		}
		@Override
		public void onActivityCreated(Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			
			charge_user_username.setText(sp.getString("username", ""));
			
			charge_user_charge_btn.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					new CustomAsynTask(getActivity()){
						protected Boolean doInBackground(Void... params) {
							super.doInBackground(params);
							PostJSONfromGson postGson = new PostJSONfromGson();
							
							PostcCharge postcCharge = new PostcCharge();
							
							ListcDef listcCharge = new ListcDef();
							
							if (sp.getString("username", null)==null||charge_user_var.getText().toString()==null)
							{
								return false;
							}
							 
							postcCharge.setUsername(sp.getString("username", null));
							
							postcCharge.setCaptcha(charge_user_var.getText().toString());
							
							postcCharge.setType("2");
							
							String result = (String) postGson.GsonPost(postcCharge, "cCharge");
							
							Type listType=new TypeToken<ListcDef>(){}.getType();
							
							Gson gson = new Gson();
							
							listcCharge = gson.fromJson(result,listType);
							
							Boolean success = false;
							
							success = listcCharge.getSuccess();
							
							msg = listcCharge.getMsg();
							
							return success;
						};
						
						protected void onPostExecute(Boolean result) {
							super.onPostExecute(result);
							
								Toast.makeText(getActivity(),
										msg,
										Toast.LENGTH_SHORT).show();
							
							
						};
						
					}.execute();
				}
			});
			
			charge_get_var_btn.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					new CustomAsynTask(getActivity()){
						protected Boolean doInBackground(Void... params) {
							super.doInBackground(params);
					ListcDef listcDef = new ListcDef();
					GetCaptacha getCaptacha = new GetCaptacha();
					listcDef = getCaptacha.GetCaptacha(charge_user_username.getText().toString());
					msg = listcDef.getMsg().toString();
					
					Boolean success = listcDef.getSuccess();
					
					return success;
					
						}
						protected void onPostExecute(Boolean result) {
							super.onPostExecute(result);
							
							if (result)
							{
								Toast.makeText(getActivity(),
										msg,
										Toast.LENGTH_SHORT).show();
							}
							else {
								Toast.makeText(getActivity(),
										R.string.toast_flase_msg,
										Toast.LENGTH_SHORT).show();
							
							}
								
							
						};
						
					}.execute();
				}
			});
		}
}
