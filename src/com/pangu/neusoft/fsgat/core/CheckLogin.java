package com.pangu.neusoft.fsgat.core;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.pangu.neusoft.fsgat.user.LoginFragment;

public class CheckLogin {
	
	public static boolean logined(Fragment fragment){
		SharedPreferences sp = fragment.getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		Editor editor = sp.edit();;
		 
		if (sp.getString("username", "").equals(""))
 		{
 			Toast.makeText(fragment.getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
 			
 			if(fragment.getFragmentManager().getBackStackEntryCount()>=1){
    			int len =fragment.getFragmentManager().getBackStackEntryCount();
    		    for (int i = 0; i < len; i++) {
    		    	fragment.getFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    		    }
    		}
			FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction(); 
     		Fragment loginFragment = new LoginFragment();  
            transaction.replace(R.id.content, loginFragment); 
            transaction.commit();
 			
 			return false;
 		}
		return true;
	}
}
