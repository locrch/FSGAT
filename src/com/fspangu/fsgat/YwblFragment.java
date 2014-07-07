package com.fspangu.fsgat;

import java.util.ArrayList;
import java.util.HashMap;

import com.pangu.neusoft.fsgat.core.CheckLogin;
import com.pangu.neusoft.fsgat.user.LoginFragment;
import com.pangu.neusoft.fsgat.visa.ApplyVisaFragment;
import com.pangu.neusoft.fsgat.visa.BookingFragment;
import com.pangu.neusoft.fsgat.visa.ShowHistoryFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public  class YwblFragment extends Fragment {
	
	private static String text1="申请签注(资料填写方式)";
	private static String text2="申请签注(照相方式)";
	private static String text3="预约办证";
	private static String text4="受理记录";
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	
    	if(CheckLogin.logined(this)){
    	
		        View view = inflater.inflate(R.layout.ywbl_fragment, null);
		        this.getActivity().setTitle("业务办理");
		        GridView gridview = (GridView)view.findViewById(R.id.ywbl_grid);
		        // 创建一个数组列表对象
		        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		        /**
		         * 为每个格子添加内容
		         */
		        for (int i = 1; i < 5; i++) {
		            HashMap<String, Object> map = new HashMap<String, Object>();// 建立hashmap对象
		
		            if (i == 1) {
		            	map.put("ItemImage", R.drawable.apply_visa1);
		                map.put("ItemText", text1);
		            }
		
		            if (i == 2) {
		            	map.put("ItemImage", R.drawable.apply_visa2);
		            	map.put("ItemText", text2);
		            }
		
		            if (i == 3) {
		            	map.put("ItemImage", R.drawable.apply_visa3);
		            	map.put("ItemText", text3);
		            }
		
		            if (i == 4) {
		            	map.put("ItemImage", R.drawable.user_main_history_btn_img);
		            	map.put("ItemText", text4);
		            }
		
		            lstImageItem.add(map);
		        }
		
		        /**
		         * 为GridView建立SimpleAdapter适配器
		         */
		        // SimpleAdapter()中的五个参数分别是：第一个context，第二个数据资源，第三个每一个子项的布局文件，第四个每一个子项中的Key数组
		        // 第五个每一个子项中的Value数组
		        SimpleAdapter saImageItems = new SimpleAdapter(this.getActivity(), lstImageItem,
		                R.layout.grid_item, new String[] { "ItemImage", "ItemText" },
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
		                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
		   				 	ApplyVisaFragment fragment=new ApplyVisaFragment();
		                	transaction.add(R.id.content, fragment);
		                	transaction.addToBackStack(null);
		                    transaction.commit();
		                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text2)){
		                	Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
		                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text3)){
		                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
		   				 	BookingFragment fragment=new BookingFragment();
		                	transaction.add(R.id.content, fragment);
		                	transaction.addToBackStack(null);
		                    transaction.commit();
		                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text4)){
		                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
		                	ShowHistoryFragment fragment=new ShowHistoryFragment();
		                	transaction.add(R.id.content, fragment);
		                	transaction.addToBackStack(null);
		                    transaction.commit();
		                }
		               
		            }
		        });// 为每一个子项设置监听
		        return view;
    	}
    	return null;
    }  
  
  
}  