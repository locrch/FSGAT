package com.pangu.neusoft.fsgat.user;

import java.util.HashMap;

import org.json.JSONObject;



import com.fspangu.fsgat.R;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.PostJson;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager.OnCancelListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

@SuppressLint("ValidFragment") 
public class ChangeAddressDialog extends Dialog
{
	SharedPreferences sp;
	Editor editor;
	String sex;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	String id;

	public ChangeAddressDialog(final String id, final Activity activity,
			Context context, boolean cancelable, OnCancelListener cancelListener)
	{
		super(context);
		//super.setContentView(R.layout.activity_change_address);
		
		// TODO Auto-generated constructor stub
		sp = activity.getSharedPreferences(
				activity.getApplication().toString(), Context.MODE_PRIVATE);
		editor = sp.edit();
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();

		LayoutInflater inflater = LayoutInflater.from(context);
		final View textEntryView = inflater.inflate(
				R.layout.activity_change_address, null);
		setContentView(textEntryView);
		final EditText changeaddress_receiver = (EditText)textEntryView.findViewById(R.id.changeaddress_receiver);
		final EditText changeaddress_address = (EditText) textEntryView.findViewById(R.id.changeaddress_address);
		final EditText changeaddress_postcode = (EditText) textEntryView.findViewById(R.id.changeaddress_postcode);
		final Button changeaddress_change_btn = (Button)textEntryView.findViewById(R.id.changeaddress_change_btn);

		changeaddress_change_btn.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				new CustomAsynTask(activity)
				{
					@Override
					protected Boolean doInBackground(Void... params)
					{

						String[] keys = new String[]
						{ "username", "id", "receiver", "address", "postcode" };

						String[] values = new String[]
						{ sp.getString("username", ""), id,
								changeaddress_receiver.getText().toString(),
								changeaddress_address.getText().toString(),
								changeaddress_postcode.getText().toString() };

						PostJson postJson = new PostJson();

						GetParamsMap = postJson.Post(keys, values,
								"editAddress");

						Boolean success = false;

						success = (Boolean) GetParamsMap.get("success");

						return success;
					}

					protected void onPostExecute(Boolean result)
					{
						super.onPostExecute(result);

						if (GetParamsMap.get("msg").toString().length() > 50)
						{
							Toast.makeText(activity.getApplicationContext(),
									R.string.toast_flase_msg, Toast.LENGTH_LONG)
									.show();
						} else
						{
							Toast.makeText(activity.getApplicationContext(),
									(String) GetParamsMap.get("msg"),
									Toast.LENGTH_LONG).show();
						}
						if (isShowing())
						{
							dismiss();
						}
					};

				}.execute();
				
			}
		});
		
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		//builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("修改地址");
		builder.setView(textEntryView);
		
		builder.setPositiveButton("修改", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int whichButton)
			{
				
			}
		});
		//builder.show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	
}
