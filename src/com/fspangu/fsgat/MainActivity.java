package com.fspangu.fsgat;



import com.pangu.neusoft.fsgat.user.LoginFragment;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	static DisplayMetrics getphonesize;
	static SharedPreferences sp;
	Editor editor;
	
	private static FragmentManager fragmentManager;  
    private static RadioGroup radioGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); //设置为圆形旋转进度条
		setContentView(R.layout.activity_main); 
		
		setTitle(this.getResources().getString(R.string.app_name));
		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}		
		sp = getSharedPreferences(getApplication().toString(),Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			fragmentManager = getFragmentManager();  
		      
			
			radioGroup = (RadioGroup) rootView.findViewById(R.id.rg_tab);
            RadioButton ta1=(RadioButton)rootView.findViewById(R.id.rg_ta1);
            RadioButton ta2=(RadioButton)rootView.findViewById(R.id.rg_ta2);
            RadioButton ta3=(RadioButton)rootView.findViewById(R.id.rg_ta3);
            RadioButton ta4=(RadioButton)rootView.findViewById(R.id.rg_ta4);
            RadioButton ta5=(RadioButton)rootView.findViewById(R.id.rg_ta5);

           OnClickListener a= new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					if(arg0.getId()==R.id.rg_ta1){
						showFragment(1);
					}
					if(arg0.getId()==R.id.rg_ta2){
						showFragment(2);
					}
					if(arg0.getId()==R.id.rg_ta3){
						showFragment(3);
					}
					if(arg0.getId()==R.id.rg_ta4){
						showFragment(4);
					}
					if(arg0.getId()==R.id.rg_ta5){
						showFragment(5);
					}
					
				}
            	 
             };
             //设置 
//            getphonesize = new DisplayMetrics();
//      		getActivity().getWindowManager().getDefaultDisplay().getMetrics(getphonesize);
//      		setsize(ta1);
//      		setsize(ta2);
//      		setsize(ta3);
//      		setsize(ta4);
//      		setsize(ta5);
//      		RelativeLayout.LayoutParams vp = (RelativeLayout.LayoutParams)radioGroup.getLayoutParams();
//      		vp.width = (int) (getphonesize.widthPixels);
//      		vp.height = (int) (getphonesize.widthPixels * 1/5);
//      		vp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
             
             ta1.setOnClickListener(a);
             ta2.setOnClickListener(a);
             ta3.setOnClickListener(a);
             ta4.setOnClickListener(a);
             ta5.setOnClickListener(a);

			

     		if (sp.getString("username", "").equals(""))
     		{
     			Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
     			if(fragmentManager.getBackStackEntryCount()>=1){
        			int len = fragmentManager.getBackStackEntryCount();
        		    for (int i = 0; i < len; i++) {
        		    	fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        		    }
        		}
     			  FragmentTransaction transaction = fragmentManager.beginTransaction(); 
     			  Fragment loginFragment = new LoginFragment();  
                  transaction.replace(R.id.content, loginFragment);  
                  transaction.commit(); 
     		}
     		else {
     			showFragment(5);
     		}
             
			return rootView;
		}
		
	
//		void setsize(View v){
//     		RadioGroup.LayoutParams vp = (RadioGroup.LayoutParams)v.getLayoutParams();
//     		    vp.width = (int) (getphonesize.widthPixels * 1/5);
//     		    vp.height = (int) (vp.width * 1/1);
//     		   
//		}
		
		public void showFragment(int checkedId){
			try{
				Log.e("count: ", ""+fragmentManager.getBackStackEntryCount());
				if(fragmentManager.getBackStackEntryCount()>=1){
        			int len = fragmentManager.getBackStackEntryCount();
        		    for (int i = 0; i < len; i++) {
        		    	fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        		    }
        		}
        		    FragmentTransaction transaction = fragmentManager.beginTransaction();
            		Fragment fragment = FragmentFactory.getInstanceByIndex(checkedId);  
            		transaction.replace(R.id.content, fragment);  
            		transaction.addToBackStack(null);
            		transaction.commit();
        		
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
		}

	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
		
        if (keyCode == KeyEvent.KEYCODE_BACK ){
        	if(fragmentManager.getBackStackEntryCount()<=1){
	            // 创建退出对话框  
	            AlertDialog isExit = new AlertDialog.Builder(this).create();  
	            // 设置对话框标题  
	            isExit.setTitle("提示");  
	            // 设置对话框消息  
	            isExit.setMessage("确定要退出吗");  
	            // 添加选择按钮并注册监听  
	            isExit.setButton("确定", listener);  
	            isExit.setButton2("取消", listener);  
	            // 显示对话框  
	            isExit.show();  
	            return false;
        	}
        }  
          
        return super.onKeyDown(keyCode, event);  
          
    }  
    /**监听对话框里面的button点击事件*/  
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
    {  
        public void onClick(DialogInterface dialog, int which)  
        {  
            switch (which)  
            {  
            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
                finish();  
                break;  
            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
                break;  
            default:  
                break;  
            }  
        }  
    };   
	

}
