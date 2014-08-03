package com.fspangu.fsgat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.model.History;
import com.pangu.neusoft.fsgat.model.PushMessage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PushMessageFragment extends Fragment{
	SharedPreferences sp;
	Editor editor;
	ListView list;
	AsyncTask<Void, Void, Boolean> loading1;
	
	
	 
	 @Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
		{
			// TODO Auto-generated method stub
			super.onCreateOptionsMenu(menu, inflater);
			
			Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
			
			actionbar_back_btn.setVisibility(View.VISIBLE);
			
			TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
			
			actionbar_title.setText("消息盒子");
		}
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	setHasOptionsMenu(true);
    	sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();		
    	View view = inflater.inflate(R.layout.history_fragment, null);
    	this.getActivity().setTitle("消息盒子");
    	if (CheckNetwork.connected(this)){
	    	list=(ListView)view.findViewById(R.id.listView1);
	    	getMessage();
    	 }
        return view;  
    }  
  
   
	 HashMap<String, Object> GetParamsMap=new HashMap<String,Object>();
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
	       	super.onPause();
	       }
	static BadgeView messageCenterBadge;
	Map<String, Object> adapterListMap;
	 List<Map<Integer,PushMessage>> listMessage=new ArrayList<Map<Integer,PushMessage>>();
	 
	 ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();  
	private void getMessage() {
		
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

						GetParamsMap = postJson.Post(keys, values, "GetPushMessage","pushMessageList");

						JSONArray pushMessageList = (JSONArray) GetParamsMap.get("pushMessageList");
						if(pushMessageList!=null){
							for (int i = 0; i < pushMessageList.length(); i++)
							{
								try
								{
									JSONObject jo = (JSONObject) pushMessageList.get(i);
			
									adapterListMap = new HashMap<String, Object>();
			
									PushMessage pushmessage = new PushMessage();			
									pushmessage.setPushMessageID(jo.getInt("pushMessageID"));
									pushmessage.setMessageTitle(jo.getString("messageTitle"));
									pushmessage.setMessageContent(jo.getString("messageContent"));
									pushmessage.setCreateTime(jo.getString("createTime"));
									pushmessage.setPushMessageStatusID(jo.getInt("pushMessageStatusID"));
									
									//记录下来方便查询
									Map<Integer,PushMessage> hismap=new HashMap<Integer,PushMessage>();
									hismap.put(pushmessage.getPushMessageID(), pushmessage);
									listMessage.add(hismap);
									
									
									//用于显示
									HashMap<String, Object> map = new HashMap<String, Object>();  
						            map.put("pushMessageID", pushmessage.getPushMessageID());  
						            map.put("messageTitle", pushmessage.getMessageTitle());
						            map.put("messageContent", pushmessage.getMessageContent());
						            map.put("createTime", pushmessage.getCreateTime());
						            
						            if(pushmessage.getPushMessageStatusID()==0){						            	
						            	map.put("pushMessageStatusID", "未读");
						            }else{
						            	map.put("pushMessageStatusID", "已读");
						            }				            
						            listItem.add(map);  
									
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
					            R.layout.messagelistitem,//ListItem的XML实现  
					            //动态数组与ImageItem对应的子项          
					            new String[] {"pushMessageID", "messageTitle","messageContent","createTime","pushMessageStatusID"},   
					            //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
					            new int[] {R.id.message_id,R.id.message_title,R.id.message_content,R.id.message_createTime,R.id.status}  
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
    
   
}
