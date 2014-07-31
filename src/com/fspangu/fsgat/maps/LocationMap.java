package com.fspangu.fsgat.maps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;













import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfigeration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MyLocationConfigeration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOvelray;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.fspangu.fsgat.R;
import com.pangu.neusoft.fsgat.model.BanZhengDT;
import com.pangu.neusoft.fsgat.user.ChangePasswordFragment;
import com.pangu.neusoft.fsgat.user.ListAddressFragment;
import com.pangu.neusoft.fsgat.user.ListPassFragment;
import com.pangu.neusoft.fsgat.user.LoginFragment;
import com.pangu.neusoft.fsgat.user.MysettingFragment;
import com.pangu.neusoft.fsgat.user.OpinionFragment;
import com.pangu.neusoft.fsgat.user.VersionInfoFragment;
import com.pangu.neusoft.fsgat.visa.ShowHistoryFragment;
import com.pangu.neusoft.tools.update.UpdateOperation;

public class LocationMap extends Activity implements OnGetRoutePlanResultListener {

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	LinearLayout showfunction;
	
	MapView mMapView;
	BaiduMap mBaiduMap;
	View viewCache;
    TextView popupText;
	
	Button requestLocButton;
	Button locButton;
	Button viewstreet;
	Button driver;
	Button transit;
	Button walk;
	
	
	boolean isFirstLoc = true;// 是否首次定位

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // 设置为圆形旋转进度条
		getActionBar().setBackgroundDrawable(
				this.getBaseContext().getResources()
						.getDrawable(R.drawable.title_background));
		getActionBar().show();
		setTitle("办证地点");
		
		SDKInitializer.initialize(getApplicationContext());
		
		setOverflowShowingAlways();
		
		
		setContentView(R.layout.activity_location);
		requestLocButton = (Button) findViewById(R.id.button1);
		locButton= (Button) findViewById(R.id.button2);
		viewstreet= (Button) findViewById(R.id.button3);
		
		 driver= (Button) findViewById(R.id.driver);
		 transit= (Button) findViewById(R.id.transit);
		 walk= (Button) findViewById(R.id.walk);
		  mBtnPre= (Button) findViewById(R.id.pre);
          mBtnNext= (Button) findViewById(R.id.next);
		 
