package com.velichefinder.activity;

import java.util.ArrayList;
import com.velichefinder.koneksi.*;
import com.velichefinder.other.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrasiActivity extends Activity {

	Context context; 
	Button regBtn;
	EditText user, pass, email;
	HttpConnection con;


	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registrasi);
        TextView tv=(TextView)findViewById(R.id.regHead);
		Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/[TOP_SECRET].ttf");
		tv.setTypeface(tf);
		tv.setText("[_REGISTRASI_]");
        regBtn=(Button)findViewById(R.id.submitBtn);
        user=(EditText)findViewById(R.id.reg_user);
		pass=(EditText)findViewById(R.id.reg_pass);
		email=(EditText)findViewById(R.id.reg_email);
		onClickBtn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_registrasi, menu);
        return true;
    }
    
    private void onClickBtn(){
    		
		
		//activity
    	
		regBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				
				if(user.getText().toString().matches("")||pass.getText().toString().matches("")||email.getText().toString().matches("")){
					Toast.makeText(getBaseContext(), "tidak boleh kosong!!",Toast.LENGTH_LONG).show();
				}else if(pass.getText().toString().length()<=5){
					Toast.makeText(getBaseContext(), "Password too short!!",Toast.LENGTH_LONG).show();
				}
				else{
					postParameters.add(new BasicNameValuePair("username",user.getText().toString()));
					postParameters.add(new BasicNameValuePair("email",email.getText().toString()));
					postParameters.add(new BasicNameValuePair("password",pass.getText().toString()));
				
					con = new HttpConnection();
					con.AddUser(postParameters);
					
					if(setGet.getStatus().equals("sukses")){
						Toast.makeText(getBaseContext(), "Register Success !!",Toast.LENGTH_LONG).show();
						finish();
						Intent i=new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(i);
					}else if(setGet.getStatus().equals("mail")){
						Toast.makeText(getBaseContext(), "Mail has registered!!",Toast.LENGTH_LONG).show();
					}else if(setGet.getStatus().equals("validasimail")){
						Toast.makeText(getBaseContext(), "Mail Address not valid !!",Toast.LENGTH_LONG).show();
					}else if(setGet.getStatus().equals("RTO")){
						startActivity(new Intent(RegistrasiActivity.this,RequestTimeOutActivity.class));
					}else{
						Toast.makeText(getBaseContext(), "Username has registered !!",Toast.LENGTH_LONG).show();
					}
				}
				
				
	
			}
			  
			
			
			
			
		});
    }
  
    
    
   
	

	


}
