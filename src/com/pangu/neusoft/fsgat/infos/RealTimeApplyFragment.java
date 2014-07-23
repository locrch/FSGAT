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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public  class RealTimeApplyFragment extends Fragment {
	
	SharedPreferences sp;
	Editor editor;
	ListView list;
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("受理大厅");
	}
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	setHasOptionsMenu(true);
    	View view = inflater.inflate(R.layout.realtimeapply, null);
//    	android.app.ActionBar actionBar = this.getActivity().getActionBar();
//		actionBar.setCustomView(R.layout.title_bar);
//		actionBar.setDisplayShowCustomEnabled(true);
//		actionBar.setDisplayShowHomeEnabled(false);
//		actionBar.show();
//		TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//		titleview.setText("受理大厅状态查询");
    	this.getActivity().setTitle("受理大厅状态查询");
    	if (CheckNetwork.connected(this)){
	    	WebView webview=(WebView)view.findViewById(R.id.webView1);
	    	webview.getSettings().setJavaScriptEnabled(true);
	    	
	    	webview.setWebViewClient(new WebViewClient(){       
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {       
	                view.loadUrl(url);       
	                return true;       
	            }       
	    	}); 
	    	
	    	webview.loadUrl(getActivity().getResources().getString(R.string.realtimeapplystate));  
    	}
        return view;  
    }  
  
    
   
}  