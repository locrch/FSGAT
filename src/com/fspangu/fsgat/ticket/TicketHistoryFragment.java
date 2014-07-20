package com.fspangu.fsgat.ticket;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fspangu.fsgat.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckLogin;
import com.pangu.neusoft.fsgat.core.PostJSONfromGson;
import com.pangu.neusoft.fsgat.model.ListAddressAdapter;
import com.pangu.neusoft.fsgat.model.ListTicketOrder;
import com.pangu.neusoft.fsgat.model.ListbuyTicket;
import com.pangu.neusoft.fsgat.model.ListticketOrderAdapter;
import com.pangu.neusoft.fsgat.model.PostgetTicketOrder;
import com.pangu.neusoft.fsgat.model.ticketOrder;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TicketHistoryFragment extends Fragment
{
	ListView ticket_history_listview;
	SharedPreferences sp;
	Editor editor;
	String msg;
	ArrayList<ticketOrder> ticketOrderList;
	ticketOrder ticketorder;
	List<HashMap<String, Object>> adapterList;
	Map<String, Object> adapterListMap;
	ListticketOrderAdapter adapter;
	
	private void init()
	{
		ticket_history_listview = (ListView)getActivity().findViewById(R.id.ticket_history_listview);
		sp = getActivity()
				.getSharedPreferences(
						"sp",
						Context.MODE_PRIVATE);
		editor = sp.edit();
		
		
		
		adapterList = new ArrayList<HashMap<String, Object>>();

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_ticket_history, null);
		
		return view;
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if(CheckLogin.logined(this))
		{	
		init();
		
		new CustomAsynTask(getActivity())
		{
			@Override
			protected Boolean doInBackground(Void... params)
			{
				PostJSONfromGson postGson = new PostJSONfromGson();
				
				PostgetTicketOrder postgetTicketOrder = new PostgetTicketOrder();
				
				postgetTicketOrder.setUsername(sp.getString("username", null));
				
				String result = (String) postGson.GsonPost(postgetTicketOrder, "getTicketOrder");
				
				ListTicketOrder listTicketOrder = new ListTicketOrder();
				
				Type listType=new TypeToken<ListTicketOrder>(){}.getType();
				
				Gson gson = new Gson();
				
				listTicketOrder = gson.fromJson(result,listType);
				
				ticketOrderList = new ArrayList<ticketOrder>();
				
				ticketOrderList = listTicketOrder.getTicketOrderList();
				
				if (ticketOrderList==null)
				return false;
				for (int i = 0; i < ticketOrderList.size(); i++)
				{
					ticketorder = new ticketOrder();
					
					ticketorder = ticketOrderList.get(i);
				
					adapterListMap = new HashMap<String, Object>();
					
					adapterListMap.put("id", ticketorder.getTicketOrderID());
					
					adapterListMap.put("way", ticketorder.getTicketDirection());
					
					adapterListMap.put("count", ticketorder.getTicketCount());
					
					adapterListMap.put("date", ticketorder.getCreateTime());
					
					adapterListMap.put("price", ticketorder.getTotalCost());
					
					adapterList.add(i,(HashMap<String, Object>) adapterListMap);
					
				}
				
			
				Boolean success = listTicketOrder.getSuccess();
				
				msg = listTicketOrder.getMsg();
				
				return success;
				
			}
			
			protected void onPostExecute(Boolean result)
			{
				super.onPostExecute(result);
				
				
				try
				{
					if (result)
					{
						Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
						
						adapter = new ListticketOrderAdapter(getActivity(),getActivity(), adapterList,
								R.layout.ticket_history_content, new String[]
								{ "id", "way","count","date","price" }, new int[]
								{ R.id.ticket_history_id,
										R.id.ticket_history_way,R.id.ticket_history_count,R.id.ticket_history_date,R.id.ticket_history_price});
						
						ticket_history_listview.setAdapter(adapter);
					}
					
					else {
						Toast.makeText(getActivity(), "暂无订票记录！", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					super.onCancelled();
					e.printStackTrace();
				}
			}
			
		}.execute();
		}
	}
}
