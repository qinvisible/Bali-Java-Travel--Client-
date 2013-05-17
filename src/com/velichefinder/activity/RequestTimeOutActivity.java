package com.velichefinder.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class RequestTimeOutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_time_out);
		Button back=(Button)findViewById(R.id.btn_back);
		back.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RequestTimeOutActivity.this.finish();
			}
		});
	}


}
