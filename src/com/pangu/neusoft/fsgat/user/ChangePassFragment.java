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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

@SuppressLint("NewApi")
public class ChangePassFragment extends Fragment
{
	EditText changeaddress_receiver,changeaddress_address,changeaddress_postcode;
	Button changeaddress_change_btn;

	
	SharedPreferences sp;
	Editor editor;
	String sex;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	String id;
	private void init()
	{
		this.getActivity().setTitle("修改通行证");
		// TODO Auto-generated method stub
		changeaddress_receiver = (EditText) getActivity().findViewById(R.id.changeaddress_receiver);
		changeaddress_address = (EditText) getActivity().findViewById(R.id.changeaddress_address);
		changeaddress_postcode = (EditText) getActivity().findViewById(R.id.changeaddress_postcode);
		
		changeaddress_change_btn = (Button) getActivity().findViewById(R.id.changeaddress_change_btn);

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
				YwblFragment ywbl = new YwblFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, ywbl).commit();
			}
		});
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("修改证件");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		if (CheckNetwork.connected(this)){
			init();
			
			Intent intent = getActivity().getIntent();
			id = intent.getStringExtra("id");
			
			changeaddress_change_btn.setOnClickListener(new OnClickListener()
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
							{ "username", "id","receiver", "address", "postcode" };
							
							String[] values = new String[]
							{ sp.getString("username", ""),
									id,
									changeaddress_receiver.getText().toString(),
									changeaddress_address.getText().toString(),
									changeaddress_postcode.getText().toString()
									};
	
							PostJson postJson = new PostJson();
	
							GetParamsMap = postJson.Post(keys, values,
									"editAddress");
							
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
								
							
							
								
						};
						
					}.execute();
				}
			});
		}
		return inflater.inflate(R.layout.activity_change_address, container,false);
	}
}
