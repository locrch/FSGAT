package com.pangu.neusoft.fsgat.CustomView;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;

public class CustomDatepickerDialog extends DatePickerDialog
{
	
	
	
	public CustomDatepickerDialog(Context context, int theme,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth,final EditText input)
	{
		super(context, theme, callBack, year, monthOfYear, dayOfMonth);
		// TODO Auto-generated constructor stub
		
		DatePickerDialog.OnDateSetListener dateListener =  
				new DatePickerDialog.OnDateSetListener() { 
				        @Override 
				        public void onDateSet(DatePicker datePicker,  
				                int year, int month, int dayOfMonth) { 
				            
				        	 //Calendar月份是从0开始,所以month要加1 
				        	input.setText(year + "年" +  
				                    (month+1) + "月" + dayOfMonth + "日"); 
				        } 
				    }; 
			
				    Calendar calendar = Calendar.getInstance();
		
	}

}
