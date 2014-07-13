package com.pangu.neusoft.fsgat.visa;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.core.PostJsonObject;
import com.pangu.neusoft.fsgat.core.StringMethods;
import com.pangu.neusoft.fsgat.model.Address;
import com.pangu.neusoft.fsgat.model.History;
import com.pangu.neusoft.fsgat.model.ListPass;
import com.pangu.neusoft.fsgat.model.Pass;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public  class ShowHistoryFragment extends Fragment {
	
	SharedPreferences sp;
	Editor editor;
	ListView list;
	AsyncTask<Void, Void, Boolean> loading1;
	AsyncTask<Void, Void, Boolean> loading2;
	
	 @Override
	   	public void onPause(){
	       	if(loading1!=null){
	       		loading1.cancel(false);
	       		try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
	       	}
	       	if(loading2!=null){
	       		loading2.cancel(false);
	       		try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
	       	}
	       	
	       	super.onDestroyView();
	       }
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
//		editor.putString("username", "pangu");
//		editor.commit();
		
    	View view = inflater.inflate(R.layout.history_fragment, null);
    	this.getActivity().setTitle("受理记录");
    	if (CheckNetwork.connected(this)){
	    	list=(ListView)view.findViewById(R.id.listView1);
	    	getHistory();
    	 }
        return view;  
    }  
  
    
    //获取历史记录
    HashMap<String, Object> GetParamsMap=new HashMap<String,Object>();
    Map<String, Object> adapterListMap;
    List<Map<String,History>> listHistory=new ArrayList<Map<String,History>>();
    
    ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();  
    
    public void getHistory(){
    	
    	loading1=new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	            try{
		    		getActivity().setProgressBarIndeterminateVisibility(true);// 执行前使进度条可见
		    	}catch(Exception ex){
		    		ex.printStackTrace();
		    	}// 执行前使进度条可见
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				String[] keys = new String[]{ "username" };

						String[] values = new String[]{ sp.getString("username", "") };

						PostJson postJson = new PostJson();

						GetParamsMap = postJson.Post(keys, values, "applyHistory","ApplyHistoryList");

						JSONArray historyList = (JSONArray) GetParamsMap.get("ApplyHistoryList");
						if(historyList!=null){
							for (int i = 0; i < historyList.length(); i++)
							{
								try
								{
									JSONObject jo = (JSONObject) historyList.get(i);
			
									adapterListMap = new HashMap<String, Object>();
			
									History history = new History();			
									history.setId(jo.getString("id"));
									history.setIdNumber(jo.getString("idNumber"));
									history.setName(jo.getString("name"));
									history.setApplyid(jo.getString("applyid"));
									history.setPassNumber(jo.getString("passNumber"));
									history.setPassword(jo.getString("password"));
									history.setApplyDatetime(jo.getString("applyDatetime"));
									history.setOperationDatetime(jo.getString("operationDatetime"));
									history.setResult(jo.getString("result"));
									
									//记录下来方便查询
									Map<String,History> hismap=new HashMap<String,History>();
									hismap.put(history.getId(), history);
									listHistory.add(hismap);
									
									
									//用于显示
									HashMap<String, Object> map = new HashMap<String, Object>();  
						            map.put("id", history.getId());  
						            map.put("idNumber", history.getIdNumber());
						            map.put("name", history.getName());
						            
						            if(history.getApplyid().equals("null")){						            	
						            	map.put("applyid", "待处理");
						            }else{
						            	map.put("applyid", history.getApplyid());
						            }
						            
						            
						            if(!history.getPassNumber().equals("null")){
						            	map.put("type", "签注申请"+"("+history.getPassNumber()+")");
						            }
						            if(!history.getIdNumber().equals("null")){
						            	map.put("type", "预约办证"+"("+history.getIdNumber()+")");
						            }
						            
						            
						            map.put("password", history.getPassword());
						            map.put("applyDatetime", history.getApplyDatetime());
						            if(!history.getOperationDatetime().equals("null")){
						            	map.put("operationDatetime", history.getOperationDatetime());
						            }else{
						            	map.put("operationDatetime", "待处理");
						            }
						            map.put("result", history.getResult());						            
						            listItem.add(map);  
									
									Log.i("History Id:" + i, history.getId().toString());
								} catch (JSONException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			
							}
						}
						
						Boolean success= (Boolean) GetParamsMap.get("success");
						return success;
			}
			
			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);	
				try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
				if(listItem!=null&&listItem.size()>0){
					 SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(),listItem,//数据源   
					            R.layout.item_history,//ListItem的XML实现  
					            //动态数组与ImageItem对应的子项          
					            new String[] {"id", "name","applyid","applyDatetime","result","operationDatetime","type"},   
					            //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
					            new int[] {R.id.id,R.id.name,R.id.applyid,R.id.textView4,R.id.textView6,R.id.textView8,R.id.textView10}  
					        );  
					list.setAdapter(listItemAdapter);
					list.setOnItemClickListener(new OnItemClickListener(){
	
						@Override
						public void onItemClick(AdapterView<?> parent, View view,
								int position, long id) {
							HashMap<String,String> map=(HashMap<String,String>)list.getItemAtPosition(position);
							String Id=map.get("id"); 		
							String Name=map.get("name");
							String Applyid=map.get("applyid");
							if(!Applyid.equals("待处理")){
								doGet(Applyid);
							}else{
								Toast.makeText(getActivity(), "您的申请正等待处理，请耐心等待。。。。", Toast.LENGTH_SHORT).show();
							}
						}
						
					});
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			
		};
		loading1.execute();
    }
    
    
    String historyresult="";
    public void doGet(final String applyid){
    	historyresult="";
    	loading2=new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute(); 
	            try{
		    		getActivity().setProgressBarIndeterminateVisibility(true);// 执行前使进度条可见
		    	}catch(Exception ex){
		    		ex.printStackTrace();
		    	}// 执行前使进度条可见
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				try{    		
		    		 //String url = "http://www.gdcrj.com/wsyw/tcustomer/tcustomer.do?&method=find&applyid="+applyid;
					String url=getActivity().getResources().getString(R.string.applystateurl)+applyid;
		    		 //Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		    		 //Elements newsHeadlines = doc.select("<td class=\"news_font\">");
		//    		 
		//    		 HttpGet httpGet = new HttpGet(url);
		//             HttpClient httpClient = new DefaultHttpClient();
		//             HttpResponse response = httpClient.execute(httpGet);
		                
		    		 Document doc = Jsoup.connect(url).get();
		    		 Elements links=doc.getElementsByAttributeValueStarting("class","news_font");
		    		
		    		 for (Element link : links) {
		    			Elements check=link.getElementsByAttributeValueStarting("bgcolor","#F5F5F5");
		    			if(check.size()==0)
		    			historyresult += link.text()+"\n";
		    		 } 
		                
		    	}catch(Exception ex){
		    		Log.e(getTag(), ex.toString());
		    		return false;
		    	}
				return true;
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);
				try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
				if(result){     
					if(historyresult.equals("")){
						historyresult="获取不到数据，请检查网络连接，或稍后重试...";
					}
	                Dialog alertDialog = new AlertDialog.Builder(getActivity()). 
		                    setTitle("受理进度"). 
		                    setMessage(historyresult). 
		                    setIcon(R.drawable.ic_launcher). 
		                    setPositiveButton("确定", new DialogInterface.OnClickListener() { 
		                         
		                        @Override 
		                        public void onClick(DialogInterface dialog, int which) { 
		                            // TODO Auto-generated method stub  
		                        } 
		                    }). 
		                    create(); 
		            alertDialog.show();
                }else{
                	Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
                }
	        
				
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				try{
					getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			
		};
		loading2.execute();
			
		    	
    	
    }
    
}  