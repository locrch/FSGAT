package com.pangu.neusoft.fsgat.infos;

import com.fspangu.fsgat.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class GuideFragment extends Fragment
{
	private String errorHtml = "";
	ProgressBar Progress;
	WebView webview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.yhhd_fragment, null);  
		this.getActivity().setTitle("办事指南");
        
		errorHtml = "<html><body><h1>error</h1></body></html>"; 
		Progress = (ProgressBar)view.findViewById(R.id.Progress);
		webview = (WebView)view.findViewById(R.id.yhhd_webview);
        
		webview.loadUrl("http://202.103.160.153:1019/News/NewsList.aspx?cid=100001");
		//webview.loadUrl("file:///android_asset/index.html"); 
		
		webview.getSettings().setJavaScriptEnabled(true);
		
		webview.canGoBack();
		
		webview.setWebViewClient(new WebViewClient() {
			// 点击网页中按钮时，在原页面打开
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				view.loadData(errorHtml, "text/html", "UTF-8");
				
			}
		});

		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				Progress.setProgress(newProgress);
				if (newProgress == 100) {
					Progress.setVisibility(View.GONE);
	            } else {
	                if (Progress.getVisibility() ==View.GONE)
	                	Progress.setVisibility(View.VISIBLE);
	                Progress.setProgress(newProgress);
	            }
				
				
				super.onProgressChanged(view, newProgress);
			}
		});
		
		webview.setOnKeyListener(new View.OnKeyListener() {    
	        @Override    
	        public boolean onKey(View v, int keyCode, KeyEvent event) {    
	            if (event.getAction() == KeyEvent.ACTION_DOWN) {    
	                if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {  //表示按返回键时的操作  
	                	webview.goBack();   //后退    

	                    //webview.goForward();//前进  
	                    return true;    //已处理    
	                }    
	            }    
	            return false;    
	        }    
	    });  
		
		return view;
	}
}
