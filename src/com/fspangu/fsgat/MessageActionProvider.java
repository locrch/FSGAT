package com.fspangu.fsgat;

import android.content.Context;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MessageActionProvider extends ActionProvider {

	   private Context context;  
	    private LayoutInflater inflater;  
	    private View view;  
	    private ImageView button;  
	    
	    
	    
	    public Context getContext() {
			return context;
		}


		public void setContext(Context context) {
			this.context = context;
		}


		public ImageView getButton() {
			return button;
		}


		public void setButton(ImageView button) {
			this.button = button;
		}


		public MessageActionProvider(Context context) {  
	        super(context);  
	        // TODO Auto-generated constructor stub  
	        this.context = context;  
	        inflater = LayoutInflater.from(context);  
	        view = inflater.inflate(R.layout.myactionprovider, null);  
	    }  
	  
	      
	    @Override  
	    public View onCreateActionView() {  
	        // TODO Auto-generated method stub  
	        button = (ImageView) view.findViewById(R.id.imageView1);  
	        button.setOnClickListener(new View.OnClickListener() {  
	              
	            @Override  
	            public void onClick(View v) {  
	                // TODO Auto-generated method stub  
	                Toast.makeText(context, "是我，没错", Toast.LENGTH_SHORT).show();  
	            }  
	        });  
	        return view;  
	    }  

}
