package com.pangu.neusoft.fsgat.CustomView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class CustomAsynTask extends AsyncTask<Void, Void, Boolean>
{
	Context context;
	
	public CustomAsynTask(Context hostcontext)
	{
		// TODO Auto-generated constructor stub
		context = hostcontext;
	}
	
	ProgressDialog progressDialog;
	@Override
	protected void onPreExecute()
	{
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		progressDialog = new CustomProgressDialog(context);
		
		progressDialog.show();
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
		if (progressDialog.isShowing())
		{
		progressDialog.dismiss();
		}
	}
	@Override
	protected void onCancelled()
	{
		// TODO Auto-generated method stub
		super.onCancelled();
		if (progressDialog.isShowing())
		{
			progressDialog.dismiss();
		}
		
	}
	
}
