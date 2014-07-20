package com.fspangu.fsgat;

import android.support.v4.app.Fragment;

public class FragmentFactory {  
    public static Fragment getInstanceByIndex(int index) {  
        Fragment fragment = null;  
        switch (index) {  
            case 1:  
                fragment = new YwblFragment();  
                break;  
            case 2:  
                fragment = new ZhcxFragment();  
                break;  
            case 3:  
                fragment = new BmfwFragment();  
                break;  
            case 4:  
                fragment = new YhhdFragment();  
                break;  
            /*case 5:  
                fragment = new GrzxFragment();  
                break; */ 
        }  
        return fragment;  
    }  
}  