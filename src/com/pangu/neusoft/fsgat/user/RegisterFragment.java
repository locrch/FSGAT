package com.pangu.neusoft.fsgat.user;

import java.util.HashMap;

import org.json.JSONObject;

import com.fspangu.fsgat.FragmentFactory;
import com.fspangu.fsgat.GrzxFragment;
import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fspangu.fsgat.*;
@SuppressLint("NewApi")
public class RegisterFragment extends Fragment
{
	EditText reg_username, reg_captcha, reg_password, reg_password_con;
	Button reg_reg_btn;
	TextView reg_getcaptacha;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	SharedPreferences sp;
	Editor editor;
	FragmentTransaction transaction;
	FragmentManager fragmentManager;
	Fragment thisfragment;
	private void init()
	{
		thisfragment = this;
		
		sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
		
		fragmentManager = getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		
//		android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("注册");
		this.getActivity().setTitle("注册");
		// TODO Auto-generated method stub
		reg_username = (EditText) getActivity().findViewById(R.id.reg_username);
		reg_captcha = (EditText) getActivity().findViewById(R.id.reg_captcha);
		reg_password = (EditText) getActivity().findViewById(R.id.reg_password);
		reg_password_con = (EditText) getActivity().findViewById(R.id.reg_password_con);
		reg_reg_btn = (Button) getActivity().findViewById(R.id.reg_reg_btn);
		reg_getcaptacha = (TextView) getActivity().findViewById(R.id.reg_getcaptacha);
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("注册");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.activity_register, container,false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (CheckNetwork.connected(this)){
			init();
	
			reg_reg_btn.setOnClickListener(new OnClickListener()
			{
	
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					new CustomAsynTask(getActivity())
					{
						@Override
						protected Boolean doInBackground(Void... params)
						{
	
							String[] keys = new String[]
							{ "username", "password", "captcha" };
	
							String[] values = new String[]
							{ reg_username.getText().toString(),
									reg_password.getText().toString(),
									reg_captcha.getText().toString() };
	
							PostJson postJson = new PostJson();
	
							GetParamsMap = postJson.Post(keys, values,
									"registerMember");
	
							Boolean success = false;
	
							success = (Boolean) GetParamsMap.get("success");
	
							return success;
						}
	
						protected void onPostExecute(Boolean result)
						{
							super.onPostExecute(result);
							
							if(result){
								editor.putString("username",reg_username.getText().toString());
								editor.commit();
								FragmentManager fragmentManager=getFragmentManager();
								if(fragmentManager.getBackStackEntryCount()>=1){
				        			int len = fragmentManager.getBackStackEntryCount();
				        		    for (int i = 0; i < len; i++) {
				        		    	fragmentManager.popBackStack();
				        		    }
				        		}
								YwblFragment ywbl = new YwblFragment();
								transaction.replace(R.id.content, ywbl).commit();
								
				        		    /*FragmentTransaction transaction = fragmentManager.beginTransaction();
				            		Fragment fragment = new LoginFragment();  
				            		transaction.replace(R.id.content, fragment);  
				            		transaction.commit();*/
							}
									Toast.makeText(getActivity().getApplicationContext(),
											(String) GetParamsMap.get("msg"),
											Toast.LENGTH_LONG).show();
								
									
						}
					}.execute();
	
				}
			});
	
			reg_getcaptacha.setOnClickListener(new OnClickListener()
			{
	
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					new CustomAsynTask(getActivity(),thisfragment)
					{
						@Override
						protected Boolean doInBackground(Void... params)
						{
							// TODO Auto-generated method stub
							String[] keys = new String[]
							{ "username" };
	
							String[] values = new String[]
							{ reg_username.getText().toString() };
	
							joget = new JSONObject();
	
							PostJson postJson = new PostJson();
	
							GetParamsMap = postJson.Post(keys, values,
									"getCaptacha");
	
							Boolean success = false;
	
							success = (Boolean) GetParamsMap.get("success");
							
							success = true;
							
							return success;
						}
	
						protected void onPostExecute(Boolean result)
						{
	
							
							Toast.makeText(getActivity().getApplicationContext(),
										(String) GetParamsMap.get("msg"),
										Toast.LENGTH_LONG).show();
							
			            		
						};
	
					}.execute();
	
				}
			});
		}
	}
}
