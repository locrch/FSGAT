package com.pangu.neusoft.fsgat.CustomView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

public class CustomAsynTask extends AsyncTask<Void, Void, Boolean>
{
	Context context;
	Fragment fragment;
	ProgressDialog progressDialog;
	public CustomAsynTask(Context hostcontext,Fragment hostfragment)
	{
		// TODO Auto-generated constructor stub
		context = hostcontext;
		fragment = hostfragment;
	}
	
	public CustomAsynTask(Context hostcontext)
	{
		// TODO Auto-generated constructor stub
		context = hostcontext;
		
	}
	
	
	@Override
	protected void onPreExecute()
	{
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		if (fragment == null)
		{
			progressDialog = new CustomProgressDialog(context);
			
			progressDialog.show();
			
		}else {
			fragment.getActivity().setProgressBarIndeterminateVisibility(true);
		}
		
		
		
	}
	@Override
	protected Boolean doInBackground(Void... params)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void onPostExecute(Boolean result)
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if (fragment == null)
		{
			if (progressDialog.isShowing())
			{
			progressDialog.dismiss();
			}
		}
		else {
			fragment.getActivity().setProgressBarIndeterminateVisibility(false);
			}
		
		
		
	}
	@Override
	protected void onCancelled()
	{
		// TODO Auto-generated method stub
		super.onCancelled();
		if (fragment == null)
		{
			if (progressDialog.isShowing())
			{
			progressDialog.dismiss();
			}
		}
		else {
			fragment.getActivity().setProgressBarIndeterminateVisibility(false);
			}
		
		
	}
	
}
