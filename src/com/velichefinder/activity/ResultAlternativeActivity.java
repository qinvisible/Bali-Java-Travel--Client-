package com.velichefinder.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.velichefinder.koneksi.JSONParser;
import com.velichefinder.other.setGet;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ResultAlternativeActivity extends ListActivity {

	private static final String url=setGet.url;
	private static final String TAG_KOTA="kota_alter";
	private static final String TAG_NAMA2="nama_kota2";
	private static final String TAG_NKOTA="nama_kota1";
	private static final String TAG_NAMA="nama_kota";
	
	JSONParser jParser;
	JSONArray data_kota=null;
	ArrayList<HashMap<String, String>> daftar_kota = new ArrayList<HashMap<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_result_alternative);
		TextView tv=(TextView)findViewById(R.id.alterHead);
		Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/[TOP_SECRET].ttf");
		tv.setTypeface(tf);
		tv.setText("[_ALTERNATIVE_ROUTE_]");
		
		onSetLayout();
				
	}
	private void onSetLayout(){
		String link=url+"get_rute_alternatif.php?kota1="+setGet.getAsal()+"&kota2="+setGet.getTujuan();
		
		jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(link);
		
		try {
			data_kota=json.getJSONArray(TAG_KOTA);
			for(int i = 0; i < data_kota.length(); i++){
				JSONObject obj = data_kota.getJSONObject(i);
				String n_kota=obj.getString(TAG_NAMA);
				
				
				HashMap<String, String> map=new HashMap<String,String>();
				map.put(TAG_NKOTA, n_kota);
				map.put(TAG_NAMA, setGet.getAsal()+"-"+n_kota);
				map.put(TAG_NAMA2, n_kota+"-"+setGet.getTujuan());
				
				
				daftar_kota.add(map);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createList();
	}
	private void createList(){
		ListAdapter adapter = new SimpleAdapter(this, daftar_kota,
				R.layout.list_rute_alternatif,
				new String[] { TAG_NAMA, TAG_NAMA2, TAG_NKOTA }, new int[] {
				  R.id.text_alt1,R.id.text_alt2, R.id.nama_alt });
		setListAdapter(adapter);
		ListView list=getListView();
		
		list.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int posision,
					long id) {
					TextView id1=(TextView)view.findViewById(R.id.nama_alt);
					String namakota=id1.getText().toString();
					Intent i=new Intent(getApplicationContext(),TabResultAlternatif.class);
					i.putExtra(TAG_NKOTA, namakota);
					startActivity(i);
					
							
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_result_alternative, menu);
		return true;
	}
	

}
