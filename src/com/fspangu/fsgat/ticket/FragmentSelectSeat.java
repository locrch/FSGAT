package com.fspangu.fsgat.ticket;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fspangu.fsgat.R;
import com.fspangu.fsgat.ticket.SeatView.ZoomChangeListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.PostJSONfromGson;
import com.pangu.neusoft.fsgat.model.ListSeatNumber;
import com.pangu.neusoft.fsgat.model.PostgetSeatNumber;
import com.pangu.neusoft.fsgat.model.PutReturnseatNumbersList;
import com.pangu.neusoft.fsgat.model.PutseatNumbersList;
import com.pangu.neusoft.fsgat.model.seatNumber;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSelectSeat extends Fragment{
	public static int ROW =15;// 设置最大列数
	public static int COL = 20;// 设置最大行数
	
	
	
	private ProgressDialog proDialog;
	private TextView yingmuTextView;
	int p1;
	protected static int width;
	protected static int height;
	StringBuilder seat = new StringBuilder();
	List<Integer> buySeatList = new ArrayList<Integer>();
	List<Integer> returnbuySeatList = new ArrayList<Integer>();
	SeatView seatView;
	seatNumber seatnumber;
	List<Integer> seatNumbersList;
	ArrayList<seatNumber> GetseatNumbersList;
	
	Button submit_seat_btn;
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setVisibility(View.INVISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("选择座位");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.layout_select_seat, null);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		submit_seat_btn = (Button)getActivity().findViewById(R.id.submit_seat_btn);
		
		initView();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		
		
		width=(displayMetrics.widthPixels);
		height=(displayMetrics.heightPixels);
		new Thread(runnable).start();
		
		
		
		submit_seat_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				List<Integer> mySeatList_1 = new ArrayList<Integer>();
				
				for (int i = 0; i < seatView.mySeatList.size(); i++)
				{
					mySeatList_1.add(seatView.mySeatList.get(i)+1);
				}
				
				if (getArguments().getString("key").equals("back")) {
					returnbuySeatList.addAll(mySeatList_1);
					PutReturnseatNumbersList.setSeatNumbersList((ArrayList<Integer>) returnbuySeatList);
					
				}
				else {
					
					buySeatList.addAll(mySeatList_1);
					PutseatNumbersList.setSeatNumbersList((ArrayList<Integer>) buySeatList);
				    
				}
				
				PutseatNumbersList.setSeatNumbersList((ArrayList<Integer>) buySeatList);
			    
				
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				
				fragmentManager.popBackStack();
				transaction.commit();
			}
		});
		
	}

	private void initView() {
		yingmuTextView = (TextView) getActivity().findViewById(R.id.yingmu);
		yingmuTextView.setText("前    车    窗");
		proDialog = ProgressDialog.show(getActivity(), "加载",
				"加载数据中，请稍候...", true, true);
		proDialog.setCanceledOnTouchOutside(false);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.arg1) {
			case 1:
				proDialog.dismiss();
				updateUI();
				break;
			}
		}
	};

	/**
	 * 显示所有座位
	 */
	private void updateUI() {
		LinearLayout myView = (LinearLayout) getActivity().findViewById(R.id.seatviewcont);
		myView.removeAllViews();
		
		GetseatNumbersList = new ArrayList<seatNumber>();
		
		if (TicketMainFragment.seatNumbersList!=null) {
			
			if (getArguments().getString("key").equals("back")) {
				GetseatNumbersList.addAll(TicketMainFragment.ReturnseatNumbersList);
				
			}
			else {
				GetseatNumbersList.addAll(TicketMainFragment.seatNumbersList);
				
			}
			
		}
		
		seatView = new SeatView(getActivity(),GetseatNumbersList);
		myView.addView(seatView);
		
		
		seatView.setZoomChangeListener(new ZoomChangeListener() {
			public void ZoomChange(int box_size) {
				// TODO Auto-generated method stub
				LinearLayout myView2 = (LinearLayout)getActivity().findViewById(R.id.seatraw);
				myView2.removeAllViews();
				for (int i = 0; i < 12; i++) {
					TextView textView = new TextView(
							getActivity());
					textView.setText((i + 1) + "");
					textView.setTextSize(9.0f);
					textView.setGravity(Gravity.CENTER_VERTICAL);
					textView.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT, box_size));
					myView2.addView(textView);
				}
				
				
			}
		});
	}

	Runnable runnable = new Runnable() {
		public void run() {
			Message msg = handler.obtainMessage();
			msg.arg1 = 1;
			handler.sendMessage(msg);
		}
	};

	private HashMap<String, Integer> toRowAndCol(int SeatListCount) {
		int row = 1;
		int col = 1;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		SeatListCount =SeatListCount+1;
		
		if (SeatListCount>=1&&SeatListCount<=5) {
			row = 1;
			col = SeatListCount;
		}else if (SeatListCount>5&&SeatListCount<=10) {
			row = 2;
			col = SeatListCount-5;
		}else if (SeatListCount>10&&SeatListCount<=15) {
			row = 3;
			col = SeatListCount-10;
		}else if (SeatListCount>15&&SeatListCount<=20) {
			row = 4;
			col = SeatListCount-15;
		}else if (SeatListCount>20&&SeatListCount<=25) {
			row = 5;
			col = SeatListCount-20;
		}else if (SeatListCount>25&&SeatListCount<=30) {
			row = 6;
			col = SeatListCount-25;
		}else if (SeatListCount>30&&SeatListCount<=35) {
			row = 7;
			col = SeatListCount-30;
		}else if (SeatListCount>35&&SeatListCount<=40) {
			row = 8;
			col = SeatListCount-35;
		}else if (SeatListCount>40&&SeatListCount<=45) {
			row = 9;
			col = SeatListCount-40;
		}else if (SeatListCount>45&&SeatListCount<=50) {
			row = 10;
			col = SeatListCount-45;
		}else if (SeatListCount>50&&SeatListCount<=55) {
			row = 11;
			col = SeatListCount-50;
		}else if (SeatListCount>55&&SeatListCount<=60) {
			row = 12;
			col = SeatListCount-55;
		}
		
		map.put("row", row);
		map.put("col", col);
		
		return map;
		
	}
	
	
		
	}

