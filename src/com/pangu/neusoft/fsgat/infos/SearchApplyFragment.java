package com.pangu.neusoft.fsgat.infos;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public  class SearchApplyFragment extends Fragment {
	
	ImageButton button1;
	TextView textview1;
	EditText editText1;
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("受理进度");
	}
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	setHasOptionsMenu(true);
    	View view = inflater.inflate(R.layout.applysearch, null);
//    	android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("办证进度查询");
    	this.getActivity().setTitle("办证进度查询");
    	button1=(ImageButton)view.findViewById(R.id.imageButton1);
    	textview1=(TextView)view.findViewById(R.id.textView1);
    	editText1=(EditText)view.findViewById(R.id.editText1);
    	
    	if (CheckNetwork.connected(this)){
    		button1.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					String applyid=editText1.getText().toString();
					if(applyid!=null&&!applyid.equals("")){
						doGet(applyid);
					}
					
				}
    		});
    	}
        return view;  
    }  
  
    
    @Override
   	public void onPause(){
       	if(loading!=null){
       		loading.cancel(false);
       		try{
				getActivity().setProgressBarIndeterminateVisibility(false);// 执行前使进度条可见
			}catch(Exception ex){
				ex.printStackTrace();
			}
       	}
       	super.onDestroyView();
       }
    
    String historyresult="";
    AsyncTask<Void, Void, Boolean> loading;
    public void doGet(final String applyid){
    	historyresult="";
    	loading=new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	            try{
		    		getActivity().setProgressBarIndeterminateVisibility(true);// 执行前使进度条可见
		    	}catch(Exception ex){
		    		ex.printStackTrace();
		    	}
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				try{    		
					String url=getActivity().getResources().getString(R.string.applystateurl)+applyid;

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
					textview1.setText(historyresult);
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
		loading.execute();
			
		    	
    	
    }
   
}  