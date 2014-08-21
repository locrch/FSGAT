package com.fspangu.fsgat.ticket;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Entities.EscapeMode;

import com.baidu.platform.comapi.map.l;
import com.fspangu.fsgat.GrzxFragment;
import com.fspangu.fsgat.R;
import com.fspangu.fsgat.R.color;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckLogin;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJSONfromGson;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.model.Address;
import com.pangu.neusoft.fsgat.model.ConfirmInfo;
import com.pangu.neusoft.fsgat.model.ListDepartureTime;
import com.pangu.neusoft.fsgat.model.ListReturnUpstation;
import com.pangu.neusoft.fsgat.model.ListSeatNumber;
import com.pangu.neusoft.fsgat.model.ListTicketLine;
import com.pangu.neusoft.fsgat.model.ListdownStation;
import com.pangu.neusoft.fsgat.model.PostbuyTicket;
import com.pangu.neusoft.fsgat.model.PostgetDepartureTime;
import com.pangu.neusoft.fsgat.model.PostgetReturnUpstation;
import com.pangu.neusoft.fsgat.model.PostgetSeatNumber;
import com.pangu.neusoft.fsgat.model.PutReturnseatNumbersList;
import com.pangu.neusoft.fsgat.model.PutseatNumbersList;
import com.pangu.neusoft.fsgat.model.ReturnDownStation;
import com.pangu.neusoft.fsgat.model.ReturnUpStation;
import com.pangu.neusoft.fsgat.model.departureTime;
import com.pangu.neusoft.fsgat.model.downStation;
import com.pangu.neusoft.fsgat.model.ListAddress;
import com.pangu.neusoft.fsgat.model.ListupStation;
import com.pangu.neusoft.fsgat.model.Upstation;
import com.pangu.neusoft.fsgat.model.PostgetDownStation;
import com.pangu.neusoft.fsgat.model.seatNumber;
import com.pangu.neusoft.fsgat.model.user;
import com.pangu.neusoft.fsgat.user.LoginFragment;

import android.R.array;
import android.R.fraction;
import android.R.integer;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.pangu.neusoft.fsgat.core.CheckLogin;

public class TicketMainFragment extends Fragment {
	RadioGroup choose_place, choose_company, choose_monoway, sendway;
	RadioButton place_hongkong, place_macao, company_yd, company_zgt,
			monoway_single, monoway_double, sendway_onself, sendway_mail;
	Spinner way_spinner,upplace_spinner, downplace_spinner, uptime_time,back_upplace,back_downplace,back_uptime_time;
	EditText contact, tellphone, address, zipcode,back_uptime_update,uptime_update;
	Button booking_btn;
	RelativeLayout selection_layout, ticket_layout;
	LinearLayout mail_layout, fragment_layout;
	TextView selection,back_selection;
	NumberPicker zgt_ticket_count_np;
	
	EditText custom_np_num;
	Button custom_np_down,custom_np_up;
	private ProgressDialog mProgressDialog;
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	TicketBackFragment tb;
	Fragment thisfragment;
	
	Integer wayID,returnwayID,busCompanyID,upStationID,downStationID,ticketLineID,goDepartureTimeID;
	Integer returnupwayID,returndownwayID,returnbusCompanyID,returnupStationID,returndownStationID,returnticketLineID,returnDepartureTimeID;
	
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	Map<String, Object> adapterListMap;
	List<HashMap<String, Object>> adapterList;
	
	
	
	Upstation upstation;
	ListupStation listupStation;
	ArrayList<String> listupStationName;
	ArrayList<Integer> listupStationID;
	
	downStation downstation;
	ArrayList<String> listdownStationName;
	ArrayList<Integer> listdownStationID;
	
	ArrayList<String> listdepartureTimeName;
	ArrayList<Integer> listdepartureTimeID;
	departureTime departureTime;
	ListDepartureTime listDepartureTime;
	
	ListSeatNumber listSeatNumber;
	
	
	ArrayList<String> listReturnUpStationName;
	ArrayList<Integer> listReturnUpStationID;
	ReturnUpStation returnUpStation;
	
	ArrayList<String> listReturnDownStationName;
	ArrayList<Integer> listReturnDownStationID;
	ArrayList<Integer> listReturndepartureTimeID;
	ReturnDownStation returnDownStation;
	
	String[] way_hongkong,way_macao;
	
	SharedPreferences sp;
	Editor editor;
	ArrayAdapter way_adapter,adapter_upplace,adapter_downplace,adapter_uptimetime,adapter_returnupplace,adapter_returndownplace,adapter_returnuptimetime;

	FragmentSelectSeat fss;
	TicketBookingConfirmFragment tbcf;
	static ArrayList<seatNumber> seatNumbersList;
	static ArrayList<seatNumber> ReturnseatNumbersList;
	
	
	static PostbuyTicket postbuyTicket;
	ListTicketLine listTicketLine;
	float oneWayPrice,doubleWayPrice;
	
	Integer zgt_ticket_count = 1;
	float conf_allprice = 0;
	String update_mindate_string;
	long update_mindate_long;
	
	DatePickerDialog  dpd_uptime_update,dpd_back_uptime_update;
	Calendar calendar;
	
	private void init() {
		// TODO Auto-generated method stub
		mProgressDialog = new ProgressDialog(getActivity());   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);   
		
		this.getActivity().setTitle("车票预订");
		adapter_uptimetime = new ArrayAdapter<ArrayList<String>>(getActivity(), R.layout.simple_spinner_item);
		choose_place = (RadioGroup) getActivity().findViewById(
				R.id.ticket_main_choose_place);
		choose_company = (RadioGroup) getActivity().findViewById(
				R.id.ticket_main_choose_company);
		choose_monoway = (RadioGroup) getActivity().findViewById(
				R.id.ticket_main_choose_monoway);
		sendway = (RadioGroup) getActivity().findViewById(
				R.id.ticket_main_sendway);
		place_hongkong = (RadioButton) getActivity().findViewById(
				R.id.place_hongkong);
		place_macao = (RadioButton) getActivity()
				.findViewById(R.id.place_macao);
		company_yd = (RadioButton) getActivity().findViewById(R.id.company_yd);
		company_zgt = (RadioButton) getActivity()
				.findViewById(R.id.company_zgt);
		monoway_single = (RadioButton) getActivity().findViewById(
				R.id.monoway_single);
		monoway_double = (RadioButton) getActivity().findViewById(
				R.id.monoway_double);
		sendway_onself = (RadioButton) getActivity().findViewById(
				R.id.sendway_onself);
		sendway_mail = (RadioButton) getActivity().findViewById(
				R.id.sendway_mail);
		way_spinner = (Spinner) getActivity().findViewById(
				R.id.ticket_main_way_spinner);
		upplace_spinner = (Spinner) getActivity().findViewById(
				R.id.ticket_main_upplace_spinner);
		downplace_spinner = (Spinner) getActivity().findViewById(
				R.id.ticket_main_downplace_spinner);
		uptime_time = (Spinner) getActivity().findViewById(
				R.id.ticket_main_uptime_uptime);
		back_upplace = (Spinner) getActivity().findViewById(
				R.id.ticket_main_back_upplace_spinner);
		back_downplace = (Spinner) getActivity().findViewById(
				R.id.ticket_main_back_downplace_spinner);
		back_uptime_time = (Spinner) getActivity().findViewById(
				R.id.ticket_main_back_uptime_time_spinner);
		contact = (EditText) getActivity().findViewById(
				R.id.ticket_main_contact);
		tellphone = (EditText) getActivity().findViewById(
				R.id.ticket_main_tellphone);
		address = (EditText) getActivity().findViewById(
				R.id.ticket_main_mail_address);
		zipcode = (EditText) getActivity().findViewById(
				R.id.ticket_main_mail_zipcode);
		back_uptime_update = (EditText)getActivity().findViewById(R.id.ticket_main_back_uptime_update);
		uptime_update = (EditText)getActivity().findViewById(R.id.ticket_main_uptime_update);
		
