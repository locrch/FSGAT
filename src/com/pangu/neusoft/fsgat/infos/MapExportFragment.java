package com.pangu.neusoft.fsgat.infos;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import android.R.interpolator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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


public  class MapExportFragment extends Fragment {
	
	MapView mMapView = null; 
	BaiduMap mBaiduMap=null;
	
	InfoWindow mInfoWindow;
	LayoutInflater inflaters;
	Spinner spinner1;
	TextView textView1;
	
	Map<String,BanZhengDT> places=new LinkedHashMap<String,BanZhengDT>();
	Map<Marker,String> markerstring=new LinkedHashMap<Marker,String>();
	
	String export="hk";
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("通关口岸");
	}
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	setHasOptionsMenu(true);
    	SDKInitializer.initialize(getActivity().getApplicationContext());
    	this.inflaters=inflater;
    	View view = inflater.inflate(R.layout.viewmap, null);
//    	android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("通关口岸分布及路线指引");
    	showid=1;
    	this.getActivity().setTitle("通关口岸分布及路线指引");
    	if(CheckNetwork.connected(this)){
	    	textView1=(TextView)view.findViewById(R.id.textView1);
	    	spinner1=(Spinner)view.findViewById(R.id.spinner1);
	    	export = getArguments().getString("export");
	    	Log.e("export",export);
	    	textView1.setText("请选择通关口岸：");
	    	setPlace();
	    	getSpinnerPlace();
	    	
	    	mMapView = (MapView) view.findViewById(R.id.bmapView);  
	    	mBaiduMap = mMapView.getMap();
	    	
	    	//普通地图  
	    	mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
	    	
	    	showMarker(0);
	    	
	    	mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				public boolean onMarkerClick(final Marker marker) {
					View view = inflaters.inflate(R.layout.map_marker, null);
					
					TextView button=(TextView)view.findViewById(R.id.textView1);
					
					final LatLng ll = marker.getPosition();
					Point p = mBaiduMap.getProjection().toScreenLocation(ll);
					p.y -= 47;
					LatLng llInfo = mBaiduMap.getProjection().fromScreenLocation(p);
					OnInfoWindowClickListener listener = null;
					
					String res=markerstring.get(marker);
					button.setText(res);
					listener = new OnInfoWindowClickListener() {
						public void onInfoWindowClick() {
							
							LatLng llNew = new LatLng(ll.latitude + 0.005,
									ll.longitude + 0.005);
							marker.setPosition(llNew);
							mBaiduMap.hideInfoWindow();
							
						}
					};
					mInfoWindow = new InfoWindow(view, llInfo, listener);
					mBaiduMap.showInfoWindow(mInfoWindow);
					return true;
				}
			});
    	}
        return view;  
    }  
  
    int showid=1;
    public Marker setMarker(double a,double b,String text){
    	//定义Maker坐标点  
    	LatLng point = new LatLng(a, b);  
    	//构建Marker图标  
    	BitmapDescriptor bitmap = BitmapDescriptorFactory  
    	    .fromResource(R.drawable.icon_mark1);  
    	//构建MarkerOption，用于在地图上添加Marker  
    	try{
    	Resources resources=getResources();
    	int id=resources.getIdentifier(getActivity().getPackageName()+":drawable/"+"icon_mark"+showid, null,null);
    	
    	bitmap = BitmapDescriptorFactory.fromResource(id); 
    	}catch(Exception rx){  }
    	if(bitmap==null){
    		bitmap = BitmapDescriptorFactory  
            	    .fromResource(R.drawable.icon_mark1);
    	}
    	OverlayOptions option = new MarkerOptions()  
    	    .position(point)  
    	    .icon(bitmap);  
    	//在地图上添加Marker，并显示  
    	Marker marker=(Marker)mBaiduMap.addOverlay(option);
    	showid++;

    	return marker;
    }
    
    
public void setPlace(){
	int exportid=R.string.export_place_hk;
	if(export.equals("macau")){
		exportid=R.string.export_place_macau;
	}
		
	String place=getActivity().getResources().getString(exportid);
	String[] resall=place.split("\\|");

	for(String resone:resall){
		String[] items=resone.split(",");
		BanZhengDT banz=new BanZhengDT();
		banz.setName(items[1]);
		banz.setAddress(items[2]);
		banz.setLot(Float.parseFloat(items[3]));
		banz.setLat(Float.parseFloat(items[4]));
		banz.setPhone(items[5]);
		places.put(items[0], banz);		
	}
}    
    
Map<String,String> obj=new LinkedHashMap<String,String>();
public void getSpinnerPlace(){
	obj.put("查看全部", "0");
	for(String key:places.keySet()){
		obj.put(places.get(key).getName(), key);
	}	
	String[] mItems = new String[obj.size()];
	int i=0;
	for(String key:obj.keySet()){
		mItems[i]=key;
		i++;
	}
	
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.dropdowntext, mItems);
	//绑定 Adapter到控件
	adapter.setDropDownViewResource(R.layout.drop_down_item);
	spinner1.setAdapter(adapter);
	spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){


		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			showMarker(position);
			showid=1;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			showMarker(0);
		}
		
	});
}
    
public void showMarker(int i){
	mBaiduMap.clear();
	showid=1;
	if(i==0){
		
		for(BanZhengDT banz:places.values()){
			Marker marker=setMarker(banz.getLat(),banz.getLot(),banz.getAddress());
			markerstring.put(marker, banz.getName()+"\n通关时间："+banz.getPhone()+" \n地址："+banz.getAddress());
		}	
		LatLng ptCenter = new LatLng(22.53496d,114.124783d);//设置禅城为中心
		if(export.equals("macau")){
			ptCenter = new LatLng(22.221558d,113.560588d);//设置禅城为中心
		}else{
			ptCenter = new LatLng(22.53496d,114.124783d);//设置禅城为中心
			
		}
		
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ptCenter ));
	}else{
		String a=spinner1.getItemAtPosition(i).toString();
	
		BanZhengDT banz=places.get(obj.get(a));
		if(banz!=null){
			Marker marker=setMarker(banz.getLat(),banz.getLot(),banz.getAddress());
			markerstring.put(marker, banz.getName()+"\n通关时间："+banz.getPhone()+" \n地址："+banz.getAddress());
			LatLng ptCenter = new LatLng(banz.getLat(),banz.getLot());//设置禅城为中心
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ptCenter ));
		}
	}
}


    
    @Override
	public void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(mMapView!=null)
        mMapView.onDestroy();  
    }  
    @Override
	public void onResume() {  
        super.onResume();  
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        if(mMapView!=null)
        mMapView.onResume();  
    }  
    @Override
	public void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        if(mMapView!=null)
        mMapView.onPause();  
    }
   
}  