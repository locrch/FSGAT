package com.fspangu.fsgat;

import java.util.ArrayList;
import java.util.HashMap;

import com.pangu.neusoft.fsgat.core.CheckLogin;
import com.pangu.neusoft.fsgat.infos.ExportStateFragment;
import com.pangu.neusoft.fsgat.infos.MapExportFragment;
import com.pangu.neusoft.fsgat.infos.MapPlaceFragment;
import com.pangu.neusoft.fsgat.infos.RealTimeApplyFragment;
import com.pangu.neusoft.fsgat.user.*;
import com.pangu.neusoft.fsgat.visa.ShowHistoryFragment;
import com.pangu.neusoft.tools.update.UpdateOperation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public  class GrzxFragment extends Fragment {  
	private static String text1="签注记录";
	private static String text2="证件管理";
	private static String text3="地址管理";
	private static String text4="密码修改";
	private static String text5="注销用户";
	private static String text6="我的设置";
	private static String text7="检查更新";
	private static String text8="意见反馈";
	private static String text9="关于我们";
	public static int  updated=1;
	public static boolean showupdate=true;
	SharedPreferences sp;
	Editor editor;
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	
    	sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
    	if(CheckLogin.logined(this)){
    	View view = inflater.inflate(R.layout.ywbl_fragment, null);
    	
//    	android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("个人中心");
    	
    	
    	this.getActivity().setTitle("个人中心");
        GridView gridview = (GridView)view.findViewById(R.id.ywbl_grid);
        // 创建一个数组列表对象
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));

        /**
         * 为每个格子添加内容
         */
        for (int i = 1; i <= 9; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();// 建立hashmap对象

            if (i == 1) {
            	map.put("ItemImage", R.drawable.user_main_history_btn_img);
                map.put("ItemText", text1);
            }

            if (i == 2) {
            	map.put("ItemImage", R.drawable.user_main_listpass_btn_img);
            	map.put("ItemText", text2);
            }

            if (i == 3) {
            	map.put("ItemImage", R.drawable.user_main_listaddress_btn_img);
            	map.put("ItemText", text3);
            }

            if (i == 4) {
            	map.put("ItemImage", R.drawable.user_main_changepassword_btn_img);
            	map.put("ItemText", text4);
            }
            if (i == 5) {
            	map.put("ItemImage", R.drawable.user_main_changeusername_btn_img);
            	map.put("ItemText", text5);
            }
            
            if (i == 6) {
            	map.put("ItemImage", R.drawable.user_main_mysetting_btn_img);
            	map.put("ItemText", text6);
            }
            if (i == 7) {
            	map.put("ItemImage", R.drawable.user_main_update_btn_img);
            	map.put("ItemText", text7);
            }
            if (i == 8) {
            	map.put("ItemImage", R.drawable.user_main_opinion_btn_img);
            	map.put("ItemText", text8);
            }
            if (i == 9) {
            	map.put("ItemImage", R.drawable.user_main_info_btn_img);
            	map.put("ItemText", text9);
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
        gridview.setClickable(true);
        gridview.setOnItemClickListener(new OnItemClickListener(){
        	@SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
                                                        // click happened
                    View arg1,// The view within the AdapterView that was clicked
                    int arg2,// The position of the view in the adapter
                    long arg3// The row id of the item that was clicked
            ) {
        		
                HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                //Log.e("testx",text1+"  ***   "+item.get("ItemText").toString());
                if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").toString().equals(text1)){
                	
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	ShowHistoryFragment fragment=new ShowHistoryFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").toString().equals(text2)){
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	ListPassFragment fragment=new ListPassFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").toString().equals(text3)){
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	ListAddressFragment fragment=new ListAddressFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").toString().equals(text4)){
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	ChangePasswordFragment fragment=new ChangePasswordFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").toString().equals(text5)){
                	
                	logout();
                    
                    
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").toString().equals(text6)){
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	MysettingFragment fragment=new MysettingFragment();
                	transaction.add(R.id.content, fragment);
                	transaction.addToBackStack(null);
                    transaction.commit();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").toString().equals(text7)){
                	UpdateOperation update = new UpdateOperation(getActivity());
            		update.checkUpdate(true);
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").toString().equals(text8)){
//                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                	OpinionFragment fragment=new OpinionFragment();
//                	transaction.add(R.id.content, fragment);
//                	transaction.addToBackStack(null);
//                    transaction.commit();
                    Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                }else if(item!=null&&item.get("ItemText")!=null&&item.get("ItemText").toString().equals(text9)){
                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
                	VersionInfoFragment fragment=new VersionInfoFragment();
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

    
    public void logout(){
    	 Dialog alertDialog = new AlertDialog.Builder(getActivity()). 
	                setTitle("注销"). 
	                setMessage("确定要注销用户吗?"). 
	                setIcon(R.drawable.ic_launcher). 
	                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
	                    @Override 
	                    public void onClick(DialogInterface dialog, int which) { 
	                    	
	                        editor.remove("username");
	                		editor.commit();
	                		FragmentTransaction transaction = getFragmentManager().beginTransaction();
	       				 	LoginFragment fragment=new LoginFragment();
	                    	transaction.replace(R.id.content, fragment);	                  
	                        transaction.commit();
	                    } 
	                }). 
	                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
	                     
	                    @Override 
	                    public void onClick(DialogInterface dialog, int which) { 
	                        // TODO Auto-generated method stub
	                    	
	                    } 
	                }).create(); 
	        alertDialog.show(); 
    }
  
}  