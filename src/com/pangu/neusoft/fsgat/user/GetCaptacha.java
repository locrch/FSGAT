package com.pangu.neusoft.fsgat.user;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pangu.neusoft.fsgat.core.PostJSONfromGson;
import com.pangu.neusoft.fsgat.model.ListcDef;
import com.pangu.neusoft.fsgat.model.PostgetCaptacha;

public class GetCaptacha
{
	public ListcDef GetCaptacha(String username)
	{
		// TODO Auto-generated constructor stub
		PostJSONfromGson postGson = new PostJSONfromGson();
		
		PostgetCaptacha postgetCaptacha = new PostgetCaptacha();
		
		postgetCaptacha.setUsername(username);
		
		ListcDef listdef = new ListcDef();
		
		String result = (String) postGson.GsonPost(postgetCaptacha, "getCaptacha");
		
		Type listType=new TypeToken<ListcDef>(){}.getType();
		
		Gson gson = new Gson();
		
		listdef = gson.fromJson(result,listType);
		
		if (listdef == null)
		{
			
			listdef.setSuccess(false);
			return listdef;
		}
		
		return listdef;
	}
}
