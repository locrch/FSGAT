package com.fspangu.fsgat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.fspangu.fsgat.pushmessage.*;
import com.pangu.neusoft.fsgat.core.CheckLogin;
import com.pangu.neusoft.fsgat.core.PostJson;
import com.pangu.neusoft.fsgat.model.History;
import com.pangu.neusoft.fsgat.model.PushMessage;
import com.pangu.neusoft.fsgat.user.ChangeAddressDialog;
import com.pangu.neusoft.fsgat.user.ChangePasswordFragment;
import com.pangu.neusoft.fsgat.user.ChargeUserFragment;
import com.pangu.neusoft.fsgat.user.DowngradeUserFragment;
import com.pangu.neusoft.fsgat.user.ListAddressFragment;
import com.pangu.neusoft.fsgat.user.ListPassFragment;
import com.pangu.neusoft.fsgat.user.LoginFragment;
import com.pangu.neusoft.fsgat.user.MysettingFragment;
import com.pangu.neusoft.fsgat.user.OpinionFragment;
import com.pangu.neusoft.fsgat.user.VersionInfoFragment;
import com.pangu.neusoft.fsgat.visa.ShowHistoryFragment;
import com.pangu.neusoft.tools.update.UpdateOperation;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ClipData.Item;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater.Factory;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity
{
	static DisplayMetrics getphonesize;
	static SharedPreferences sp;
	Editor editor;
	BadgeView badge;
	View showbadge;
	
	private static FragmentManager fragmentManager;
	private static RadioGroup radioGroup;

	MenuItem action_userinfo,action_login,action_logout,action_changepassword,action_setting,action_pass,action_address,action_bookinghistory,action_messagebox;
	
	String usertype;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); // 设置为圆形旋转进度条

		// android.app.ActionBar actionBar = this.getActionBar();
		// actionBar.setCustomView(R.layout.title_bar);
		// actionBar.setDisplayShowCustomEnabled(true);
		// actionBar.setDisplayShowHomeEnabled(false);
		// actionBar.show();
		// TextView
		// titleview=(TextView)actionBar.getCustomView().findViewById(R.id.title);
		// titleview.setText(this.getResources().getString(R.string.app_name));
		Resources resource = this.getResources();
		String pkgName = this.getPackageName();

		getActionBar().setBackgroundDrawable(
				this.getBaseContext().getResources()
						.getDrawable(R.drawable.title_background));
		getActionBar().show();
		
		setContentView(R.layout.activity_main);

		setTitle(this.getResources().getString(R.string.app_name));

		setOverflowShowingAlways();
		
		if (savedInstanceState == null)
		{
			/*
			 * PlaceholderFragment phf = new PlaceholderFragment();
			 * getFragmentManager().beginTransaction().replace(R.id.container,
			 * phf); fragmentManager = getFragmentManager();
			 */
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		sp = getSharedPreferences("sp", Context.MODE_PRIVATE);
		editor = sp.edit();

		checkShortCut();

		UpdateOperation update = new UpdateOperation(MainActivity.this);
		update.checkUpdate(false);

		if (!Utils.hasBind(getApplicationContext()))
		{
			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY,
					Utils.getMetaValue(MainActivity.this, "api_key"));
		}

		// Push: 设置自定义的通知样式，具体API介绍见用户手册，如果想使用系统默认的可以不加这段代码
		// 请在通知推送界面中，高级设置->通知栏样式->自定义样式，选中并且填写值：1，
		// 与下方代码中 PushManager.setNotificationBuilder(this, 1, cBuilder)中的第二个参数对应
		CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
				getApplicationContext(), resource.getIdentifier(
						"notification_custom_builder", "layout", pkgName),
				resource.getIdentifier("notification_icon", "id", pkgName),
				resource.getIdentifier("notification_title", "id", pkgName),
				resource.getIdentifier("notification_text", "id", pkgName));
		cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
		cBuilder.setNotificationDefaults(Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE);
		cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
		cBuilder.setLayoutDrawable(resource.getIdentifier(
				"simple_notification_icon", "drawable", pkgName));
		PushManager.setNotificationBuilder(this, 1, cBuilder);
		
	
		
	}


	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		android.app.ActionBar actionBar = getActionBar();
		
		
	}
	

	// 更新界面显示内容
	private void updateDisplay()
	{
		if (!Utils.hasBind(getApplicationContext()))
		{
			Log.d("", "updateDisplay, logText:" + Utils.logStringCache);
		}
		if (!sp.getString("pushuserid", "").equals(""))
		{
			/*Toast.makeText(
					MainActivity.this,
					"用于推送的用户id：" + sp.getString("pushuserid", "") + "\n"
							+ Utils.logStringCache, Toast.LENGTH_LONG).show();*/
		}
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		String action = intent.getAction();
		updateDisplay();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		updateDisplay();
		
		
	}
	
	AsyncTask<Void, Void, Boolean> loading1;
	 HashMap<String, Object> GetParamsMap=new HashMap<String,Object>();
	 @Override
	   	public void onPause(){
	       	if(loading1!=null){
	       		loading1.cancel(true);
	       	}	
	       	super.onPause();
	       }
	static BadgeView messageCenterBadge;
	
	static int count=0;
	
	
	
	public void setupMessagesBadge(final MenuItem msgItem) {
		if (!sp.getString("username", "").equals("")){
		count=0;
		loading1=new AsyncTask<Void, Void, Boolean>(){
		    @SuppressWarnings("deprecation")
			@Override  
	        protected void onPreExecute() {   
	            super.onPreExecute();   
	           
	        }			
			@Override
			protected Boolean doInBackground(Void... params){
				
				String[] keys = new String[]{ "username" };

						String[] values = new String[]{ sp.getString("username", "") };

						PostJson postJson = new PostJson();
						
						GetParamsMap = postJson.Post(keys, values, "GetPushMessage","pushMessageList");

						JSONArray pushMessageList = (JSONArray) GetParamsMap.get("pushMessageList");
						if(pushMessageList!=null){
							for (int i = 0; i < pushMessageList.length(); i++)
							{
								try
								{
									JSONObject jo = (JSONObject) pushMessageList.get(i);
			
									
						            if(jo.getInt("pushMessageStatusID")==1){						            	
						            	count++;
						            }
									
								} catch (JSONException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			
							}
						}
						
						Boolean success= (Boolean) GetParamsMap.get("success");
						return success;
			}
			
			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(Boolean result){
				super.onPostExecute(result);	
				
				if(count>0){
					
				   msgItem.setTitle("消息盒子 "+"("+count+")");
				   //showbadge.setVisibility(View.VISIBLE);
				   badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
				   badge.setTextSize(10);
				   badge.setText(""+count);
				   badge.show();
				}else{
					//showbadge.setVisibility(View.GONE);
				   msgItem.setTitle("消息盒子 ");
				   badge.hide();
				}
			}
			@SuppressWarnings("deprecation")
			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				
			}
			
		};
		loading1.execute();
	}   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		getMenuInflater().inflate(R.menu.main, menu);

		android.app.ActionBar actionBar = getActionBar();

		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.setDisplayShowCustomEnabled(true);

		actionBar.setCustomView(R.layout.title_bar);

		action_userinfo = menu.findItem(R.id.action_userinfo);
		action_login = menu.findItem(R.id.action_login);
		action_logout = menu.findItem(R.id.action_logout);
		action_changepassword = menu.findItem(R.id.action_changepassword);
		action_setting = menu.findItem(R.id.action_setting);
		action_pass = menu.findItem(R.id.action_pass);
		action_address = menu.findItem(R.id.action_address);
		action_bookinghistory = menu.findItem(R.id.action_bookinghistory);
		action_messagebox = menu.findItem(R.id.action_messagebox);
		
		usertype = sp.getString("usertype", "");
		
		if (usertype.equals("1"))
		{
			action_userinfo.setTitle(getResources().getText(R.string.user_level_content_1));
		}
		if (usertype.equals("2"))
		{
			action_userinfo.setTitle(getResources().getText(R.string.user_level_content_2));
		}
		
		if (usertype.equals("3"))
		{
			action_userinfo.setTitle(getResources().getText(R.string.user_level_content_3));
		}
		
		showbadge = (ImageView)findViewById(R.id.badge);
		showbadge.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				List<Fragment> fragments=fragmentManager.getFragments();
				for(Fragment fragment:fragments){
					if(fragment!=null&&fragment.getClass()!=null&&fragment.getClass().getName().equals("com.fspangu.fsgat.PushMessageFragment")){
						Log.e("fragment", fragment.getClass().getName());
						fragmentManager.popBackStackImmediate();
					}					
				}
				
				FragmentTransaction transaction = fragmentManager.beginTransaction();
				PushMessageFragment pshf=new PushMessageFragment();
	        	transaction.replace(R.id.content, pshf);
	        	transaction.addToBackStack(null);
	            transaction.commit();
			}
			
		});
		if (sp.getString("username", "").equals(""))
		{
			action_login.setVisible(true);
			action_logout.setVisible(false);
			action_changepassword.setVisible(false);
			action_setting.setVisible(false);
			action_pass.setVisible(false);
			action_address.setVisible(false);
			action_bookinghistory.setVisible(false);
			action_messagebox.setVisible(false);
			//showbadge.setVisibility(View.GONE);
		}
		else {
			action_logout.setVisible(true);
			action_changepassword.setVisible(true);
			action_setting.setVisible(true);
			action_pass.setVisible(true);
			action_address.setVisible(true);
			action_bookinghistory.setVisible(true);
			action_messagebox.setVisible(true);
			
			
			//showbadge.setVisibility(View.GONE);
			badge = new BadgeView(MainActivity.this, showbadge);
			setupMessagesBadge(action_messagebox);
		}
		
		
		
		Button actionbar_back_btn = (Button) findViewById(R.id.actionbar_back_btn);

		actionbar_back_btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				fragmentManager = getSupportFragmentManager();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				if (!(fragmentManager.getBackStackEntryCount() == 0 || fragmentManager
						.getBackStackEntryCount() == 1))
				{
					fragmentManager.popBackStackImmediate();
				}

			}
		});
		
		
		
			actionbar_back_btn.setVisibility(View.VISIBLE);
			
		return super.onCreateOptionsMenu(menu);  
	}

	
	/*@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu)
	{
		// TODO Auto-generated method stub
		action_userinfo = menu.findItem(R.id.action_userinfo);
		if (featureId == R.id.action_userinfo)
		{
			View v = getLayoutInflater().inflate(  
	                R.layout.menu_user_info, null);
			 action_userinfo.setActionView(v);
		}
		return true;
	}*/
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		if (!(sp.getString("username", "").equals("")))
		{
			action_userinfo.setVisible(true);
			action_login.setVisible(false);
			action_logout.setVisible(true);
			action_changepassword.setVisible(true);
			action_setting.setVisible(true);
			action_pass.setVisible(true);
			action_address.setVisible(true);
			action_bookinghistory.setVisible(true);
			action_messagebox.setVisible(true);
			//showbadge.setVisibility(View.GONE);
		}
		
		
		switch (id)
		{
		case R.id.action_login:
			LoginFragment lf = new LoginFragment();
			if (!lf.isAdded()||!lf.isVisible())
			{
				getSupportFragmentManager().beginTransaction().add(R.id.content, lf).commit();
			}
			break;
		case R.id.action_messagebox:
			//显示消息盒子
			
			PushMessageFragment pshf=new PushMessageFragment();
        	transaction.add(R.id.content, pshf);
        	transaction.addToBackStack(null);
            transaction.commit();
			break;
		case R.id.action_logout:
			logout();			
			
			break;
		
		case R.id.action_changepassword:
			ChangePasswordFragment cpf=new ChangePasswordFragment();
        	transaction.add(R.id.content, cpf);
        	transaction.addToBackStack(null);
            transaction.commit();
			break;
			
		case R.id.action_setting:
			MysettingFragment msf=new MysettingFragment();
        	transaction.add(R.id.content, msf);
        	transaction.addToBackStack(null);
            transaction.commit();
			break;
			
		case R.id.action_pass:
			ListPassFragment lpf=new ListPassFragment();
			if (!lpf.isVisible())
			{
				transaction.replace(R.id.content, lpf);
	        	
	            transaction.commit();
			}
        	
			break;
		case R.id.action_address:
			ListAddressFragment laf=new ListAddressFragment();
			if (!laf.isVisible())
			{
				transaction.replace(R.id.content, laf);
	        	
				transaction.commit();
			}
        	
			break;
		case R.id.action_bookinghistory:
			ShowHistoryFragment shf=new ShowHistoryFragment();
        	transaction.add(R.id.content, shf);
        	transaction.addToBackStack(null);
            transaction.commit();
			break;
		case R.id.action_update:
			UpdateOperation update = new UpdateOperation(this);
    		update.checkUpdate(true);
			break;
		case R.id.action_opinion:
			OpinionFragment of=new OpinionFragment();
        	transaction.add(R.id.content, of);
        	transaction.addToBackStack(null);
            transaction.commit();
			break;
			
		case R.id.action_versioninfo:
			VersionInfoFragment vif=new VersionInfoFragment();
        	transaction.add(R.id.content, vif);
        	transaction.addToBackStack(null);
            transaction.commit();
			break;
			
		case R.id.action_userinfo:
			if (usertype.equals("1"))
			{
			ChargeUserFragment cuf = new ChargeUserFragment();
			if (!cuf.isVisible())
			{
			transaction.replace(R.id.content, cuf);
        	transaction.commit();
			}
			
			}
			else {
			DowngradeUserFragment duf = new DowngradeUserFragment();
			if (!duf.isVisible())
			{
			transaction.replace(R.id.content, duf);
	        transaction.commit();
			}
				
			}
			
			break;
		default:
			
			
			break;
		}

	
		return super.onOptionsItemSelected(item);
	}
	
	  
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{
		// TODO Auto-generated method stub
		
		
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
		{
			if (menu.getClass().getSimpleName().equals("MenuBuilder"))
			{
				try
				{
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
					setupMessagesBadge(action_messagebox);
				} catch (Exception e)
				{
				}
			}
		}
	
		return super.onMenuOpened(featureId, menu);
	}

	private void setOverflowShowingAlways()
	{
		try
		{
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void checkShortCut()
	{
		if (!hasShortcut(MainActivity.this) && sp.getBoolean("alertshoutcut", true))
		{
			Dialog alertDialog = new AlertDialog.Builder(MainActivity.this)
					.setTitle("")
					.setMessage("是否添加到桌面快捷方式？")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("不在提醒",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									editor.putBoolean("alertshoutcut", false);
									editor.commit();
								}
							})
					.setNeutralButton("确定",
							new DialogInterface.OnClickListener()
							{

								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO Auto-generated method stub
									addShortcutToDesktop(MainActivity.this);
									editor.putBoolean("alertshoutcut", false);
									editor.commit();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener()
							{

								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO Auto-generated method stub

								}
							}).create();
			alertDialog.show();

		}
	}


