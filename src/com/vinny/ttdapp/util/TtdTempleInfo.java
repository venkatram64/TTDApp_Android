package com.vinny.ttdapp.util;

import java.io.Serializable;
import java.util.ArrayList;

public class TtdTempleInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5458435724150305922L;
	private String id;
	private String distName;
	private String mandalName;
	private String villageName;
	private String catName;
	private String templeName;
	private String etTempleName;
	private String name;
	private String email;
	private String phone;
	private String type;
	private ArrayList<SpinnerObject> districtDataList;
	private ArrayList<SpinnerObject> mandalDataList;
	private ArrayList<SpinnerObject> villageDataList;
	private ArrayList<SpinnerObject> templeDataList;
	private boolean turnOffDropdown ;
	
	public TtdTempleInfo(){}
	
	public TtdTempleInfo(String id,String distName, String mandalName,
			String villageName, String catName, String templeName,String etTempleName, String name,
			String email, String phone,String type) {
		super();
		this.id = id;
		this.distName = distName;
		this.mandalName = mandalName;
		this.villageName = villageName;
		this.catName = catName;
		this.templeName = templeName;
		this.etTempleName = etTempleName;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.type = type;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public String getMandalName() {
		return mandalName;
	}
	public void setMandalName(String mandalName) {
		this.mandalName = mandalName;
	}
	public String getVillageName() {
		return villageName;
	}
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEtTempleName() {
		return etTempleName;
	}

	public void setEtTempleName(String etTempleName) {
		this.etTempleName = etTempleName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<SpinnerObject> getDistrictDataList() {
		return districtDataList;
	}

	public void setDistrictDataList(ArrayList<SpinnerObject> districtDataList) {
		this.districtDataList = districtDataList;
	}

	public ArrayList<SpinnerObject> getMandalDataList() {
		return mandalDataList;
	}

	public void setMandalDataList(ArrayList<SpinnerObject> mandalDataList) {
		this.mandalDataList = mandalDataList;
	}

	public ArrayList<SpinnerObject> getVillageDataList() {
		return villageDataList;
	}

	public void setVillageDataList(ArrayList<SpinnerObject> villageDataList) {
		this.villageDataList = villageDataList;
	}

	public ArrayList<SpinnerObject> getTempleDataList() {
		return templeDataList;
	}

	public void setTempleDataList(ArrayList<SpinnerObject> templeDataList) {
		this.templeDataList = templeDataList;
	}

	public boolean isTurnOffDropdown() {
		return turnOffDropdown;
	}

	public void setTurnOffDropdown(boolean turnOffDropdown) {
		this.turnOffDropdown = turnOffDropdown;
	}
	
}

