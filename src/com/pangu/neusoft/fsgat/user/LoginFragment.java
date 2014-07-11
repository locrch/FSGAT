package com.pangu.neusoft.fsgat.user;

import java.util.HashMap;

import org.json.JSONObject;






import com.fspangu.fsgat.GrzxFragment;
import com.fspangu.fsgat.R;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.model.user;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class LoginFragment extends Fragment
{
	View rootView;
	EditText username, password;
	Button login_btn;
	
	TextView login_forget_password, reg_btn;
	CheckBox login_member_CheckBox;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	SharedPreferences sp;
	Editor editor;
	
	FragmentTransaction transaction;
	FragmentManager fragmentManager;
	Fragment registerFragment,changepasswordFragment;
	LoginFragment loginFragment;
	private void init()
	{
		this.getActivity().setTitle("登录");
		// TODO Auto-generated method stub
		username = (EditText) getActivity().findViewById(R.id.username);
		password = (EditText) getActivity().findViewById(R.id.password);

		login_btn = (Button) getActivity().findViewById(R.id.login_btn);
		reg_btn = (TextView) getActivity().findViewById(R.id.reg_btn);
		login_forget_password = (TextView) getActivity().findViewById(R.id.login_forget_password);
		login_member_CheckBox = (CheckBox)getActivity().findViewById(R.id.login_member_CheckBox);
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();
		sp = getActivity().getSharedPreferences(getActivity().getApplication().toString(),Context.MODE_PRIVATE);
		editor = sp.edit();
		
		reg_btn.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		
		fragmentManager = getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		registerFragment = new RegisterFragment();
		changepasswordFragment = new ChangePasswordFragment();
		loginFragment = this;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		
		rootView = inflater.inflate(R.layout.activity_login, null);
		
		return inflater.inflate(R.layout.activity_login,container,false);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (CheckNetwork.connected(this)){    	  
				init();
				login_btn.setOnClickListener(new OnClickListener()
				{
		
					@Override
					public void onClick(View v)
					{
						
						new CustomAsynTask(getActivity())
						{
							@Override
							protected Boolean doInBackground(Void... params)
							{
								// TODO Auto-generated method stub
								// 收起键盘
								((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
								.hideSoftInputFromWindow(getActivity()
										.getCurrentFocus().getWindowToken(),
										InputMethodManager.HIDE_NOT_ALWAYS);
								
								String[] keys = new String[]
								{ "username", "password" };
		
								String[] values = new String[]
								{ username.getText().toString(),
										password.getText().toString() };
		
								PostJson postJson = new PostJson();
		
								GetParamsMap = postJson.Post(keys, values,
										"loginmember");
		
								Boolean success = false;
		
								success = (Boolean) GetParamsMap.get("success");
								
								if (success ==null) {
									return false;
								}
								
								if (success)
								{
									
									
									user user = new user();
									
									user.setUsername(values[0]);
									
									user.setPassword(values[1]);
									
									editor.putString(keys[0], values[0]);
									
									editor.commit();
								}
								
								
								return success;
							}
		
							protected void onPostExecute(Boolean result)
							{
								super.onPostExecute(result);
								
								Toast.makeText(getActivity().getApplicationContext(),
										(String) GetParamsMap.get("msg"),
										Toast.LENGTH_LONG).show();
								if (result)
								{
									GrzxFragment um = new GrzxFragment();
								fragmentManager.beginTransaction().replace(R.id.content,um).commit();
								//fragmentManager.beginTransaction().remove(LoginFragment.this).commit();
								
								}
								
								if (login_member_CheckBox.isChecked()&&result) {
									editor.putString("username", username.getText().toString());
									editor.putString("switchstatus1", "off");
									editor.putString("switchstatus2", "off");
									editor.putString("switchstatus3", "off");
									editor.putString("switchstatus4", "off");
									editor.putString("switchstatus5", "off");
									editor.commit();
								}
							}
						}.execute();
						
						
		
					}
				});
		
				reg_btn.setOnClickListener(new OnClickListener()
				{
		
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.content,registerFragment);
						
						transaction.addToBackStack(null); 
						
						transaction.commit();
					}
				});
				
				login_forget_password.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						transaction = getFragmentManager().beginTransaction();
						
						fragmentManager.popBackStack();
						transaction.commit();
					}
				});
		
    	  }
		
	}
	
}
