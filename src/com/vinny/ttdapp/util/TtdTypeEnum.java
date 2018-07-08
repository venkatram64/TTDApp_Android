package com.vinny.ttdapp.util;

public enum TtdTypeEnum {
	
	DISTRICT(1, "District" ),
    MANDAL(2, "Mandal"),
    VILLAGE(3, "Village"),
	CATEGORY(4,"Category"),
	SEARCH(5,"Search"),
	TEMPLE(6,"Temple"),
	DISPLAY(7,"Display"),
	POST(8,"POST"),
	GET(9,"GET"),
	EDIT_DELETE(10,"Edit"),
	DELETE(11,"Delete"),
	ADMIN_DISPLAY(12,"AdminDisplay"),
	APPROVE(13,"Approve");
	
	 private int id;
	 private String type;
	 
	 TtdTypeEnum(int id,String type){
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
