package com.velichefinder.activity;




import com.velichefinder.other.setGet;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class ResultSearchActivity extends TabActivity {

	public static final String JENIS="jenis";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_result_search);
		setFilteringJenis();
		
		TextView tv=(TextView)findViewById(R.id.resultHead);
		Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/[TOP_SECRET].ttf");
		tv.setTypeface(tf);
		tv.setText("[_RESULT_]");
		
	}
	private void setTab(){
		TabHost tabHost = getTabHost();
		
		TabSpec bus_result = tabHost.newTabSpec("Bus");
		bus_result.setIndicator("",getResources().getDrawable(R.drawable.tab_bus));
		Intent busIntent = new Intent(this, BusTabActivity.class);
        bus_result.setContent(busIntent);
        
        TabSpec kereta_result = tabHost.newTabSpec("Train");
        kereta_result.setIndicator("",getResources().getDrawable(R.drawable.tab_kereta));
		Intent keretaIntent = new Intent(this, KeretaTabActivity.class);
        kereta_result.setContent(keretaIntent);
        
        TabSpec travel_result = tabHost.newTabSpec("Travel");
        travel_result.setIndicator("",getResources().getDrawable(R.drawable.tab_travel));
		Intent travelIntent = new Intent(this, TravelTabActivity.class);
        travel_result.setContent(travelIntent);
        
        TabSpec airplane_result = tabHost.newTabSpec("Airplane");
        airplane_result.setIndicator("",getResources().getDrawable(R.drawable.tab_airplane));
		Intent airplaneIntent = new Intent(this, PesawatTabActivity.class);
        airplane_result.setContent(airplaneIntent);
        
        try{
        	tabHost.addTab(bus_result);
        	tabHost.addTab(kereta_result); 
        	tabHost.addTab(travel_result);
        	tabHost.addTab(airplane_result);
        }catch(Exception e){
        	e.printStackTrace();
        	startActivity(new Intent(getApplicationContext(),RequestTimeOutActivity.class));
        }
        
	}
	private void setFilteringJenis(){
		
		String jenis=setGet.getJenis();
		if(jenis.equals("Train")){
			startActivity(new Intent(getApplicationContext(), KeretaTabActivity.class));
			this.finish();
		}else if(jenis.equals("Bus")){
			startActivity(new Intent(getApplicationContext(), BusTabActivity.class));
			this.finish();
		}else if(jenis.equals("Travel")){
			startActivity(new Intent(getApplicationContext(), TravelTabActivity.class));
			this.finish();
		}else if(jenis.equals("Airplane")){
			startActivity(new Intent(getApplicationContext(), PesawatTabActivity.class));
			this.finish();
		}else{
			setTab();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result_search, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
    	case R.id.menu_alt:
    		startActivity(new Intent(this, ResultAlternativeActivity.class));
    		return true;
    	default:
    	return super.onOptionsItemSelected(item);
	}
		
	}
	public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
        finish();
    }

}
