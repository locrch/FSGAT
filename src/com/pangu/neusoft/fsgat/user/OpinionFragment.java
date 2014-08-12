package com.pangu.neusoft.fsgat.user;

import com.fspangu.fsgat.R;
import com.fspangu.fsgat.YwblFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OpinionFragment extends Fragment
{
	View rootview;
	EditText opinion_content;
	Button opinion_commit_btn;
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
				YwblFragment ywbl = new YwblFragment();
				getFragmentManager().beginTransaction().replace(R.id.content, ywbl).commit();
			}
		});
		
		actionbar_back_btn.setVisibility(View.VISIBLE);
		
		TextView actionbar_title = (TextView)getActivity().findViewById(R.id.actionbar_title);
		
		actionbar_title.setText("意见反馈");
	}
	
	private void init()
	{
		// TODO Auto-generated method stub
		opinion_content = (EditText)rootview.findViewById(R.id.opinion_content);
		opinion_commit_btn = (Button)rootview.findViewById(R.id.opinion_commit_btn);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		
		rootview = inflater.inflate(R.layout.fragment_opinion,null);
		
		this.getActivity().setTitle(this.getActivity().getResources().getString(R.string.app_name));
		
		init();
		
		return rootview;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		opinion_commit_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "反馈成功！",
						Toast.LENGTH_SHORT).show();
				
				opinion_content.getText().clear();
			}
		});
	}
}
