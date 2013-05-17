package com.velichefinder.activity;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.velichefinder.koneksi.JSONParser;
import com.velichefinder.other.setGet;


import android.app.ListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class BusTabActivity extends ListActivity{
	
	private static final String url=setGet.url;
	private static final String TAG_BUS="bus";
	private static final String TAG_ID="id_bus";
	private static final String TAG_IDK="id";
	private static final String TAG_NAMA="nama_bus";
	private static final String TAG_KELAS="kelas_bus";
	private static final String TAG_HARGA="harga_bus";

	TextView state;
	JSONParser jParser;
	JSONArray data_bus;
	ArrayList<HashMap<String, String>> daftar_bus = new ArrayList<HashMap<String, String>>();
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_result_bus);
           
        state=(TextView)findViewById(R.id.status_hasil_bus);
        String link=url+"search_bus.php?awal="+setGet.getAsal()+"&tujuan="+setGet.getTujuan()+"&waktu="+setGet.getWaktu()+"&akm="+setGet.getAkumulasi();
        
        jParser = new JSONParser();

		
		try {
			JSONObject json = jParser.getJSONFromUrl(link);
			data_bus=json.getJSONArray(TAG_BUS);
			
			for(int i = 0; i < data_bus.length(); i++){
				JSONObject obj = data_bus.getJSONObject(i);
				String id=obj.getString(TAG_ID);
				String nama=obj.getString(TAG_NAMA);
				String kelas=obj.getString(TAG_KELAS);
				String harga=obj.getString(TAG_HARGA);
				String has=obj.getString("success");
				
				HashMap<String, String> map = new HashMap<String, String>();
				if(has.equals("YES")){
					map.put(TAG_ID, id);
					map.put(TAG_NAMA, nama);
					map.put(TAG_KELAS, kelas);
					map.put(TAG_HARGA, harga);
					daftar_bus.add(map);
					state.setVisibility(View.INVISIBLE);
					state.setTextSize(1);
				}else{
					state.setText("No vehicle for this Route,"+"\n"+ "try to find alternative route");
					state.setVisibility(View.VISIBLE);
					state.setTextSize(25);
				}
			}
				
				
			listBus();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
	public void listBus(){
		ListAdapter adapter = new SimpleAdapter(this, daftar_bus,
				R.layout.list_tab,
				new String[] { TAG_ID, TAG_NAMA, TAG_KELAS, TAG_HARGA }, new int[] {
					R.id.id_k,R.id.nama_k, R.id.kelas_k, R.id.harga_k  });

		setListAdapter(adapter);
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int posision,
					long id) {
					TextView id1=(TextView)view.findViewById(R.id.id_k);
					String id_bus=id1.getText().toString();
					Intent i=new Intent(getApplicationContext(),DetailDataActivity.class);
					i.putExtra(TAG_IDK, id_bus);
					startActivity(i);
					
							
			}
			
		});
	}
	
	public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
        finish();
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
	

}
