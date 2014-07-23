package com.pangu.neusoft.fsgat.visa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.pangu.neusoft.fsgat.core.PostJsonObject;
import com.pangu.neusoft.fsgat.core.StringMethods;
import com.pangu.neusoft.fsgat.model.Address;
import com.pangu.neusoft.fsgat.model.ApplyVisa;
import com.pangu.neusoft.fsgat.model.ListPass;
import com.pangu.neusoft.fsgat.model.Pass;






















import com.pangu.neusoft.fsgat.user.AddpassFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public  class ApplyVisaFragment extends Fragment {
	
	SharedPreferences sp;
	Editor editor;
	Spinner passselecter;
	
	boolean shipment=false;
	
	TableRow tableRow4;
	TableRow tableRow5;
	TableRow tableRow6;
	
	EditText editText1;
	EditText editText2;
	EditText editText3;
	EditText editText4;
	
	TextView hk;
	TextView macau;
	
	CheckBox checkBox1;
	CheckBox checkBox2;
	Button confirm;
	
	RadioButton radio0;
	RadioButton radio1;
	AsyncTask<Void, Void, Boolean> loading1;
	AsyncTask<Void, Void, Boolean> loading2;
	AsyncTask<Void, Void, Boolean> loading3;
	 @Override
	   	public void onPause(){
	       	if(loading1!=null){
	       		loading1.cancel(false);
	       		try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
	       	}
	       	if(loading2!=null){
	       		loading2.cancel(false);
	       		try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
	       	}
	       	if(loading3!=null){
	       		loading3.cancel(false);
	       		try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
	       	}
	       	super.onDestroyView();
	       }
	
	 @Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
		{
			// TODO Auto-generated method stub
			super.onCreateOptionsMenu(menu, inflater);
			
			Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
			
			actionbar_back_btn.setVisibility(View.VISIBLE);
			
			TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
			
			actionbar_title.setText("申请签注");
		}
	 
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	setHasOptionsMenu(true);
    	sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
//		editor.putString("username", "pangu");
//		editor.commit();
		
    	View view = inflater.inflate(R.layout.applyvisa_fragment, null);
//    	android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("申请签注");
    	this.getActivity().setTitle("申请签注");
    	ConnectivityManager cm=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo info=cm.getActiveNetworkInfo();
    	
    	if (CheckNetwork.connected(this)){
    	
		    	passselecter=(Spinner)view.findViewById(R.id.spinner1);
		    	getPass();//设置通行证
		    
		    	tableRow4=(TableRow)view.findViewById(R.id.tableRow4);
		    	tableRow5=(TableRow)view.findViewById(R.id.tableRow5);
		    	tableRow6=(TableRow)view.findViewById(R.id.tableRow6);
		    	
		    	editText1=(EditText)view.findViewById(R.id.editText1);
		    	editText2=(EditText)view.findViewById(R.id.editText2);
		    	editText3=(EditText)view.findViewById(R.id.editText3);
		    	editText4=(EditText)view.findViewById(R.id.editText4);
		    	
		    	editText1.setText(sp.getString("username", ""));
		    	
		    	hk=(TextView)view.findViewById(R.id.hk);
		    	macau=(TextView)view.findViewById(R.id.macau);
		    	
		    	macau.setVisibility(View.GONE);
		    	hk.setVisibility(View.GONE);
		    	
		    	checkBox1=(CheckBox)view.findViewById(R.id.checkBox1);
		    	checkBox2=(CheckBox)view.findViewById(R.id.checkBox2);
		    	confirm=(Button)view.findViewById(R.id.button1);
		    	
		    	radio0=(RadioButton)view.findViewById(R.id.radio0);
		    	radio1=(RadioButton)view.findViewById(R.id.radio1);
		    	radio0.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked){
							shipment=false;
							tableRow4.setVisibility(View.GONE);
							tableRow5.setVisibility(View.GONE);
							tableRow6.setVisibility(View.GONE);
						}
					}
		    		
		    	});
		   
		    	radio1.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked){
							shipment=true;
							tableRow4.setVisibility(View.VISIBLE);
							tableRow5.setVisibility(View.VISIBLE);
							tableRow6.setVisibility(View.VISIBLE);
							showAddressDialog();
						}
					}
		    		
		    	});
		    	
		    	checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked){
							showHKSelect();
						}else{
							hk.setText("");
							hk.setVisibility(View.GONE);
						}
					}
		    		
		    	});
		    	
		    	checkBox2.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if(isChecked){
							showMacauSelect();
						}else{
							macau.setText("");
							macau.setVisibility(View.GONE);
						}
					}
		    		
		    	});
		    	
		    	confirm.setOnClickListener(new OnClickListener(){
		
					@Override
					public void onClick(View v) {
						// 发送信息
						
						sendData();
					}
		    		
		    	});
    	  }
        return view;  
    }  
  
    //使用快递
    boolean express=false;
    HashMap<String, Object> GetParamsMap=new HashMap<String,Object>();
    //发送请求
    public void sendData(){
    	
    	String passnumbertext=passselecter.getSelectedItem().toString();
    	String[] passarray=passnumbertext.split("\\(");
    	final String passnumber=passarray[0];   	
    	final String phone=editText1.getText().toString();
    	//
    	if(radio0.isChecked()){
    		express=false;
    	}else{
    		express=true;
    	}
    	
    	final String receiver=editText2.getText().toString();
    	final String address=editText3.getText().toString();
    	final String postcode=editText4.getText().toString();
    	
    	
    	final List<String> qwdlist=new ArrayList<String>();
    	if(checkBox1.isChecked()){
    		qwdlist.add("HKG");
    	}
    	if(checkBox2.isChecked()){
    		qwdlist.add("MAC");
    	}
    	
    	final String[] qwd=new String[qwdlist.size()];
    	for(int i=0;i<qwd.length;i++){
    		qwd[i]=qwdlist.get(i).toString();
    	}
    	
    	final String HKGZJYXQ=hk.getText().toString().replace("香港:", "");
    	final String MACZJYXQ=macau.getText().toString().replace("澳门:", "");
    	
    	boolean currect=true;
    	if(passnumbertext.equals("")){
    		currect=false;
    		Toast.makeText(getActivity(), "请选择通行证", Toast.LENGTH_SHORT).show();;
    	}
    	if (!StringMethods.isMobileNO(phone)|| phone.length() != 11)
		{
    		currect=false;
			String msg = "请输入11位手机号码";
			editText1.setText("");
			editText1.setHint(msg);
			editText1.setHintTextColor(Color.RED);
			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
    	if(radio1.isChecked()){
    		if(receiver.equals("")){
    			currect=false;
    			String msg = "请输入收件人";
    			editText2.setText("");
    			editText2.setHint(msg);
    			editText2.setHintTextColor(Color.RED);
    			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
    		}if(address.equals("")){
    			currect=false;
    			String msg = "请输入收件地址";
    			editText3.setText("");
    			editText3.setHint(msg);
    			editText3.setHintTextColor(Color.RED);
    			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
    		}if(postcode.equals("")){
    			currect=false;
    			String msg = "请输入邮编";
    			editText3.setText("");
    			editText3.setHint(msg);
    			editText3.setHintTextColor(Color.RED);
    			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
    		}
    	}    	
    	if(!checkBox1.isChecked()&&!checkBox2.isChecked()){
    		currect=false;
    		Toast.makeText(getActivity(), "至少请选择一种签注类型", Toast.LENGTH_SHORT).show();
    	}
    	if(currect){
	    	loading1=new AsyncTask<Void, Void, Boolean>(){
			    @SuppressWarnings("deprecation")
				@Override  
		        protected void onPreExecute() {   
		            super.onPreExecute();   
		            try{
			    		getActivity().setProgressBarIndeterminateVisibility(true);// 执行前使进度条可见
			    	}catch(Exception ex){
			    		ex.printStackTrace();
			    	}// 执行前使进度条可见
		        }			
				@Override
				protected Boolean doInBackground(Void... params){
					//HKG、MAC
					String[] keys = new String[]
					{ "username", "passNumber", "phone", "express","receiver","address","postcode","qwd","HKGZJYXQ","MACZJYXQ"};
	
					Object[] values = new Object[]
					{ sp.getString("username", ""),passnumber,phone,express,receiver,address,postcode,qwd,HKGZJYXQ,MACZJYXQ};
	
					ApplyVisa apply=new ApplyVisa();
					apply.setAddress(address);					
					apply.setDatetime(getNowDateTime());
					apply.setExpress(express);
					apply.setHKGZJYXQ(HKGZJYXQ);
					apply.setMACZJYXQ(MACZJYXQ);
					apply.setPassNumber(passnumber);
					apply.setPhone(phone);
					apply.setPostcode(postcode);
					apply.setQwd(qwd);
					apply.setReceiver(receiver);
					apply.setUsername(sp.getString("username", ""));
					//记录下来用于快速申请
					
					
					PostJsonObject postJson = new PostJsonObject();
					
					GetParamsMap = postJson.Post(keys, values,"applyVisa");
					
					Boolean success = false;
					
					success = (Boolean) GetParamsMap.get("success");
					
					return success;
				}
				
				@SuppressWarnings("deprecation")
				@Override
				protected void onPostExecute(Boolean result){
					try{
						getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
					}catch(Exception ex){
						ex.printStackTrace();
					}
					super.onPostExecute(result);				
					if (GetParamsMap.get("msg")==null||GetParamsMap.get("msg").toString().length()>50)
					{
						Toast.makeText(getActivity(),"发送数据失败",	Toast.LENGTH_SHORT).show();
					}
					else {
						Toast.makeText(getActivity().getApplicationContext(),
								(String) GetParamsMap.get("msg"),
								Toast.LENGTH_SHORT).show();
						
						if(getFragmentManager().getBackStackEntryCount()>=1){
		        			int len = getFragmentManager().getBackStackEntryCount();
		        		    for (int i = 0; i < len; i++) {
		        		    	getFragmentManager().popBackStack();
		        		    }
		        		}
						FragmentTransaction transaction = getFragmentManager().beginTransaction(); 
			     		Fragment loginFragment = new YwblFragment();  
			            transaction.replace(R.id.content, loginFragment); 
			            transaction.commit();
					}
				}
				@SuppressWarnings("deprecation")
				@Override
				protected void onCancelled()
				{
					super.onCancelled();
					try{
						getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				
			};
			loading1.execute();
	    	
    	}
   
    }
    
    
  //获取当前时间
    @SuppressLint("SimpleDateFormat")
    public String getNowDateTime(){

    					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    					Date curDate = new Date(System.currentTimeMillis());
    					return formatter.format(curDate);
    				}	   
    
 //获得通行证列表
List<Pass> listpass=new ArrayList<Pass>();//通行证列表
public void getPass(){	  
		loading2=new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	            try{
		    		getActivity().setProgressBarIndeterminateVisibility(true);// 执行前使进度条可见
		    	}catch(Exception ex){
		    		ex.printStackTrace();
		    	}// 执行前使进度条可见
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				String[] keys = new String[]{ "username"};
				String[] values = new String[]{ sp.getString("username", "")};
				PostJson postJson = new PostJson();
				HashMap<String, Object>  GetParamsMap = postJson.Post(keys, values,"listPass","passList");
				
				JSONArray passList = (JSONArray)GetParamsMap.get("passList");
					if(passList!=null)	
						for (int i = 0; i < passList.length(); i++)
						{
							try	{
								JSONObject jo = (JSONObject) passList.get(i);
								Pass pass = new Pass();	
								pass.setPassNumber(jo.getString("passNumber"));
								pass.setSurName(jo.getString("surName"));
								pass.setGivenName(jo.getString("givenName"));
								pass.setArea(jo.getString("area"));
								pass.setDob(jo.getString("dob"));
								pass.setSex(jo.getString("sex"));
								pass.setPob(jo.getString("pob"));
								pass.setExpireDate(jo.getString("expireDate"));
								pass.setIssueArea(jo.getString("issueArea"));
								pass.setIssueDate(jo.getString("issueDate"));
								listpass.add(pass);
								
							} catch (JSONException e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
				Boolean success = (Boolean) GetParamsMap.get("success");
				return success;
			}
			
			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);
				try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
				if(result){
					if(listpass.size()>0){
						String[] mItems = new String[listpass.size()];
						int i=0;	
						for(Pass pass:listpass){
							mItems[i]=pass.getPassNumber()+"("+pass.getSurName()+pass.getGivenName()+")";
							i++;
						}
						try{
							ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.dropdowntext, mItems);
							//绑定 Adapter到控件
							adapter.setDropDownViewResource(R.layout.drop_down_item);
							passselecter.setAdapter(adapter);
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}else{
						showDialog();	
					}
				}else{					
					showDialog();
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			
		};
		loading2.execute();
  }
    
//显示没有通行证的提示
	public void showDialog(){
		 Dialog alertDialog = new AlertDialog.Builder(getActivity()). 
	                setTitle("提示"). 
	                setMessage("请先绑定通行证,如没有通行证,请预约办证"). 
	                setIcon(R.drawable.ic_launcher). 
	                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
	                    @Override 
	                    public void onClick(DialogInterface dialog, int which) { 
	                    	FragmentTransaction transaction = getFragmentManager().beginTransaction();
	       				 	AddpassFragment fragment=new AddpassFragment();
	                    	transaction.add(R.id.content, fragment);
	                    	transaction.addToBackStack(null);
	                        transaction.commit();
	                    } 
	                }). 
	                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
	                     
	                    @Override 
	                    public void onClick(DialogInterface dialog, int which) { 
	                        // TODO Auto-generated method stub
	                    	Toast.makeText(getActivity(), "没有通行证，不能申请签注！", Toast.LENGTH_SHORT).show();
	                    } 
	                }).
	                setNeutralButton("预约办证", new DialogInterface.OnClickListener() { 
	                     
	                    @Override 
	                    public void onClick(DialogInterface dialog, int which) { 
	                    	FragmentTransaction transaction = getFragmentManager().beginTransaction();
	       				 	BookingFragment fragment=new BookingFragment();
	                    	transaction.add(R.id.content, fragment);
	                    	transaction.addToBackStack(null);
	                        transaction.commit(); 
	                    } 
	                }). 
	                create(); 
	        alertDialog.show(); 
	}

