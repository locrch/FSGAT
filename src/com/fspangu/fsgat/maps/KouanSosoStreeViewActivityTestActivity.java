
package com.fspangu.fsgat.maps;

import com.baidu.mapapi.SDKInitializer;
import com.fspangu.fsgat.R;
import com.tencent.tencentmap.streetviewsdk.StreetThumbListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewShow;
import com.tencent.tencentmap.streetviewsdk.map.basemap.GeoPoint;
import com.tencent.tencentmap.streetviewsdk.overlay.ItemizedOverlay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class KouanSosoStreeViewActivityTestActivity extends Activity implements StreetViewListener {
	  
    /**
     * View Container
     */
    private ViewGroup mContainer;

    /**
     * 缩略图View
     */
  //  private ImageView mThumbImage;

   // private Handler mHandler;
    
    /**
     * 街景View
     */
    private View mStreetView;
    static GeoPoint center ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // 设置为圆形旋转进度条
      		getActionBar().setBackgroundDrawable(
      				this.getBaseContext().getResources()
      						.getDrawable(R.drawable.title_background));
      		getActionBar().show();
      		setTitle("通关口岸");
      		
      		SDKInitializer.initialize(getApplicationContext());
      		
      		setOverflowShowingAlways();
        
        
        setContentView(R.layout.fragment_tec_street);
        mContainer = (LinearLayout)findViewById(R.id.layout);
       // mThumbImage = (ImageView)findViewById(R.id.image);

        Intent intent=getIntent();
    	double lon=intent.getDoubleExtra("long", 0.0D);
		double lat=intent.getDoubleExtra("lat", 0.0D); 
		//Toast.makeText(SosoStreeViewActivityTestActivity.this, " sL:" +lon+" lat:"+lat, Toast.LENGTH_LONG).show();
        // 使用经纬度获取街景
		center = new GeoPoint((int)(lat * 1e6), (int)(lon * 1e6));
         StreetViewShow.getInstance().showStreetView(this, center, 300, this, -170, 0);
        
        // 使用svid获取街景
        //StreetViewShow.getInstance().showStreetView(this, "10011026130910162137500", this, -170, 0);
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
    
    
    @Override
    protected void onDestroy() {
    	StreetViewShow.getInstance().destory();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
       
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onViewReturn(final View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	mStreetView = v;
                mContainer.addView(mStreetView);
                Log.d("street", StreetViewShow.getInstance().getStreetStatus().toString());
            }
        });
    }

    @Override
    public void onNetError() {
        // 网络错误处理
    }

    @Override
    public void onDataError() {
        // 解析数据错误处理
    }

   
    
    @Override
    public ItemizedOverlay getOverlay() {
       
        return null;
    }

   

	@Override
	public void onLoaded() {
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	mStreetView.setVisibility(View.VISIBLE);
            }
        });
	}

    @Override
    public void onAuthFail() {
        // 验证失败
    }
}
