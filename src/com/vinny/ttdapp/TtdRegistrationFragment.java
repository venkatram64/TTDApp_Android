package com.vinny.ttdapp;

import java.util.ArrayList;

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

import com.vinny.ttdapp.TtdProcessFragment.TtdSearchTemplateHandler;
import com.vinny.ttdapp.util.NothingSelectedSpinnerAdapter;
import com.vinny.ttdapp.util.SpinnerObject;
import com.vinny.ttdapp.util.TtdTempleInfo;
import com.vinny.ttdapp.util.TtdTempleTask;
import com.vinny.ttdapp.util.TtdTypeEnum;

public class TtdRegistrationFragment extends Fragment implements OnItemSelectedListener,OnClickListener
					{
	
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
	
	Button ttdAdd,ttdSearch;
	
	EditText etTempleName,etName,etEmail,etPhone;
	String etStrTempleName,etStrName,etStrEmail,etStrPhone;
	
	TtdAddTemplateHandler ttdAddTemplateHandler;
	
	int selectedDistId;
	int selectedMandalId;
	int selectedVillageId;
	int selectedTempleId;
	
	int distPosition = -1;
	int mandalPosition = -1;
	int villagePosition = -1;
	int catPosition = -1;
	int templePosition = -1;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			ttdAddTemplateHandler = (TtdAddTemplateHandler) getActivity();
		} catch (ClassCastException ex) {
			Log.d("TtdRegistrationFragment : " , getActivity().getClass().getSimpleName() + 
					" not implementd " + TtdSearchTemplateHandler.class.getSimpleName());
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		View view = inflater.inflate(R.layout.ttd_registration_fragment, container,false);
		ttdAdd = (Button) view.findViewById(R.id.ttd_registration);
		ttdSearch = (Button) view.findViewById(R.id.ttd_rsearch);
		ttdAdd.setOnClickListener(this);
		ttdSearch.setOnClickListener(this);
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		onMyStateRestored(savedInstanceState);
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
			this.templeName = (String) savedInstanceState.getSerializable("TEMPLE_DISPLAY");
			
			this.distPosition = (Integer) savedInstanceState.getSerializable("DIST_POSITION");
			this.mandalPosition = (Integer) savedInstanceState.getSerializable("MAND_POSITION");
			this.villagePosition = (Integer) savedInstanceState.getSerializable("VILLAGE_POSITION");
			this.catPosition = (Integer) savedInstanceState.getSerializable("CAT_POSITION");
			this.templePosition = (Integer) savedInstanceState.getSerializable("TEMPLE_POSITION");
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(this.districtDataList == null){
			TtdTempleTask dtask = new TtdTempleTask(TtdRegistrationFragment.this, TtdTypeEnum.DISTRICT.toString());
			try{
				dtask.execute("all");
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}else if(this.districtDataList != null){
			this.setDistrictData(this.districtDataList);
			this.ttdDistrict.setSelection(this.distPosition,false);
		}
		if(this.mandalDataList != null){
			this.setMandalData(this.mandalDataList);
			this.ttdMandal.setSelection(this.mandalPosition,false);
		}
		if(this.villageDataList != null){
			this.setVillageData(villageDataList);
			this.ttdVillage.setSelection(this.villagePosition,false);
			this.ttdCatetory.setSelection(this.catPosition,false);
		}
		if(this.templeDataList != null){
			this.setTempleData(templeDataList);
			this.ttdTemple.setSelection(this.templePosition,false);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		
		if(parent.getItemAtPosition(position) == null || parent.getSelectedItem() == null ||  position == -1)
			return;
		if(R.id.ttd_rdistrictId == parent.getId()){
			if(this.distPosition == position){
				return;
			}
			this.distPosition = position;
			this.distName = parent.getItemAtPosition(position).toString();
			if(this.distName.contains("Select")){
				return;
			}
			selectedDistId = ((SpinnerObject) parent.getSelectedItem()).getId();
			this.distName = parent.getItemAtPosition(position).toString();
			//Toast.makeText(parent.getContext(), "Selected: " + itemId +"," + itemName, Toast.LENGTH_LONG).show();
			TtdTempleTask dtask = new TtdTempleTask(TtdRegistrationFragment.this, TtdTypeEnum.MANDAL.toString());
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
			this.mandalName = parent.getItemAtPosition(position).toString();
			if(this.mandalName.contains("Select")){
				return;
			}
			selectedMandalId = ((SpinnerObject) parent.getSelectedItem()).getId();
			
			//Toast.makeText(parent.getContext(), "Selected: " + itemId +"," + itemName, Toast.LENGTH_LONG).show();
			TtdTempleTask dtask = new TtdTempleTask(TtdRegistrationFragment.this, TtdTypeEnum.VILLAGE.toString());
			try{
				dtask.execute(selectedDistId +"",selectedMandalId +"");
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
			
			
		}
		if(R.id.ttd_rvillageId == parent.getId()){
			this.villageName = parent.getItemAtPosition(position).toString();
			if(this.villageName.contains("Select")){
				return;
			}
			if(this.villagePosition == position){
				return;
			}
			this.villagePosition = position;
			
			TtdTempleTask dtask = new TtdTempleTask(TtdRegistrationFragment.this, TtdTypeEnum.TEMPLE.toString());
			try{
				dtask.execute(this.catName,distName,mandalName,villageName);
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
			
		}
		
		if(R.id.ttd_rtempleId == parent.getId()){
			this.templeName = parent.getItemAtPosition(position).toString();
			this.templePosition = position;
		}
		
		if(R.id.ttd_rcategoryId == parent.getId()){
			this.catName = parent.getItemAtPosition(position).toString();
			this.catPosition = position;
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		//isLoadRequired = false;
		
	}
	
	public void alert (String msg)
    {
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
	
	interface TtdAddTemplateHandler{
		void onTtdAddTemplate(TtdTempleInfo temple);
	}

	@Override
	public void onClick(View v) {
		info = new TtdTempleInfo();
		switch(v.getId()){
		case R.id.ttd_registration:
			info.setCatName(catName);
			info.setDistName(distName);
			info.setMandalName(mandalName);
			info.setVillageName(villageName);
			if(templeName != null && !templeName.equalsIgnoreCase("Do not exist temple name")){
				etStrTempleName = etTempleName.getText().toString();
				info.setTempleName(etStrTempleName);
			}else{
				info.setTempleName(templeName);
			}
			etStrName = etName.getText().toString();
			info.setName(etStrName);
			etStrEmail = etEmail.getText().toString();
			info.setEmail(etStrEmail);
			etStrPhone = etPhone.getText().toString();
			info.setPhone(etStrPhone);
			info.setDistrictDataList(this.districtDataList);
			info.setMandalDataList(this.mandalDataList);
			info.setVillageDataList(this.villageDataList);
			info.setTempleDataList(this.templeDataList);
			info.setType(TtdTypeEnum.DISPLAY.toString());
			ttdAddTemplateHandler.onTtdAddTemplate(info);
			break;
		
		case R.id.ttd_rsearch:
			info.setCatName(catName);
			info.setDistName(distName);
			info.setMandalName(mandalName);
			info.setVillageName(villageName);
			if(templeName != null && !templeName.equalsIgnoreCase("Do not exist temple name")){
				etStrTempleName = etTempleName.getText().toString();
				info.setTempleName(etStrTempleName);
			}else{
				info.setTempleName(templeName);
			}
			etStrName = etName.getText().toString();
			info.setName(etStrName);
			etStrEmail = etEmail.getText().toString();
			info.setEmail(etStrEmail);
			etStrPhone = etPhone.getText().toString();
			info.setPhone(etStrPhone);
			info.setType(TtdTypeEnum.SEARCH.toString());
			info.setDistrictDataList(this.districtDataList);
			info.setMandalDataList(this.mandalDataList);
			info.setVillageDataList(this.villageDataList);
			info.setTempleDataList(this.templeDataList);
			info.setTurnOffDropdown(true);
			ttdAddTemplateHandler.onTtdAddTemplate(info);
			break;
		}
		
	}

	


}
