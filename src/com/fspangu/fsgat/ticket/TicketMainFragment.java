package com.fspangu.fsgat.ticket;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Entities.EscapeMode;

import com.baidu.platform.comapi.map.l;
import com.fspangu.fsgat.GrzxFragment;
import com.fspangu.fsgat.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJSONfromGson;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.model.Address;
import com.pangu.neusoft.fsgat.model.ListDepartureTime;
import com.pangu.neusoft.fsgat.model.ListReturnUpstation;
import com.pangu.neusoft.fsgat.model.ListdownStation;
import com.pangu.neusoft.fsgat.model.PostgetDepartureTime;
import com.pangu.neusoft.fsgat.model.PostgetReturnUpstation;
import com.pangu.neusoft.fsgat.model.ReturnDownStation;
import com.pangu.neusoft.fsgat.model.ReturnUpStation;
import com.pangu.neusoft.fsgat.model.departureTime;
import com.pangu.neusoft.fsgat.model.downStation;
import com.pangu.neusoft.fsgat.model.ListAddress;
import com.pangu.neusoft.fsgat.model.ListupStation;
import com.pangu.neusoft.fsgat.model.Upstation;
import com.pangu.neusoft.fsgat.model.PostgetDownStation;
import com.pangu.neusoft.fsgat.model.user;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TicketMainFragment extends Fragment {
	RadioGroup choose_place, choose_company, choose_monoway, sendway;
	RadioButton place_hongkong, place_macao, company_yd, company_zgt,
			monoway_single, monoway_double, sendway_onself, sendway_mail;
	Spinner way_spinner, upplace_spinner, downplace_spinner, uptime_date,back_upplace,back_downplace,back_uptime_time;
	EditText contact, tellphone, address, zipcode;
	Button booking_btn;
	RelativeLayout selection_layout, ticket_layout;
	LinearLayout mail_layout, fragment_layout;
	TextView selection;

	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	TicketBackFragment tb;

	int upwayID,downwayID,busCompanyID,upStationID,downStationID,ticketLineID;
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
	
	ArrayList<String> listReturnUpStationName;
	ArrayList<Integer> listReturnUpStationID;
	ReturnUpStation returnUpStation;
	
	SharedPreferences sp;
	Editor editor;
	ArrayAdapter adapter_upplace,adapter_downplace,adapter_uptimetime,adapter_returnupplace,adapter_returndownplace,adapter_returnuptimetime;

	private void init() {
		// TODO Auto-generated method stub
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
		uptime_date = (Spinner) getActivity().findViewById(
				R.id.ticket_main_uptime_date);
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
		fragment_layout = (LinearLayout) getActivity().findViewById(
				R.id.ticket_main_fragment_layout);
		selection_layout = (RelativeLayout) getActivity().findViewById(
				R.id.ticket_main_selection_layout);
		ticket_layout = (RelativeLayout) getActivity().findViewById(
				R.id.ticket_main_ticket_layout);
		mail_layout = (LinearLayout) getActivity().findViewById(
				R.id.ticket_main_mail_layout);
		selection = (TextView) getActivity().findViewById(
				R.id.ticket_main_selection);

		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();

		listupStation = new ListupStation();

		adapterListMap = new HashMap<String, Object>();

		adapterList = new ArrayList<HashMap<String, Object>>();

		listupStationName = new ArrayList<String>();
		
		listupStationID = new ArrayList<Integer>();
		
		listdownStationID = new ArrayList<Integer>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_ticket_main, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if (CheckNetwork.connected(this)){
		
			busCompanyID = 1;
			
			
			init();

		company_zgt.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					selection_layout.setVisibility(View.GONE);
					ticket_layout.setVisibility(View.VISIBLE);
					busCompanyID = 2;
				}
			}
		});

		company_yd.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					ticket_layout.setVisibility(View.GONE);
					selection_layout.setVisibility(View.VISIBLE);
					busCompanyID = 1;
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
							
							if(fragment_layout.getVisibility()==0||fragment_layout!=null){
								
								new CustomAsynTask(getActivity())
								{
									@Override
									protected Boolean doInBackground(Void... params)
									{
										
										/*获取返程上车点*/
										PostJSONfromGson postGson = new PostJSONfromGson();
										
										Gson gson = new Gson();
										
										returnUpStation = new ReturnUpStation();
										
										PostgetDepartureTime postgetDepartureTime = new PostgetDepartureTime();
										
										/*postgetDepartureTime.setBusCompanyID(busCompanyID);
										
										postgetDepartureTime.setTicketDirectionID(upwayID);
										
										postgetDepartureTime.setUpStationID(upStationID);
										
										postgetDepartureTime.setDownStationID(downStationID);
										
										String result1 = (String) postGson.GsonPost(postgetDepartureTime, "getDepartureTime");
										
										
										ListDepartureTime listDepartureTime = gson.fromJson(result1,listType1);*/
										
										ticketLineID = listDepartureTime.getTicketLineID();
										
										postgetDepartureTime.setTicketLineID(ticketLineID);
										
										Type listType1=new TypeToken<ListDepartureTime>(){}.getType();
										
										
										String result1 = (String) postGson.GsonPost(postgetDepartureTime, "getDepartureTime");
										
										ListDepartureTime listDepartureTime1 = gson.fromJson(result1,listType1);
										
										
										ArrayList<ReturnUpStation> returnUpStationList = new ArrayList<ReturnUpStation>();
										
										returnUpStationList = listDepartureTime1.getUpStationList();
										
										listReturnUpStationName = new ArrayList<String>();
										
										listReturnUpStationID = new ArrayList<Integer>();
										
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
								
										
										return null;
									}
										
										protected void onPostExecute(Boolean result)
										{
											super.onPostExecute(result);
											
											adapter_returnupplace=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,listReturnUpStationName);
											
											back_upplace.setAdapter(adapter_returnupplace);
											
												
											
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
						switch (arg2) {
						case 0:
							upwayID= arg2+1;
							downwayID = arg2+2;
							break;

						case 1:
							upwayID= arg2+1;
							downwayID = arg2;
							break;
							
						case 2:
							upwayID= arg2+1;
							downwayID = arg2+2;
							break;
							
						case 3:
							upwayID= arg2+1;
							downwayID = arg2;
							break;
							
						default:
							break;
						}
						
						new CustomAsynTask(getActivity())
						{
							@Override
							protected Boolean doInBackground(Void... params)
							{
								// TODO Auto-generated method stub
								
								String[] keys = new String[]
								{ "ticketDirectionID","busCompanyID"};
		
								int[] values = new int[]
								{upwayID,busCompanyID};
		
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
									
									postgetDownStation.setTicketDirectionID(downwayID);
									
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
								
								if (result) {
									
								adapter_upplace=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,listupStationName);
								
								upplace_spinner.setAdapter(adapter_upplace);
								
								adapter_downplace=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,listdownStationName);
								
								downplace_spinner.setAdapter(adapter_downplace);
								
								
								}else if (!result) {
									Toast.makeText(getActivity().getApplicationContext(),
											R.string.toast_flase_msg,
											Toast.LENGTH_LONG).show();
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
				case 1:
					upStationID = listupStationID.get(arg2);
					break;
				case 2:
					upStationID = listupStationID.get(arg2);
					break;
				case 3:
					upStationID = listupStationID.get(arg2);
					break;
				case 4:
					upStationID = listupStationID.get(arg2);
					break;
				case 5:
					upStationID = listupStationID.get(arg2);
					break;
				case 6:
					upStationID = listupStationID.get(arg2);
					break;
				case 7:
					upStationID = listupStationID.get(arg2);
					break;
				case 8:
					upStationID = listupStationID.get(arg2);
					break;
				case 9:
					upStationID = listupStationID.get(arg2);
					break;
					
				default:
					break;
					
					
				}
				
				
				
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
				case 1:
					downStationID = listdownStationID.get(arg2);
					break;
				case 2:
					downStationID = listdownStationID.get(arg2);
					break;
				case 3:
					downStationID = listdownStationID.get(arg2);
					break;
				case 4:
					downStationID = listdownStationID.get(arg2);
					break;
				case 5:
					downStationID = listdownStationID.get(arg2);
					break;
				case 6:
					downStationID = listdownStationID.get(arg2);
					break;
				case 7:
					downStationID = listdownStationID.get(arg2);
					break;
				case 8:
					downStationID = listdownStationID.get(arg2);
					break;
				case 9:
					downStationID = listdownStationID.get(arg2);
					break;
					
				default:
					break;
					
					
				}
				
				new CustomAsynTask(getActivity())
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{
						PostJSONfromGson postGson = new PostJSONfromGson();
						
						PostgetDepartureTime postgetDepartureTime = new PostgetDepartureTime();
						
						postgetDepartureTime.setBusCompanyID(busCompanyID);
						
						postgetDepartureTime.setTicketDirectionID(upwayID);
						
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
						
						Boolean success = listDepartureTime.getSuccess();
						
						return success;
						
					}
					
					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);
						
						if (result) {
							
							adapter_uptimetime=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,listdepartureTimeName);
							
							uptime_date.setAdapter(adapter_uptimetime);
						}
						else {
							adapter_uptimetime.clear();
							uptime_date.setAdapter(adapter_uptimetime);
							Toast.makeText(getActivity(), "该线路暂无发车时间，请重新选择上车点或下车点！", Toast.LENGTH_SHORT).show();
						}
						
					}
					
				}.execute();
				
				
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
					upStationID = listupStationID.get(arg2);
					break;
				
					
				default:
					break;
					
				}
				
				
				
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}
	}
}
