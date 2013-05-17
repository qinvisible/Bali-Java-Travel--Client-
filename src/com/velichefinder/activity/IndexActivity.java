package com.velichefinder.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.velichefinder.koneksi.HttpConnection;
import com.velichefinder.koneksi.JSONParser;
import com.velichefinder.other.SessionManager;
import com.velichefinder.other.setGet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexActivity extends Activity {

	TextView menu1, menu2, menu3, logout;
	ImageView about,  help , close_help;
	SessionManager session;
	Dialog progressDialog, alertDialog, aboutDialog, helpDialog;
	TextView ok, exit, cancel, dialog_close;
	TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		HttpConnection con=new HttpConnection();
		if(con.cek_status(getApplicationContext())){
			new MyFetchTask().execute();
			setContentView(R.layout.activity_index);
			menu1=(TextView)findViewById(R.id.menu_1);
			menu2=(TextView)findViewById(R.id.menu_2);
			menu3=(TextView)findViewById(R.id.menu_3);
			about=(ImageView)findViewById(R.id.menu_about);
			help=(ImageView)findViewById(R.id.menu_help);
			logout=(TextView)findViewById(R.id.menu_logout);
			title=(TextView)findViewById(R.id.indexHead);
			
			
			alertDialog=new Dialog(IndexActivity.this);
			aboutDialog=new Dialog(IndexActivity.this);
			helpDialog=new Dialog(IndexActivity.this);
			
			session=new SessionManager(IndexActivity.this);
			alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			aboutDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			aboutDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			helpDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			helpDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			
	        alertDialog.setContentView(R.layout.dialog_alert_doublebtn);
	        aboutDialog.setContentView(R.layout.dialog_about);
	        helpDialog.setContentView(R.layout.help_layout);
	        
	        close_help=(ImageView)helpDialog.findViewById(R.id.close_help);
	        dialog_close=(TextView)aboutDialog.findViewById(R.id.dialog_close);
	        exit=(TextView)alertDialog.findViewById(R.id.exitAsking);
	        ok=(TextView)alertDialog.findViewById(R.id.d_ok);
	        cancel=(TextView)alertDialog.findViewById(R.id.d_cancel);
	        exit.setText("Are You sure to exit?");
	        ok.setText("OK");
	        cancel.setText("CANCEL");
			onCreateView();
		}else{
			setContentView(R.layout.no_network);
        	TextView retry=(TextView)findViewById(R.id.retry);
        	retry.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(IndexActivity.this, IndexActivity.class));
			    	IndexActivity.this.finish();
				}
			});
		}
	}
	private void onCreateView(){
		Typeface type_ttl=Typeface.createFromAsset(getAssets(), "fonts/[TOP_SECRET].ttf");
		Typeface type=Typeface.createFromAsset(getAssets(), "fonts/Exo-ExtraBoldItalic.ttf");
		menu1.setTypeface(type);
		menu2.setTypeface(type);
		menu3.setTypeface(type);
		logout.setTypeface(type);
		title.setText("[_HOME_]");
		title.setTypeface(type_ttl);
		
		menu1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(IndexActivity.this, SearchActivity.class));
			}
		});
		menu2.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(IndexActivity.this, ListKotaActivity.class));
			}
		});
		menu3.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(IndexActivity.this,UserProfileActivity.class));
			}
		});
		logout.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				session.logoutUser();
				IndexActivity.this.finish();
			}
		});
		about.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aboutDialog.show();
								
			}
		});
		help.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				helpDialog.show();
			}
			
		});
		dialog_close.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				aboutDialog.dismiss();
			}
		});
		close_help.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				helpDialog.dismiss();
			}
		});
	
	}

	@Override
	public void onBackPressed() {
		alertDialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
				IndexActivity.this.finish();
			}
		});
        cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});
      
    }
	
	public class MyFetchTask extends AsyncTask<Object, Object, Object>
	{
	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	        
	        progressDialog= new Dialog(IndexActivity.this);
	        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	        progressDialog.setContentView(R.layout.progress);
	        progressDialog.setCancelable(false);
	        progressDialog.show();	        
	    }

	    @Override
	    protected Object doInBackground(Object... params) {
	    	JSONParser jParser = new JSONParser();
	    	JSONObject json = jParser.getJSONFromUrl(setGet.url+"data_kota.php");
	    	ArrayList<String> list=new ArrayList<String>();
			try {
				JSONArray data_kota=json.getJSONArray("kota");
				for(int i = 0; i < data_kota.length(); i++){
					JSONObject obj = data_kota.getJSONObject(i);
					String id_kota=obj.getString("nama_kota");
					list.add(id_kota);
				}
				setGet.setListKota(list);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	        // TODO Auto-generated method stub
	        super.onPostExecute(result);
	        
	        progressDialog.dismiss();
	    }

	}

	

}
