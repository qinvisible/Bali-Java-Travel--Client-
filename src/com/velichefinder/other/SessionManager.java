package com.velichefinder.other;

import java.util.HashMap;

import com.velichefinder.activity.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	SharedPreferences pref;
	Editor editor;
	Context context;
	int PRIVATE_MODE=0;
	private static final String PREF_USER="TransportFinder";
	private static final String IS_LOGIN="isLoggedIn";
	public static final String KEY_USER="user";
	public static final String KEY_PASS="pass";
	public static final String USER_="user";
	public static final String PASS_="pass";
	
	public SessionManager(Context context){
		this.context = context;
		pref = context.getSharedPreferences(PREF_USER, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	public void createLoginSession(String user, String pass){
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_USER, user);
		editor.putString(KEY_PASS, pass);
		editor.commit();
		HashMap<String, String> map=new HashMap<String, String>();
		map.put(USER_, user);
		map.put(PASS_, pass);
	}	
	
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			Intent i = new Intent(context, LoginActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}else{
			Intent i = new Intent(context, IndexActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
			
	}
	
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(KEY_USER, pref.getString(KEY_USER, null));
		user.put(KEY_PASS, pref.getString(KEY_PASS, null));
		return user;
	}
	public String getUsername(){
		HashMap<String, String> user = new HashMap<String, String>();
		return user.get(USER_);
		
	}
	
	public void logoutUser(){
		editor.clear();
		editor.commit();
		
		Intent i = new Intent(context, LoginActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
	
	private boolean isLoggedIn() {
		
		return pref.getBoolean(IS_LOGIN, false);
	}

}
