package com.vinny.ttdapp.util;

import java.io.Serializable;

public class TtdSearchObject implements Serializable{
	
	private String category;
	private String templeName;
	private String name;
	private String designation;
	private String phone;
	private String email;
	
	public TtdSearchObject(String category,String templeName,String name,String designation,String phone,String email){
		this.category = category;
		this.templeName = templeName;
		this.name = name;
		this.designation = designation;
		this.phone = phone;
		this.email = email;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTempleName() {
		return templeName;
	}
	public void setTempleName(String templeName) {
		this.templeName = templeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