		booking_btn = (Button)getActivity().findViewById(R.id.ticket_main_booking_btn);
		
		zgt_ticket_count_np = (NumberPicker)getActivity().findViewById(R.id.ticket_main_ticket_np);
		
		fragment_layout = (LinearLayout) getActivity().findViewById(
				R.id.ticket_main_back_fragment_layout);
		selection_layout = (RelativeLayout) getActivity().findViewById(
				R.id.ticket_main_selection_layout);
		ticket_layout = (RelativeLayout) getActivity().findViewById(
				R.id.ticket_main_ticket_layout);
		mail_layout = (LinearLayout) getActivity().findViewById(
				R.id.ticket_main_mail_layout);
		selection = (TextView) getActivity().findViewById(
				R.id.ticket_main_selection);
		back_selection = (TextView)getActivity().findViewById(R.id.ticket_main_back_selection);
		
		custom_np_num = (EditText)getActivity().findViewById(R.id.custom_np_num);
		custom_np_down = (Button)getActivity().findViewById(R.id.custom_np_down);
		custom_np_up = (Button)getActivity().findViewById(R.id.custom_np_up);
		
		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();


		way_hongkong=new String[]{"佛山→香港","香港→佛山"};
		
		way_macao=new String[]{"佛山→澳门","澳门→佛山"};
		
		
		listupStation = new ListupStation();

		adapterListMap = new HashMap<String, Object>();

		adapterList = new ArrayList<HashMap<String, Object>>();

		listupStationName = new ArrayList<String>();
		
		listupStationID = new ArrayList<Integer>();
		
		listdownStationID = new ArrayList<Integer>();
		
		fss = new FragmentSelectSeat();
		
		tbcf = new TicketBookingConfirmFragment();
		
		postbuyTicket = new PostbuyTicket();
		
		thisfragment = this;
		
		sp = getActivity()
				.getSharedPreferences(
						"sp",
						Context.MODE_PRIVATE);
		editor = sp.edit();
		
