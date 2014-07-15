package com.pangu.neusoft.fsgat.visa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
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










import com.pangu.neusoft.fsgat.user.LoginFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public  class BookingFragment extends Fragment {
	
	
	private AQuery aq;
	  
	SharedPreferences sp;
	Editor editor;
	Spinner placeselecter;
	Spinner selectTime;
	AsyncTask<Void, Void, Boolean> loading1;
	AsyncTask<Void, Void, Boolean> loading2;
	//获得地址列表
	Map<String,String> obj=new LinkedHashMap<String,String>();
	
	boolean shipment=false;
	
	TableRow tableRow4;
	TableRow tableRow5;
	TableRow tableRow6;
	
	EditText editText1;
	EditText editText2;
	EditText editText3;
	EditText editText4;
	
	EditText editText5;
	EditText editText6;
	EditText editText7;
	EditText editText8;
	EditText editText9;
	
	
	Spinner choosedatetxt;
	
	
	EditText editText10;
	
	
	
	
	
	TextView hk;
	TextView macau;
	
	CheckBox checkBox1;
	CheckBox checkBox2;
	Button confirm;
	
	RadioButton radio0;
	RadioButton radio1;
	RadioButton radio3;
	RadioButton radio4;
	
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
	       	
	       	super.onDestroyView();
	       }
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
//		editor.putString("username", "pangu");
//		editor.commit();
		
    	View view = inflater.inflate(R.layout.booking_fragment, null);
    	aq = new AQuery(getActivity(), view);
    	
    	this.getActivity().setTitle("预约办证");
    	if (CheckNetwork.connected(this)){
		    	placeselecter=(Spinner)view.findViewById(R.id.spinner1);
		    	selectTime=(Spinner)view.findViewById(R.id.spinner2);
		    	
		    	getPlace();//设置受理地址
		    	setSelectTime();//设置选择时间
		    	
		    	Calendar calendar = Calendar.getInstance();    	
		    	choosedatetxt=(Spinner)view.findViewById(R.id.spinner3);
		    	
		    	
		
		    	
		    	
		    	
		    	tableRow4=(TableRow)view.findViewById(R.id.tableRow4);
		    	tableRow5=(TableRow)view.findViewById(R.id.tableRow5);
		    	tableRow6=(TableRow)view.findViewById(R.id.tableRow6);
		    	
		    	editText1=(EditText)view.findViewById(R.id.editText1);
		    	editText2=(EditText)view.findViewById(R.id.editText2);
		    	editText3=(EditText)view.findViewById(R.id.editText3);
		    	editText4=(EditText)view.findViewById(R.id.editText4);
		    	editText5=(EditText)view.findViewById(R.id.editText5);
		    	editText6=(EditText)view.findViewById(R.id.editText6);
		    	editText7=(EditText)view.findViewById(R.id.editText7);
		    	editText8=(EditText)view.findViewById(R.id.editText8);
		    	editText9=(EditText)view.findViewById(R.id.editText9);
		    	editText10=(EditText)view.findViewById(R.id.editText10);
		    	editText10.setText(sp.getString("username", ""));
		    	
		    	hk=(TextView)view.findViewById(R.id.hk);
		    	macau=(TextView)view.findViewById(R.id.macau);
		    	
		    	macau.setVisibility(View.GONE);
		    	hk.setVisibility(View.GONE);
		    	
		    	checkBox1=(CheckBox)view.findViewById(R.id.checkBox1);
		    	checkBox2=(CheckBox)view.findViewById(R.id.checkBox2);
		    	confirm=(Button)view.findViewById(R.id.button1);
		    	
		    	radio0=(RadioButton)view.findViewById(R.id.radio0);
		    	radio1=(RadioButton)view.findViewById(R.id.radio1);
		    	radio3=(RadioButton)view.findViewById(R.id.radio3);
		    	radio4=(RadioButton)view.findViewById(R.id.radio4);
		    	
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
  
//ax 获得所有日期和时间
    Map<String,List<String>> ax=new LinkedHashMap<String,List<String>>(); 

public void asyncJson(String id){        
        //perform a Google search in just a few lines of code        
		String url = getActivity().getResources().getString(R.string.getdatetime);
        Map<String, String> params = new HashMap<String, String>();  
        params.put("dwdm", id);
        
        
        
        aq.ajax(url, params,String.class, new AjaxCallback<String>() {

                @Override
                public void callback(String url, String html, AjaxStatus status) {
                        
                        
                        if(html != null){
                        	
                        	
                        	String[] datetimes = html.split("\\|\\|");
                        	
                        	for(String datetime : datetimes){
                        		String[] datetimeinfo=datetime.split(",");
                        		if(ax.get(datetimeinfo[0])==null){
                        			List<String> newlist=new LinkedList<String>();
                        			ax.put(datetimeinfo[0],newlist);
                        		}
                        		
                        		List<String> timer=ax.get(datetimeinfo[0]);
                        		timer.add(datetimeinfo[1]);
                        		ax.put(datetimeinfo[0],timer);
                        	}
                        	
                        	String[] allday=new String[ax.size()];
                        	int i=0;
                        	for(String key:ax.keySet()){
                        		allday[i]=key;
                        		i++;
                        	}
                        	
                        	ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.dropdowntext, allday);
                        	adapter.setDropDownViewResource(R.layout.drop_down_item);
                        	choosedatetxt.setAdapter(adapter);
                        	
                        	selttime();
                        	
                        	choosedatetxt.setOnItemSelectedListener(new OnItemSelectedListener(){								@Override
								public void onItemSelected(
										AdapterView<?> parent, View view,
										int position, long id) {
                        			selttime();
								}
								@Override
								public void onNothingSelected(
										AdapterView<?> parent) {
									// TODO Auto-generated method stub
									
								}
                        	});
                        	
                        	
                            
                        
                        }else{
                                
                                //ajax error, show error code
                                Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
                        }
                }
        });
        
}
	
