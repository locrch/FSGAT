package com.pangu.neusoft.fsgat.user;

import java.util.HashMap;

import org.json.JSONObject;
import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class AddAddressFragment extends Fragment
{
	EditText addaddress_receiver,addaddress_address,addaddress_postcode;
	Button addaddress_add_btn;
	SharedPreferences sp;
	Editor editor;
	String sex;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	private void init()
	{
//		android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("添加地址");
		this.getActivity().setTitle("添加地址");
		// TODO Auto-generated method stub
		addaddress_receiver = (EditText)getActivity().findViewById(R.id.addaddress_receiver);
		addaddress_address = (EditText) getActivity().findViewById(R.id.addaddress_address);
		addaddress_postcode = (EditText) getActivity().findViewById(R.id.addaddress_postcode);
		
		addaddress_add_btn = (Button) getActivity().findViewById(R.id.addaddress_add_btn);

		sp = getActivity().getSharedPreferences("sp",
				Context.MODE_PRIVATE);
		
		editor = sp.edit();
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				/*YwblFragment ywbl = new YwblFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, ywbl).commit();
				*/
				getFragmentManager().popBackStack();
			}
		});
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("添加地址");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.activity_addaddress, container, false);
		
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if (CheckNetwork.connected(this)){
			init();
			addaddress_add_btn.setOnClickListener(new OnClickListener()
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
							{ "username", "receiver", "address", "postcode" };
	
							String[] values = new String[]
							{ sp.getString("username", ""),
									addaddress_receiver.getText().toString(),
									addaddress_address.getText().toString(),
									addaddress_postcode.getText().toString()
									};
	
							PostJson postJson = new PostJson();
	
							GetParamsMap = postJson.Post(keys, values,
									"addAddress");
							
							Boolean success = false;
							
							success = (Boolean) GetParamsMap.get("success");
							
							return success;
						}
						
						protected void onPostExecute(Boolean result) {
							super.onPostExecute(result);
							
							
							
							if (GetParamsMap.get("msg").toString().length()>50)
							{
								Toast.makeText(getActivity().getApplicationContext(),
										R.string.toast_flase_msg,
										Toast.LENGTH_LONG).show();
							}
							else {
								Toast.makeText(getActivity().getApplicationContext(),
										(String) GetParamsMap.get("msg"),
										Toast.LENGTH_LONG).show();
							}
								
							
							
							try{
							// 收起键盘
							((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(getActivity()
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
							}catch(Exception ex){
								ex.printStackTrace();
							}
							
							if(result){
								ListAddressFragment alistAddressFragment = new ListAddressFragment();
								FragmentManager fragmentManager = getFragmentManager();
								Log.e("count add 1","count1:"+fragmentManager.getBackStackEntryCount()+"");
								
								if(fragmentManager.getBackStackEntryCount()>1){
				        			int len = fragmentManager.getBackStackEntryCount();
				        		    for (int i = 0; i < len-1; i++) {
				        		    	fragmentManager.popBackStack();
				        		    }
				        		}
								Log.e("count add 2","count2:"+fragmentManager.getBackStackEntryCount()+""); 
								FragmentTransaction transaction = fragmentManager.beginTransaction();
								transaction.add(R.id.content,alistAddressFragment);
								transaction.addToBackStack(null); 
								transaction.commit();
								Log.e("count add 3 ","count3:"+fragmentManager.getBackStackEntryCount()+"");
							}
							
							
						};
						
					}.execute();
				}
			});
		}
	}
}