//显示地址列表
	Map<String,Address> address_map=new LinkedHashMap<String,Address>();
	
	
	private int selectedAddressIndex = 0;
	
	public void showAddressDialog(){
		selectedAddressIndex=0;
		loading3=new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	            try{
		    		getActivity().setProgressBarIndeterminateVisibility(true);// 执行前使进度条可见
		    	}catch(Exception ex){
		    		ex.printStackTrace();
		    	}// 执行前使进度条可见
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				String[] keys = new String[]{ "username"};
				String[] values = new String[]{ sp.getString("username", "")};
				PostJson postJson = new PostJson();
				HashMap<String, Object>  GetParamsMap = postJson.Post(keys, values,"listAddress","addressList");
				JSONArray addressList = (JSONArray)GetParamsMap.get("addressList");
					if(addressList!=null)	
						for (int i = 0; i < addressList.length(); i++)
						{
							try	{
								JSONObject jo = (JSONObject) addressList.get(i);
								Address address = new Address();	
								address.setAddress(jo.getString("address"));
								address.setReceiver(jo.getString("receiver"));
								address.setPostcode(jo.getString("postcode"));
								address.setId(""+(i+1));								
								address_map.put(""+(i+1), address);
								
							} catch (JSONException e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
				Boolean success = (Boolean) GetParamsMap.get("success");
				return success;
			}
			
			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);
				try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
				if(result){
					if(address_map.size()>0){
						final String[] mItems = new String[address_map.size()];
						int i=0;	
						for(String key : address_map.keySet()){
							Address address=address_map.get(key);
							//mItems[i]=address.getId()+":"+address.getAddress()+"("+address.getReceiver()+")";
							mItems[i]=address.getAddress()+"("+address.getReceiver()+")";
							i++;
						}
						

						 	Dialog alertDialog = new AlertDialog.Builder(getActivity()).
						    setTitle("请选择已经添加的地址？").
						    setIcon(R.drawable.ic_launcher)
						    .setSingleChoiceItems(mItems, 0, new DialogInterface.OnClickListener() {
						 
						     @Override
						     public void onClick(DialogInterface dialog, int which) {
						    	 selectedAddressIndex = which;
						     }
						    }).
						    setPositiveButton("确认", new DialogInterface.OnClickListener() {

						     @Override
						     public void onClick(DialogInterface dialog, int which) {
						    	 //Toast.makeText(getActivity(), mItems[selectedFruitIndex], Toast.LENGTH_SHORT).show();
						    	 
						    	 Address address=address_map.get(""+(selectedAddressIndex+1));
						    	 editText2.setText(address.getReceiver());
						    	 editText3.setText(address.getAddress());
						    	 editText4.setText(address.getPostcode());
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
					}else{
						Toast.makeText(getActivity(), "没有地址数据！请输入收件人信息",Toast.LENGTH_SHORT).show();
					}
				}else{					
					Toast.makeText(getActivity(), "没有地址数据！请输入收件人信息",Toast.LENGTH_SHORT).show();
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
			
		};
		loading3.execute(); 
	}
	
	//显示HK可选类型
	private int selectedHKVisaIndex=0;
	public void showHKSelect(){
		selectedHKVisaIndex=0;
 		final String[] visas = new String[] { "三个月一次有效", "三个月二次有效", "一年一次有效", "一年二次有效" };
 		
 			Dialog alertDialog = new AlertDialog.Builder(getActivity()).				
			    setTitle("请选择香港签注类型？").
			    setIcon(R.drawable.ic_launcher)
			    .setSingleChoiceItems(visas, 0, new DialogInterface.OnClickListener() {
			 
			     @Override
			     public void onClick(DialogInterface dialog, int which) {
			    	 selectedHKVisaIndex = which;
			     }
			    }).
			    setPositiveButton("确认", new DialogInterface.OnClickListener() {

			     @Override
			     public void onClick(DialogInterface dialog, int which) {
			    	// Toast.makeText(getActivity(), visas[selectedFruitIndex], Toast.LENGTH_SHORT).show();
			    	 hk.setText("香港:"+visas[selectedHKVisaIndex]);
			    	 hk.setVisibility(View.VISIBLE);
			     }
			    }).
			    setNegativeButton("取消", new DialogInterface.OnClickListener() {

			     @Override
			     public void onClick(DialogInterface dialog, int which) {
			      // TODO Auto-generated method stub
			    	 if(hk.getText().equals("")){
			    		 checkBox1.setChecked(false);
			    		 hk.setVisibility(View.GONE);
			    	 }
			     }
			    }).
			    create();
			  alertDialog.show();
	}
	//显示Macau可选类型
	private int selectedMacauVisaIndex=0;
	public void showMacauSelect(){
		selectedMacauVisaIndex=0;
 		final String[] visas = new String[] { "三个月一次有效", "一年一次有效" };
 		
 			Dialog alertDialog = new AlertDialog.Builder(getActivity()).				
			    setTitle("请选择澳门签注类型？").
			    setIcon(R.drawable.ic_launcher)
			    .setSingleChoiceItems(visas, 0, new DialogInterface.OnClickListener() {
			 
			     @Override
			     public void onClick(DialogInterface dialog, int which) {
			    	 selectedMacauVisaIndex = which;
			     }
			    }).
			    setPositiveButton("确认", new DialogInterface.OnClickListener() {

			     @Override
			     public void onClick(DialogInterface dialog, int which) {
			    	// Toast.makeText(getActivity(), visas[selectedFruitIndex], Toast.LENGTH_SHORT).show();
			    	 macau.setText("澳门:"+visas[selectedMacauVisaIndex]);
			    	 macau.setVisibility(View.VISIBLE);
			     }
			    }).
			    setNegativeButton("取消", new DialogInterface.OnClickListener() {

			     @Override
			     public void onClick(DialogInterface dialog, int which) {
			      // TODO Auto-generated method stub
			    	 if(macau.getText().equals("")){
			    		 checkBox2.setChecked(false);
			    		 macau.setVisibility(View.GONE);
			    	 }
			     }
			    }).
			    create();
			  alertDialog.show();
	}
    
}  