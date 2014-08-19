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
import com.pangu.neusoft.fsgat.model.ListPass;
import com.pangu.neusoft.fsgat.model.ListPassAdapter;
import com.pangu.neusoft.fsgat.model.Pass;

import android.annotation.SuppressLint;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

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

	View rootview;
	Dialog alertDialog;
	
	private void init()
	{
		
		listpass_list = (ListView) rootview.findViewById(
				R.id.listpass_list);
		
		listpass_addpass_btn = (Button)rootview.findViewById(R.id.list_pass_addpass_btn);
		
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
		
		actionbar_title.setText("证件管理");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		
		rootview = inflater.inflate(R.layout.activity_list_pass, null);
		
		init();
		
		return rootview;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (CheckNetwork.connected(this)){
			
	
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
							
							pass.setSurName(jo.getString("surName").toString());
							
							pass.setGivenName(jo.getString("givenName").toString());
							
							pass.setDob(jo.getString("dob").toString());
							
							pass.setIssueDate(jo.getString("issueDate").toString());
							
							pass.setExpireDate(jo.getString("expireDate").toString());
							
							String sexcode = jo.getString("sex").toString();
							
							if (sexcode.equals("1"))
							{
								pass.setSex("男");
								
							}
							else {
								pass.setSex("女");
							}
							
							pass.setPob(jo.getString("pob").toString());
							
							pass.setIssueArea(jo.getString("issueArea").toString());
							
							
							
							String areacode = jo.getString("area").toString();
							
							if (areacode.equals("07"))
							{
								pass.setArea("佛山市（不包括顺德）");
							}
							else {
								pass.setArea("佛山市顺德区");
							}
							
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
					
				}
			}.execute();
	
			listpass_list.setOnItemClickListener(new OnItemClickListener()
			{
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3)
				{
					// TODO Auto-generated method stub
					final int p=arg2;
					ListPass LP = (ListPass)ListPass.getListpass();
					final Pass pass = LP.get(p);
					
    				alertDialog = new AlertDialog.Builder(getActivity()). 
			                setTitle("证件详情"). 
			                setMessage("通行证号："+pass.getPassNumber()+"\n"
			                			+"姓名："+pass.getSurName()+pass.getGivenName()+"\n"
			                			+"出生日期："+pass.getDob()+"\n"
			                			+"签发日期："+pass.getIssueDate()+"\n"
			                			+"有效期至："+pass.getExpireDate()+"\n"
			                			+"性别："+pass.getSex()+"\n"
			                			+"出生地："+pass.getPob()+"\n"
			                			+"签发地："+pass.getIssueArea()+"\n"
			                			+"户口所在地："+pass.getArea()). 
			                setIcon(R.drawable.ic_launcher).
			                setNegativeButton("确定", new DialogInterface.OnClickListener() { 
			                     
			                    @Override 
			                    public void onClick(DialogInterface dialog, int which) { 
			                        // TODO Auto-generated method stub
			                    	alertDialog.dismiss();
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
	    			                    	deletePass(pass);			    			        				
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
			                create(); 
			        alertDialog.show(); 
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
	
	public void deletePass(final Pass pass){
		new CustomAsynTask(getActivity()) {
			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO Auto-generated method stub
				String[] keys = new String[] { "username", "passNumber" };
				
				
				String[] values = new String[] {
						sp.getString("username", ""),
						pass.getPassNumber().toString() };

				PostJson postJson = new PostJson();

				GetParamsMap = postJson
						.Post(keys, values, "deletePass");

				Boolean success = false;

				success = (Boolean) GetParamsMap.get("success");

				

				return success;
			}

			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);

				Toast.makeText(getActivity().getApplicationContext(),
						(String) GetParamsMap.get("msg"),
						Toast.LENGTH_LONG).show();
				
				if (result)
				{
					transaction.replace(R.id.content, new ListPassFragment());
					transaction.commit();
				}
				
			   
			}
		}.execute();
	}
}
