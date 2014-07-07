package com.pangu.neusoft.fsgat.user;

import java.util.HashMap;

import org.json.JSONObject;

import com.fspangu.fsgat.R;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class RegisterFragment extends Fragment
{
	EditText reg_username, reg_captcha, reg_password, reg_password_con;
	Button reg_reg_btn;
	TextView reg_getcaptacha;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;

	private void init()
	{
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		
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
					new CustomAsynTask(getActivity())
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
