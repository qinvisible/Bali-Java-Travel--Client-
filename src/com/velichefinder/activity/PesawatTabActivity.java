package com.velichefinder.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.velichefinder.koneksi.JSONParser;
import com.velichefinder.other.setGet;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
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

public class PesawatTabActivity extends ListActivity {

	private static final String url=setGet.url;
	private static final String TAG_AIRPLANE="airplane";
	private static final String TAG_ID="id_airplane";
	private static final String TAG_IDK="id";
	private static final String TAG_NAMA="nama_airplane";
	private static final String TAG_KELAS="kelas_airplane";
	private static final String TAG_HARGA="harga_airplane";
	
	
	TextView state;
	JSONParser jParser;
	JSONArray data_airplane=null;
	ArrayList<HashMap<String, String>> daftar_airplane = new ArrayList<HashMap<String, String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tab_result_pesawat);
		
		state=(TextView)findViewById(R.id.status_hasil_pesawat);
		jParser = new JSONParser();
        String link=url+"search_airplane.php?awal="+setGet.getAsal()+"&tujuan="+setGet.getTujuan()+"&waktu="+setGet.getWaktu()+"&akm="+setGet.getAkumulasi();
		JSONObject json = jParser.getJSONFromUrl(link);
		
		try {
			data_airplane=json.getJSONArray(TAG_AIRPLANE);
			
			for(int i = 0; i < data_airplane.length(); i++){
				JSONObject obj = data_airplane.getJSONObject(i);
				String id=obj.getString(TAG_ID);
				String nama=obj.getString(TAG_NAMA);
				String kelas=obj.getString(TAG_KELAS);
				String harga=obj.getString(TAG_HARGA);
				String has=obj.getString("success");
				Log.d("result",has);
				HashMap<String, String> map = new HashMap<String, String>();
				if(has.equals("YES")){
					state.setVisibility(View.INVISIBLE);
					state.setTextSize(1);
					map.put(TAG_ID, id);
					map.put(TAG_NAMA, nama);
					map.put(TAG_KELAS, kelas);
					map.put(TAG_HARGA, harga);
					daftar_airplane.add(map);
				}else{
					state.setText("No vehicle for this Route,"+"\n"+ "try to find alternative route");
					state.setVisibility(View.VISIBLE);
					state.setTextSize(25);
				}
	
			}
			listAirplane();
			
		} catch (JSONException e) {
			Log.d("url", link);
			e.printStackTrace();
		}
    }
	private void listAirplane() {
		ListAdapter adapter = new SimpleAdapter(this, daftar_airplane,
				R.layout.list_tab,
				new String[] { TAG_ID, TAG_NAMA, TAG_KELAS, TAG_HARGA }, new int[] {
					R.id.id_k,R.id.nama_k, R.id.kelas_k, R.id.harga_k  });

		setListAdapter(adapter);
		ListView lv=getListView();
		lv.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int posision,
					long id) {
					TextView id1=(TextView)view.findViewById(R.id.id_k);
					String id_pes=id1.getText().toString();
					Intent i=new Intent(getApplicationContext(),DetailDataActivity.class);
					i.putExtra(TAG_IDK, id_pes);
					Log.d("id", id_pes);
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
