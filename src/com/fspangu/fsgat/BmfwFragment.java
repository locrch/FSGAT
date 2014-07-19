package com.fspangu.fsgat;

import java.util.ArrayList;
import java.util.HashMap;

import com.fspangu.fsgat.ticket.TicketHistoryFragment;
import com.fspangu.fsgat.ticket.TicketMainFragment;
import com.pangu.neusoft.fsgat.core.CheckLogin;
import com.pangu.neusoft.fsgat.infos.ExportStateFragment;
import com.pangu.neusoft.fsgat.infos.GuideFragment;
import com.pangu.neusoft.fsgat.infos.KnowledgeFragment;
import com.pangu.neusoft.fsgat.infos.LawsFragment;
import com.pangu.neusoft.fsgat.infos.MapPlaceFragment;
import com.pangu.neusoft.fsgat.infos.RealTimeApplyFragment;
import com.pangu.neusoft.fsgat.infos.SearchApplyFragment;
import com.pangu.neusoft.fsgat.user.LoginFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public  class BmfwFragment extends Fragment {
	private static String text1="车票预订";
	private static String text2="订票记录";
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	
    	
    	View view = inflater.inflate(R.layout.ywbl_fragment, null); 
//    	android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("便民服务");
        this.getActivity().setTitle("便民服务");
       
        GridView gridview = (GridView)view.findViewById(R.id.ywbl_grid);
        // 创建一个数组列表对象
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));

        /**
         * 为每个格子添加内容
         */
        for (int i = 1; i <= 2; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();// 建立hashmap对象

            if (i == 1) {
            	map.put("ItemImage", R.drawable.bmfw_booking_ticket_img);
                map.put("ItemText", text1);
            }

            if (i == 2) {
            	map.put("ItemImage", R.drawable.bmfw_history_ticket_img);
            	map.put("ItemText", text2);
            }

           
            lstImageItem.add(map);
        }

        /**
         * 为GridView建立SimpleAdapter适配器
         */
        // SimpleAdapter()中的五个参数分别是：第一个context，第二个数据资源，第三个每一个子项的布局文件，第四个每一个子项中的Key数组
        // 第五个每一个子项中的Value数组
        SimpleAdapter saImageItems = new SimpleAdapter(this.getActivity(), lstImageItem,
                R.layout.grid_item_grzx, new String[] { "ItemImage", "ItemText" },
                new int[] { R.id.ItemImage, R.id.ItemText });
        gridview.setAdapter(saImageItems);// 添加适配器
        gridview.setOnItemClickListener(new OnItemClickListener(){
        	@SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
                                                        // click happened
                    View arg1,// The view within the AdapterView that was clicked
                    int arg2,// The position of the view in the adapter
                    long arg3// The row id of the item that was clicked
            ) {
        		
                HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                
                if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text1)){
                	FragmentTransaction transaction =getFragmentManager().beginTransaction(); 
    				TicketMainFragment tm = new TicketMainFragment();
    				transaction.replace(R.id.content, tm); 
    				transaction.addToBackStack(null);
    	            transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text2)){
                	FragmentTransaction transaction =getFragmentManager().beginTransaction(); 
                	TicketHistoryFragment thf = new TicketHistoryFragment();
        			transaction.replace(R.id.content, thf); 
        			transaction.addToBackStack(null);
        			transaction.commit();
                }
               
            }
        });// 为每一个子项设置监听
    
   
        
//        Button ticket_booking_btn = (Button)view.findViewById(R.id.ticket_booking_btn);
//        Button ticket_history_btn = (Button)view.findViewById(R.id.ticket_history_btn);
//        
//        
//    	ticket_booking_btn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				FragmentTransaction transaction =getFragmentManager().beginTransaction(); 
//				TicketMainFragment tm = new TicketMainFragment();
//				transaction.replace(R.id.content, tm); 
//				transaction.addToBackStack(null);
//	            transaction.commit();
//	        }
//		});
//    	
//    	ticket_history_btn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				FragmentTransaction transaction =getFragmentManager().beginTransaction(); 
//				TicketHistoryFragment thf = new TicketHistoryFragment();
//				transaction.replace(R.id.content, thf); 
//				transaction.addToBackStack(null);
//	            transaction.commit();
//	            
//			}
//		});
        
        return view;  
    }  
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    	
    	
    	
    	
    	
    }
}  