package com.velichefinder.activity;


import java.util.ArrayList;
import java.util.Collections;

import com.velichefinder.other.*;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SearchActivity extends Activity {

	CheckBox waktu, akumulasi, jenis_kendaraan;
	RadioGroup waktu1;
	Spinner akumulasi1,jenis_kendaraan1;
	Button btnSearch;
	String wkt="", akm="", jns="", awl=null, tjn=null;
	Spinner awal, tujuan;
	RadioButton siang,malam;
	ProgressDialog dialog;
	ArrayList<String> list=new ArrayList<String>();
	Dialog progressDialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        TextView tv=(TextView)findViewById(R.id.searchHead);
		Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/[TOP_SECRET].ttf");
		tv.setTypeface(tf);
		tv.setText("[_SEARCH_]");
        setAwalTujuan();
        layoutSet();
        onSearch();
       
        
    }


    
    private void layoutSet(){
	    waktu=(CheckBox)findViewById(R.id.ChekWaktu);
	    akumulasi=(CheckBox)findViewById(R.id.ChekAkumulasiBiaya);
	    akumulasi1=(Spinner)findViewById(R.id.AkumulasiBiaya1);
	    jenis_kendaraan=(CheckBox)findViewById(R.id.ChekJenisKendaraan);
	    jenis_kendaraan1=(Spinner)findViewById(R.id.JenisKendaraan);
	    jenis_kendaraan1=(Spinner)findViewById(R.id.JenisKendaraan);
		akumulasi1=(Spinner)findViewById(R.id.AkumulasiBiaya1);
		waktu1=(RadioGroup)findViewById(R.id.waktu);
		btnSearch=(Button)findViewById(R.id.btnSearch);
	    siang=(RadioButton)findViewById(R.id.siang);
	    malam=(RadioButton)findViewById(R.id.malam);
		
	    
	    //*inisialisasi awal
	    akumulasi1.setEnabled(false);
	    jenis_kendaraan1.setEnabled(false);
	    for(int i = 0; i < waktu1.getChildCount(); i++){
            ((RadioButton)waktu1.getChildAt(i)).setEnabled(false);
        }
	    	    

		//*input drop down spinner
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jenis_kendaraan_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		jenis_kendaraan1.setAdapter(adapter);
		
		//*spinner akumulasi
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.akumulasi_array, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		akumulasi1.setAdapter(adapter1);
	    
		//*CheckBox Change
	    waktu.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					for(int i = 0; i < waktu1.getChildCount(); i++){
			            ((RadioButton)waktu1.getChildAt(i)).setEnabled(true);
			            
			        }
					
				}else{
					for(int i = 0; i < waktu1.getChildCount(); i++){
			            ((RadioButton)waktu1.getChildAt(i)).setEnabled(false);
			            
					}
					wkt="";
				}
			}
		});
	    
	    akumulasi.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					akumulasi1.setEnabled(true);
				}
				else{
					akumulasi1.setEnabled(false);
					akm="";
				}
			}
		});
	    jenis_kendaraan.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					jenis_kendaraan1.setEnabled(true);
					
				}else{
					jenis_kendaraan1.setEnabled(false);
					jns="";
				}
				
			}
		});
	    
	    //*getValue from Spinner
	    
	    jenis_kendaraan1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
					jns=jenis_kendaraan1.getSelectedItem().toString();
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
					jns="";
			}
	    	
	    	
	    });
	    
	    akumulasi1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String temp=akumulasi1.getSelectedItem().toString();
				if(temp.equals("to 50000")){
					akm="1";
				}else if(temp.equals("50.000 to 100.000")){
					akm="2";
				}else if(temp.equals("100.000 to 200.000")){
					akm="3";
				}else if(temp.equals("200.000 to 500.000")){
					akm="4";
				}else if(temp.equals("More than 500.000")){
					akm="5";
				}else{
					akm="0";
				}
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				akm="";
			}
	    	
	    	
	    });

	    
	      
	    

    }
    //*getValue From RadioButton
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.siang:
                if (checked)
                    wkt="Siang";
                break;
            case R.id.malam:
                if (checked)
                    wkt="Malam";
                break;
        }
       
   	   
    }
    
    //*Search On click
    private void onSearch(){
    	btnSearch.setOnClickListener(new View.OnClickListener() {
	    	
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				setGet.setSearch(wkt, awl, tjn, jns, akm);
				Intent i=new Intent(getApplicationContext(),ResultSearchActivity.class);
				startActivity(i);
				SearchActivity.this.finish();
			}
		});
    }
    
    //*setSpinner Awal Tujuan
    private void setAwalTujuan(){
    	
    	list=setGet.getListKota();
		Collections.sort(list);
		
		//*Adapter Spinner
		awal=(Spinner)findViewById(R.id.KotaAwal);
		tujuan=(Spinner)findViewById(R.id.tujuan);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		awal.setAdapter(adapter);
		ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tujuan.setAdapter(adapter1);
		
		//*getValue Spinner
	    awal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				awl=awal.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				awl=null;
			}
	    	
	    	
	    });
	    tujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				tjn=tujuan.getSelectedItem().toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				tjn=null;
			}
	    	
	    	
	    });
	    
    }
    
   


   
}