public void selttime(){
	String dayselected=choosedatetxt.getSelectedItem().toString();
	List<String> times=ax.get(dayselected);
	if(times!=null){
		String[] alltimes=new String[times.size()];
		int j=0;
		for(String time:times){
			alltimes[j]=time;
			j++;
		}
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.dropdowntext, alltimes);
		adapter.setDropDownViewResource(R.layout.drop_down_item);
		selectTime.setAdapter(adapter);
	}
}

    //使用快递
    boolean express=false;
    String sex="男";
    HashMap<String, Object> GetParamsMap=new HashMap<String,Object>();
    //发送请求
    public void sendData(){
    	
    	String selectplace=placeselecter.getSelectedItem().toString();
    	final String SLDW=obj.get(selectplace);   	
    	
    	final String idNumber=editText1.getText().toString();
    	final String phone=editText10.getText().toString();
    	
    	//
    	if(radio0.isChecked()){
    		express=false;
    	}else{
    		express=true;
    	}
    	if(radio3.isChecked()){
    		sex="男";
    	}else{
    		sex="女";
    	}
    	
    	final String receiver=editText2.getText().toString();
    	final String address=editText3.getText().toString();
    	final String postcode=editText4.getText().toString();
    	
    	String bookingdate=choosedatetxt.getSelectedItem().toString();
    	String choosetime=selectTime.getSelectedItem().toString();
    	final String bookingtime=bookingdate+" "+choosetime;
    	
    	
    	final String surName=editText5.getText().toString();
    	final String givenName=editText6.getText().toString();
    	final String residentCity=editText7.getText().toString();
    	final String contactPeople=editText8.getText().toString();
    	final String contactPhone=editText9.getText().toString();
    	
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
    	
    	if (!StringMethods.isMobileNO(phone)|| phone.length() != 11){
    		currect=false;
			String msg = "请输入11位手机号码";
			editText10.setText("");
			editText10.setHint(msg);
			editText10.setHintTextColor(Color.RED);
			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
    	if (!StringMethods.isMobileNO(contactPhone)|| contactPhone.length() != 11){
    		currect=false;
			String msg = "请输入11位手机号码";
			editText9.setText("");
			editText9.setHint(msg);
			editText9.setHintTextColor(Color.RED);
			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
    	if (idNumber.length()!=18){
    		currect=false;
			String msg = "请输入18位身份证号码";
			editText1.setText("");
			editText1.setHint(msg);
			editText1.setHintTextColor(Color.RED);
			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
    	if (bookingdate.equals("")){
    		currect=false;
			String msg = "请选择预约日期";
			Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
		if (choosetime.equals("")){
    		currect=false;
			String msg = "请选择预约时间";
			Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
		if (surName.equals("")){
    		currect=false;
			String msg = "请输入姓";
			editText5.setText("");
			editText5.setHint(msg);
			editText5.setHintTextColor(Color.RED);
			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
		if (givenName.equals("")){
    		currect=false;
			String msg = "请输入名";
			editText6.setText("");
			editText6.setHint(msg);
			editText6.setHintTextColor(Color.RED);
			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
		if (residentCity.equals("")){
    		currect=false;
			String msg = "请输入户口所在地";
			editText7.setText("");
			editText7.setHint(msg);
			editText7.setHintTextColor(Color.RED);
			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
		if (contactPeople.equals("")){
    		currect=false;
			String msg = "请输入紧急联系人";
			editText8.setText("");
			editText8.setHint(msg);
			editText8.setHintTextColor(Color.RED);
			//Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT).show();
		}
		if (contactPhone.equals("")){
    		currect=false;
			String msg = "请输入紧急联系电话";
			editText9.setText("");
			editText9.setHint(msg);
			editText9.setHintTextColor(Color.RED);
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
					//HKG、MAC
					String[] keys = new String[]
					{ "username", "SLDW", "idNumber","bookingtime","surName","givenName","sex","residentCity","contactPeople","contactPhone","phone", "express","receiver","address","postcode","qwd","HKGZJYXQ","MACZJYXQ"};
	
					Object[] values = new Object[]
					{ sp.getString("username", ""),SLDW, idNumber,bookingtime,surName,givenName,sex,residentCity,contactPeople,contactPhone,phone,express,receiver,address,postcode,qwd,HKGZJYXQ,MACZJYXQ};
	
					
					PostJsonObject postJson = new PostJsonObject();					
					GetParamsMap = postJson.Post(keys, values,"applyBooking");					
					Boolean success = false;					
					success = (Boolean) GetParamsMap.get("success");					
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
			loading2.execute();
	    	
    	}
   
    }
    
    
  //获取当前时间
    @SuppressLint("SimpleDateFormat")
    public String getNowDateTime(){

    					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    					Date curDate = new Date(System.currentTimeMillis());
    					return formatter.format(curDate);
    				}	   
    

public void getPlace(){	
		
	obj.put("佛山市公安局出入境管理支队","440600000000");
	obj.put("佛山市公安局禅城分局出入境管理大队","440604000000");
	obj.put("佛山市公安局南海分局出入境管理大队","440605000000");	
	obj.put("佛山市公安局三水分局出入境管理大队","440607000000");
	obj.put("佛山市公安局高明分局出入境管理大队","440608000000");	
	obj.put("南海大沥前移受理点(只受理港澳通行证)","440605000001");
	obj.put("南海西樵前移受理点(只受理港澳通行证)","440605000002");
	obj.put("南海狮山前移受理点(只受理港澳通行证)","440605000003");
	obj.put("南海里水前移受理点(只受理港澳通行证)","440605000004");
	obj.put("南海九江前移受理点(只受理港澳通行证)","440605000005");
	obj.put("南海丹灶前移受理点(只受理港澳通行证)","440605000006");
	obj.put("南海罗村前移受理点(只受理港澳通行证)","440605000007");
	
	String[] mItems = new String[obj.size()];
	int i=0;
	for(String key:obj.keySet()){
		mItems[i]=key;
		i++;
	}
	
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.dropdowntext, mItems);
	//绑定 Adapter到控件
	adapter.setDropDownViewResource(R.layout.drop_down_item);
	placeselecter.setAdapter(adapter);

	placeselecter.setOnItemSelectedListener(new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			setSelectTime();
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}

		
	});
	
	
}

public void setSelectTime(){
	ax.clear();
	String placeid=obj.get(placeselecter.getSelectedItem().toString());
	asyncJson(placeid);
	
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
	       				 	YwblFragment fragment=new YwblFragment();
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
	       				 	YwblFragment fragment=new YwblFragment();
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
		loading1.execute(); 
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