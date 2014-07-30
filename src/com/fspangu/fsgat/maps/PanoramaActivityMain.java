package com.fspangu.fsgat.maps;

import java.lang.reflect.Field;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fspangu.fsgat.R;
import com.baidu.lbsapi.controller.PanoramaController;
import com.baidu.lbsapi.panoramadata.IndoorPanorama;
import com.baidu.lbsapi.panoramaview.*;
import com.baidu.lbsapi.BMapManager;
import com.baidu.lbsapi.MKGeneralListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.pplatform.comapi.basestruct.GeoPoint;

/**
 * 全景Demo主Activity
 */
public class PanoramaActivityMain extends Activity implements PanoramaViewListener {
   
	private static final String LTAG = PanoramaActivityMain.class.getSimpleName();
    private PanoramaView mPanoView;
    private static com.baidu.frontia.FrontiaApplication mInstance = null;
    BMapManager mBMapManager = null;
    
    private PanoramaController panoController;    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //先初始化BMapManager
        mInstance = (com.baidu.frontia.FrontiaApplication)getApplication();
		initEngineManager(getApplication());
        
        
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(getApplication());

            mBMapManager.init(new MyGeneralListener());
        }
        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // 设置为圆形旋转进度条
		getActionBar().setBackgroundDrawable(
				this.getBaseContext().getResources()
						.getDrawable(R.drawable.title_background));
		getActionBar().show();
		setTitle("办证地点");
		
		SDKInitializer.initialize(getApplicationContext());
		
		setOverflowShowingAlways();
        
        setContentView(R.layout.activity_street);
        
        
        mPanoView = (PanoramaView) findViewById(R.id.panorama);
    	mPanoView.setPanoramaImageLevel(5);
    	mPanoView.setPanoramaViewListener(this);
    	panoController = new PanoramaController();
    	
    	
        //UI初始化
    	
    	  mPanoView.setShowTopoLink(true);
          
          mPanoView.setZoomGestureEnabled(true);
          mPanoView.setRotateGestureEnabled(true);
        
          processType();
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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		android.app.ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(R.layout.map_title_bar);
		Button actionbar_back_btn = (Button) findViewById(R.id.actionbar_back_btn);
		Button near_back_btn = (Button) findViewById(R.id.near);
		TextView actionbar_title=(TextView)findViewById(R.id.actionbar_title);
		actionbar_title.setText("街景地图");
		actionbar_back_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				finish();
			}
		});
		
		near_back_btn.setVisibility(View.INVISIBLE);
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
			
		return true;
	}
    
    private void processType() {
    	Intent intent=getIntent();
    	double lon=intent.getDoubleExtra("long", 0.0D);
		double lat=intent.getDoubleExtra("lat", 0.0D);
		
		//Toast.makeText(PanoramaActivityMain.this, " sL:" +lon+" lat:"+lat, Toast.LENGTH_LONG).show();
		lat=23.125872d;
		lon=113.320579d;	
		
		mPanoView.setPanorama(lon, lat);
    }
    
    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(new MyGeneralListener())) {
            Toast.makeText(getInstance().getApplicationContext(), 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
    public static com.baidu.frontia.FrontiaApplication getInstance() {
		return mInstance;
	}
	
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetPermissionState(int iError) {
        	//非零值表示key验证未通过
            if (iError != 0) {
                //授权Key错误：
                Toast.makeText(getInstance().getApplicationContext(), 
                        "请在AndoridManifest.xml中输入正确的授权Key,并检查您的网络连接是否正常！error: "+iError, Toast.LENGTH_LONG).show();
            }
            else{
            	Toast.makeText(getInstance().getApplicationContext(), 
                        "百度地图暂时没有佛山地区的街景，敬请期待！", Toast.LENGTH_LONG).show();
            }
        }
    }
   
   
    
    @Override
    protected void onPause() {
        super.onPause();
        mPanoView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPanoView.onResume();
    }

    @Override
    protected void onDestroy() {
        mPanoView.destroy();
        super.onDestroy();
    }
 
    @Override
	public void onLoadPanoramBegin() {
		Log.d(LTAG, "loadPanoramBegin");
	}


	@Override
	public void onLoadPanoramaEnd() {
		Log.d(LTAG, "loadPanoramaEnd");
	}


	@Override
	public void onLoadPanoramaError() {
		Log.d(LTAG, "loadPanoramaError");
	}


}
