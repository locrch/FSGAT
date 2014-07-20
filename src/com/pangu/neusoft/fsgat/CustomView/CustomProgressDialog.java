package com.pangu.neusoft.fsgat.CustomView;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomProgressDialog extends ProgressDialog
{

	public CustomProgressDialog(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		super.setMessage("正在加载数据....");
		super.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		super.setCancelable(false);
		super.setCanceledOnTouchOutside(false);
		
		
	}

	public CustomProgressDialog(Context context, int theme)
	{
		super(context, theme);
		// TODO Auto-generated constructor stub

	}

}
