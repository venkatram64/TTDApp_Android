package com.vinny.ttdapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vinny.ttdapp.util.NothingSelectedSpinnerAdapter;
import com.vinny.ttdapp.util.SpinnerObject;
import com.vinny.ttdapp.util.TtdDisplayTask;
import com.vinny.ttdapp.util.TtdTempleEditDeleteTask;
import com.vinny.ttdapp.util.TtdTempleInfo;
import com.vinny.ttdapp.util.TtdTypeEnum;

public class TtdTempleEditDeleteFragment extends Fragment implements OnItemSelectedListener,OnClickListener{
	
	Context context;
	
	Spinner ttdCatetory;
	ArrayAdapter<CharSequence> catAdapter;
	
	Spinner ttdDistrict;
	ArrayAdapter<SpinnerObject> districtAdapter;
	ArrayList<SpinnerObject> districtDataList;
	
	Spinner ttdMandal;
	ArrayAdapter<SpinnerObject> mandalAdapter;
	ArrayList<SpinnerObject> mandalDataList;
	
	Spinner ttdVillage;
	ArrayAdapter<SpinnerObject> villageAdapter;
	ArrayList<SpinnerObject> villageDataList;
	
	Spinner ttdTemple;
	ArrayAdapter<SpinnerObject> templeAdapter;
	ArrayList<SpinnerObject> templeDataList;
	
	String distName, mandalName, villageName,catName,templeName;
	
	TtdTempleInfo info = null;
	
	Button ttdEdit,ttdDelete;
	
	EditText etTempleName,etName,etEmail,etPhone;
	String etStrTempleName,etStrName,etStrEmail,etStrPhone;
	
	TtdEditDeleteTemplateHandler ttdEditDeleteTemplateHandler;
	
	int selectedDistId;
	int selectedMandalId;
	int selectedVillageId;
	int selectedTempleId;
	String selectedTempId;
	
