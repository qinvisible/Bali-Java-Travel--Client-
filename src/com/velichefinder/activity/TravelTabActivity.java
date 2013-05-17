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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TravelTabActivity extends ListActivity{

	private static String url=setGet.url;
	private static String TAG_TRAVEL="travel";
	private static String TAG_ID="id_travel";
	private static String TAG_IDK="id";
	private static String TAG_NAMA="nama_travel";
	private static String TAG_KELAS="kelas_travel";
	private static String TAG_HARGA="harga_travel";
	
	TextView state;
	JSONParser jParser;
	JSONArray data_travel=null;
	ArrayList<HashMap<String, String>> daftar_travel = new ArrayList<HashMap<String, String>>();
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tab_result_travel);
        
        state=(TextView)findViewById(R.id.status_hasil_travel);
        jParser = new JSONParser();
        
        String link=url+"search_travel.php?awal="+setGet.getAsal()+"&tujuan="+setGet.getTujuan()+"&waktu="+setGet.getWaktu()+"&akm="+setGet.getAkumulasi();
		JSONObject json = jParser.getJSONFromUrl(link);
		
		try {
			data_travel=json.getJSONArray(TAG_TRAVEL);
			
			for(int i = 0; i < data_travel.length(); i++){
				JSONObject obj = data_travel.getJSONObject(i);
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
					
				}else{
					state.setText("No vehicle for this Route,"+"\n"+ "try to find alternative route");
					state.setVisibility(View.VISIBLE);
					state.setTextSize(25);
				}
				daftar_travel.add(map);
				}
			listTravel();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	private void listTravel() {
		ListAdapter adapter = new SimpleAdapter(this, daftar_travel,
				R.layout.list_tab,
				new String[] { TAG_ID, TAG_NAMA, TAG_KELAS, TAG_HARGA }, new int[] {
					R.id.id_k,R.id.nama_k, R.id.kelas_k, R.id.harga_k  });

		setListAdapter(adapter);
		ListView lv=getListView();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result_search, menu);
		return true;
	}
	public boolean onItemSelected(MenuItem item){
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
