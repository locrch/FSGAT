package com.pangu.neusoft.fsgat.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fspangu.fsgat.R;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.model.ListPass;
import com.pangu.neusoft.fsgat.model.ListPassAdapter;
import com.pangu.neusoft.fsgat.model.Pass;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class ListPassFragment extends Fragment
{
	Button listpass_addpass_btn;
	ListView listpass_list;
	SimpleAdapter adapter;
	List<HashMap<String, Object>> adapterList;
	Map<String, Object> adapterListMap;
	SharedPreferences sp;
	Editor editor;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	Pass pass;
	ListPass ListPass;
	
	FragmentTransaction transaction;
	FragmentManager fragmentManager;
	AddpassFragment addpassFragment;

	private void init()
	{
//		android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("证件管理");
		this.getActivity().setTitle("证件管理");
		// TODO Auto-generated method stub
		listpass_list = (ListView) getActivity().findViewById(
				R.id.listpass_list);
		
		listpass_addpass_btn = (Button)getActivity().findViewById(R.id.list_pass_addpass_btn);
		
		adapterListMap = new HashMap<String, Object>();

		adapterList = new ArrayList<HashMap<String, Object>>();

		ListPass = new ListPass();

		sp = getActivity()
				.getSharedPreferences(
						"sp",
						Context.MODE_PRIVATE);
		editor = sp.edit();
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();

		adapterList = new ArrayList<HashMap<String, Object>>();

		
		fragmentManager = getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		addpassFragment = new AddpassFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.activity_list_pass, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (CheckNetwork.connected(this)){
			init();
	
			new CustomAsynTask(getActivity())
			{
				@Override
				protected Boolean doInBackground(Void... params)
				{
					// TODO Auto-generated method stub
	
					String[] keys = new String[]
					{ "username" };
	
					String[] values = new String[]
					{ sp.getString("username", "") };
	
					PostJson postJson = new PostJson();
	
					GetParamsMap = postJson.Post(keys, values, "listPass",
							"passList");
	
					JSONArray passList = (JSONArray) GetParamsMap.get("passList");
					if(passList!=null)
					for (int i = 0; i < passList.length(); i++)
					{
						try
						{
							JSONObject jo = (JSONObject) passList.get(i);
	
							adapterListMap = new HashMap<String, Object>();
	
							pass = new Pass();
	
							adapterListMap.put("passNumber",
									jo.getString("passNumber").toString());
							adapterListMap.put("name", jo.getString("surName")
									.toString() + jo.getString("givenName"));
	
							pass.setPassNumber(jo.getString("passNumber")
									.toString());
	
							ListPass.add(i, pass);
	
							adapterList.add(i,
									(HashMap<String, Object>) adapterListMap);
	
							Log.i("pass No:" + i, pass.getPassNumber().toString());
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	
					}
					ListPass.setListpass(ListPass);
					Boolean success = false;
	
					success = (Boolean) GetParamsMap.get("success");
	
					if (success)
					{
	
					}
	
					return success;
				}
	
				@Override
				protected void onPostExecute(Boolean result)
				{
					// TODO Auto-generated method stub
					super.onPostExecute(result);
	
					adapter = new ListPassAdapter(getActivity(), adapterList,
							R.layout.listpass_content, new String[]
							{ "passNumber", "name" }, new int[]
							{ R.id.listpass_content_passNumber,
									R.id.listpass_content_name });
					
					listpass_list.setAdapter(adapter);
					adapter.notifyDataSetChanged();
	
				}
			}.execute();
	
			listpass_list.setOnItemClickListener(new OnItemClickListener()
			{
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3)
				{
					// TODO Auto-generated method stub
	
				}
	
			});
			
			listpass_addpass_btn.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					transaction = getFragmentManager().beginTransaction();
					transaction.replace(R.id.content,addpassFragment);
					
					transaction.addToBackStack(null); 
					
					transaction.commit();
				}
			});
		}
	}
}
