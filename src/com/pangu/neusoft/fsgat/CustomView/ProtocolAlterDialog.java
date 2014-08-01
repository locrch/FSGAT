package com.pangu.neusoft.fsgat.CustomView;

import com.fspangu.fsgat.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public abstract class ProtocolAlterDialog extends AlertDialog implements  
android.view.View.OnClickListener
{
	TextView protocol_dialog_content,protocol_dialog_title;
	Button agree_btn,disagree_btn;
	String protocol_content,protocol_title;
	protected ProtocolAlterDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener)
	{
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}
	
	protected ProtocolAlterDialog(Context context,String content,String title)
	{
		super(context);
		// TODO Auto-generated constructor stub
		protocol_content = content;
		protocol_title = title;
	}
	
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
        // 加载自定义布局  
        setContentView(R.layout.protocol_dialog);  
        
        // setDialogSize(300, 200);  
        protocol_dialog_title = (TextView)findViewById(R.id.protocol_dialog_title);
        protocol_dialog_content = (TextView) findViewById(R.id.protocol_dialog_content);  
        protocol_dialog_title.setText(protocol_title.toString());
        protocol_dialog_content.setText(protocol_content.toString());
        agree_btn = (Button)findViewById(R.id.protocol_dialog_agree_btn);
        disagree_btn = (Button)findViewById(R.id.protocol_dialog_disagree_btn);
        
        agree_btn.setOnClickListener(this);
        disagree_btn.setOnClickListener(this);
    }  
  
    /**  
     * 修改 框体大小  
     *   
     * @param width  
     * @param height  
     */  
    /*public void setDialogSize(int width, int height) {  
        WindowManager.LayoutParams params = getWindow().getAttributes();  
        params.width = 350;  
        params.height = 200;  
        this.getWindow().setAttributes(params);  
    }*/

    public abstract void agree_clickCallBack();  
    public abstract void diaagree_clickCallBack();
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		
		if (v == agree_btn)
		{
			agree_clickCallBack();
			
		}else {
			diaagree_clickCallBack();
			
		}
	}  
	 
   
}
