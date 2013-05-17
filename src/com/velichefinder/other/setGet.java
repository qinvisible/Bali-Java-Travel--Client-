package com.velichefinder.other;

import java.util.ArrayList;


public class setGet {	
	private static String id, status;
	private static String waktu, asal, tujuan, akumulasi, jenis;
	private static ArrayList<String> list;
	public static final String url="http://192.168.157.1/transport_finder/";
	
	public static void setId(String str) {
		id=str;
	}
	public static Object getId(){
		return id;
	}

	public static void setStatus(String str) {
		status=str;
	}

	public static Object getStatus() {
		return status;
	}
	public static void setSearch(String waktu1, String asal1, String tujuan1, String jenis1, String akumulasi1){
		waktu=waktu1;
		asal=asal1;
		tujuan=tujuan1;
		akumulasi=akumulasi1;
		jenis=jenis1;
	}
	public static String getAsal(){
		return asal;
	}
	public static String getTujuan(){
		return tujuan;
	}
	public static String getWaktu(){
		return waktu;
	}
	public static String getAkumulasi(){
		return akumulasi;
	}
	public static String getJenis(){
		return jenis;
	}
	public static void setListKota(ArrayList<String> List){
		list=List;
	}
	public static ArrayList<String> getListKota(){
		return list;
	}

	
	
}