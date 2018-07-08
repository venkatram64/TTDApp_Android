package com.vinny.ttdapp.util;

public enum HostAddressEnum {
	
	MY_LOCAL(1, "http://10.0.2.2:8082/restSpring" ),
	//MY_HOME(2, "http://192.168.1.149:8082/restSpring" ),
	MY_HOME(2, "http://10.0.2.2:8888/restSpring" ),
	MY_OFFICE(3, "http://10.0.2.2:8082/restSpring" );
	
	private int id;
	 private String type;
	 
	 HostAddressEnum(int id,String type){
		 this.id = id;
		 this.type = type;
	 }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
