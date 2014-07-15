package com.pangu.neusoft.fsgat.core;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.w3c.dom.ls.LSException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.model.ListdownStation;
import com.pangu.neusoft.fsgat.model.downStation;

public class PostJSONfromGson {
	private static final String TAG = "FSGAT";
	String result;
	public Object GsonPost(Object putobject,String methodname)
	{
		
		try
		{

			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost("http://202.103.160.153:1009/GetInfo.ashx?method="+methodname);
			
			//HttpPost hp = new HttpPost("http://192.168.1.133:167/GetInfo.ashx?method="+methodname);
			
			Gson gson = new Gson();
			
			// 请求json报文
			 JSONObject joput = new JSONObject(gson.toJson(putobject));
			
			 
			 
			joput.put("AppKey", "GangAoTongAndroid");
			joput.put("AppSecret", "LLNvI2xxTLwZhAAORdHyRWC9yLfrSsppnn6uDMD-");
					
			Log.d(TAG, putobject.toString()+"Gkeys : " + joput.toString());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("jsonBody", joput.toString())); 
			hp.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse hr = hc.execute(hp);
			
			
			
			// 获取返回json报文
			
				result = EntityUtils.toString(hr.getEntity());
				Log.d(TAG, putobject.toString()+"Gresult : " + result);
				
				
				
				
			
			// 关闭连接
			if (hc != null)
			{
				hc.getConnectionManager().shutdown();
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			Log.e(TAG, e.getMessage());
		}
		
		finally{
			
			
		}
		
		return result;
		
	}
	
}
