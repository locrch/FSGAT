package com.pangu.neusoft.fsgat.infos;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.core.PostJsonObject;
import com.pangu.neusoft.fsgat.core.StringMethods;
import com.pangu.neusoft.fsgat.model.Address;
import com.pangu.neusoft.fsgat.model.BanZhengDT;
import com.pangu.neusoft.fsgat.model.History;
import com.pangu.neusoft.fsgat.model.ListPass;
import com.pangu.neusoft.fsgat.model.Pass;
import com.baidu.mapapi.map.Marker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public  class ExportStateFragment extends Fragment {
	
	ListView list;
	Spinner spinner;
	private ProgressDialog mProgressDialog;
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("口岸状态");
	}
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	setHasOptionsMenu(true);
    	View view = inflater.inflate(R.layout.exportstate, null);
//    	android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("通关口岸状态查询");
    	mProgressDialog = new ProgressDialog(getActivity());   
        mProgressDialog.setMessage("正在加载数据...");   
        mProgressDialog.setIndeterminate(false);  
        mProgressDialog.setCanceledOnTouchOutside(false);//设置进度条是否可以按退回键取消  
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);   
    	
    	this.getActivity().setTitle("通过口岸状态查询");
    	if(CheckNetwork.connected(this)){
	    	list=(ListView)view.findViewById(R.id.listView1);
	    	spinner=(Spinner)view.findViewById(R.id.spinner1);
	    	String[] mItems=new String[]{"深圳","珠海"};
	    	
	    	ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),R.layout.dropdowntext, mItems);
	    	//绑定 Adapter到控件
	    	adapter.setDropDownViewResource(R.layout.drop_down_item);
	    	spinner.setAdapter(adapter);
	    	spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
	    		@Override
	    		public void onItemSelected(AdapterView<?> parent, View view,
	    				int position, long id) {
	    			// TODO Auto-generated method stub
	    			if(position==0){
	    				getShenZhenDatas();
	    			}else{
	    				getZhuHaiDatas();
	    			}
	    		}
	
	    		@Override
	    		public void onNothingSelected(AdapterView<?> parent) {
	    			// TODO Auto-generated method stub
	    			getShenZhenDatas();
	    		}
	    		
	    	});
    	}
    	//getShenZhenDatas();
        return view;  
    }  
    
    List<String> listdatazhu=new ArrayList<String>();
    ArrayList<Map<String,Object>> listdata= new ArrayList<Map<String,Object>>();
    
    AsyncTask<Void, Void, Boolean> loading;
    public void getShenZhenDatas(){
        listdata.clear();
        loading=new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
		    	try{

					if(mProgressDialog.isShowing()){
						mProgressDialog.dismiss();
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
		    	super.onPreExecute();   
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				 
				try{    		
					
			   		String url=getActivity().getResources().getString(R.string.shenzhenexport);      
			   		 Document doc = Jsoup.connect(url).get();
			   		 Element select=doc.getElementById("portStatus");
			   		 Elements links=select.getElementsByTag("li");
			   		 for (Element link : links) {
			   			String text=link.text().replace(Jsoup.parse("&nbsp;").text(), "");
			   			String export=text.replace("顺畅","").replace("正常","").replace("繁忙","");
			   			int drawable;
			   			if(text.contains("顺畅")){
			   				drawable=R.drawable.export_status_fast;
			   			}else if(text.contains("繁忙")){
			   				drawable=R.drawable.export_status_busy;
			   			}else{
			   				drawable=R.drawable.export_status_normal;
			   			}
			   				
			   			Map<String,Object> item = new HashMap<String,Object>();  
			   	        item.put("status", drawable);  
			   	        item.put("export", export);  
			   			listdata.add(item);
			   		 } 
			   		        
			   	}catch(Exception ex){
			   		ex.printStackTrace();
			   	}
				return true;
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
					try{
					SimpleAdapter adapter = new SimpleAdapter(getActivity(),listdata,R.layout.export_status_item,  
						        new String[]{"export","status"},new int[]{R.id.export,R.id.status});  
					list.setAdapter(adapter);
					}catch(Exception ex){
						
					}
				}else{
                	Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
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
		loading.execute();
    	
    } 
    
    
    @Override
   	public void onPause(){
       	if(loading!=null){
       		loading.cancel(false);
       		try{

				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
       	}
       	super.onDestroyView();
       }
  
    public void getZhuHaiDatas(){
    	listdata.clear();
    	loading=new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
		    	try{

					if(mProgressDialog.isShowing()){
						mProgressDialog.dismiss();
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
	            super.onPreExecute();   
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				 
				try{    		
					
							String urls=getActivity().getResources().getString(R.string.zhuhaiexport);  
							String[] urllist=urls.split("\\|");
							for(String url:urllist){
								Document doc = Jsoup.connect(url.trim()).get();
								Element select=doc.getElementById("ctl00_ContentPlaceHolder1_PortInfo1_kadmslab");
								String state="正常";
								
								Element select2=doc.getElementById("ctl00_ContentPlaceHolder1_PortInfo1_Label2");
								Elements links = select2.select("img[src$=.gif]");
								int drawable=R.drawable.export_status_normal;
			   	                    for (Element link : links) {
			   	                        String imagesPath = link.attr("src");	
			   	                        if(imagesPath.equals("images/tj/bulb1.gif"))
			   	                        	state="繁忙";
			   	                        else if(imagesPath.equals("images/tj/bulb2.gif"))
			   	                        	state="顺畅";
			   	                        else if(imagesPath.equals("images/tj/bulb4.gif"))
			   	                        	state="闭关";
				   	                    else
			 	                        	state="正常";
			   	                        
			   	                         
				   	                    if(state.contains("繁忙")){
				   	                    	drawable=R.drawable.export_status_busy;
				   	                    }
				   	                    else if(state.contains("顺畅")){
				   	                    	drawable=R.drawable.export_status_fast;
				   	                    }else{
				   	                    	drawable=R.drawable.export_status_normal;
				   	                    }
				 			   			//listdatazhu.add(select.text()+" "+state);
			   	                    }
			   	                    
			   	                 Map<String,Object> item = new HashMap<String,Object>();  
			 			   	        item.put("status", drawable);  
			 			   	        item.put("export", select.text());  
			 			   			listdata.add(item);
							}
			   		        
			   	}catch(Exception ex){
			   		ex.printStackTrace();
			   	}
				return true;
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
					try{
					 SimpleAdapter adapter = new SimpleAdapter(getActivity(),listdata,R.layout.export_status_item,  
						        new String[]{"export","status"},new int[]{R.id.export,R.id.status});  
					list.setAdapter(adapter);
					
						//list.setAdapter(new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, listdatazhu));
					}catch(Exception ex){
						
					}
                }else{
                	Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
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
    	loading.execute();
    	
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
}  