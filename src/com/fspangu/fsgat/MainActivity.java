package com.fspangu.fsgat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.fspangu.fsgat.pushmessage.*;
import com.pangu.neusoft.fsgat.core.CheckLogin;
import com.pangu.neusoft.fsgat.user.ChangeAddressDialog;
import com.pangu.neusoft.fsgat.user.ChangePasswordFragment;
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
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity
{
	static DisplayMetrics getphonesize;
	static SharedPreferences sp;
	Editor editor;

	private static FragmentManager fragmentManager;
	private static RadioGroup radioGroup;

	MenuItem action_logout,action_changepassword,action_setting,action_pass,action_address,action_bookinghistory;
	
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		getMenuInflater().inflate(R.menu.main, menu);

		android.app.ActionBar actionBar = getActionBar();

		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.setDisplayShowCustomEnabled(true);

		actionBar.setCustomView(R.layout.title_bar);

		action_logout = menu.findItem(R.id.action_logout);
		action_changepassword = menu.findItem(R.id.action_changepassword);
		action_setting = menu.findItem(R.id.action_setting);
		action_pass = menu.findItem(R.id.action_pass);
		action_address = menu.findItem(R.id.action_address);
		action_bookinghistory = menu.findItem(R.id.action_bookinghistory);
		
		if (sp.getString("username", "").equals(""))
		{
			action_logout.setVisible(false);
			action_changepassword.setVisible(false);
			action_setting.setVisible(false);
			action_pass.setVisible(false);
			action_address.setVisible(false);
			action_bookinghistory.setVisible(false);
		}
		else {
			action_logout.setVisible(true);
			action_changepassword.setVisible(true);
			action_setting.setVisible(true);
			action_pass.setVisible(true);
			action_address.setVisible(true);
			action_bookinghistory.setVisible(true);
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
		
			
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		if (!(sp.getString("username", "").equals("")))
		{
			action_logout.setVisible(true);
			action_changepassword.setVisible(true);
			action_setting.setVisible(true);
			action_pass.setVisible(true);
			action_address.setVisible(true);
			action_bookinghistory.setVisible(true);
		}
		
		switch (id)
		{
		case R.id.action_logout:
			logout();
			
			action_logout.setVisible(false);
			action_changepassword.setVisible(false);
			action_setting.setVisible(false);
			action_pass.setVisible(false);
			action_address.setVisible(false);
			action_bookinghistory.setVisible(false);
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
		if (!isAddShortCut() && sp.getBoolean("alertshoutcut", true))
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
									addShortCut();
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

	public boolean isAddShortCut()
	{

		boolean isInstallShortcut = false;
		final ContentResolver cr = this.getContentResolver();

		int versionLevel = android.os.Build.VERSION.SDK_INT;
		String AUTHORITY = "com.android.launcher2.settings";

		// 2.2以上的系统的文件文件名字是不一样的
		if (versionLevel >= 8)
		{
			AUTHORITY = "com.android.launcher2.settings";
		} else
		{
			AUTHORITY = "com.android.launcher.settings";
		}

		final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
				+ "/favorites?notify=true");
		Cursor c = cr.query(CONTENT_URI, new String[]
		{ "title", "iconResource" }, "title=?", new String[]
		{ getString(R.string.app_name) }, null);

		if (c != null && c.getCount() > 0)
		{
			isInstallShortcut = true;
		}
		return isInstallShortcut;
	}

	public void addShortCut()
	{

		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 设置属性
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				getResources().getString(R.string.app_name));
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
				this.getApplicationContext(), R.drawable.ic_launcher);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconRes);

		// 是否允许重复创建
		shortcut.putExtra("duplicate", false);

		// 设置桌面快捷方式的图标
		Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
				R.drawable.ic_launcher);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

		// 点击快捷方式的操作
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setClass(MainActivity.this, MainActivity.class);

		// 设置启动程序
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

		// 广播通知桌面去创建
		this.sendBroadcast(shortcut);
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
						/*FragmentTransaction transaction = getSupportFragmentManager()
								.beginTransaction();
						LoginFragment fragment = new LoginFragment();
						transaction.replace(R.id.content, fragment);
						transaction.commit();*/
						
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
