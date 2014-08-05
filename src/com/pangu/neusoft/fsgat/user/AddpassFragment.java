package com.pangu.neusoft.fsgat.user;

import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONObject;

import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.CheckNetwork;
import com.pangu.neusoft.fsgat.core.PostJson;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("NewApi")
public class AddpassFragment extends Fragment
{
	EditText addpass_passNumber, addpass_surName, addpass_givenName,
			addpass_dob, addpass_issueArea, addpass_area, addpass_pob,
			addpass_expireDate, addpass_issueDate;
	Button addpass_bundle_btn;

	RadioGroup addpass_sex;
	RadioButton addpass_male, addpass_female;

	Spinner addpass_spinner;
	private ArrayAdapter<String> adapter;
	DatePickerDialog dpd_dob, dpd_expireDate, dpd_issueDate;
	SharedPreferences sp;
	Editor editor;
	String sex;
	String areaid = "07";
	JSONObject joget;
	HashMap<String, Object> GetParamsMap;
	private static final String m[] =
	{ "佛山市（不包括顺德）", "佛山市顺德区" };

	FragmentTransaction transaction;
	FragmentManager fragmentManager;
	AddpassFragment addpassFragment;
	
	private void init(){
//	android.app.ActionBar actionBar = this.getActivity().getActionBar();
//	actionBar.setCustomView(R.layout.title_bar);
//	actionBar.setDisplayShowCustomEnabled(true);
//	actionBar.setDisplayShowHomeEnabled(false);
//	actionBar.show();
//	TextView titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
//	titleview.setText("添加通行证");
		this.getActivity().setTitle("添加通行证");
		// TODO Auto-generated method stub
		addpass_passNumber = (EditText) getActivity().findViewById(
				R.id.addpass_passNumber);
		addpass_surName = (EditText) getActivity().findViewById(
				R.id.addpass_surName);
		addpass_givenName = (EditText) getActivity().findViewById(
				R.id.addpass_givenName);
		addpass_issueArea = (EditText) getActivity().findViewById(
				R.id.addpass_issueArea);
		addpass_area = (EditText) getActivity().findViewById(R.id.addpass_area);
		addpass_pob = (EditText) getActivity().findViewById(R.id.addpass_pob);

		addpass_bundle_btn = (Button) getActivity().findViewById(
				R.id.addpass_bundle_btn);

		addpass_dob = (EditText) getActivity().findViewById(R.id.addpass_dob);
		addpass_expireDate = (EditText) getActivity().findViewById(
				R.id.addpass_expireDate);
		addpass_issueDate = (EditText) getActivity().findViewById(
				R.id.addpass_issueDate);

		addpass_sex = (RadioGroup) getActivity().findViewById(R.id.addpass_sex);
		addpass_male = (RadioButton) getActivity().findViewById(
				R.id.addpass_male);
		addpass_female = (RadioButton) getActivity().findViewById(
				R.id.addpass_female);

		addpass_spinner = (Spinner) getActivity().findViewById(
				R.id.addpass_spinner);

		sp = getActivity()
				.getSharedPreferences(
						"sp",
						Context.MODE_PRIVATE);
		editor = sp.edit();
		
		fragmentManager = getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		addpassFragment = this;
		
		GetParamsMap = new HashMap<String, Object>();
		joget = new JSONObject();

		adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.dropdowntext, m);
		adapter.setDropDownViewResource(R.layout.drop_down_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		addpass_spinner.setAdapter(adapter);
		addpass_spinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3)
			{
				// TODO Auto-generated method stub

				switch (arg2)
				{
				case 0:
					areaid = "07";
					break;
				case 1:
					areaid = "23";
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		DatePickerDialog.OnDateSetListener dateListener_dob = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month,
					int dayOfMonth)
			{

				addpass_dob
						.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
			}
		};

		DatePickerDialog.OnDateSetListener dateListener_expireDate = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month,
					int dayOfMonth)
			{

				addpass_expireDate.setText(year + "-" + (month + 1) + "-"
						+ dayOfMonth);
			}
		};
		DatePickerDialog.OnDateSetListener dateListener_issueDate = new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month,
					int dayOfMonth)
			{

				addpass_issueDate.setText(year + "-" + (month + 1) + "-"
						+ dayOfMonth);
			}
		};
		Calendar calendar = Calendar.getInstance();

		dpd_dob = new DatePickerDialog(getActivity(), dateListener_dob,
				calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dpd_expireDate = new DatePickerDialog(getActivity(),
				dateListener_expireDate, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		dpd_issueDate = new DatePickerDialog(getActivity(),
				dateListener_issueDate, calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));

		addpass_dob.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// TODO Auto-generated method stub
				dpd_dob.show();
				return false;
			}
		});

		addpass_expireDate.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// TODO Auto-generated method stub
				dpd_expireDate.show();
				return false;
			}
		});

		addpass_issueDate.setOnTouchListener(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				// TODO Auto-generated method stub
				dpd_issueDate.show();
				return false;
			}
		});
		RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// TODO Auto-generated method stub
				if (checkedId == addpass_male.getId())
				{
					sex = addpass_male.getText().toString();
				} else if (checkedId == addpass_female.getId())
				{
					sex = addpass_female.getText().toString();
				}
			}
		};

		addpass_sex.setOnCheckedChangeListener(mChangeRadio);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		Button actionbar_back_btn = (Button)getActivity().findViewById(R.id.actionbar_back_btn);
		
		actionbar_back_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				/*YwblFragment ywbl = new YwblFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, ywbl).commit();
				*/
				getFragmentManager().popBackStack();
			}
		});
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("添加证件");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		setHasOptionsMenu(true);

		return inflater.inflate(R.layout.activity_addpass, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if (CheckNetwork.connected(this)){
			init();
	
			addpass_bundle_btn.setOnClickListener(new OnClickListener()
			{
	
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
	
					new CustomAsynTask(getActivity())
					{
						@Override
						protected Boolean doInBackground(Void... params)
						{
	
							String[] keys = new String[]
							{ "username", "passNumber", "surName", "givenName",
									"dob", "sex", "pob", "expireDate", "issueDate",
									"issueArea", "area" };
	
							String[] values = new String[]
							{ sp.getString("username", ""),
									addpass_passNumber.getText().toString(),
									addpass_surName.getText().toString(),
									addpass_givenName.getText().toString(),
									addpass_dob.getText().toString(), sex,
									addpass_pob.getText().toString(),
									addpass_expireDate.getText().toString(),
									addpass_issueDate.getText().toString(),
									addpass_issueArea.getText().toString(), areaid };
	
							PostJson postJson = new PostJson();
	
							GetParamsMap = postJson.Post(keys, values, "addPass");
	
							Boolean success = false;
	
							success = (Boolean) GetParamsMap.get("success");
	
							return success;
						}
	
						protected void onPostExecute(Boolean result)
						{
							super.onPostExecute(result);
	
							if (GetParamsMap.get("msg").toString().length() > 50)
							{
								Toast.makeText(
										getActivity().getApplicationContext(),
										R.string.toast_flase_msg, Toast.LENGTH_LONG)
										.show();
							} else
							{
								Toast.makeText(
										getActivity().getApplicationContext(),
										(String) GetParamsMap.get("msg"),
										Toast.LENGTH_LONG).show();
							}
							if (result)
							{
								fragmentManager.popBackStack();
								transaction.commit();
							}
							
							
						};
	
					}.execute();
				}
			});
		}
	}
}
