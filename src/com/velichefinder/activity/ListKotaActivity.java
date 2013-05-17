package com.velichefinder.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.velichefinder.koneksi.HttpConnection;
import com.velichefinder.koneksi.JSONParser;
import com.velichefinder.other.setGet;



import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class ListKotaActivity extends ListActivity {

	private static final String link_url = setGet.url+"data_kota.php";
	
	private static final String TAG_ID = "id_kota";
	private static final String TAG_NAMA = "nama_kota";
	private static final String TAG_PROPINSI = "propinsi";
	private static final String TAG_KOTA = "kota";

	Dialog progressDialog;
	JSONArray data_kota = null;
	JSONParser jParser;
	ArrayList<HashMap<String, String>> daftar_kota = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		new MyFetchTask().execute();
	}
	private void setLayout(){
			try {
				JSONObject json = jParser.getJSONFromUrl(link_url);
				data_kota=json.getJSONArray(TAG_KOTA);
				for(int i = 0; i < data_kota.length(); i++){
					JSONObject dtk = data_kota.getJSONObject(i);
					String id=dtk.getString(TAG_ID);
					String nama=dtk.getString(TAG_NAMA);
					String propinsi=dtk.getString(TAG_PROPINSI);
					
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TAG_ID, "ID: "+id);
					map.put(TAG_NAMA, nama);
					map.put(TAG_PROPINSI, "Propinsi : "+propinsi);
					
					daftar_kota.add(map);
					
					}
				
				ListAdapter adapter = new SimpleAdapter(this, daftar_kota,
						R.layout.list_item_kota,
						new String[] { TAG_ID, TAG_NAMA, TAG_PROPINSI }, new int[] {
							R.id.id_kota,R.id.nama_kota, R.id.propinsi  });

				setListAdapter(adapter);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		
		
	}

	
	public class MyFetchTask extends AsyncTask<Object, Object, Object>{
	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	        
	        progressDialog= new Dialog(ListKotaActivity.this);
	        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	        progressDialog.setContentView(R.layout.progress);
	        progressDialog.setCancelable(false);
	        progressDialog.show();	        
	    }

	    @Override
	    protected Object doInBackground(Object... params) {
	    	HttpConnection con=new HttpConnection();
	    	con.getResponse();

			if(setGet.getStatus().equals("RTO")){
				startActivity(new Intent(ListKotaActivity.this, RequestTimeOutActivity.class));
				ListKotaActivity.this.finish();
			}else{
				jParser = new JSONParser();
				}
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	        // TODO Auto-generated method stub
	        super.onPostExecute(result);
	        ListKotaActivity.this.setContentView(R.layout.activity_listkota);
			setLayout();
	        progressDialog.dismiss();
	    }

	}
    
}