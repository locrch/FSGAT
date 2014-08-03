package com.pangu.neusoft.fsgat.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.model.Address;
import com.pangu.neusoft.fsgat.model.ListAddress;
import com.pangu.neusoft.fsgat.model.ListAddressAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
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

	View rootview;
	
	private void init()
	{
//		android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("地址管理");
		this.getActivity().setTitle("地址管理");
		// TODO Auto-generated method stub
		listaddress_addaddress_btn = (Button)rootview.findViewById(R.id.listaddress_addaddress_btn);
		listaddress_list = (ListView) rootview.findViewById(R.id.listaddress_list);

		adapterListMap = new HashMap<String, Object>();

		adapterList = new ArrayList<HashMap<String, Object>>();

		listAddress = new ListAddress();

		sp = getActivity()
				.getSharedPreferences(
						"sp",
						Context.MODE_PRIVATE);
		editor = sp.edit();
		


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
		
		actionbar_title.setText("地址管理");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		
		rootview = inflater.inflate(R.layout.activity_list_address,null);
		
		init();
		
		return rootview;
	}
	
	public void updateList(){
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();
		adapterListMap = new HashMap<String, Object>();
		adapterList = new ArrayList<HashMap<String, Object>>();
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
						address.setPostcode(jo.getString("postcode").toString());
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
				return success;
			}

			@Override
			protected void onPostExecute(Boolean result)
			{
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(result){
				adapter = new ListAddressAdapter(getActivity(),getActivity(), adapterList,
						R.layout.listaddress_content, new String[]
						{ "receiver", "address","postcode" }, new int[]
						{ R.id.listaddress_content_receiver,
								R.id.listaddress_content_address,R.id.listaddress_content_postcode});
				//adapter.notifyDataSetChanged();
				listaddress_list.setAdapter(adapter);
				}else{
					Toast.makeText(getActivity(), "获取数据失败,请稍后重试！", Toast.LENGTH_SHORT).show();
				}
			}
		}.execute();
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (CheckNetwork.connected(this)){
			
			
			updateList();
			
			listaddress_list.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					final int p=arg2;
					ListAddress lA = (ListAddress) listAddress.getListAddresses();
    				final Address address = lA.get(p);
					
					Dialog alertDialog = new AlertDialog.Builder(getActivity()). 
			                setTitle("编辑地址"). 
			                setMessage("收件人："+address.getReceiver()+"\n"
			                			+"地址："+address.getAddress()+"\n"
			                			+"邮编："+address.getPostcode()). 
			                setIcon(R.drawable.ic_launcher). 
			                setPositiveButton("修改", new DialogInterface.OnClickListener() { 
			                    @Override 
			                    public void onClick(DialogInterface dialog, int which) { 
			        				new ChangeAddressDialog(address, getActivity(), ListAddressFragment.this, true, null).show();
			        				
			                    } 
			                }). 
			                setNeutralButton("删除", new DialogInterface.OnClickListener() { 
			                     
			                    @Override 
			                    public void onClick(DialogInterface dialog, int which) { 
			                        // TODO Auto-generated method stub
			                    	new AlertDialog.Builder(getActivity()). 
	    			                setTitle("提示"). 
	    			                setMessage("您确定要删除地址吗？"). 
	    			                setIcon(R.drawable.ic_launcher). 
	    			                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
	    			                    @Override 
	    			                    public void onClick(DialogInterface dialog, int which) { 
	    			                    	deleteAddress(address);			    			        				
	    			                    } 
	    			                }). 
	    			                setNeutralButton("取消", new DialogInterface.OnClickListener() { 
	    			                     
	    			                    @Override 
	    			                    public void onClick(DialogInterface dialog, int which) { 
	    			                        // TODO Auto-generated method stub

	    			                    } 
	    			                }).show(); 
			                    	
			                    } 
			                }).
			                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
			                     
			                    @Override 
			                    public void onClick(DialogInterface dialog, int which) { 
			                        // TODO Auto-generated method stub
			                    	
			                    } 
			                }).
			                create(); 
			        alertDialog.show(); 
					
				}
				
			});
			
			
			
			
			listaddress_addaddress_btn.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					AddAddressFragment addAddressFragment = new AddAddressFragment();
					FragmentManager fragmentManager = getFragmentManager();
					Log.e("count1","count1:"+fragmentManager.getBackStackEntryCount()+"");
					if(fragmentManager.getBackStackEntryCount()>1){
	        			int len = fragmentManager.getBackStackEntryCount();
	        		    for (int i = 0; i < len-1; i++) {
	        		    	fragmentManager.popBackStack();
	        		    }
	        		}
					Log.e("count2","count2:"+fragmentManager.getBackStackEntryCount()+"");
					FragmentTransaction transaction = fragmentManager.beginTransaction();
					transaction.add(R.id.content,addAddressFragment);
					transaction.addToBackStack(null); 
					transaction.commit();
					Log.e("count3","count3:"+fragmentManager.getBackStackEntryCount()+"");
				}
			});
		}
		
	}
	
	
	
	public void deleteAddress(final Address address){
		new CustomAsynTask(getActivity())
		{
			@Override
			protected Boolean doInBackground(Void... params)
			{
				// TODO Auto-generated method stub
				String[] keys = new String[]
				{ "username", "id" };

				String[] values = new String[]
				{ sp.getString("username", ""),
						address.getId().toString() };

				PostJson postJson = new PostJson();

				GetParamsMap = postJson.Post(keys, values,
						"deleteAddress");

				Boolean success = false;

				success = (Boolean) GetParamsMap.get("success");

				if (success)
				{

				}

				return success;
			}

			protected void onPostExecute(Boolean result)
			{
				super.onPostExecute(result);

				Toast.makeText(getActivity().getApplicationContext(),
						(String) GetParamsMap.get("msg"),
						Toast.LENGTH_LONG).show();
				updateList();

			}
		}.execute();
	}
}
