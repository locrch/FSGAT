package com.pangu.neusoft.fsgat.user;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.fspangu.fsgat.GrzxFragment;
import com.fspangu.fsgat.MainActivity;
import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.fspangu.fsgat.pushmessage.Utils;
import com.fspangu.fsgat.ticket.TicketHistoryFragment;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.CustomView.ProtocolAlterDialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
	CheckBox login_member_CheckBox,autoCheckBox;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	SharedPreferences sp;
	Editor editor;
	
	FragmentTransaction transaction;
	FragmentManager fragmentManager;
	Fragment registerFragment,changepasswordFragment;
	LoginFragment loginFragment;
	int usertype;
	
	ProtocolAlterDialog PAdialog;
	
	MenuItem action_logout,action_changepassword,action_setting,action_pass,action_address,action_bookinghistory;
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
		
		actionbar_back_btn.setVisibility(View.INVISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("登录");
	}
	
	
	
	private void init()
	{
		
		android.app.ActionBar actionBar = this.getActivity().getActionBar();
		
		
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("登录");
		this.getActivity().setTitle("登录");
		// TODO Auto-generated method stub
		username = (EditText) rootView.findViewById(R.id.username);
		password = (EditText) rootView.findViewById(R.id.password);

		login_btn = (Button) rootView.findViewById(R.id.login_btn);
		reg_btn = (TextView) rootView.findViewById(R.id.reg_btn);
		login_forget_password = (TextView) rootView.findViewById(R.id.login_forget_password);
		login_member_CheckBox = (CheckBox)rootView.findViewById(R.id.login_member_CheckBox);
		autoCheckBox= (CheckBox)rootView.findViewById(R.id.auto_CheckBox);
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();
		sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
		
		reg_btn.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		
		fragmentManager = getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		registerFragment = new RegisterFragment();
		changepasswordFragment = new ChangePasswordFragment();
		loginFragment = this;
		/*
		 * editor.putBoolean("remberpassword", true);
									editor.putString("tempusername",username.getText().toString());
									editor.putString("temppassword",password.getText().toString());*/
		if(sp.getBoolean("remberpassword", false)){
			//username.setText(sp.getString("tempusername", ""));
			password.setText(sp.getString("temppassword", ""));
			login_member_CheckBox.setChecked(true);
		}else{
			login_member_CheckBox.setChecked(false);
		}
		if(sp.getBoolean("autologin", false)){
			autoCheckBox.setChecked(true);
		}else{
			autoCheckBox.setChecked(false);
		}
		
		username.setText(sp.getString("tempusername", ""));
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		
		rootView = inflater.inflate(R.layout.activity_login, null);
		init();
		setHasOptionsMenu(true);
		
		
		return rootView;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (CheckNetwork.connected(this)){    	  
				
				login_btn.setOnClickListener(new OnClickListener()
				{
		
					@Override
					public void onClick(View v){
						
						if(checkdata()){
							if (!Utils.hasBind(getActivity().getApplicationContext())){
								PushManager.startWork(getActivity().getApplicationContext(),
										PushConstants.LOGIN_TYPE_API_KEY,
										Utils.getMetaValue(getActivity(), "api_key"));
							}

							if (!sp.getString("pushuserid", "0").equals("0")){
								new CustomAsynTask(getActivity())
								{
									@Override
									protected Boolean doInBackground(Void... params)
									{
										// TODO Auto-generated method stub
										// 收起键盘
										try{
										((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
										.hideSoftInputFromWindow(getActivity()
												.getCurrentFocus().getWindowToken(),
												InputMethodManager.HIDE_NOT_ALWAYS);
										}catch(Exception ex){
											ex.printStackTrace();
										}
										String[] keys = new String[]
										{ "username", "password","pushKey","channelId"};
				
										String[] values = new String[]
										{ username.getText().toString(),
												password.getText().toString(),sp.getString("pushuserid", "0"),sp.getString("pushchannelid", "0")};
				
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
											//usertype = (Integer)GetParamsMap.get("type");
											
											user user = new user();
											
											user.setUsername(values[0]);
											
											user.setPassword(values[1]);
											
											editor.putString("username", username.getText().toString());
											
											editor.putString("usertype", values[2]);
											
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
										
										editor.putString("tempusername",username.getText().toString());
										editor.commit();
										
//										Toast.makeText(getActivity().getApplicationContext(),
//												String.valueOf(usertype),
//												Toast.LENGTH_LONG).show();
										
										if (login_member_CheckBox.isChecked()&&result) {
											editor.putBoolean("remberpassword", true);									
											editor.putString("temppassword",password.getText().toString());
											editor.commit();
										}else if (!login_member_CheckBox.isChecked()) {
											editor.putBoolean("remberpassword", false);
											editor.remove("temppassword");
											editor.commit();
										}
										if (autoCheckBox.isChecked()&&result) {
											editor.putBoolean("autologin", true);
											editor.commit();
										}else if(!autoCheckBox.isChecked()){
											editor.putBoolean("autologin", false);
										}
										
										if (result)
										{
											//fragmentManager.beginTransaction().remove(LoginFragment.this).commit();
											editor.putString("username", username.getText().toString());
											editor.putString("switchstatus1", sp.getString("switchstatus1", "off"));
											editor.putString("switchstatus2", sp.getString("switchstatus2", "off"));
											editor.putString("switchstatus3", sp.getString("switchstatus3", "off"));
											editor.putString("switchstatus4", sp.getString("switchstatus4", "off"));
											editor.putString("switchstatus5", sp.getString("switchstatus5", "off"));
											editor.commit();
											
											//
											
											
											YwblFragment ywbl = new YwblFragment();
											
											if (fragmentManager.getBackStackEntryCount()<= 1)
											{
												transaction.replace(R.id.content, ywbl).commit();
												
												
											}else if(fragmentManager.getBackStackEntryCount()>0){
												fragmentManager.popBackStackImmediate();
												transaction.commit();
											}
											
											
											action_logout.setVisible(true);
											action_changepassword.setVisible(true);
											action_setting.setVisible(true);
											action_pass.setVisible(true);
											action_address.setVisible(true);
											action_bookinghistory.setVisible(true);
										}
									}
								}.execute();
							}else{
								Toast.makeText(getActivity(), "网络不稳定，请重试！", Toast.LENGTH_SHORT).show();
								PushManager.startWork(getActivity().getApplicationContext(),
										PushConstants.LOGIN_TYPE_API_KEY,
										Utils.getMetaValue(getActivity(), "api_key"));
							}
						
						}
					}
					
					
				});
		
				
				
				reg_btn.setOnClickListener(new OnClickListener()
				{
		
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						
						PAdialog = new ProtocolAlterDialog
						(getActivity(),
						 getActivity().getString(R.string.reg_protocol_content1)+"\n"
						+getActivity().getString(R.string.reg_protocol_content2)+"\n"
						+getActivity().getString(R.string.reg_protocol_content3)+"\n"
						+getActivity().getString(R.string.reg_protocol_content4)+"\n"
						+getActivity().getString(R.string.reg_protocol_content5)+"\n"
						+getActivity().getString(R.string.reg_protocol_content6)+"\n"
						+getActivity().getString(R.string.reg_protocol_content7)+"\n"
						+getActivity().getString(R.string.reg_protocol_content8)+"\n"
						+getActivity().getString(R.string.reg_protocol_content9)+"\n"
						+getActivity().getString(R.string.reg_protocol_content10)+"\n"
						+getActivity().getString(R.string.reg_protocol_content11)+"\n"
						+getActivity().getString(R.string.reg_protocol_content12)+"\n"
						+getActivity().getString(R.string.reg_protocol_content13)+"\n"
						+getActivity().getString(R.string.reg_protocol_content14)+"\n"
						+getActivity().getString(R.string.reg_protocol_content15)+"\n"
						+getActivity().getString(R.string.reg_protocol_content16)+"\n"
						+getActivity().getString(R.string.reg_protocol_content17)+"\n"
						+getActivity().getString(R.string.reg_protocol_content18)+"\n"
						+getActivity().getString(R.string.reg_protocol_content19)+"\n"
						+getActivity().getString(R.string.reg_protocol_content20)+"\n"
						+getActivity().getString(R.string.reg_protocol_content21)+"\n"
						,"注册协议")
						{
							
							@Override
							public void agree_clickCallBack()
							{
								// TODO Auto-generated method stub
								transaction = getFragmentManager().beginTransaction();
								transaction.add(R.id.content,registerFragment);
								
								transaction.addToBackStack(null); 
								
								transaction.commit();
								
								PAdialog.dismiss();
							}

							@Override
							public void diaagree_clickCallBack()
							{
								// TODO Auto-generated method stub
								
								PAdialog.dismiss();
							}
						};
						
						PAdialog.show();
						
						/**/
					}
				});
				
				login_forget_password.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						transaction = getFragmentManager().beginTransaction();
						transaction.add(R.id.content,changepasswordFragment);
						transaction.addToBackStack(null); 
						transaction.commit();
					}
				});
		
    	  }
		
	}
	public boolean checkdata(){
		boolean res=true;
		if(username.getText().toString().equals("")||password.getText().toString().equals("")){
			res=false;
			Toast.makeText(getActivity(), "请输入用户名和密码！", Toast.LENGTH_SHORT).show();
		}
		else if(!isMobile(username.getText().toString())){
			res=false;
			Toast.makeText(getActivity(), "用户名必须为11位手机号码！", Toast.LENGTH_SHORT).show();
		}
		
		return res;
	}
	public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
	
	
}
