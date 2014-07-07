package com.pangu.neusoft.fsgat.user;

import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONObject;




import com.fspangu.fsgat.R;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.CustomView.CustomDatepickerDialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class ChangeAddressActivity extends Activity
{
	EditText changeaddress_receiver,changeaddress_address,changeaddress_postcode;
	Button changeaddress_change_btn;

	
	SharedPreferences sp;
	Editor editor;
	String sex;
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	String id;
	private void init()
	{
		// TODO Auto-generated method stub
		changeaddress_receiver = (EditText) findViewById(R.id.changeaddress_receiver);
		changeaddress_address = (EditText) findViewById(R.id.changeaddress_address);
		changeaddress_postcode = (EditText) findViewById(R.id.changeaddress_postcode);
		
		changeaddress_change_btn = (Button) findViewById(R.id.changeaddress_change_btn);

		sp = getSharedPreferences(getApplication().toString(),
				Context.MODE_PRIVATE);
		editor = sp.edit();
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (CheckNetwork.connected(this)){
			setContentView(R.layout.activity_change_address);
			init();
			setTitle("修改地址");
			Intent intent = this.getIntent();
			id = intent.getStringExtra("id");
			
			changeaddress_change_btn.setOnClickListener(new OnClickListener()
			{
	
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
	
					new CustomAsynTask(ChangeAddressActivity.this)
					{
						@Override
						protected Boolean doInBackground(Void... params)
						{
	
							String[] keys = new String[]
							{ "username", "id","receiver", "address", "postcode" };
							
							String[] values = new String[]
							{ sp.getString("username", ""),
									id,
									changeaddress_receiver.getText().toString(),
									changeaddress_address.getText().toString(),
									changeaddress_postcode.getText().toString()
									};
	
							PostJson postJson = new PostJson();
	
							GetParamsMap = postJson.Post(keys, values,
									"editAddress");
							
							Boolean success = false;
							
							success = (Boolean) GetParamsMap.get("success");
							
							return success;
						}
						
						protected void onPostExecute(Boolean result) {
							super.onPostExecute(result);
							
							if (GetParamsMap.get("msg").toString().length()>50)
							{
								Toast.makeText(getApplicationContext(),
										R.string.toast_flase_msg,
										Toast.LENGTH_LONG).show();
							}
							else {
								Toast.makeText(getApplicationContext(),
										(String) GetParamsMap.get("msg"),
										Toast.LENGTH_LONG).show();
							}
								
							
							
								
						};
						
					}.execute();
				}
			});
		}
	}

}
