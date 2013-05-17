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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class AlternatifKota1 extends ListActivity {

	private static final String TAG_KOTA="nama_kota";
	private static final String TAG_KENDARAAN="kendaraan";
	private static final String TAG_IDK="id";
	private static final String TAG_NAMA="nama";
	private static final String TAG_KELAS="kelas";
	private static final String TAG_HARGA="harga";
	private static final String TAG_JENIS="jenis";
	private static final String TAG_RUTE="rute";
	
	private static String url=setGet.url;
	JSONParser jParser;
	JSONArray data_kendaraan=null;
	ArrayList<HashMap<String, String>> daftar_kendaraan = new ArrayList<HashMap<String, String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alternatif_kota1);
		getDataKendaraan();
	}
	private void getDataKendaraan(){
		Intent intent=getIntent();
		String alternatif=intent.getStringExtra(TAG_KOTA);
		String link=url+"result_alternatif1.php?awal="+setGet.getAsal()+"&tujuan="+alternatif+"&t_awal="+setGet.getTujuan();
		jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(link);
		try {
			
			data_kendaraan=json.getJSONArray(TAG_KENDARAAN);
			for(int i = 0; i < data_kendaraan.length(); i++){
				JSONObject obj=data_kendaraan.getJSONObject(i);
				String id=obj.getString(TAG_IDK);
				String nama=obj.getString(TAG_NAMA);
				String kelas=obj.getString(TAG_KELAS);
				String harga=obj.getString(TAG_HARGA);
				String jenis=obj.getString(TAG_JENIS);
				String rute=obj.getString(TAG_RUTE);
				
				HashMap<String, String> map=new HashMap<String, String>();
				map.put(TAG_IDK, id);
				map.put(TAG_NAMA, nama);
				map.put(TAG_KELAS, kelas);
				map.put(TAG_HARGA, harga);
				map.put(TAG_JENIS, jenis);
				map.put(TAG_RUTE, "("+rute+")");
				
				daftar_kendaraan.add(map);
			}
			
			setListResult();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setListResult(){
		Intent intent=getIntent();
		final String alternatif1=intent.getStringExtra(TAG_KOTA);
		ListAdapter adapter = new SimpleAdapter(this, daftar_kendaraan,
				R.layout.list_alternatif,
				new String[] { TAG_IDK, TAG_NAMA, TAG_RUTE, TAG_KELAS, TAG_HARGA, TAG_JENIS }, new int[] {
					R.id.alt_idk,R.id.alt_namak, R.id.alt_rutek, R.id.alt_kelask, R.id.harga_k, R.id.alt_jenisk  });

		setListAdapter(adapter);
		
		ListView lv = getListView();

		// Launching new screen on Selecting Single ListItem
		lv.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int posision,
					long id) {
					TextView id1=(TextView)view.findViewById(R.id.alt_idk);
					String id_k=id1.getText().toString();
					Intent i=new Intent(getApplicationContext(),DetailDataActivity.class);
					i.putExtra(TAG_IDK, id_k);
					i.putExtra(TAG_KOTA, alternatif1);
					startActivity(i);
					
							
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_alternatif_kota1, menu);
		return true;
	}

}
