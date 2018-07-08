package com.vinny.ttdapp.util;

import com.vinny.ttdapp.R;

public enum TtdSearchDisplayEnum {

	CATEGORY(R.id.ttd_cat,"category"),
	TEMPLE_NAME(R.id.ttd_temple_name,"templeName"),
	NAME(R.id.ttd_name,"name"),
	DESIGNATION(R.id.ttd_designation,"designation"),
	PHONE(R.id.ttd_phone,"phone"),
	EMAIL(R.id.ttd_email,"email");
	
	private int  id;
	private String name;
	
	TtdSearchDisplayEnum(int id,String name){
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	

}