	int distPosition = -1;
	int mandalPosition = -1;
	int villagePosition = -1;
	int catPosition = -1;
	int templePosition = -1;
	boolean turnOffDropdown ;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			ttdEditDeleteTemplateHandler = (TtdEditDeleteTemplateHandler) getActivity();
		} catch (ClassCastException ex) {
			Log.d("TtdEditDeleteTemplateHandler : " , getActivity().getClass().getSimpleName() + 
					" not implementd " + TtdTempleEditDeleteFragment.class.getSimpleName());
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onMyStateRestored(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		context = getActivity();
		View view = inflater.inflate(R.layout.ttd_edit_delete_fragment, container, false);
		Bundle element = this.getArguments();
		info = (TtdTempleInfo) element.getSerializable(TtdTypeEnum.EDIT_DELETE.toString());
		
		ttdEdit = (Button) view.findViewById(R.id.ttd_redit);
		ttdDelete = (Button) view.findViewById(R.id.ttd_rdelete);
		ttdEdit.setOnClickListener(this);
		ttdDelete.setOnClickListener(this);
		ttdCatetory = (Spinner) view.findViewById(R.id.ttd_rcategoryId);//new SpinnerHelper( view.findViewById(R.id.ttd_categoryId));
		ttdCatetory.setOnItemSelectedListener(this);
		ttdCatetory.setPrompt("Select District");
		catAdapter = ArrayAdapter.createFromResource(context,  R.array.ttd_category, android.R.layout.simple_spinner_item);
		// Drop down layout style - list view with radio button
		catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ttdCatetory.setAdapter(catAdapter);
		//district spinner
		ttdDistrict = (Spinner) view.findViewById(R.id.ttd_rdistrictId);//new SpinnerHelper( view.findViewById(R.id.ttd_districtId));
		ttdDistrict.setOnItemSelectedListener(this);
		ttdDistrict.setPrompt("Select District");
		//district spinner ends here
		//Mandal
		ttdMandal = (Spinner) view.findViewById(R.id.ttd_rmandalId);//new SpinnerHelper( view.findViewById(R.id.ttd_mandalId));
		ttdMandal.setOnItemSelectedListener(this);
		ttdMandal.setPrompt("Select Mandal");
		//end Mandal
			
		//Village
		ttdVillage = (Spinner) view.findViewById(R.id.ttd_rvillageId);//new SpinnerHelper( view.findViewById(R.id.ttd_villageId));
		ttdVillage.setOnItemSelectedListener(this);
		ttdVillage.setPrompt("Select Village");
		//end Village
		
		//Village
		ttdTemple = (Spinner) view.findViewById(R.id.ttd_rtempleId);//new SpinnerHelper( view.findViewById(R.id.ttd_villageId));
		ttdTemple.setOnItemSelectedListener(this);
		ttdTemple.setPrompt("Select Temple");
		//end Village
		
		etTempleName = (EditText) view.findViewById(R.id.ttd_temple_name);
		etName = (EditText) view.findViewById(R.id.ttd_name);
		etPhone = (EditText) view.findViewById(R.id.ttd_phone);
		etEmail = (EditText) view.findViewById(R.id.ttd_email);
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putSerializable("DIST_LIST", districtDataList);
		outState.putSerializable("DIST_DISPLAY", this.distName);
		outState.putSerializable("MAND_LIST", mandalDataList);
		outState.putSerializable("MAND_DISPLAY", this.mandalName);
		outState.putSerializable("VILLAGE_LIST", villageDataList);
		outState.putSerializable("VILLAGE_DISPLAY", this.villageName);
		outState.putSerializable("TEMPLE_LIST", templeDataList);
		outState.putSerializable("TEMPLE_DISPLAY", this.templeName);
		
		outState.putSerializable("DIST_POSITION", this.distPosition);
		outState.putSerializable("MAND_POSITION", this.mandalPosition);
		outState.putSerializable("VILLAGE_POSITION", this.villagePosition);
		outState.putSerializable("TEMPLE_POSITION", this.templePosition);
		
		outState.putSerializable("CAT_DISPLAY", this.catName);
		outState.putSerializable("CAT_POSITION", this.catPosition);
	}
	
	private void onMyStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState != null){
			districtDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("DIST_LIST");
			mandalDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("MAND_LIST");
			villageDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("VILLAGE_LIST");
			templeDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("TEMPLE_LIST");
			
			this.distName =  (String) savedInstanceState.getSerializable("DIST_DISPLAY");
			this.mandalName =  (String) savedInstanceState.getSerializable("MAND_DISPLAY");
			this.villageName =  (String) savedInstanceState.getSerializable("VILLAGE_DISPLAY");
			this.catName =  (String) savedInstanceState.getSerializable("CAT_DISPLAY");
			this.templeName =  (String) savedInstanceState.getSerializable("TEMPLE_DISPLAY");
			
			this.distPosition = (Integer) savedInstanceState.getSerializable("DIST_POSITION");
			this.mandalPosition = (Integer) savedInstanceState.getSerializable("MAND_POSITION");
			this.villagePosition = (Integer) savedInstanceState.getSerializable("VILLAGE_POSITION");
			this.catPosition = (Integer) savedInstanceState.getSerializable("CAT_POSITION");
			this.templePosition = (Integer) savedInstanceState.getSerializable("TEMPLE_POSITION");
		}
	}
	private void transferData(){
		if(info != null){
			this.selectedTempId = info.getId();
			this.catName = info.getCatName();
			this.distName = info.getDistName();
			this.mandalName = info.getMandalName();
			this.villageName = info.getVillageName();
			this.templeName = info.getTempleName();
			this.etStrName = info.getName();
			this.etStrEmail = info.getEmail();
			this.etStrPhone = info.getPhone();
			this.districtDataList = info.getDistrictDataList();
			this.mandalDataList = info.getMandalDataList();
			this.villageDataList = info.getVillageDataList();
			this.templeDataList = info.getTempleDataList();
			this.turnOffDropdown = info.isTurnOffDropdown();
			etTempleName.setText(this.templeName);
			etName.setText(this.etStrName);
			etEmail.setText(this.etStrEmail);
			etPhone.setText(this.etStrPhone);
		}
	}
	
	private int getPosition(List<SpinnerObject> obj,String name){
		int count = 1;
		for(SpinnerObject so : obj){
			if(name.equals(so.getValue())){
				return count;
			}
			count++;
		}
		return count;
	}
	
	private int getId(List<SpinnerObject> obj,String name){
		for(SpinnerObject so : obj){
			if(name.equals(so.getValue())){
				return so.getId();
			}
		}
		return 0;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		transferData();
		info = null;
		if(this.districtDataList == null){
			TtdTempleEditDeleteTask dtask = new TtdTempleEditDeleteTask(TtdTempleEditDeleteFragment.this, TtdTypeEnum.DISTRICT.toString());
			try{
				dtask.execute("all");
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}else if(this.districtDataList != null){
			this.setDistrictData(this.districtDataList);
			this.distPosition = getPosition(districtDataList, distName);
			this.selectedDistId = getId(districtDataList,distName);
			this.ttdDistrict.setSelection(this.distPosition,false);
		}
		if(this.mandalDataList != null){
			this.setMandalData(this.mandalDataList);
			this.mandalPosition = getPosition(mandalDataList, mandalName);
			this.selectedMandalId = getId(mandalDataList,mandalName);
			this.ttdMandal.setSelection(this.mandalPosition,false);
		}
		if(this.villageDataList != null){
			this.setVillageData(villageDataList);
			this.villagePosition = getPosition(villageDataList, villageName);
			this.selectedVillageId = getId(villageDataList,villageName);
			this.ttdVillage.setSelection(this.villagePosition,false);
		}
		if(this.templeDataList != null){
			this.setTempleData(templeDataList);
			this.templePosition = getPosition(templeDataList, templeName);
			this.ttdTemple.setSelection(this.templePosition-1,false);
		}
		this.turnOffDropdown = false;
	}


	@Override
	public void onClick(View view) {
		TtdTempleInfo info1 = new TtdTempleInfo();
		info1.setDistrictDataList(districtDataList);
		info1.setVillageDataList(villageDataList);
		info1.setMandalDataList(mandalDataList);
		info1.setTempleDataList(templeDataList);
		switch(view.getId()){
		case R.id.ttd_redit:
			info1.setId(selectedTempId);
			info1.setCatName(catName);
			info1.setDistName(distName);
			info1.setMandalName(mandalName);
			info1.setVillageName(villageName);
			if(templeName != null && !templeName.equalsIgnoreCase("Do not exist temple name")){
				etStrTempleName = etTempleName.getText().toString();
				info1.setTempleName(etStrTempleName);
			}else{
				info1.setTempleName(templeName);
			}
			etStrName = etName.getText().toString();
			info1.setName(etStrName);
			etStrEmail = etEmail.getText().toString();
			info1.setEmail(etStrEmail);
			etStrPhone = etPhone.getText().toString();
			info1.setPhone(etStrPhone);
			info1.setType(TtdTypeEnum.EDIT_DELETE.toString());
			ttdEditDeleteTemplateHandler.onTtdUpdateTemplateHandler(info1);
			return;
		case R.id.ttd_rdelete:	
			info1.setId(selectedTempId);
			info1.setCatName(catName);
			info1.setDistName(distName);
			info1.setMandalName(mandalName);
			info1.setVillageName(villageName);
			if(templeName != null && !templeName.equalsIgnoreCase("Do not exist temple name")){
				etStrTempleName = etTempleName.getText().toString();
				info1.setTempleName(etStrTempleName);
			}else{
				info1.setTempleName(templeName);
			}
			etStrName = etName.getText().toString();
			info1.setName(etStrName);
			etStrEmail = etEmail.getText().toString();
			info1.setEmail(etStrEmail);
			etStrPhone = etPhone.getText().toString();
			info1.setPhone(etStrPhone);
			info1.setType(TtdTypeEnum.DELETE.toString());
			ttdEditDeleteTemplateHandler.onTtdDeleteTemplateHandler(info1);
			return;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		
		if(this.turnOffDropdown)
			return;
		if(R.id.ttd_rdistrictId == parent.getId()){
			if(this.distPosition == position){
				return;
			}
			this.distPosition = position;
			if(parent.getItemAtPosition(position) == null){
				return;
			}
			this.distName = parent.getItemAtPosition(position).toString();
			if(this.distName.contains("Select")){
				return;
			}
			selectedDistId = ((SpinnerObject) parent.getSelectedItem()).getId();
			this.distName = parent.getItemAtPosition(position).toString();
			//Toast.makeText(parent.getContext(), "Selected: " + itemId +"," + itemName, Toast.LENGTH_LONG).show();
			TtdTempleEditDeleteTask dtask = new TtdTempleEditDeleteTask(TtdTempleEditDeleteFragment.this, TtdTypeEnum.MANDAL.toString());
			try{
				dtask.execute(selectedDistId +"");
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
			
		}
		if(R.id.ttd_rmandalId == parent.getId()){
			
			
			if(this.mandalPosition == position){
				return;
			}
			
			this.mandalPosition = position;
			if(parent.getItemAtPosition(position) == null){
				return;
			}
			this.mandalName = parent.getItemAtPosition(position).toString();
			if(this.mandalName.contains("Select")){
				return;
			}
			selectedMandalId = ((SpinnerObject) parent.getSelectedItem()).getId();
			
			//Toast.makeText(parent.getContext(), "Selected: " + itemId +"," + itemName, Toast.LENGTH_LONG).show();
			TtdTempleEditDeleteTask dtask = new TtdTempleEditDeleteTask(TtdTempleEditDeleteFragment.this, TtdTypeEnum.VILLAGE.toString());
			try{
				dtask.execute(selectedDistId +"",selectedMandalId +"");
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
			
			
		}
		if(R.id.ttd_rvillageId == parent.getId()){
			if(parent.getItemAtPosition(position) == null){
				return;
			}
			this.villageName = parent.getItemAtPosition(position).toString();
			if(this.villageName.contains("Select")){
				return;
			}
			if(this.villagePosition == position){
				return;
			}
			this.villagePosition = position;
			
			TtdTempleEditDeleteTask dtask = new TtdTempleEditDeleteTask(TtdTempleEditDeleteFragment.this, TtdTypeEnum.TEMPLE.toString());
			try{
				dtask.execute(this.catName,distName,mandalName,villageName);
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
			
		}
		
		if(R.id.ttd_rtempleId == parent.getId()){
			if(parent.getItemAtPosition(position) == null){
				return;
			}
			this.templeName = parent.getItemAtPosition(position).toString();
			this.templePosition = position;
		}
		
		if(R.id.ttd_rcategoryId == parent.getId()){
			if(parent.getItemAtPosition(position) == null){
				return;
			}
			this.catName = parent.getItemAtPosition(position).toString();
			this.catPosition = position;
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	public void alert (String msg){
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
	
	
	public void setDistrictData(ArrayList<SpinnerObject> data) {
		this.districtDataList = data;
		districtAdapter = new ArrayAdapter<SpinnerObject>(context, android.R.layout.simple_spinner_item, this.districtDataList);
		districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.ttdDistrict.setAdapter( new NothingSelectedSpinnerAdapter(districtAdapter,R.layout.contact_spinner_row_nothing_selected,context));
	}
	
	public void setMandalData(ArrayList<SpinnerObject> data) {
		this.mandalDataList = data;
		mandalAdapter = new ArrayAdapter<SpinnerObject>(context, android.R.layout.simple_spinner_item, this.mandalDataList);
		mandalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.ttdMandal.setAdapter(new NothingSelectedSpinnerAdapter(mandalAdapter,R.layout.contact_spinner_row_nothing_selected,context));
	}
	
	public void setVillageData(ArrayList<SpinnerObject> data) {
		this.villageDataList = data;
		villageAdapter = new ArrayAdapter<SpinnerObject>(context, android.R.layout.simple_spinner_item, this.villageDataList);
		villageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.ttdVillage.setAdapter(new NothingSelectedSpinnerAdapter(villageAdapter,R.layout.contact_spinner_row_nothing_selected,context));
	}
	
	public void setTempleData(ArrayList<SpinnerObject> data) {
		this.templeDataList = data;
		templeAdapter = new ArrayAdapter<SpinnerObject>(context, android.R.layout.simple_spinner_item, this.templeDataList);
		templeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.ttdTemple.setAdapter(new NothingSelectedSpinnerAdapter(templeAdapter,R.layout.contact_spinner_row_nothing_selected,context));
	}
	
	interface TtdEditDeleteTemplateHandler{
		void onTtdUpdateTemplateHandler(TtdTempleInfo info);
		void onTtdDeleteTemplateHandler(TtdTempleInfo info);
	}
	
}
