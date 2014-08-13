package com.pangu.neusoft.fsgat.user;

import java.lang.reflect.Type;

import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.PostJSONfromGson;
import com.pangu.neusoft.fsgat.model.ListcDef;
import com.pangu.neusoft.fsgat.model.PostcCharge;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DowngradeUserFragment extends Fragment
{
	View rootview;
	EditText downgrade_user_username,downgrade_user_var;
	Button downgrade_get_var_btn,downgrade_user_downgrade_btn;
	SharedPreferences sp;
	Editor editor;
	String msg;
	MenuItem action_userlevelcontent,action_userinfo,action_logout,action_changepassword,action_setting,action_pass,action_address,action_bookinghistory;
	String usertype;
	private void init()
	{
		// TODO Auto-generated method stub
		
		downgrade_user_username = (EditText)rootview.findViewById(R.id.downgrade_user_username);
		downgrade_user_var = (EditText)rootview.findViewById(R.id.downgrade_user_var);
		downgrade_get_var_btn = (Button)rootview.findViewById(R.id.downgrade_get_var_btn);
		downgrade_user_downgrade_btn = (Button)rootview.findViewById(R.id.downgrade_user_downgrade_btn);
		
		sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		action_userlevelcontent = menu.findItem(R.id.action_userlevelcontent);
		action_userinfo = menu.findItem(R.id.action_userinfo);
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
		
		actionbar_title.setText("取消会员");
		
		
	}
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			rootview = inflater.inflate(R.layout.fragment_downgrade_user, null);
			init();
			setHasOptionsMenu(true);
			return rootview;
		}
		@Override
		public void onActivityCreated(Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			
			downgrade_user_username.setText(sp.getString("username", ""));
			
			downgrade_user_downgrade_btn.setOnClickListener(new OnClickListener()
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
							
							ListcDef listcdowngrade = new ListcDef();
							
							if (sp.getString("username", null)==null||downgrade_user_var.getText().toString()==null)
							{
								return false;
							}
							 
							postcCharge.setUsername(sp.getString("username", null));
							
							postcCharge.setCaptcha(downgrade_user_var.getText().toString());
							
							postcCharge.setType("1");
							
							String result = (String) postGson.GsonPost(postcCharge, "cCharge");
							
							Type listType=new TypeToken<ListcDef>(){}.getType();
							
							Gson gson = new Gson();
							
							listcdowngrade = gson.fromJson(result,listType);
							
							Boolean success = false;
							
							success = listcdowngrade.getSuccess();
							
							msg = listcdowngrade.getMsg();
							
							return success;
						};
						
						protected void onPostExecute(Boolean result) {
							super.onPostExecute(result);
							
								if (result)
								{
									Toast.makeText(getActivity(),
										"会员降级成功！",
										Toast.LENGTH_SHORT).show();
									
									usertype = sp.getString("usertype", "");
									
									if (usertype.equals("1"))
									{
										action_userinfo.setTitle(getResources().getText(R.string.user_level_content_0));
									}
									if (usertype.equals("2"))
									{
										action_userinfo.setTitle(getResources().getText(R.string.user_level_content_1));
									}
									
									if (usertype.equals("3"))
									{
										action_userinfo.setTitle(getResources().getText(R.string.user_level_content_2));
									}
									
									YwblFragment ywbl = new YwblFragment();
									getFragmentManager().beginTransaction().replace(R.id.content, ywbl).commit();
								}
								
							
							
						};
						
					}.execute();
				}
			});
			
			downgrade_get_var_btn.setOnClickListener(new OnClickListener()
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
					listcDef = getCaptacha.GetCaptacha(downgrade_user_username.getText().toString());
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