public static void addShortcutToDesktop(Context context) {
    if (!hasShortcut(context)) {
      Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
      shortcutIntent.putExtra("duplicate", false);
      shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,context.getString(R.string.app_name));
      shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
      Intent.ShortcutIconResource.fromContext(context,R.drawable.ic_launcher));
      Intent intent = new Intent(context, MainActivity.class);
      intent.setAction("android.intent.action.MAIN");
      intent.addCategory("android.intent.category.LAUNCHER");
      shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
      context.sendBroadcast(shortcutIntent);
    }
  }

  private static boolean hasShortcut(Context context) {
    String AUTHORITY = getAuthorityFromPermission(context,"com.android.launcher.permission.READ_SETTINGS");
    System.out.println(" AUTHORITY ..." + AUTHORITY);
    if (AUTHORITY == null) {
      return false;
    }
    Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY+ "/favorites?notify=true");
    String title = "";
    final PackageManager packageManager = context.getPackageManager();
    try {
      title = packageManager.getApplicationLabel(
          packageManager.getApplicationInfo(context.getPackageName(),
          PackageManager.GET_META_DATA)).toString();
    } catch (NameNotFoundException e) {
              e.printStackTrace();
    }
    Cursor c = context.getContentResolver().
           query(CONTENT_URI,new String[] { "title" }, "title=?", new String[] { title },null);
    if (c != null && c.getCount() > 0) {
      return true;
    }
    return false;
  }

  private static String getAuthorityFromPermission(Context context,String permission) {
    if (TextUtils.isEmpty(permission)) {
      return null;
    }
    List<PackageInfo> packageInfoList = 
    context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
    if (packageInfoList == null) {
      return null;
    }
    for (PackageInfo packageInfo : packageInfoList) {
      ProviderInfo[] providerInfos = packageInfo.providers;
      if (providerInfos != null) {
        for (ProviderInfo providerInfo : providerInfos) {
          if (permission.equals(providerInfo.readPermission)|| 
            permission.equals(providerInfo.writePermission)) {
            return providerInfo.authority;
          }
        }
      }
    }
    return null;
  }
  
	@SuppressLint("NewApi")
	private void showpop(View v)
	{
		// TODO Auto-generated method stub
		PopupMenu popup = new PopupMenu(this, v);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.main, popup.getMenu());
		popup.show();
	}

	

	public void logout()
	{
		Dialog alertDialog = new AlertDialog.Builder(this)
				.setTitle("注销")
				.setMessage("确定要注销用户吗?")
				.setIcon(R.drawable.ic_launcher)
				.setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

						editor.remove("username");
						editor.commit();
						action_userinfo.setVisible(false);
						action_login.setVisible(true);
						action_logout.setVisible(false);
						action_changepassword.setVisible(false);
						action_setting.setVisible(false);
						action_pass.setVisible(false);
						action_address.setVisible(false);
						action_bookinghistory.setVisible(false);
						action_messagebox.setVisible(false);
						showbadge.setVisibility(View.GONE);
						getSupportFragmentManager().beginTransaction().add(R.id.content, new LoginFragment()).commit();
						
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

					}
				}).create();
		alertDialog.show();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{
		
		public PlaceholderFragment()
		{
		}

	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			fragmentManager = getFragmentManager();

			radioGroup = (RadioGroup) rootView.findViewById(R.id.rg_tab);
			RadioButton ta1 = (RadioButton) rootView.findViewById(R.id.rg_ta1);
			RadioButton ta2 = (RadioButton) rootView.findViewById(R.id.rg_ta2);
			RadioButton ta3 = (RadioButton) rootView.findViewById(R.id.rg_ta3);
			RadioButton ta4 = (RadioButton) rootView.findViewById(R.id.rg_ta4);
			//RadioButton ta5 = (RadioButton) rootView.findViewById(R.id.rg_ta5);
			
			
			OnClickListener a = new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					// TODO Auto-generated method stub
						
					if (arg0.getId() == R.id.rg_ta1)
					{
						showFragment(1);
					}
					if (arg0.getId() == R.id.rg_ta2)
					{
						showFragment(2);
					}
					if (arg0.getId() == R.id.rg_ta3)
					{
						showFragment(3);
					}
					if (arg0.getId() == R.id.rg_ta4)
					{
						showFragment(4);
					}
					/*if (arg0.getId() == R.id.rg_ta5)
					{
						showFragment(5);
					}*/

				}

			};
			
			
			
			// 设置
			// getphonesize = new DisplayMetrics();
			// getActivity().getWindowManager().getDefaultDisplay().getMetrics(getphonesize);
			// setsize(ta1);
			// setsize(ta2);
			// setsize(ta3);
			// setsize(ta4);
			// setsize(ta5);
			// RelativeLayout.LayoutParams vp =
			// (RelativeLayout.LayoutParams)radioGroup.getLayoutParams();
			// vp.width = (int) (getphonesize.widthPixels);
			// vp.height = (int) (getphonesize.widthPixels * 1/5);
			// vp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

			ta1.setOnClickListener(a);
			ta2.setOnClickListener(a);
			ta3.setOnClickListener(a);
			ta4.setOnClickListener(a);
			//ta5.setOnClickListener(a);

			
			if (sp.getString("username", "").equals(""))
			{
				Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT)
						.show();
				if (fragmentManager.getBackStackEntryCount() >= 1)
				{
					int len = fragmentManager.getBackStackEntryCount();
					for (int i = 0; i < len; i++)
					{
						fragmentManager.popBackStack();
					}
				}
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				Fragment loginFragment = new LoginFragment();
				transaction.replace(R.id.content, loginFragment);
				transaction.commit();
			} else
			{
				//默认打开显示的fragment
				showFragment(2);
			}

			return rootView;
		}

		// void setsize(View v){
		// RadioGroup.LayoutParams vp =
		// (RadioGroup.LayoutParams)v.getLayoutParams();
		// vp.width = (int) (getphonesize.widthPixels * 1/5);
		// vp.height = (int) (vp.width * 1/1);
		//
		// }

		public void showFragment(int checkedId)
		{
			try
			{
				// Log.e("count: ",
				// ""+fragmentManager.getBackStackEntryCount());
				if (fragmentManager.getBackStackEntryCount() >= 1)
				{
					int len = fragmentManager.getBackStackEntryCount();
					for (int i = 0; i < len; i++)
					{
						fragmentManager.popBackStack();
					}
				}
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				Fragment fragment = FragmentFactory
						.getInstanceByIndex(checkedId);
				transaction.replace(R.id.content, fragment);
				transaction.addToBackStack(null);
				transaction.commit();

			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (fragmentManager.getBackStackEntryCount() <= 1)
			{
				// 创建退出对话框
				AlertDialog isExit = new AlertDialog.Builder(this).create();
				// 设置对话框标题
				isExit.setTitle("提示");
				// 设置对话框消息
				isExit.setMessage("确定要退出吗");
				// 添加选择按钮并注册监听
				isExit.setButton("确定", listener);
				isExit.setButton2("取消", listener);
				// 显示对话框
				isExit.show();
				return false;
			}
		}

		return super.onKeyDown(keyCode, event);

	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
	{
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which)
			{
			case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
				if (!sp.getBoolean("autologin", false))
				{
					editor.remove("username");
					editor.commit();
				}
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

}
