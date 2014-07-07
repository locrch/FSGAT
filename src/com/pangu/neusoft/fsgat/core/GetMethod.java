package com.pangu.neusoft.fsgat.core;

import java.util.HashMap;

public class GetMethod
{
	public static String URL="http://202.103.160.153:1009/GetInfo.ashx?method=";
	
	public static final HashMap<String, String> Methodname = new HashMap<String, String>();
	
	public static final void  PutMethodname(){
		
		Methodname.put("registermember", "registerMember");//用户注册
		Methodname.put("getCaptacha", "getCaptacha");//获取验证码
		Methodname.put("loginmember", "loginMember");//登录
		Methodname.put("resetpassword", "resetPassword");//修改密码
		Methodname.put("getmessage", "getMessage");//信息服务
		Methodname.put("addpass", "addPass");//绑定证件
		Methodname.put("listpass", "listPass");//通行证列表
		Methodname.put("deletepass", "deletePass");//删除通行证	
		Methodname.put("uploadpass", "uploadPass");//上传通行证照片
		Methodname.put("deleteuploadpass", "deleteUploadPass");//删除通行证照片
	}
	
	public static void main(String[] args)
	{
		PutMethodname();
	}
}
