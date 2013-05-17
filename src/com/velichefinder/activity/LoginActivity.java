package com.velichefinder.activity;

import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.velichefinder.koneksi.*;
import com.velichefinder.other.*;

public class LoginActivity extends Activity {
	private EditText textUser, textPassword;
	private Button login;
	
	private TextView user,pass,reg;
	private Context ctx;
	SessionManager session;
	Dialog progressDialog, errorDialog;
	TextView errorMessage, errorBtn;
	HttpConnection con;
	ArrayList<NameValuePair> auth;
	
	
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_login);
		 TextView tv=(TextView)findViewById(R.id.loginHead);
		 Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/[TOP_SECRET].ttf");
		 tv.setTypeface(tf);
		 tv.setText("[_LOGIN_]");
		 
		 //*deklarasi variabel dll
		 ctx=LoginActivity.this;
		 textUser = (EditText) findViewById(R.id.username);
	     textPassword = (EditText) findViewById(R.id.password);
	     login = (Button) findViewById(R.id.loginBtn);
	     user=(TextView)findViewById(R.id.user);
	     pass=(TextView)findViewById(R.id.pass);
	     user.setText("Username :");
	     pass.setText("Password :");
	     reg=(TextView)findViewById(R.id.regLink);
	     session = new SessionManager(getApplicationContext());
	     con=new HttpConnection();
	     errorDialog=new Dialog(getApplicationContext());
	     errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	     errorDialog.setContentView(R.layout.dialog_alert_singlebtn);
	     errorDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	     errorMessage=(TextView)errorDialog.findViewById(R.id.error_dlg);
	     errorBtn=(TextView)errorDialog.findViewById(R.id.error_ok);
	     errorBtn.setText(" OK ");
	     
	     //login button on Click Action
	     login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				
				String user=textUser.getText().toString();
				setGet.setId(user);
				String users=textUser.getText().toString();
				String pass=textPassword.getText().toString();
				auth = new ArrayList<NameValuePair>();
			    auth.add(new BasicNameValuePair("username", users));
			    auth.add(new BasicNameValuePair("password", pass));

				try {
					//*cek koneksi
					if(con.cek_status(ctx)){
					//	this.sendAuthData(users, pass);
						new MyFetchTask().execute();
			
					}else{
						errorMessage.setText("No Nerwork Acces");
						errorDialog.show();
						errorBtn.setOnClickListener(new View.OnClickListener() {
							
							public void onClick(View v) {
								errorDialog.dismiss();
							}
						});
						
					}
				} catch (Exception e) {
					startActivity(new Intent(LoginActivity.this,RequestTimeOutActivity.class));
					e.printStackTrace();
				}
				
			
		}});
	    
	     
	     //reg on click
	     reg.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i= new Intent(LoginActivity.this, RegistrasiActivity.class);
				startActivity(i);
			}
		});
	     
	     
	     
	     
	}
	private void cekLogin(){
		if(setGet.getStatus().equals("TRUE")){
			Toast.makeText(getBaseContext(), "Welcome "+textUser.getText().toString(), Toast.LENGTH_LONG).show();
			Intent i = new Intent(getApplicationContext(), IndexActivity.class);
			startActivity(i);
			session.createLoginSession(textUser.getText().toString(), textPassword.getText().toString());
			LoginActivity.this.finish();
		}else if((setGet.getStatus().equals("RTO"))){
			startActivity(new Intent(LoginActivity.this,RequestTimeOutActivity.class));
			
		}else{
			Toast.makeText(getBaseContext(), "Cek username/password!! ", Toast.LENGTH_LONG).show();
		}
	}
	
	public class MyFetchTask extends AsyncTask<Object, Object, Object>{
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        
	        progressDialog= new Dialog(LoginActivity.this);
	        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	        progressDialog.setContentView(R.layout.progress);
	        progressDialog.setCancelable(false);
	        progressDialog.show();	        
	    }

	    @Override
	    protected Object doInBackground(Object... params) {
	    	con.LoginUser(auth);
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	        super.onPostExecute(result);
			cekLogin();
			progressDialog.dismiss();
	    }

	}
}
	