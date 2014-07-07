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
import com.pangu.neusoft.fsgat.model.Address;
import com.pangu.neusoft.fsgat.model.ListAddress;
import com.pangu.neusoft.fsgat.model.ListAddressAdapter;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.view.View.OnClickListener;


@SuppressLint("NewApi")
public class ListAddressFragment extends Fragment
{
	Button listaddress_addaddress_btn;
	ListView listaddress_list;
	SimpleAdapter adapter;
	List<HashMap<String, Object>> adapterList;
	Map<String, Object> adapterListMap;
	SharedPreferences sp;
	Editor editor;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	Address address;
	ListAddress listAddress;

	FragmentTransaction transaction;
	FragmentManager fragmentManager;
	
	private void init()
	{
		this.getActivity().setTitle("地址管理");
		// TODO Auto-generated method stub
		listaddress_addaddress_btn = (Button)getActivity().findViewById(R.id.listaddress_addaddress_btn);
		listaddress_list = (ListView) getActivity().findViewById(
				R.id.listaddress_list);

		adapterListMap = new HashMap<String, Object>();

		adapterList = new ArrayList<HashMap<String, Object>>();

		listAddress = new ListAddress();

		sp = getActivity()
				.getSharedPreferences(
						getActivity().getApplication().toString(),
						Context.MODE_PRIVATE);
		editor = sp.edit();
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();

		adapterList = new ArrayList<HashMap<String, Object>>();
		
		fragmentManager = getFragmentManager();
		transaction = fragmentManager.beginTransaction();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.activity_list_address, container,
				false);
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
	
					GetParamsMap = postJson.Post(keys, values, "listAddress",
							"addressList");
	
					JSONArray addressList = (JSONArray) GetParamsMap
							.get("addressList");
					if(addressList!=null)
					for (int i = 0; i < addressList.length(); i++)
					{
						try
						{
							JSONObject jo = (JSONObject) addressList.get(i);
	
							adapterListMap = new HashMap<String, Object>();
	
							address = new Address();
	
							adapterListMap.put("id", jo.getString("id").toString());
							adapterListMap.put("receiver", jo.getString("receiver")
									.toString());
							adapterListMap.put("address", jo.getString("address")
									.toString());
							adapterListMap.put("postcode", jo.getString("postcode")
									.toString());
	
							address.setId(jo.getString("id").toString());
							address.setReceiver(jo.getString("receiver").toString());
							address.setAddress(jo.getString("address").toString());
	
							listAddress.add(i, address);
	
							adapterList.add(i,
									(HashMap<String, Object>) adapterListMap);
	
							Log.i("address No:" + i, address.getId().toString());
						} catch (JSONException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	
					}
					listAddress.setListAddresses(listAddress);
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
	
					adapter = new ListAddressAdapter(getActivity(),getActivity(), adapterList,
							R.layout.listaddress_content, new String[]
							{ "receiver", "address" }, new int[]
							{ R.id.listaddress_content_receiver,
									R.id.listaddress_content_address});
					adapter.notifyDataSetChanged();
					listaddress_list.setAdapter(adapter);
	
				}
			}.execute();
			
			listaddress_addaddress_btn.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					AddAddressFragment addAddressFragment = new AddAddressFragment();
					
					transaction.replace(R.id.content,addAddressFragment);
					
					transaction.addToBackStack(null); 
					
					transaction.commit();
				}
			});
		}
	}
}
