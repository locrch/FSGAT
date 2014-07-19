package com.fspangu.fsgat;

import java.util.ArrayList;
import java.util.HashMap;

import com.pangu.neusoft.fsgat.infos.ExportStateFragment;
import com.pangu.neusoft.fsgat.infos.GuideFragment;
import com.pangu.neusoft.fsgat.infos.KnowledgeFragment;
import com.pangu.neusoft.fsgat.infos.LawsFragment;
import com.pangu.neusoft.fsgat.infos.MapExportFragment;
import com.pangu.neusoft.fsgat.infos.MapPlaceFragment;
import com.pangu.neusoft.fsgat.infos.RealTimeApplyFragment;
import com.pangu.neusoft.fsgat.infos.SearchApplyFragment;
import com.pangu.neusoft.fsgat.visa.ApplyVisaFragment;
import com.pangu.neusoft.fsgat.visa.BookingFragment;
import com.pangu.neusoft.fsgat.visa.ShowHistoryFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public  class ZhcxFragment extends Fragment {
	private static String text1="受理大厅";
	private static String text2="办证地点";
	private static String text3="通关口岸";
	private static String text4="口岸状态";
	private static String text5="受理进度";
	private static String text6="办事指南";
	private static String text7="法律法规";
	private static String text8="证件知识";
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	View view = inflater.inflate(R.layout.ywbl_fragment, null);
//    	android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("综合查询");
    	this.getActivity().setTitle("综合查询");
    	
        GridView gridview = (GridView)view.findViewById(R.id.ywbl_grid);
        // 创建一个数组列表对象
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));

        /**
         * 为每个格子添加内容
         */
        for (int i = 1; i <= 8; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();// 建立hashmap对象

            if (i == 1) {
            	map.put("ItemImage", R.drawable.zhcx_dating_img);
                map.put("ItemText", text1);
            }

            if (i == 2) {
            	map.put("ItemImage", R.drawable.zhcx_didian_img);
            	map.put("ItemText", text2);
            }

            if (i == 3) {
            	map.put("ItemImage", R.drawable.zhcx_kouan_img);
            	map.put("ItemText", text3);
            }

            if (i == 4) {
            	map.put("ItemImage", R.drawable.zhcx_zhuangtai_img);
            	map.put("ItemText", text4);
            }
            if (i == 5) {
            	map.put("ItemImage", R.drawable.zhcx_jindu_img);
            	map.put("ItemText", text5);
            }
            if (i == 6) {
            	map.put("ItemImage", R.drawable.zhcx_zhinan_img);
            	map.put("ItemText", text6);
            }
            if (i == 7) {
            	map.put("ItemImage", R.drawable.zhcx_falv_img);
            	map.put("ItemText", text7);
            }
            if (i == 8) {
            	map.put("ItemImage", R.drawable.zhcx_zhishi_img);
            	map.put("ItemText", text8);
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
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	RealTimeApplyFragment fragment=new RealTimeApplyFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text2)){
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	MapPlaceFragment fragment=new MapPlaceFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text3)){
                	showExport();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text4)){
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	ExportStateFragment fragment=new ExportStateFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text5)){
                	
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	SearchApplyFragment fragment=new SearchApplyFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text6)){
                	
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	GuideFragment fragment=new GuideFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text7)){
                	
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	LawsFragment fragment=new LawsFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").equals(text8)){
                	
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	KnowledgeFragment fragment=new KnowledgeFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }
               
            }
        });// 为每一个子项设置监听
    
   
        return view;  
    }  
    
    
    int exportid=0;
    public void showExport(){
    	
 		final String[] visas = new String[] { "香港", "澳门" };
    		Dialog alertDialog = new AlertDialog.Builder(getActivity()).				
			    setTitle("请选择您想去的地方？").
			    setIcon(R.drawable.ic_launcher)
			    .setSingleChoiceItems(visas, 0, new DialogInterface.OnClickListener() {
			     @Override
			     public void onClick(DialogInterface dialog, int which) {
			    	exportid=which; 
			     }
			    }).
			    setPositiveButton("确认", new DialogInterface.OnClickListener() {

			     @Override
			     public void onClick(DialogInterface dialog, int which) {
			    	// Toast.makeText(getActivity(), visas[selectedFruitIndex], Toast.LENGTH_SHORT).show();
			    	 FragmentTransaction transaction = getFragmentManager().beginTransaction();
	                	MapExportFragment fragment=new MapExportFragment();   
	                	
			    	 if(exportid==1){
		                	Bundle args = new Bundle();  
		                    args.putString("export", "macau");  
		                    fragment.setArguments(args);                    
			    	 }else{
		                	Bundle args = new Bundle();  
		                    args.putString("export", "hk");  
		                    fragment.setArguments(args);                    
			    	 }
			    	 transaction.add(R.id.content, fragment);
	                	transaction.addToBackStack(null);
	                    transaction.commit();
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

}  