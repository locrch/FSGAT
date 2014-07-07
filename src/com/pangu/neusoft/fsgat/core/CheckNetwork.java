package com.pangu.neusoft.fsgat.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class CheckNetwork {
	public static boolean connected(final Fragment fragment){
		ConnectivityManager cm=(ConnectivityManager)fragment.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo info=cm.getActiveNetworkInfo();
    	
    	  if (info == null || !info.isConnected()){
    		  
    		  AlertDialog.Builder builder = new Builder(fragment.getActivity());
		    	builder.setMessage("马上进入设置网络？");
		    	builder.setTitle("联网失败");
		    	builder.setPositiveButton("确认", new OnClickListener() {
		    		@Override
		    		public void onClick(DialogInterface dialog, int which) {
		    			if(android.os.Build.VERSION.SDK_INT > 10 ){
		    			     //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
		    			    fragment.getActivity().startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
		    			} else {
		    				fragment.getActivity().startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		    			}
		    		}
		    	});
		    	builder.setNegativeButton("取消", new OnClickListener() {
		    		@Override
		    		public void onClick(DialogInterface dialog, int which) {
		    		}
		    	});
		    	builder.create().show();
    		  return false;
    	  } else{
    		  return true;
    	  }
	}
	public static boolean connected(final Activity activity){
		ConnectivityManager cm=(ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo info=cm.getActiveNetworkInfo();
    	
    	  if (info == null || !info.isConnected()){
    		  
    		  AlertDialog.Builder builder = new Builder(activity);
		    	builder.setMessage("马上进入设置网络？");
		    	builder.setTitle("联网失败");
		    	builder.setPositiveButton("确认", new OnClickListener() {
		    		@Override
		    		public void onClick(DialogInterface dialog, int which) {
		    			if(android.os.Build.VERSION.SDK_INT > 10 ){
		    			     //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
		    				activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
		    			} else {
		    				activity.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		    			}
		    		}
		    	});
		    	builder.setNegativeButton("取消", new OnClickListener() {
		    		@Override
		    		public void onClick(DialogInterface dialog, int which) {
		    		}
		    	});
		    	builder.create().show();
    		  return false;
    	  } else{
    		  return true;
    	  }
	}
}
