package com.velichefinder.koneksi;



import java.util.ArrayList;



import android.content.Context;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.velichefinder.other.*;


import org.apache.http.NameValuePair;




public class HttpConnection {
	
	
	private String url = setGet.url;
	private String url1=null;
	
	public void AddUser(ArrayList<NameValuePair> data){
		this.url1 = url+"registrasi.php";
		Execute(data);
		
	}
	public void LoginUser(ArrayList<NameValuePair> data){
		this.url1 =url+"login_user.php";
		Execute(data);
	}
	public void getUser(ArrayList<NameValuePair> data){
		this.url1=url+"get_user.php";
		Execute(data);
	}
	public void UpdateUser(ArrayList<NameValuePair> data){
		this.url1=url+"update_user.php";
		Execute(data);
	}
	public String Execute(ArrayList<NameValuePair> data){
		String response;
		try {
			response = KoneksiClientServer.eksekusiHttpPost(url1, data);
			String res = response.toString();
			res = res.trim();
			setGet.setStatus(res);
			Log.d("state",res);
			Log.d("state",url1);
			return res;
		} catch (Exception e) {
			setGet.setStatus("RTO");
			return "Error Execute !!";
		}
		
	}
	public boolean cek_status(Context cek) {
		ConnectivityManager cm = (ConnectivityManager) cek.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		
		if (info != null && info.isConnected())
			{
			return true;
		}else{
			return false;
		}
	}
	public void getResponse(){
		this.url1=url+"cek_koneksi.php";
		ExecutePost();
	}
	
	public String ExecutePost(){
		String response;
		try {
			response=KoneksiClientServer.eksekusiHttpGet(url1);
			String res=response.toString();
			res=res.trim();
			setGet.setStatus("Parsing OK");
			return res;
		} catch (Exception e) {
			setGet.setStatus("RTO");
			e.printStackTrace();
			return "error";
		}
	}


}
