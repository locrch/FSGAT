package com.pangu.neusoft.fsgat.model;

import org.json.JSONObject;

public class user 
{
 private static String username,password;

public static String getUsername()
{
	return username;
}

public static void setUsername(String username)
{
	user.username = username;
}

public static String getPassword()
{
	return password;
}

public static void setPassword(String password)
{
	user.password = password;
}


}