		 mBtnNext.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				nodeClick(1);
			}
			 
		 });
		 mBtnPre.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					nodeClick(0);
				}
				 
			 });
		mCurrentMode = LocationMode.NORMAL;
		requestLocButton.setText("普通");
		showfunction=(LinearLayout)findViewById(R.id.showfunction);
		driver.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SearchButtonProcess(1);
			}
			
		});
		
		transit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SearchButtonProcess(2);
			}
			
		});
		walk.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SearchButtonProcess(3);
			}
			
		});
		
		OnClickListener locButtonListener=new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//mCurrentMode = LocationMode.FOLLOWING;
				double lat=mBaiduMap.getLocationData().latitude;
				double lon=mBaiduMap.getLocationData().longitude;
				LatLng ptCenter = new LatLng(lat,lon);//设置当前位置为中心
				mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ptCenter ));
 				
			}
			
		};
		
		OnClickListener btnClickListener = new OnClickListener() {
			public void onClick(View v) {
				switch (mCurrentMode) {
				
				case COMPASS:
					requestLocButton.setText("普通");
					mCurrentMode = LocationMode.NORMAL;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfigeration(
									mCurrentMode, true, mCurrentMarker));
					break;
				case NORMAL:
					requestLocButton.setText("罗盘");
					mCurrentMode = LocationMode.COMPASS;
					mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfigeration(
									mCurrentMode, true, mCurrentMarker));
					break;
				}
			}
		};
		viewstreet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// 全景
				Intent intent = getIntent();
				//LocationClientOption option = new LocationClientOption();
				//option.setCoorType("gcj02");//如果是百度坐标参数为 bd0911
				
				CoordinateConverter converter  = new CoordinateConverter();  
				converter.from(CoordType.COMMON); 
				
				// sourceLatLng待转换坐标  
				converter.coord(lle);  
				LatLng desLatLng = converter.convert();  
				
				double bd_lat=lle.latitude;
				double bd_lon=lle.longitude;
				double rlat; double rlon;
				    double x = bd_lon - 0.0065, y = bd_lat - 0.006;  
				    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);  
				    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);  
				    rlon = z * Math.cos(theta);  
				    rlat = z * Math.sin(theta); 
				    
				intent.putExtra("long", rlon);
				intent.putExtra("lat", rlat);
                intent.setClass(LocationMap.this, SosoStreeViewActivityTestActivity.class);   
                startActivity(intent); 
			}
			
		});
		
		requestLocButton.setOnClickListener(btnClickListener);
		locButton.setOnClickListener(locButtonListener);

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		
		LatLng ptCenter = new LatLng(22.997531d,113.121134d);//设置禅城为中心
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ptCenter ));
		setPlace();//设置所有Place
		showMarker("all");
		
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfigeration(
				mCurrentMode, true, mCurrentMarker));
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				final LatLng ll = marker.getPosition();
				Point p = mBaiduMap.getProjection().toScreenLocation(ll);
				p.y -= 47;
				String res=markerstring.get(marker);
				Toast.makeText(LocationMap.this, res, Toast.LENGTH_LONG).show();
				return true;
			}
		});
		
		
		 mSearch = RoutePlanSearch.newInstance();
	     mSearch.setOnGetRoutePlanResultListener(this);
	}

	private void setOverflowShowingAlways()
	{
		try
		{
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				lls=ll;
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mBaiduMap.hideInfoWindow();
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		super.onDestroy();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(R.layout.map_title_bar);
		Button actionbar_back_btn = (Button) findViewById(R.id.actionbar_back_btn);
		Button near_back_btn = (Button) findViewById(R.id.near);
		TextView actionbar_title=(TextView)findViewById(R.id.actionbar_title);
		actionbar_title.setText("办证地点");
		actionbar_back_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				finish();
			}
		});
		near_back_btn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v){
				choosePlace();
			}
		});
		near_back_btn.setVisibility(View.VISIBLE);
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
			
		return true;
	}

	
	public void choosePlace(){
		LinearLayout linearLayoutMain = new LinearLayout(this);//自定义一个布局文件
		linearLayoutMain.setLayoutParams(new LayoutParams(
		        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		ListView listView = new ListView(this);//this为获取当前的上下文
		listView.setFadingEdgeLength(0);
		 
		List<Map<String, String>> nameList = new LinkedList<Map<String, String>>();//建立一个数组存储listview上显示的数据
		for(BanZhengDT banz:places.values()){//initData为一个list类型的数据源
		    Map<String, String> nameMap = new HashMap<String, String>();
		    nameMap.put("name", banz.getName());
		    nameMap.put("address",banz.getAddress());
		    nameMap.put("phone",banz.getPhone());
		    LatLng point = new LatLng(banz.getLat(), banz.getLot());
		    
		    nameMap.put("distance", ""+((double)(Math.round((DistanceUtil.getDistance(lls, point))*100)))/100);
		    nameMap.put("lat",banz.getLat()+"");
		    nameMap.put("lot",banz.getLot()+"");
		    nameList.add(nameMap);
		}
		try {
			listSort(nameList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleAdapter adapter = new SimpleAdapter(LocationMap.this,
		        nameList, R.layout.place_items,
		        new String[] { "name","address","phone","distance"},
		        new int[] { R.id.name,R.id.address,R.id.phone, R.id.distance });
		listView.setAdapter(adapter);
		 
		linearLayoutMain.addView(listView);//往这个布局中加入listview
		 
		final AlertDialog dialog = new AlertDialog.Builder(this)
		        .setTitle("选择办证大厅").setView(linearLayoutMain)//在这里把写好的这个listview的布局加载dialog中
		        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
		 
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		                // TODO Auto-generated method stub
		                dialog.cancel();
		            }
		        }).create();
		dialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击
		dialog.show();
		listView.setOnItemClickListener(new OnItemClickListener() {//响应listview中的item的点击事件
		 
		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		            long arg3) {
		        // TODO Auto-generated method stub
		        TextView tv = (TextView) arg1.findViewById(R.id.name);//取得每条item中的textview控件
		        showMarker(tv.getText().toString());
		        dialog.cancel();
		    }
		});
	}
	
	 public void listSort(List<Map<String,String>> resultList) throws Exception{  
         // resultList是需要排序的list，其内放的是Map  
         // 返回的结果集  
         Collections.sort(resultList,new Comparator<Map<String,String>>() {  
 
          public int compare(Map<String, String> o1,Map<String, String> o2) {  
 
           //o1，o2是list中的Map，可以在其内取得值，按其排序，此例为升序，s1和s2是排序字段值  
              double s1 = Double.parseDouble(o1.get("distance").toString());  
              double s2 = Double.parseDouble(o2.get("distance").toString());
 
           if(s1>s2) {  
            return 1;  
           }else {  
            return -1;  
           }  
          }  
         });  
          
        } 
	
	//set 所有办证大厅
	Map<String,BanZhengDT> places=new LinkedHashMap<String,BanZhengDT>();
	Map<Marker,String> markerstring=new LinkedHashMap<Marker,String>();
	Map<String,String> obj=new LinkedHashMap<String,String>();
	
	static int showid=1;
    public Marker setMarker(double a,double b,String text){
    	//定义Maker坐标点  
    	LatLng point = new LatLng(a, b);  
    	//构建Marker图标  
    	BitmapDescriptor bitmap = BitmapDescriptorFactory  
    	    .fromResource(R.drawable.icon_mark1);  
    	//构建MarkerOption，用于在地图上添加Marker  
    	try{
    	Resources resources=getResources();
    	int id=resources.getIdentifier(getPackageName()+":drawable/"+"icon_mark"+showid, null,null);
    	
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
		String place=getResources().getString(R.string.banzheng_place);
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
		for(String key:places.keySet()){
			obj.put(places.get(key).getName(), key);
		}	
	}    
	
	private static LatLng lle;
	private static LatLng lls;
	
	public void showMarker(String name){
		mBaiduMap.clear();
		showid=1;
		if(name.equals("all")){
			showfunction.setVisibility(View.GONE);
			for(BanZhengDT banz:places.values()){
				Marker marker=setMarker(banz.getLat(),banz.getLot(),banz.getAddress());
				markerstring.put(marker, banz.getName()+"\n电话："+banz.getPhone()+" \n地址："+banz.getAddress());
				
			}
			
			LatLng ptCenter = new LatLng(22.997531d,113.121134d);//设置禅城为中心
			mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ptCenter ));
		}else{
			
			showfunction.setVisibility(View.VISIBLE);
			
			BanZhengDT banz=places.get(obj.get(name));
			if(banz!=null){
				Marker marker=setMarker(banz.getLat(),banz.getLot(),banz.getAddress());
				markerstring.put(marker, banz.getName()+"\n电话："+banz.getPhone()+" \n地址："+banz.getAddress());
				LatLng ptCenter = new LatLng(banz.getLat(),banz.getLot());//设置禅城为中心
				mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ptCenter ));
				lle=ptCenter;
			}
			   
		}
	}


	
	Button mBtnPre = null;//上一个节点
	Button mBtnNext = null;//下一个节点
	RouteLine route = null;
	RoutePlanSearch mSearch = null;
	int nodeIndex = -2;//节点索引,供浏览节点时使用
	 /**
     * 发起路线规划搜索示例
     *
     * @param v
     */
    public void SearchButtonProcess(int type) {
    	
    	if(lls!=null&&lle!=null){
	        //重置浏览节点的路线数据
	        route = null;
	        mBtnPre.setVisibility(View.INVISIBLE);
	        mBtnNext.setVisibility(View.INVISIBLE);
	        mBaiduMap.clear();
	        // 处理搜索按钮响应
	       
	        
	        PlanNode stNode = PlanNode.withLocation(lls);
	        PlanNode enNode = PlanNode.withLocation(lle);
	
	        // 实际使用中请对起点终点城市进行正确的设定
	        if (type == 1) {
	            mSearch.drivingSearch((new DrivingRoutePlanOption())
	                    .from(stNode)
	                    .to(enNode));
	        } else if (type ==2) {
	            mSearch.transitSearch((new TransitRoutePlanOption())
	                    .from(stNode)
	                    .city("佛山")
	                    .to(enNode));
	        } else if (type==3) {
	            mSearch.walkingSearch((new WalkingRoutePlanOption())
	                    .from(stNode)
	                    .to(enNode));
	        }
    	}else{
    			Toast.makeText(LocationMap.this, "定位错误，请先选择目的地！", Toast.LENGTH_SHORT).show();
    	}
    }

    /**
     * 节点浏览示例
     *
     * @param v
     */
    public void nodeClick(int type) {
        if (nodeIndex < -1 || route == null ||
                route.getAllStep() == null
                || nodeIndex > route.getAllStep().size()) {
            return;
        }
        //设置节点索引
        if (type == 1 && nodeIndex < route.getAllStep().size() - 1) {
            nodeIndex++;
        } else if (type == 0 && nodeIndex > 1) {
            nodeIndex--;
        }
        if (nodeIndex < 0 || nodeIndex >= route.getAllStep().size()) {
            return;
        }

        //获取节结果信息
        LatLng nodeLocation = null;
        String nodeTitle = null;
        Object step = route.getAllStep().get(nodeIndex);
        if (step instanceof DrivingRouteLine.DrivingStep) {
            nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrace().getLocation();
            nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
        } else if (step instanceof WalkingRouteLine.WalkingStep) {
            nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrace().getLocation();
            nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
        } else if (step instanceof TransitRouteLine.TransitStep) {
            nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrace().getLocation();
            nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
        }

        if (nodeLocation == null || nodeTitle == null) {
            return;
        }
        //移动节点至中心
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
        // show popup
        viewCache = getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText = (TextView) viewCache.findViewById(R.id.textcache);
        popupText.setBackgroundResource(R.drawable.popup);
        popupText.setText(nodeTitle);
        mBaiduMap.showInfoWindow(new InfoWindow(popupText, nodeLocation, null));
       //Toast.makeText(LocationMap.this, nodeTitle, Toast.LENGTH_LONG).show();

    }

    
    OverlayManager routeOverlay = null;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(LocationMap.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
            route = result.getRouteLines().get(0);
            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }

    }

  //定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOvelray {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
           
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
         
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
          
        }
    }

    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
          
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
           
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
           
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            
        }
    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
           
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
          
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
          
        }
    }

    
    
    @Override
    public void onGetTransitRouteResult(TransitRouteResult result) {

        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(LocationMap.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
            route = result.getRouteLines().get(0);
            TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(LocationMap.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            mBtnPre.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
            route = result.getRouteLines().get(0);
            DrivingRouteOvelray overlay = new MyDrivingRouteOverlay(mBaiduMap);
            routeOverlay = overlay;
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

   

}
