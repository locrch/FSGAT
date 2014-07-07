package com.pangu.neusoft.fsgat.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pangu.neusoft.fsgat.model.History;

public class StringMethods {
	public static boolean isMobileNO(String mobiles){  
		  
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");  
		Matcher m = p.matcher(mobiles);  		  
		return m.matches();  
		  
	}  
	
	public static List<Map<String,String>> getPhone(String data){
		boolean rs=false;
		//((\\d+)-(\\d+))+
		String sp="";
		Pattern p;
		Matcher m;
		int i=0; 
        String regEx3=";|\\s|,";
        p=Pattern.compile(regEx3);
        String[] r=p.split(data);
        
		List<Map<String,String>> res=new ArrayList<Map<String,String>>();
        
        for(i=0;i<r.length;i++){
		        p = Pattern.compile("([\\D&&[^\\(\\锛圿]*)[\\(|\\锛圿?(\\d*)[\\)|\\锛塢?[- -]?(\\d+)");
		        m = p.matcher(r[i]); 
	      while (m.find()) {
	    	  Map<String,String> map=new HashMap<String,String>();
	    	       // System.out.println(m.group(1));
	    	  map.put(m.group(1), m.group(2)+m.group(3));
	    	        res.add(map);
		          
	       }
        }
        return res;
	}
}

class ComparatorUser implements Comparator{
	public int compare(Object arg0, Object arg1) {
		History h0=(History)arg0;
		History h1=(History)arg1;
		//首先比较年龄，如果年龄相同，则比较名字
		int flag=h1.getId().compareTo(h0.getId());
		return flag;
	}

}
 
