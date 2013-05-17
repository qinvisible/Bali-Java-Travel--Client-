package com.velichefinder.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.velichefinder.koneksi.HttpConnection;
import com.velichefinder.other.SessionManager;
import com.velichefinder.other.setGet;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileActivity extends Activity {

	private String username;
	HttpConnection con;
	EditText user, pass, mail, pass_con, old_pass;
	Button submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_profile);
		TextView tv=(TextView)findViewById(R.id.headUser);
		Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/[TOP_SECRET].ttf");
		tv.setTypeface(tf);
		tv.setText("[_USER_PROFILE_]");
		
		con=new HttpConnection();
		user=(EditText)findViewById(R.id.up_username);
		mail=(EditText)findViewById(R.id.up_mail);
		pass=(EditText)findViewById(R.id.up_password);
		pass_con=(EditText)findViewById(R.id.up_password1);
		old_pass=(EditText)findViewById(R.id.up_old_password);
		submit=(Button)findViewById(R.id.up_submit);
				
		SessionManager session= new SessionManager(getApplicationContext());
		HashMap<String, String> map=session.getUserDetails();
		username= map.get(SessionManager.KEY_USER);
		Log.d("username",username);
		getUser();
		
		
	}
	private void getUser(){
		
		ArrayList<NameValuePair> data=new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("username", username));
		con.getUser(data);
		user.setText(username);
		if(setGet.getStatus()!="RTO"){
			mail.setText(setGet.getStatus().toString());
		}else{
			mail.setText("Connection Loss");
			mail.setEnabled(false);
			submit.setEnabled(false);
			
		}
	}
	
	public void updateUser(View view){
		String email=mail.getText().toString();
		String password=pass.getText().toString();
		String c_password=pass_con.getText().toString();
		String old_password=old_pass.getText().toString();
		Log.d("mail",email);
		if(email.matches("")||password.matches("")||c_password.matches("")){
			Toast.makeText(getBaseContext(), "Empty input is not permitted!!", Toast.LENGTH_LONG).show();
		}else if(password.length()<=5){
			Toast.makeText(getBaseContext(), "Password Too Short!!", Toast.LENGTH_LONG).show();
		}else{
			if(password.matches(c_password)){
				ArrayList<NameValuePair> data=new ArrayList<NameValuePair>();
				data.add(new BasicNameValuePair("email", email));
				data.add(new BasicNameValuePair("username", username));
				data.add(new BasicNameValuePair("password", password));
				data.add(new BasicNameValuePair("old_password", old_password));
				con.UpdateUser(data);
				if(setGet.getStatus().equals("TRUE")){
					Toast.makeText(getBaseContext(), "Data Telah Di Update!!", Toast.LENGTH_LONG).show();
					getUser();
					pass.setText("");
					pass_con.setText("");
				}else if(setGet.getStatus().equals("MAIL")){
					Toast.makeText(getBaseContext(), "Mail has registered!!", Toast.LENGTH_LONG).show();
				}else if(setGet.getStatus().equals("PASSWORD")){
					Toast.makeText(getBaseContext(), "Old Password False!!", Toast.LENGTH_LONG).show();
				}else if(setGet.getStatus().equals("RTO")){
					startActivity(new Intent(UserProfileActivity.this,RequestTimeOutActivity.class));
				}else{
					Toast.makeText(getBaseContext(), "Check data input, make sure the input is valid!!", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(getBaseContext(), "Password not match!!", Toast.LENGTH_LONG).show();
			}
		}
		
		
	}




}
