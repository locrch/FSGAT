package com.pangu.neusoft.fsgat.user;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.fspangu.fsgat.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.CustomView.CustomAsynTask;
import com.pangu.neusoft.fsgat.core.PostJSONfromGson;
import com.pangu.neusoft.fsgat.model.ListcCharge;
import com.pangu.neusoft.fsgat.model.ListdownStation;
import com.pangu.neusoft.fsgat.model.PostcCharge;
import com.pangu.neusoft.fsgat.model.PostgetDownStation;
import com.pangu.neusoft.fsgat.model.downStation;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class ChargeUserFragment extends Fragment
{ 
	View rootview;
	EditText charge_user_username,charge_user_var;
	Button charge_get_var_btn,charge_user_charge_btn;
	SharedPreferences sp;
	Editor editor;
	private void init()
	{
		// TODO Auto-generated method stub
		charge_user_username = (EditText)rootview.findViewById(R.id.charge_user_username);
		charge_user_var = (EditText)rootview.findViewById(R.id.charge_user_var);
		charge_get_var_btn = (Button)rootview.findViewById(R.id.charge_get_var_btn);
		charge_user_charge_btn = (Button)rootview.findViewById(R.id.charge_user_charge_btn);
		
		sp = getActivity().getSharedPreferences("sp",Context.MODE_PRIVATE);
		editor = sp.edit();
	}
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			rootview = inflater.inflate(R.layout.fragment_charge_user, null);
			init();
			return rootview;
		}
		@Override
		public void onActivityCreated(Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			charge_user_charge_btn.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					new CustomAsynTask(getActivity()){
						protected Boolean doInBackground(Void... params) {
							
							/*PostJSONfromGson postGson = new PostJSONfromGson();
							
							PostcCharge postcCharge = new PostcCharge();
							
							ListcCharge listcCharge = new ListcCharge();
							
							
							postcCharge.setUsername(sp.getString("username", null));
							
							
							
							
							postgetDownStation.setBusCompanyID(busCompanyID);
							
							ListdownStation listdownStation = new ListdownStation();
							
							String result = (String) postGson.GsonPost(postgetDownStation, "getDownStation");
							
							Type listType=new TypeToken<ListdownStation>(){}.getType();
							
							Gson gson = new Gson();
							
							listdownStation = gson.fromJson(result,listType);
							
							ArrayList<downStation> downStationsList = new ArrayList<downStation>();
							
							downStationsList = listdownStation.getDownStationList();
							
							success = listdownStation.getSuccess();
							
							listdownStationName = new ArrayList<String>();
							
							if (!listdownStationName.isEmpty()) {
								listdownStationName.clear();
							}
							
							if (!listdownStationID.isEmpty()) {
								listdownStationID.clear();
							}
							
							for (int j = 0; j < downStationsList.size(); j++) {
								
								downstation = downStationsList.get(j);
								
								listdownStationName.add(downstation.getStationName());
								
								listdownStationID.add(downstation.getStationID());
							}
							*/
							return null;
						};
						
						protected void onPostExecute(Boolean result) {
							
							
							
						};
						
					}.execute();
				}
			});
		}
}
