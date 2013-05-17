package com.velichefinder.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import com.velichefinder.koneksi.JSONParser;
import com.velichefinder.other.setGet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class DetailDataActivity extends Activity{
	
	private static final String url=setGet.url+"get_detail_kendaraan.php?"; 
	private static final String TAG_IDK="id";
	private static final String TAG_KENDARAAN="kendaraan";
	private static final String TAG_NAMA="nama";
	private static final String TAG_KELAS="kelas";
	private static final String TAG_HARGA="harga";
	private static final String TAG_JAM="jam";
	private static final String TAG_JALAN="perjalanan";
	private static final String TAG_INFO="info";
	private static final String TAG_INFKEND="info_kendaraan";
	private static final String TAG_IF_K="info_k";
	private static final String TAG_IFK_1="dta";
	private static final String TAG_IFK_2="indx";
	private static final String TAG_RUTE="rute";
	private static final String TAG_KOTA="nama_kota";
	
	String nama1, kelas1, harga1, jam1, perjalanan1, info1, inf_kend1, rute1, jenis_k, promo_k, info_promo;
	private TextView nama, kelas, harga, jam, perjalanan, info, info_kendaraan, promo, promo1, text_index1, text_index2;
	private TextView kelas_k, harga_k, jam_k, perjalanan_k, info_k, infkend_k;
	private TextView btn_info;
	String id_search, kota_alt, index, isindex;
	JSONParser jParser;
	JSONArray data_kendaraan=null, data_info=null;
	ArrayList<HashMap<String, String>> daftar_info = new ArrayList<HashMap<String, String>>();
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_info_kendaraan);
		TextView tv=(TextView)findViewById(R.id.detailHead);
		Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/[TOP_SECRET].ttf");
		tv.setTypeface(tf);
		tv.setText("[_DETAIL_DATA_]");

		nama=(TextView)findViewById(R.id.nama_kend);
		kelas=(TextView)findViewById(R.id.kelas_kend);
		harga=(TextView)findViewById(R.id.harga_kend);
		jam=(TextView)findViewById(R.id.jam_kend);
		perjalanan=(TextView)findViewById(R.id.waktu_kend);
		info=(TextView)findViewById(R.id.info_kend);
		info_kendaraan=(TextView)findViewById(R.id.ticketing);
		kelas_k=(TextView)findViewById(R.id.kelas_kend1);
		harga_k=(TextView)findViewById(R.id.harga_kend1);
		jam_k=(TextView)findViewById(R.id.jam_kend1);
		perjalanan_k=(TextView)findViewById(R.id.waktu_kend1);
		info_k=(TextView)findViewById(R.id.info_kend1);
		infkend_k=(TextView)findViewById(R.id.ticketing1);
		btn_info=(TextView)findViewById(R.id.btn_info);
		promo=(TextView)findViewById(R.id.promo);
		promo1=(TextView)findViewById(R.id.promo1);
		text_index1=(TextView)findViewById(R.id.text_indx);
		text_index2=(TextView)findViewById(R.id.text_indx2);
		
		info.setText("Info");
		info_kendaraan.setText("Route");
		kelas.setText("Class");
		harga.setText("Price");
		jam.setText("Time");
		perjalanan.setText("Travel time");
		
		Intent in=getIntent();
		id_search=in.getStringExtra(TAG_IDK);
		kota_alt=in.getStringExtra(TAG_KOTA);
		
		tampilJSON1();

	}
	public void tampilJSON1(){
		String link;
		
		
		if(kota_alt==null || kota_alt==""){
			link=url+"awal="+setGet.getAsal()+"&id_kend="+id_search;
		}else{
			link=url+"awal="+kota_alt+"&id_kend="+id_search;
		}
        jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(link);
		

		
		try {
			data_kendaraan=json.getJSONArray(TAG_KENDARAAN);
			
			
		
			for(int i = 0; i < data_kendaraan.length(); i++){
				JSONObject obj = data_kendaraan.getJSONObject(i);
				jenis_k=obj.getString("jenis");
				String promo_av=obj.getString("state_promo");
				if(promo_av.equals("NO")){
					promo.setVisibility(View.INVISIBLE);
					promo1.setVisibility(View.INVISIBLE);
				}else{
					promo.setVisibility(View.VISIBLE);
					promo1.setVisibility(View.VISIBLE);
					promo_k=obj.getString("masa_berlaku");
					info_promo=obj.getString("info_promo");
					promo.setText("PROMO");
					promo1.setText("Masa Berlaku :"+promo_k+"\n"+info_promo);
				}
				
				if(jenis_k.equals("Airplane")){
					btn_info.setVisibility(View.VISIBLE);
					btn_info.setText("Info Lengkap");
					btn_info.setOnClickListener(new View.OnClickListener() {
						
						public void onClick(View v) {
							
							 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.tiketdomestik.com/mflight.php"));
							 startActivity(browserIntent);
						}
					});
				}else if(jenis_k.equals("Kereta")){
					btn_info.setVisibility(View.VISIBLE);
					btn_info.setText("Info Lengkap");
					btn_info.setOnClickListener(new View.OnClickListener() {
						
						public void onClick(View v) {
							
							 Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kereta-api.co.id/"));
							 startActivity(browserIntent);
						}
					});
				}else{
					btn_info.setVisibility(View.INVISIBLE);
				}
				
				nama1=obj.getString(TAG_NAMA);
				kelas1=obj.getString(TAG_KELAS);
				harga1=obj.getString(TAG_HARGA);
				jam1=obj.getString(TAG_JAM);
				perjalanan1=obj.getString(TAG_JALAN);
				info1=obj.getString(TAG_INFO);
				inf_kend1=obj.getString(TAG_INFKEND);
				rute1=obj.getString(TAG_RUTE);
								
			
				nama.setText(nama1);
				kelas_k.setText(kelas1);
				harga_k.setText("Rp. "+harga1+",-");
				jam_k.setText(jam1);
				perjalanan_k.setText(perjalanan1);
				infkend_k.setText(rute1);
				
				
			}
			if(jenis_k.equals("Train")){
				JSONObject dataKereta = jParser.getJSONFromUrl(setGet.url+"get_informasi_kereta.php?id_kend="+id_search);
				data_info=dataKereta.getJSONArray(TAG_IF_K);
				String indx=null, isndx=null;
				for(int i = 0; i < data_info.length(); i++){
					JSONObject obj = data_info.getJSONObject(i);
					indx=obj.getString(TAG_IFK_1);
					index=index+indx;
					isndx=obj.getString(TAG_IFK_2);
					isindex=isindex+isndx;
				}
				info_k.setText(info1+"\n"+"\n"+"Info Kendaraan"+"\n");
				text_index1.setText(isindex);
				text_index2.setText(index);
			}else{
				info_k.setText(info1+"\n"+"\n"+"Info Kendaraan"+"\n"+inf_kend1);
				text_index1.setVisibility(View.INVISIBLE);
				text_index2.setVisibility(View.INVISIBLE);
			}
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	public void onBackPressed() {
		DetailDataActivity.this.finish();
	}
	
}
