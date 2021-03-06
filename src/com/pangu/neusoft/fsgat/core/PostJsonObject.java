package com.pangu.neusoft.fsgat.core;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fspangu.fsgat.R;

import android.util.JsonReader;
import android.util.Log;

public class PostJsonObject
{
	private static final String TAG = "FSGAT";
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	@SuppressWarnings("finally")
	public HashMap<String, Object> Post(String[] keys,Object[] values,String methodname)
	{
		GetParamsMap = new HashMap<String, Object>();
		
		try
		{

			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(
					PostJson.serverurl+methodname);
			
			// 请求json报文
			JSONObject joput = new JSONObject();
			
			
			/*if (values.length != 1)
			{
				
			} else
			{
				joput.put("err", "error");
			}*/
				for (int i = 0; i < values.length; i++)
				{
					if(keys[i].equals("qwd")){
						JSONArray qwdvalue=new JSONArray();
						String[] qwdlist=(String[])values[i];
						for(String a:qwdlist){
							qwdvalue.put(a);
						}
						joput.put(keys[i], qwdvalue);
					}else{
						joput.put(keys[i], values[i]);
					}
				}
				
				joput.put("AppKey", "GangAoTongAndroid");
				joput.put("AppSecret", "LLNvI2xxTLwZhAAORdHyRWC9yLfrSsppnn6uDMD-");
						
			
			Log.d(TAG, "keys : " + joput.toString());
			//hp.setEntity(new StringEntity(joput.toString()));
			 List<NameValuePair> params = new ArrayList<NameValuePair>();
			 params.add(new BasicNameValuePair("jsonBody", joput.toString())); 
			hp.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse hr = hc.execute(hp);
			String result;
			
			
			// 获取返回json报文
			
				result = EntityUtils.toString(hr.getEntity());
				Log.d(TAG, "result : " + result);
				
				
				
				joget = new JSONObject(result);
				
				Log.i("joget", joget.toString());
				
				if (joget.equals(null))
				{
					GetParamsMap.put("success", false);
					
					return GetParamsMap;
				}
			
			// 关闭连接
			if (hc != null)
			{
				hc.getConnectionManager().shutdown();
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			
		}
		
		finally{
			
			
		}
		GetParamsMap.put("joget", joget);
		
		try
		{
			if (joget!=null&&joget.getString("msg")!=null)
			{
				GetParamsMap.put("success", joget.getBoolean("success"));
				
				GetParamsMap.put("msg", joget.getString("msg"));
				
				
			}
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return GetParamsMap;
		
	}
	
	public HashMap<String, Object> Post(String[] keys,String[] values,String methodname,String getArrayName)
	{
		GetParamsMap = new HashMap<String, Object>();
		
		try
		{

			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(PostJson.serverurl+methodname);
			
			// 请求json报文
			JSONObject joput = new JSONObject();
			
			
			/*if (values.length != 1)
			{
				
			} else
			{
				joput.put("err", "error");
			}*/
				for (int i = 0; i < values.length; i++)
				{
					joput.put(keys[i], values[i]);
				}
				
				joput.put("AppKey", "GangAoTongAndroid");
				joput.put("AppSecret", "LLNvI2xxTLwZhAAORdHyRWC9yLfrSsppnn6uDMD-");
					
				
				
			
			Log.d(TAG, "keys : " + joput.toString());
			//hp.setEntity(new StringEntity(joput.toString()));
			 List<NameValuePair> params = new ArrayList<NameValuePair>();
			 params.add(new BasicNameValuePair("jsonBody", joput.toString())); 
			hp.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse hr = hc.execute(hp);
			String result;
			
			
			// 获取返回json报文
			
				result = EntityUtils.toString(hr.getEntity());
				Log.d(TAG, "result : " + result);
				
				
				
				joget = new JSONObject(result);
				
				Log.i("joget", joget.toString());
				
				if (joget.equals(null))
				{
					GetParamsMap.put("success", false);
					
					return GetParamsMap;
				}
			
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
		GetParamsMap.put("joget", joget);
		
		try
		{
			if (joget.getString("msg")!=null)
			{
				GetParamsMap.put("success", joget.getBoolean("success"));
				
				GetParamsMap.put("msg", joget.getString("msg"));
				
				GetParamsMap.put(getArrayName, joget.getJSONArray(getArrayName));
				
			}
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block	
			e.printStackTrace();
		}
		
		return GetParamsMap;
		
	}
}