		calendar = Calendar.getInstance();
		
		
		DatePickerDialog.OnDateSetListener dateListener_uptime_update = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month,
					int dayOfMonth)
			{
				
				update_mindate_string = String.valueOf(year)+String.valueOf(month)+String.valueOf(dayOfMonth);
				uptime_update
						.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
			}
		};
		DatePickerDialog.OnDateSetListener dateListener_back_uptime_update = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month,
					int dayOfMonth)
			{

				back_uptime_update
						.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
			}
		};
		
		
		
		dpd_uptime_update = new DatePickerDialog(getActivity(), dateListener_uptime_update,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)+3);
		dpd_uptime_update.getDatePicker().setMinDate(calendar.getTime().getTime()+259200000);
		dpd_back_uptime_update = new DatePickerDialog(getActivity(), dateListener_back_uptime_update,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH)+3);
		
		if (!back_uptime_update.getText().toString().equals(""))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
			  
			  try
			{
				update_mindate_long = sdf.parse(update_mindate_string).getTime();
			} catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//毫秒
			  dpd_back_uptime_update.getDatePicker().setMinDate(update_mindate_long);
		}
		else {
			dpd_back_uptime_update.getDatePicker().setMinDate(calendar.getTime().getTime()+259200000);
		}
		
		
		
		zgt_ticket_count_np.setMaxValue(10);
		zgt_ticket_count_np.setMinValue(1);
		
	}

	Map<String,Address> address_map=new LinkedHashMap<String,Address>();
	private int selectedAddressIndex = 0;
	AsyncTask<Void, Void, Boolean> loading3;
	public void showAddressDialog(){
		selectedAddressIndex=0;
		loading3=new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	            try{
	            	mProgressDialog.show();
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

					if(mProgressDialog.isShowing()){
						mProgressDialog.dismiss();
					}
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
						    	 
						    	 Address getaddress=address_map.get(""+(selectedAddressIndex+1));
						    	 contact.setText(getaddress.getReceiver());
						    	 address.setText(getaddress.getAddress());
						    	 zipcode.setText(getaddress.getPostcode());
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

					if(mProgressDialog.isShowing()){
						mProgressDialog.dismiss();
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
			
		};
		loading3.execute(); 
	}
	@Override
	public void onDestroy(){

		try{
			if(mProgressDialog.isShowing()){
				mProgressDialog.dismiss();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		super.onDestroy();
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("车票预订");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_ticket_main, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if (CheckNetwork.connected(this)){
		
			
			busCompanyID = 1;
			
			init();
			
			
			way_spinner.getBackground().setAlpha(100);
			upplace_spinner.getBackground().setAlpha(100);
			downplace_spinner.getBackground().setAlpha(100);
			uptime_time.getBackground().setAlpha(100);
			back_upplace.getBackground().setAlpha(100);
			back_downplace.getBackground().setAlpha(100);
			back_uptime_time.getBackground().setAlpha(100);
			
			
			way_adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.simple_spinner_item,way_hongkong);	
			way_spinner.setAdapter(way_adapter);
			
		place_hongkong.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// TODO Auto-generated method stub
					if (isChecked)
					{
						way_adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.simple_spinner_item,way_hongkong);	
						way_spinner.setAdapter(way_adapter);
						
						if (company_yd.isChecked())
						{
							/*ticket_layout.setVisibility(View.GONE);
							selection.setVisibility(View.VISIBLE);
							*/
							ticket_layout.setVisibility(View.VISIBLE);
							selection.setVisibility(View.GONE);
							if (monoway_double.isChecked())
							{
								/*back_selection.setVisibility(View.VISIBLE);*/
								back_selection.setVisibility(View.GONE);
							}
						}
					}
					
			}
		});
			
		place_macao.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked)
				{	
					way_adapter=new ArrayAdapter(getActivity().getApplicationContext(),R.layout.simple_spinner_item,way_macao);	
					way_spinner.setAdapter(way_adapter);
						
					
					back_selection.setVisibility(View.GONE);
					selection.setVisibility(View.GONE);
					ticket_layout.setVisibility(View.VISIBLE);
				}
					
			}
		});
		
		
		company_zgt.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					ticket_layout.setVisibility(View.VISIBLE);
					busCompanyID = 2;
					
					back_selection.setVisibility(View.GONE);
					selection.setVisibility(View.GONE);
					ticket_layout.setVisibility(View.VISIBLE);
				
				new CustomAsynTask(getActivity(),thisfragment)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						// TODO Auto-generated method stub
						
						String[] keys = new String[]
						{ "ticketDirectionID","busCompanyID"};

						
						if (wayID==null|| busCompanyID==null)
						return false;
						
						int[] values = new int[]
						{wayID,busCompanyID};

						PostJson postJson = new PostJson();

						GetParamsMap = postJson.Post(keys, values,
								"getUpStation","upStationList");

						Boolean success = false;
						
						success = (Boolean) GetParamsMap.get("success");
						JSONArray upStationList = (JSONArray) GetParamsMap
								.get("upStationList"); 
						if (upStationList==null) {
							return false;
						}
						
						success = (Boolean) GetParamsMap
						.get("success");
						
							if (!listupStationName.isEmpty()) {
								listupStationName.clear();
							}
							
							for (int i = 0; i < upStationList.length(); i++)
							{
								try
								{
									JSONObject jo = (JSONObject) upStationList.get(i);
			
									adapterListMap = new HashMap<String, Object>();
			
									upstation = new Upstation();
			
									adapterListMap.put("stationID", jo.getString("stationID").toString());
									adapterListMap.put("stationName", jo.getString("stationName")
											.toString());
									
			
									upstation.setStationID(jo.getString("stationID").toString());
									upstation.setStationName(jo.getString("stationName").toString());
									
									listupStationName.add(i, jo.getString("stationName").toString());
									
									listupStationID.add(i,Integer.valueOf(jo.getString("stationID")));
									
									listupStation.add(i,upstation);
									
									adapterList.add(i,
											(HashMap<String, Object>) adapterListMap);
									
									Log.i("upstation ID:" + i, listupStationName.get(i));
									
								} catch (JSONException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								}
							
							Log.i("upstation Size:", String.valueOf(listupStationName.size()) );
							
							/*上下车获取数据分割*/
							
							PostJSONfromGson postGson = new PostJSONfromGson();
							
							PostgetDownStation postgetDownStation = new PostgetDownStation();
							
							downstation = new downStation();
							
							if (wayID==null||busCompanyID==null)
							return false;
							
							postgetDownStation.setTicketDirectionID(wayID);
							
							
							postgetDownStation.setBusCompanyID(busCompanyID);
							
							ListdownStation listdownStation = new ListdownStation();
							
							String result = (String) postGson.GsonPost(postgetDownStation, "getDownStation");
							
							Type listType=new TypeToken<ListdownStation>(){}.getType();
							
							Gson gson = new Gson();
							
							listdownStation = gson.fromJson(result,listType);
							
							ArrayList<downStation> downStationsList = new ArrayList<downStation>();
							
							success = false;
							
							downStationsList = listdownStation.getDownStationList();
							
							success = listdownStation.getSuccess();
							
							listdownStationName = new ArrayList<String>();
							
							if (!listdownStationName.isEmpty()) {
								listdownStationName.clear();
							}
							
							if (!listdownStationID.isEmpty()) {
								listdownStationID.clear();
							}
							
							for (int j = 0; j < downStationsList.size(); j++) {
								
								downstation = downStationsList.get(j);
								
								listdownStationName.add(downstation.getStationName());
								
								listdownStationID.add(downstation.getStationID());
							}
							
							
						return success;
					}

					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						
						try
						{
							if (result)
							{

								adapter_upplace = new ArrayAdapter(
										getActivity(),
										R.layout.simple_spinner_item,
										listupStationName);

								upplace_spinner
										.setAdapter(adapter_upplace);

								adapter_downplace = new ArrayAdapter(
										getActivity(),
										R.layout.simple_spinner_item,
										listdownStationName);

								downplace_spinner
										.setAdapter(adapter_downplace);

							} else if (!result)
							{
								Toast.makeText(
										getActivity()
												.getApplicationContext(),
										R.string.toast_flase_msg,
										Toast.LENGTH_LONG).show();
							}
						} catch (Exception e)
						{
							// TODO: handle exception
						}
							
						
					}
				}.execute();
			}
			}
		});
		
		company_yd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if (place_hongkong.isChecked())
					{
					/*	ticket_layout.setVisibility(View.GONE);
					selection.setVisibility(View.VISIBLE);
					*/
					ticket_layout.setVisibility(View.VISIBLE);
					selection.setVisibility(View.GONE);
					}else {
						ticket_layout.setVisibility(View.VISIBLE);
					selection.setVisibility(View.GONE);
					}
					
					
					if (monoway_double.isChecked())
					{
						if (place_hongkong.isChecked())
						{
							/*back_selection.setVisibility(View.VISIBLE);*/
							back_selection.setVisibility(View.GONE);
						}
						else {
							ticket_layout.setVisibility(View.VISIBLE);
							selection.setVisibility(View.GONE);
							
						}
						
						
					}
					busCompanyID = 1;
				
				
				
				
				new CustomAsynTask(getActivity(),thisfragment)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						// TODO Auto-generated method stub
						
						String[] keys = new String[]
						{ "ticketDirectionID","busCompanyID"};

						
						if (wayID==null|| busCompanyID==null)
						return false;
						
						int[] values = new int[]
						{wayID,busCompanyID};

						PostJson postJson = new PostJson();

						GetParamsMap = postJson.Post(keys, values,
								"getUpStation","upStationList");

						Boolean success = false;
						
						success = (Boolean) GetParamsMap.get("success");
						JSONArray upStationList = (JSONArray) GetParamsMap
								.get("upStationList"); 
						if (upStationList==null) {
							return false;
						}
						
						success = (Boolean) GetParamsMap
						.get("success");
						
							if (!listupStationName.isEmpty()) {
								listupStationName.clear();
							}
							
							for (int i = 0; i < upStationList.length(); i++)
							{
								try
								{
									JSONObject jo = (JSONObject) upStationList.get(i);
			
									adapterListMap = new HashMap<String, Object>();
			
									upstation = new Upstation();
			
									adapterListMap.put("stationID", jo.getString("stationID").toString());
									adapterListMap.put("stationName", jo.getString("stationName")
											.toString());
									
			
									upstation.setStationID(jo.getString("stationID").toString());
									upstation.setStationName(jo.getString("stationName").toString());
									
									listupStationName.add(i, jo.getString("stationName").toString());
									
									listupStationID.add(i,Integer.valueOf(jo.getString("stationID")));
									
									listupStation.add(i,upstation);
									
									adapterList.add(i,
											(HashMap<String, Object>) adapterListMap);
									
									Log.i("upstation ID:" + i, listupStationName.get(i));
									
								} catch (JSONException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								}
							
							Log.i("upstation Size:", String.valueOf(listupStationName.size()) );
							
							/*上下车获取数据分割*/
							
							PostJSONfromGson postGson = new PostJSONfromGson();
							
							PostgetDownStation postgetDownStation = new PostgetDownStation();
							
							downstation = new downStation();
							
							if (wayID==null||busCompanyID==null)
							return false;
							
							postgetDownStation.setTicketDirectionID(wayID);
							
							
							postgetDownStation.setBusCompanyID(busCompanyID);
							
							ListdownStation listdownStation = new ListdownStation();
							
							String result = (String) postGson.GsonPost(postgetDownStation, "getDownStation");
							
							Type listType=new TypeToken<ListdownStation>(){}.getType();
							
							Gson gson = new Gson();
							
							listdownStation = gson.fromJson(result,listType);
							
							ArrayList<downStation> downStationsList = new ArrayList<downStation>();
							
							downStationsList = listdownStation.getDownStationList();
							
							success = listdownStation.getSuccess();
							
							listdownStationName = new ArrayList<String>();
							
							if (!listdownStationName.isEmpty()) {
								listdownStationName.clear();
							}
							
							if (!listdownStationID.isEmpty()) {
								listdownStationID.clear();
							}
							
							for (int j = 0; j < downStationsList.size(); j++) {
								
								downstation = downStationsList.get(j);
								
								listdownStationName.add(downstation.getStationName());
								
								listdownStationID.add(downstation.getStationID());
							}
							
							
						return success;
					}

					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						
						try
						{
							if (result)
							{

								adapter_upplace = new ArrayAdapter(
										getActivity(),
										R.layout.simple_spinner_item,
										listupStationName);

								upplace_spinner
										.setAdapter(adapter_upplace);

								adapter_downplace = new ArrayAdapter(
										getActivity(),
										R.layout.simple_spinner_item,
										listdownStationName);

								downplace_spinner
										.setAdapter(adapter_downplace);

							} else if (!result)
							{
								Toast.makeText(
										getActivity()
												.getApplicationContext(),
										R.string.toast_flase_msg,
										Toast.LENGTH_LONG).show();
							}
						} catch (Exception e)
						{
							// TODO: handle exception
						}
							
						
					}
				}.execute();
				}
			}
		});

		
		
		monoway_double
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							fragment_layout.setVisibility(View.VISIBLE);
							
							if (company_zgt.isChecked())
							{
								back_selection.setVisibility(View.GONE);
							}
							
							if (place_hongkong.isChecked()&&company_yd.isChecked())
							{
								/*selection.setVisibility(View.VISIBLE);
								back_selection.setVisibility(View.VISIBLE);
								ticket_layout.setVisibility(View.GONE);
								*/
								selection.setVisibility(View.GONE);
								back_selection.setVisibility(View.GONE);
								ticket_layout.setVisibility(View.VISIBLE);
							}
							
							if(fragment_layout.getVisibility()==0||fragment_layout!=null){
								
								new CustomAsynTask(getActivity(),thisfragment)
								{
									@Override
									protected Boolean doInBackground(Void... params)
									{
										
										/*获取返程上车点*/
										PostJSONfromGson postGson = new PostJSONfromGson();
										
										Gson gson = new Gson();
										
										returnUpStation = new ReturnUpStation();
										
										returnDownStation = new ReturnDownStation();
										
										PostgetDepartureTime postgetDepartureTime = new PostgetDepartureTime();
										PostgetReturnUpstation postgetReturnUpstation = new PostgetReturnUpstation();
										
										
										ticketLineID = listDepartureTime.getTicketLineID();
										
										if (ticketLineID == null)
										return false;
										
										postgetReturnUpstation.setTicketLineID(ticketLineID);
										
										Type listType1=new TypeToken<ListDepartureTime>(){}.getType();
										
										String result1 = (String) postGson.GsonPost(postgetReturnUpstation, "getDepartureTime");
										
										ListDepartureTime listDepartureTime1 = gson.fromJson(result1,listType1);
										
										ArrayList<ReturnUpStation> returnUpStationList = new ArrayList<ReturnUpStation>();
										
										ArrayList<ReturnDownStation> returnDownStationList = new ArrayList<ReturnDownStation>();
										
										returnUpStationList = listDepartureTime1.getUpStationList();
										
										returnDownStationList = listDepartureTime1.getDownStationList();
										
										returnticketLineID = listDepartureTime1.getTicketLineID();
										
										listReturnUpStationName = new ArrayList<String>();
										
										listReturnUpStationID = new ArrayList<Integer>();
										
										listReturnDownStationName = new ArrayList<String>();
										
										listReturnDownStationID = new ArrayList<Integer>();
										
										if (!listReturnUpStationName.isEmpty()) {
											listReturnUpStationName.clear();
										}
										
										if (returnUpStationList==null) {
											return false;
										}
										
										for (int j = 0; j < returnUpStationList.size(); j++) {
											
											returnUpStation = returnUpStationList.get(j);
											
											listReturnUpStationName.add(returnUpStation.getStationName());
											
											listReturnUpStationID.add(returnUpStation.getStationID());
											
										}
										
										Boolean success = listDepartureTime1.getSuccess();
										
										if (!listReturnDownStationName.isEmpty()) {
											listReturnDownStationName.clear();
										}
										
										if (returnDownStationList==null) {
											return false;
										}
										
										for (int j = 0; j < returnDownStationList.size(); j++) {
											
											returnDownStation = returnDownStationList.get(j);
											
											listReturnDownStationName.add(returnDownStation.getStationName());
											
											listReturnDownStationID.add(returnDownStation.getStationID());
											
										}
									    
										
										return success;
									}
										
										protected void onPostExecute(Boolean result)
										{
											super.onPostExecute(result);
											
											try
											{
												if (result)
												{
													adapter_returnupplace = new ArrayAdapter(
															getActivity(),
															R.layout.simple_spinner_item,
															listReturnUpStationName);

													back_upplace
															.setAdapter(adapter_returnupplace);

													adapter_returndownplace = new ArrayAdapter(
															getActivity(),
															R.layout.simple_spinner_item,
															listReturnDownStationName);

													back_downplace
															.setAdapter(adapter_returndownplace);
												} else
												{
													Toast.makeText(
															getActivity(),
															R.string.toast_flase_msg,
															Toast.LENGTH_SHORT)
															.show();
												}
											} catch (Exception e)
											{
												// TODO: handle exception
											}
											
											
										}
									}.execute();
									
									
									
									
								}
							
							
							
						}
						
					}
				});

		monoway_single
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							fragment_layout.setVisibility(View.GONE);
							
							
						}
					}
				});
		
		sendway_mail.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					mail_layout.setVisibility(View.VISIBLE);
				}
					
				
				
				
			}
		});
		
		sendway_mail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(CheckLogin.logined(thisfragment))
				{
					showAddressDialog();
				}
				
			}
		});

		sendway_onself
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							mail_layout.setVisibility(View.GONE);
						}
					}
				});

		selection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		way_spinner
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (place_hongkong.isChecked())
						{
							switch (arg2) {
							case 0:
								wayID= arg2+1;
								returnwayID = wayID+1;
								break;

							case 1:
								wayID= arg2+1;
								returnwayID = wayID-1;
								break;
								
							default:
								break;
							}
						}else if (place_macao.isChecked()) {
							switch (arg2) {
							case 0:
								wayID= arg2+3;
								returnwayID = wayID+1;
								break;

							case 1:
								wayID= arg2+3;
								returnwayID = wayID-1;
								break;
								
							default:
								break;
							}
						}
						
						
						
							
						
						
						new CustomAsynTask(getActivity())
						{
							@Override
							protected Boolean doInBackground(Void... params)
							{
								// TODO Auto-generated method stub
								
								String[] keys = new String[]
								{ "ticketDirectionID","busCompanyID"};
		
								
								if (wayID==null|| busCompanyID==null)
								return false;
								
								int[] values = new int[]
								{wayID,busCompanyID};
		
								PostJson postJson = new PostJson();
		
								GetParamsMap = postJson.Post(keys, values,
										"getUpStation","upStationList");
		
								Boolean success = false;
								
								success = (Boolean) GetParamsMap.get("success");
								JSONArray upStationList = (JSONArray) GetParamsMap
										.get("upStationList"); 
								if (upStationList==null) {
									return false;
								}
								
								success = (Boolean) GetParamsMap
								.get("success");
								
									if (!listupStationName.isEmpty()) {
										listupStationName.clear();
									}
									
									for (int i = 0; i < upStationList.length(); i++)
									{
										try
										{
											JSONObject jo = (JSONObject) upStationList.get(i);
					
											adapterListMap = new HashMap<String, Object>();
					
											upstation = new Upstation();
					
											adapterListMap.put("stationID", jo.getString("stationID").toString());
											adapterListMap.put("stationName", jo.getString("stationName")
													.toString());
											
					
											upstation.setStationID(jo.getString("stationID").toString());
											upstation.setStationName(jo.getString("stationName").toString());
											
											listupStationName.add(i, jo.getString("stationName").toString());
											
											listupStationID.add(i,Integer.valueOf(jo.getString("stationID")));
											
											listupStation.add(i,upstation);
											
											adapterList.add(i,
													(HashMap<String, Object>) adapterListMap);
											
											Log.i("upstation ID:" + i, listupStationName.get(i));
											
										} catch (JSONException e)
										{
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										}
									
									Log.i("upstation Size:", String.valueOf(listupStationName.size()) );
									
									/*上下车获取数据分割*/
									
									PostJSONfromGson postGson = new PostJSONfromGson();
									
									PostgetDownStation postgetDownStation = new PostgetDownStation();
									
									downstation = new downStation();
									
									if (wayID==null||busCompanyID==null)
									return false;
									
									postgetDownStation.setTicketDirectionID(wayID);
									
									
									postgetDownStation.setBusCompanyID(busCompanyID);
									
									ListdownStation listdownStation = new ListdownStation();
									
									String result = (String) postGson.GsonPost(postgetDownStation, "getDownStation");
									
									Type listType=new TypeToken<ListdownStation>(){}.getType();
									
									Gson gson = new Gson();
									
									listdownStation = gson.fromJson(result,listType);
									
									ArrayList<downStation> downStationsList = new ArrayList<downStation>();
									
									downStationsList = listdownStation.getDownStationList();
									
									success = listdownStation.getSuccess();
									
									listdownStationName = new ArrayList<String>();
									
									if (!listdownStationName.isEmpty()) {
										listdownStationName.clear();
									}
									
									if (!listdownStationID.isEmpty()) {
										listdownStationID.clear();
									}
									
									for (int j = 0; j < downStationsList.size(); j++) {
										
										downstation = downStationsList.get(j);
										
										listdownStationName.add(downstation.getStationName());
										
										listdownStationID.add(downstation.getStationID());
									}
									
									
								return success;
							}
		
							protected void onPostExecute(Boolean result)
							{
								super.onPostExecute(result);
								
								try
								{
									if (result)
									{

										adapter_upplace = new ArrayAdapter(
												getActivity(),
												R.layout.simple_spinner_item,
												listupStationName);

										upplace_spinner
												.setAdapter(adapter_upplace);

										adapter_downplace = new ArrayAdapter(
												getActivity(),
												R.layout.simple_spinner_item,
												listdownStationName);

										downplace_spinner
												.setAdapter(adapter_downplace);

									} else if (!result)
									{
										Toast.makeText(
												getActivity()
														.getApplicationContext(),
												R.string.toast_flase_msg,
												Toast.LENGTH_LONG).show();
									}
								} catch (Exception e)
								{
									// TODO: handle exception
								}
									
								
							}
						}.execute();
						
						
						
					}
					
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				
					
				});
		
		upplace_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					upStationID = listupStationID.get(arg2);
					break;
				default:
					upStationID = listupStationID.get(arg2);
					break;
					}
				
				new CustomAsynTask(getActivity(),thisfragment)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						PostJSONfromGson postGson = new PostJSONfromGson();
						
						PostgetDepartureTime postgetDepartureTime = new PostgetDepartureTime();
						
						if (busCompanyID==null||wayID==null||upStationID==null||downStationID==null)
						return false;
						
						postgetDepartureTime.setBusCompanyID(busCompanyID);
						
						postgetDepartureTime.setTicketDirectionID(wayID);
						
						postgetDepartureTime.setUpStationID(upStationID);
						
						postgetDepartureTime.setDownStationID(downStationID);
						
						listDepartureTime = new ListDepartureTime();
						
						String result = (String) postGson.GsonPost(postgetDepartureTime, "getDepartureTime");
						
						Type listType=new TypeToken<ListDepartureTime>(){}.getType();
						
						Gson gson = new Gson();
						
						listDepartureTime = gson.fromJson(result,listType);
						
						ArrayList<departureTime> departureTimesList = new ArrayList<departureTime>();
						
						if (listDepartureTime.getDepartureTimeList()==null) {
							return false;
						}
						departureTimesList = listDepartureTime.getDepartureTimeList();
						
						listdepartureTimeName = new ArrayList<String>();
						
						listdepartureTimeID = new ArrayList<Integer>();
						
						if (!listdepartureTimeName.isEmpty()) {
							listdepartureTimeName.clear();
						}
						
							
						for (int j = 0; j < departureTimesList.size(); j++) {
							
							departureTime = departureTimesList.get(j);
							
							listdepartureTimeName.add(departureTime.getDepartureTimeName());
							
							listdepartureTimeID.add(departureTime.getDepartureTimeID());
							
							
						}
						
						ticketLineID = listDepartureTime.getTicketLineID();
						
						Boolean success = listDepartureTime.getSuccess();
						
						return success;
						
					}
					
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						
						try
						{
							if (result)
							{

								adapter_uptimetime = new ArrayAdapter(
										getActivity(),
										R.layout.simple_spinner_item,
										listdepartureTimeName);

								uptime_time.setAdapter(adapter_uptimetime);
							} else
							{
								//adapter_uptimetime.clear();
								uptime_time.setAdapter(adapter_uptimetime);
								Toast.makeText(getActivity(),
										"该线路暂无发车时间，请重新选择上车点或下车点！",
										Toast.LENGTH_SHORT).show();

								if (uptime_time.getSelectedItem() != null)
								{
									adapter_uptimetime.clear();

									uptime_time.setAdapter(adapter_uptimetime);

								}
							}
						} catch (Exception e)
						{
							// TODO: handle exception
						}
						
					}
					
				}.execute();
				
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		downplace_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					downStationID = listdownStationID.get(arg2);
					 
					break;
				default:
					downStationID = listdownStationID.get(arg2);
					break;
					
					
				}
				
				new CustomAsynTask(getActivity(),thisfragment)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						PostJSONfromGson postGson = new PostJSONfromGson();
						
						PostgetDepartureTime postgetDepartureTime = new PostgetDepartureTime();
						
						if (busCompanyID==null||wayID==null||upStationID==null||downStationID==null)
							return false;
							
						
						postgetDepartureTime.setBusCompanyID(busCompanyID);
						
						postgetDepartureTime.setTicketDirectionID(wayID);
						
						postgetDepartureTime.setUpStationID(upStationID);
						
						postgetDepartureTime.setDownStationID(downStationID);
						
						listDepartureTime = new ListDepartureTime();
						
						String result = (String) postGson.GsonPost(postgetDepartureTime, "getDepartureTime");
						
						Type listType=new TypeToken<ListDepartureTime>(){}.getType();
						
						Gson gson = new Gson();
						
						listDepartureTime = gson.fromJson(result,listType);
						
						
						
						ArrayList<departureTime> departureTimesList = new ArrayList<departureTime>();
						
						if (listDepartureTime.getDepartureTimeList()==null) {
							return false;
						}
						departureTimesList = listDepartureTime.getDepartureTimeList();
						
						
						
						listdepartureTimeName = new ArrayList<String>();
						
						listdepartureTimeID = new ArrayList<Integer>();
						
						if (!listdepartureTimeName.isEmpty()) {
							listdepartureTimeName.clear();
						}
						
							
						for (int j = 0; j < departureTimesList.size(); j++) {
							
							departureTime = departureTimesList.get(j);
							
							listdepartureTimeName.add(departureTime.getDepartureTimeName());
							
							listdepartureTimeID.add(departureTime.getDepartureTimeID());
							
							
						}
						
						ticketLineID = listDepartureTime.getTicketLineID();
						
						Boolean success = listDepartureTime.getSuccess();
						
						return success;
						
					}
					
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						
						try
						{
							if (result)
							{
								adapter_uptimetime = new ArrayAdapter(
										getActivity(),
										R.layout.simple_spinner_item,
										listdepartureTimeName);

								uptime_time.setAdapter(adapter_uptimetime);
							} else
							{
								//adapter_uptimetime.clear();
								uptime_time.setAdapter(adapter_uptimetime);
								Toast.makeText(getActivity(),
										"该线路暂无发车时间，请重新选择上车点或下车点！",
										Toast.LENGTH_SHORT).show();

								if (uptime_time.getSelectedItem() != null)
								{
									adapter_uptimetime.clear();

									uptime_time.setAdapter(adapter_uptimetime);

								}
								
								if (monoway_double.isChecked())
								{
									adapter_returnupplace.clear();
									adapter_returndownplace.clear();
									
									back_upplace.setAdapter(adapter_returnupplace);
									back_downplace.setAdapter(adapter_returndownplace);
									
									adapter_returnuptimetime.clear();
									back_uptime_time.setAdapter(adapter_returnuptimetime);
								}
							}
						} catch (Exception e)
						{
							// TODO: handle exception
						}
						
					}
					
				}.execute();
				
				if (monoway_double.isChecked())
				new CustomAsynTask(getActivity(),thisfragment)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						
						/*获取返程上车点*/
						PostJSONfromGson postGson = new PostJSONfromGson();
						
						Gson gson = new Gson();
						
						returnUpStation = new ReturnUpStation();
						
						returnDownStation = new ReturnDownStation();
						
						PostgetDepartureTime postgetDepartureTime = new PostgetDepartureTime();
						PostgetReturnUpstation postgetReturnUpstation = new PostgetReturnUpstation();
						
						
						ticketLineID = listDepartureTime.getTicketLineID();
						
						if (ticketLineID == null)
						return false;
						
						postgetReturnUpstation.setTicketLineID(ticketLineID);
						
						Type listType1=new TypeToken<ListDepartureTime>(){}.getType();
						
						String result1 = (String) postGson.GsonPost(postgetReturnUpstation, "getDepartureTime");
						
						ListDepartureTime listDepartureTime1 = gson.fromJson(result1,listType1);
						
						ArrayList<ReturnUpStation> returnUpStationList = new ArrayList<ReturnUpStation>();
						
						ArrayList<ReturnDownStation> returnDownStationList = new ArrayList<ReturnDownStation>();
						
						returnUpStationList = listDepartureTime1.getUpStationList();
						
						returnDownStationList = listDepartureTime1.getDownStationList();
						
						returnticketLineID = listDepartureTime1.getTicketLineID();
						
						listReturnUpStationName = new ArrayList<String>();
						
						listReturnUpStationID = new ArrayList<Integer>();
						
						listReturnDownStationName = new ArrayList<String>();
						
						listReturnDownStationID = new ArrayList<Integer>();
						
						if (!listReturnUpStationName.isEmpty()) {
							listReturnUpStationName.clear();
						}
						
						if (returnUpStationList==null) {
							return false;
						}
						
						for (int j = 0; j < returnUpStationList.size(); j++) {
							
							returnUpStation = returnUpStationList.get(j);
							
							listReturnUpStationName.add(returnUpStation.getStationName());
							
							listReturnUpStationID.add(returnUpStation.getStationID());
							
						}
						
						Boolean success = listDepartureTime1.getSuccess();
						
						if (!listReturnDownStationName.isEmpty()) {
							listReturnDownStationName.clear();
						}
						
						if (returnDownStationList==null) {
							return false;
						}
						
						for (int j = 0; j < returnDownStationList.size(); j++) {
							
							returnDownStation = returnDownStationList.get(j);
							
							listReturnDownStationName.add(returnDownStation.getStationName());
							
							listReturnDownStationID.add(returnDownStation.getStationID());
							
						}
					    
						
						return success;
					}
						
						protected void onPostExecute(Boolean result)
						{
							super.onPostExecute(result);
							
							try
							{
								if (result)
								{
									adapter_returnupplace = new ArrayAdapter(
											getActivity(),
											R.layout.simple_spinner_item,
											listReturnUpStationName);

									back_upplace
											.setAdapter(adapter_returnupplace);

									adapter_returndownplace = new ArrayAdapter(
											getActivity(),
											R.layout.simple_spinner_item,
											listReturnDownStationName);

									back_downplace
											.setAdapter(adapter_returndownplace);
								} else
								{
									Toast.makeText(getActivity(),
											"该线路暂无发车时间，请重新选择上车点或下车点！",
											Toast.LENGTH_SHORT).show();

									if (back_uptime_time.getSelectedItem() != null)
									{
										adapter_returnuptimetime.clear();

										back_uptime_time
												.setAdapter(adapter_returnuptimetime);

									}
								}
							} catch (Exception e)
							{
								// TODO: handle exception
							}
							
							
						}
					}.execute();
				
				
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		uptime_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					goDepartureTimeID = listdepartureTimeID.get(arg2);
					break;
				default:
					goDepartureTimeID = listdepartureTimeID.get(arg2);
					break;
					
				}
				
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		
		
		back_upplace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					returnupStationID = listReturnUpStationID.get(arg2);
					break;
				default:
					returnupStationID = listReturnUpStationID.get(arg2);
					break;
					
				}
				
				new CustomAsynTask(getActivity(),thisfragment)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						PostJSONfromGson postGson = new PostJSONfromGson();
						
						PostgetDepartureTime postgetDepartureTime = new PostgetDepartureTime();
						
						if (busCompanyID==null||returnwayID==null||returnupStationID==null||returndownStationID==null)
							return false;
							
						
						postgetDepartureTime.setBusCompanyID(busCompanyID);
						
						postgetDepartureTime.setTicketDirectionID(returnwayID);
						
						postgetDepartureTime.setUpStationID(returnupStationID);
						
						postgetDepartureTime.setDownStationID(returndownStationID);
						
						listDepartureTime = new ListDepartureTime();
						
						String result = (String) postGson.GsonPost(postgetDepartureTime, "getDepartureTime");
						
						Type listType=new TypeToken<ListDepartureTime>(){}.getType();
						
						Gson gson = new Gson();
						
						listDepartureTime = gson.fromJson(result,listType);
						
						ArrayList<departureTime> departureTimesList = new ArrayList<departureTime>();
						
						if (listDepartureTime.getDepartureTimeList()==null) {
							return false;
						}
						departureTimesList = listDepartureTime.getDepartureTimeList();
						
						listdepartureTimeName = new ArrayList<String>();
						
						listReturndepartureTimeID = new ArrayList<Integer>();
						
						if (!listdepartureTimeName.isEmpty()) {
							listdepartureTimeName.clear();
						}
						
							
						for (int j = 0; j < departureTimesList.size(); j++) {
							
							departureTime = departureTimesList.get(j);
							
							listdepartureTimeName.add(departureTime.getDepartureTimeName());
							
							listReturndepartureTimeID.add(departureTime.getDepartureTimeID());
							
							
						}
						
						Boolean success = listDepartureTime.getSuccess();
						
						return success;
						
					}
					
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						
						try
						{
							if (result)
							{

								adapter_returnuptimetime = new ArrayAdapter(
										getActivity(),
										R.layout.simple_spinner_item,
										listdepartureTimeName);

								back_uptime_time
										.setAdapter(adapter_returnuptimetime);
							} else
							{
								//adapter_uptimetime.clear();
								back_uptime_time
										.setAdapter(adapter_returnuptimetime);
								Toast.makeText(getActivity(),
										"该线路暂无发车时间，请重新选择上车点或下车点！",
										Toast.LENGTH_SHORT).show();

								if (back_uptime_time.getSelectedItem() != null)
								{
									adapter_returnuptimetime.clear();

									back_uptime_time
											.setAdapter(adapter_returnuptimetime);

								}
							}
						} catch (Exception e)
						{
							// TODO: handle exception
						}
						
					}
					
				}.execute();
				
				
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		
		back_downplace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					returndownStationID = listReturnDownStationID.get(arg2);
					break;
				default:
					returndownStationID = listReturnDownStationID.get(arg2);
					break;
					
				}
				
				new CustomAsynTask(getActivity(),thisfragment)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						PostJSONfromGson postGson = new PostJSONfromGson();
						
						PostgetDepartureTime postgetDepartureTime = new PostgetDepartureTime();
						
						if (busCompanyID==null||returnwayID==null||returnupStationID==null||returndownStationID==null)
							return false;
						
						postgetDepartureTime.setBusCompanyID(busCompanyID);
						
						postgetDepartureTime.setTicketDirectionID(returnwayID);
						
						postgetDepartureTime.setUpStationID(returnupStationID);
						
						postgetDepartureTime.setDownStationID(returndownStationID);
						
						listDepartureTime = new ListDepartureTime();
						
						String result = (String) postGson.GsonPost(postgetDepartureTime, "getDepartureTime");
						
						Type listType=new TypeToken<ListDepartureTime>(){}.getType();
						
						Gson gson = new Gson();
						
						listDepartureTime = gson.fromJson(result,listType);
						
						ArrayList<departureTime> departureTimesList = new ArrayList<departureTime>();
						
						if (listDepartureTime.getDepartureTimeList()==null) {
							return false;
						}
						departureTimesList = listDepartureTime.getDepartureTimeList();
						
						listdepartureTimeName = new ArrayList<String>();
						
						listdepartureTimeID = new ArrayList<Integer>();
						
						if (!listdepartureTimeName.isEmpty()) {
							listdepartureTimeName.clear();
						}
						
							
						for (int j = 0; j < departureTimesList.size(); j++) {
							
							departureTime = departureTimesList.get(j);
							
							listdepartureTimeName.add(departureTime.getDepartureTimeName());
							
							listdepartureTimeID.add(departureTime.getDepartureTimeID());
							
							
						}
						
						Boolean success = listDepartureTime.getSuccess();
						
						return success;
						
					}
					
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						
						try
						{
							if (result)
							{

								adapter_returnuptimetime = new ArrayAdapter(
										getActivity(),
										R.layout.simple_spinner_item,
										listdepartureTimeName);

								back_uptime_time
										.setAdapter(adapter_returnuptimetime);
							} else
							{
								//adapter_uptimetime.clear();
								back_uptime_time
										.setAdapter(adapter_returnuptimetime);
								Toast.makeText(getActivity(),
										"该线路暂无发车时间，请重新选择上车点或下车点！",
										Toast.LENGTH_SHORT).show();

								if (back_uptime_time.getSelectedItem() != null)
								{
									adapter_returnuptimetime.clear();

									back_uptime_time
											.setAdapter(adapter_returnuptimetime);

								}
							}
						} catch (Exception e)
						{
							// TODO: handle exception
						}
						
					}
					
				}.execute();
				
				
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		back_uptime_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					returnDepartureTimeID = listReturndepartureTimeID.get(arg2);
					break;
				default:
					returnDepartureTimeID = listReturndepartureTimeID.get(arg2);
					break;
					
				}
				
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		back_selection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new CustomAsynTask(getActivity(),thisfragment)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						PostJSONfromGson postGson = new PostJSONfromGson();
						
						PostgetSeatNumber postgetSeatNumber = new PostgetSeatNumber();
						
						if (returnticketLineID == null)
							return false;
						
						postgetSeatNumber.setTicketLineID(returnticketLineID);
						
						listSeatNumber = new ListSeatNumber();
						
						String result = (String) postGson.GsonPost(postgetSeatNumber, "getSeatNumber");
						
						Type listType=new TypeToken<ListSeatNumber>(){}.getType();
						
						Gson gson = new Gson();
						
						listSeatNumber = gson.fromJson(result,listType);
						
						ReturnseatNumbersList = new ArrayList<seatNumber>();
						
						/*if (listSeatNumber.getSeatNumberList() == null) {
							return false;
						}*/
						ReturnseatNumbersList = listSeatNumber.getSeatNumberList();
						
						Boolean success = listSeatNumber.getSuccess();
						
						return success;
						
					}
					
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						try
						{
							if (result)
							{
								Bundle bundle = new Bundle();

								bundle.putString("key", "back");

								fss.setArguments(bundle);

								fragmentTransaction = getFragmentManager()
										.beginTransaction();

								fragmentTransaction.add(R.id.content, fss);

								fragmentTransaction.addToBackStack(null);

								fragmentTransaction.hide(thisfragment);

								fragmentTransaction.show(fss);

								fragmentTransaction.commit();

							} else
							{
								Toast.makeText(getActivity(),
										R.string.toast_flase_msg,
										Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e)
						{
							// TODO: handle exception
						}
					
					 
					 	
						
					}
					
				}.execute();
				
			}
		});
		
		selection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new CustomAsynTask(getActivity(),thisfragment)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						PostJSONfromGson postGson = new PostJSONfromGson();
						
						PostgetSeatNumber postgetSeatNumber = new PostgetSeatNumber();
						
						if (ticketLineID == null)
						return false;
						
						postgetSeatNumber.setTicketLineID(ticketLineID);
						
						listSeatNumber = new ListSeatNumber();
						
						String result = (String) postGson.GsonPost(postgetSeatNumber, "getSeatNumber");
						
						Type listType=new TypeToken<ListSeatNumber>(){}.getType();
						
						Gson gson = new Gson();
						
						listSeatNumber = gson.fromJson(result,listType);
						
						seatNumbersList = new ArrayList<seatNumber>();
						
						/*if (listSeatNumber.getSeatNumberList() == null) {
							return false;
						}*/
						seatNumbersList = listSeatNumber.getSeatNumberList();
						
						Boolean success = listSeatNumber.getSuccess();
						
						return success;
						
					}
					
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						
						try
						{
							if (result)
							{
								Bundle bundle = new Bundle();

								bundle.putString("key", "def");

								fss.setArguments(bundle);

								fragmentTransaction = getFragmentManager()
										.beginTransaction();

								fragmentTransaction.add(R.id.content, fss);

								fragmentTransaction.addToBackStack(null);

								fragmentTransaction.hide(thisfragment);

								fragmentTransaction.show(fss);

								fragmentTransaction.commit();
							} else
							{
								Toast.makeText(getActivity(),
										R.string.toast_flase_msg,
										Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e)
						{
							// TODO: handle exception
						}
						 
					 
					 
					 	
						
					}
					
				}.execute();
				
			}
		});
		
		uptime_update.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// TODO Auto-generated method stub
				dpd_uptime_update.show();
				return false;
			}
		});
		back_uptime_update.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// TODO Auto-generated method stub
				
				
				dpd_back_uptime_update.show();
				return false;
			}
		});
		
	 booking_btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (CheckLogin.logined(thisfragment))
			{
			new CustomAsynTask(getActivity())
			{
				@Override
				protected Boolean doInBackground(Void... params)
				{
					PostJSONfromGson postGson = new PostJSONfromGson();
					
					PostgetDepartureTime postgetDepartureTime = new PostgetDepartureTime();
					
					if (busCompanyID==null||wayID==null||upStationID==null||downStationID==null)
						return false;
					
					postgetDepartureTime.setBusCompanyID(busCompanyID);
					
					postgetDepartureTime.setTicketDirectionID(wayID);
					
					postgetDepartureTime.setUpStationID(upStationID);
					
					postgetDepartureTime.setDownStationID(downStationID);
					
					listTicketLine = new ListTicketLine();
					
					String result = (String) postGson.GsonPost(postgetDepartureTime, "getTicketLine");
					
					Type listType=new TypeToken<ListTicketLine>(){}.getType();
					
					Gson gson = new Gson();
					
					listTicketLine = gson.fromJson(result,listType);
					
					
					if (listTicketLine.getMsg()==null||ticketLineID == null||listTicketLine.getSuccess()==null||contact.getText().toString()==null||tellphone.getText().toString()==null) {
						return false;
					}
					
					
					oneWayPrice = listTicketLine.getOneWayPrice();
					
					doubleWayPrice = listTicketLine.getDoubleWayPrice();
					
					Boolean success = listTicketLine.getSuccess();
					
					postbuyTicket.setUsername(sp.getString("username", null));
					
					
					postbuyTicket.setGoTicketLineID(ticketLineID);
					
					postbuyTicket.setGoDepartureTimeID(goDepartureTimeID);
					
					if (sendway_mail.isChecked())
					{
						if (contact.getText().toString()==null||address.getText().toString()==null)
						return false;
						
						postbuyTicket.setIsExpress(true);
						
						postbuyTicket.setReceiver(contact.getText().toString());
						
						postbuyTicket.setAddress(address.getText().toString());
						
						postbuyTicket.setPostcode("528000");
					}
					
					postbuyTicket.setContacter(contact.getText().toString());
					
					postbuyTicket.setContactNumber(tellphone.getText().toString());
					
					postbuyTicket.setIsExpress(sendway_mail.isChecked());
					
					postbuyTicket.setIsDoubleWay(monoway_double.isChecked());
					
					postbuyTicket.setGoTicketDate(uptime_update.getText().toString());
					
					if (monoway_double.isChecked()) {
						
						if (returnticketLineID==null||returnDepartureTimeID==null||back_uptime_update.getText().toString()==null)
						return false;
						
						postbuyTicket.setIsDoubleWay(true);
						
						postbuyTicket.setReturnTicketLineID(returnticketLineID);
						
						postbuyTicket.setReturnDepartureTimeID(returnDepartureTimeID);
						
						postbuyTicket.setReturnTicketDate(back_uptime_update.getText().toString());
						
						if (company_yd.isChecked()&&place_hongkong.isChecked())
						{
							/*postbuyTicket.setReturnSeatNumbers(PutReturnseatNumbersList.getSeatNumbersList());*/
							
							zgt_ticket_count =Integer.valueOf(custom_np_num.getText().toString());
							
							postbuyTicket.setTicketCount(zgt_ticket_count);
						}
						else 
						{
							zgt_ticket_count =Integer.valueOf(custom_np_num.getText().toString());
							
							postbuyTicket.setTicketCount(zgt_ticket_count);
						}
						
					}
					else if (monoway_single.isChecked()){
						zgt_ticket_count =Integer.valueOf(custom_np_num.getText().toString());
						
						postbuyTicket.setTicketCount(zgt_ticket_count);
					}
					
					if (company_yd.isChecked()&&place_hongkong.isChecked())
					{
						/*if (PutseatNumbersList.getSeatNumbersList() == null)
						return false;*/
						
						
						/*postbuyTicket.setTicketCount(PutseatNumbersList.getSeatNumbersList().size());
						
						postbuyTicket.setGoSeatNumbers(PutseatNumbersList.getSeatNumbersList());
						*/
						
						zgt_ticket_count =Integer.valueOf(custom_np_num.getText().toString());
						
						postbuyTicket.setTicketCount(zgt_ticket_count);
						
					}else{
						
						zgt_ticket_count =Integer.valueOf(custom_np_num.getText().toString());
						
						postbuyTicket.setTicketCount(zgt_ticket_count);
					}
					
					
					
					
					return success;
					
				}
				
				protected void onPostExecute(Boolean result)
				{
					super.onPostExecute(result);
					
					try
					{
						if (result)
						{

							ConfirmInfo
									.setContant(contact.getText().toString());
							ConfirmInfo.setTellphone(tellphone.getText()
									.toString());
							ConfirmInfo
									.setAddress(address.getText().toString());
							ConfirmInfo.setUpplace(upplace_spinner
									.getSelectedItem().toString());
							ConfirmInfo.setDownplace(downplace_spinner
									.getSelectedItem().toString());
							ConfirmInfo.setUpdatetime(uptime_update.getText()
									.toString()
									+ "  "
									+ uptime_time.getSelectedItem().toString());

							if (company_yd.isChecked()&&place_hongkong.isChecked())
							{
								/*ConfirmInfo.setTicketcount(PutseatNumbersList
										.getSeatNumbersList().size());

								conf_allprice = PutseatNumbersList
										.getSeatNumbersList().size()
										* oneWayPrice;*/
								
								ConfirmInfo.setTicketcount(zgt_ticket_count);

								conf_allprice = zgt_ticket_count * oneWayPrice;
							} else
							{

								ConfirmInfo.setTicketcount(zgt_ticket_count);

								conf_allprice = zgt_ticket_count * oneWayPrice;

							}

							ConfirmInfo.setPrice(conf_allprice);

							if (company_yd.isChecked())
							{
								ConfirmInfo.setCompany("永东巴士");

							} else if (company_zgt.isChecked())
							{
								ConfirmInfo.setCompany("中港通");
							}
							
							
							
							fragmentTransaction = getFragmentManager()
									.beginTransaction();

							fragmentTransaction.replace(R.id.content, tbcf);

							fragmentTransaction.addToBackStack(null);

							fragmentTransaction.commit();
						} else
						{
							
							Toast.makeText(getActivity(), "请完整输入订票信息！",
									Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e)
					{
						// TODO: handle exception
					}
					
				}
				
			}.execute();
			}
		}
	});
	 
	 custom_np_down.setOnClickListener(new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Integer count;
			if (custom_np_num.getText().toString() == null||custom_np_num.getText().toString().equals(null)||custom_np_num.getText().toString().equals(""))
			{
				count = 0;
			}
			
			count =Integer.valueOf(custom_np_num.getText().toString()) ;
			
			if (count>1)
			{
				count -=1;
				
				custom_np_num.setText(String.valueOf(count));
			}
			
			
		}
	});
	
	 
	 
	 custom_np_num.addTextChangedListener(new TextWatcher()
	{
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after)
		{
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s)
		{
			// TODO Auto-generated method stub
			if (s.length()==0)
			{
				custom_np_num.setText("0");
			}
		}
	});
	 
	 custom_np_up.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Integer count;
				if (custom_np_num.getText().toString() == null||custom_np_num.getText().toString().equals(null)||custom_np_num.getText().toString().equals(""))
				{
					count = 0;
				}
				
				count =Integer.valueOf(custom_np_num.getText().toString()) ;
				
				if (count<100)
				{
					count +=1;
					
					custom_np_num.setText(String.valueOf(count));
				}
				
				
				
				
			}
		});
		 
	 
	}
	}
	
}
