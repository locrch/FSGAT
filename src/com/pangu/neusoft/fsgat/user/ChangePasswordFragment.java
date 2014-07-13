package com.pangu.neusoft.fsgat.user;

import java.util.HashMap;

import org.json.JSONObject;




import com.fspangu.fsgat.R;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ChangePasswordFragment extends Fragment
{
	EditText cpw_username, cpw_captcha, cpw_new_password, cpw_confirm_pass;
	Button  cpw_reset_password_btn;
	JSONObject joget;
	TextView cpw_getcaptacha_btn;
	HashMap<String, Object> GetParamsMap;
	PostJson postJson;
	
	SharedPreferences sp;
	Editor editor;
	
	FragmentTransaction transaction;
	FragmentManager fragmentManager;
	ChangePasswordFragment thisfragment;
	private void init()
	{
		this.getActivity().setTitle("修改密码");
		// TODO Auto-generated method stub
		cpw_username = (EditText) getActivity().findViewById(R.id.cpw_username);
		cpw_captcha = (EditText) getActivity().findViewById(R.id.cpw_captcha);
		cpw_new_password = (EditText) getActivity().findViewById(R.id.cpw_new_password);
		cpw_confirm_pass = (EditText) getActivity().findViewById(R.id.cpw_confirm_pass);
		cpw_getcaptacha_btn = (TextView) getActivity().findViewById(R.id.cpw_getcaptacha_btn);
		cpw_reset_password_btn = (Button) getActivity().findViewById(R.id.cpw_reset_password_btn);
		joget = new JSONObject();
		GetParamsMap = new HashMap<String, Object>();
		postJson = new PostJson();
		
		sp = getActivity()
				.getSharedPreferences(
						"sp",
						Context.MODE_PRIVATE);
		editor = sp.edit();
		
		fragmentManager = getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		
		thisfragment = this;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		
		return inflater.inflate(R.layout.activity_change_password, container,false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (CheckNetwork.connected(this)){
			init();
			
			String username = sp.getString("username", "");
			
			cpw_username.setText(username);
			
			
			
			cpw_getcaptacha_btn.setOnClickListener(new OnClickListener()
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
							// TODO Auto-generated method stub
							String[] keys = new String[]
							{ "username" };
	
							String[] values = new String[]
							{ cpw_username.getText().toString() };
	
							
	
							GetParamsMap = postJson.Post(keys, values,
									"getCaptacha");
	
							Boolean success = false;
	
							success = (Boolean) GetParamsMap.get("success");
	
							return success;
						}
	
						protected void onPostExecute(Boolean result)
						{
	
							super.onPostExecute(result);
	
							Toast.makeText(getActivity().getApplicationContext(),
									(String) GetParamsMap.get("msg"),
									Toast.LENGTH_LONG).show();
						};
	
					}.execute();
				}
			});
	
			cpw_reset_password_btn.setOnClickListener(new OnClickListener()
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
							// TODO Auto-generated method stub
							String[] keys = new String[]
							{ "username", "password", "captcha" };
	
							String[] values = new String[]
							{ cpw_username.getText().toString(),
									cpw_new_password.getText().toString(),
									cpw_captcha.getText().toString() };
	
							
							GetParamsMap = postJson.Post(keys, values,
									"resetPassword");
	
							Boolean success = false;
	
							success = (Boolean) GetParamsMap.get("success");
	
							return success;
						}
	
						protected void onPostExecute(Boolean result)
						{
	
							super.onPostExecute(result);
							
							Toast.makeText(getActivity().getApplicationContext(),
									(String) GetParamsMap.get("msg"),
									Toast.LENGTH_LONG).show();
							
							fragmentManager.popBackStack();
							transaction.commit();
							
							// 收起键盘
							((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(getActivity()
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
						};
	
					}.execute();
				}
			});
		}
	}
}
