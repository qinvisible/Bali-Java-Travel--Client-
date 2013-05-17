package com.velichefinder.activity;


import com.velichefinder.other.SessionManager;

import android.app.Activity;

import android.os.Bundle;
import android.view.Window;

public class Splash extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        
        
        Thread timer = new Thread() {
	          @Override
	          public void run() {
	              long startTime = System.currentTimeMillis();
	              
	              long now = System.currentTimeMillis();
	              if (now - startTime < 1000){
	                  try {
	                      sleep(1000 - (now - startTime));
	                  } catch (InterruptedException iEx){
	                      
	                  }
	              }
	              SessionManager session;
	              session=new SessionManager(getApplicationContext());
	              session.checkLogin();
	              Splash.this.finish();
	              
	          }
	      };
	      
	      timer.start();
    }
}
