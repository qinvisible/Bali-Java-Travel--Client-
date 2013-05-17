package com.velichefinder.activity;

import com.velichefinder.other.setGet;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TabResultAlternatif extends TabActivity {
	
	private static final String TAG_NKOTA="nama_kota1";
	private static final String TAG_KOTA="nama_kota";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tab_result_alternatif);
		TextView tv=(TextView)findViewById(R.id.tabalterHead);
		Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/[TOP_SECRET].ttf");
		tv.setTypeface(tf);
		tv.setText("[_ALTERNATIVE_VEHICLE_]");
		
		Intent i=getIntent();
		String kota=i.getStringExtra(TAG_NKOTA);
					
		TabHost tabHost = getTabHost();

		TabSpec kota1_result = tabHost.newTabSpec("Kota1");
		kota1_result.setIndicator(setGet.getAsal()+"-"+kota);
		Intent kota1 = new Intent(this, AlternatifKota1.class);
		kota1.putExtra(TAG_KOTA, kota);
        kota1_result.setContent(kota1);
        
        TabSpec kota2_result = tabHost.newTabSpec("Kota2");
		kota2_result.setIndicator(kota+"-"+setGet.getTujuan());
		Intent kota2 = new Intent(this, AlternatifKota2.class);
		kota2.putExtra(TAG_KOTA, kota);
        kota2_result.setContent(kota2);
		
        
		tabHost.addTab(kota1_result);
		tabHost.addTab(kota2_result);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_tab_result_alternatif, menu);
		return true;
	}

}
