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
import android.widget.Spinner;
import android.widget.Toast;

import com.vinny.ttdapp.util.NothingSelectedSpinnerAdapter;
import com.vinny.ttdapp.util.SpinnerObject;
import com.vinny.ttdapp.util.TtdDistrictTask;
import com.vinny.ttdapp.util.TtdTypeEnum;


public class TtdProcessFragment extends Fragment implements OnItemSelectedListener,OnClickListener{

	
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
	
	int selectedDistId;
	int selectedMandalId;
	int selectedVillageId;
	
	Button ttdSearch;
	String distName, mandalName, villageName,catName;
	
	private TtdSearchTemplateHandler ttdSearchTemplateHandler;
	
	Bundle saveStateBundle;
	View view;
	boolean isLoadRequired = false;
	int distPosition = -1;
	int mandalPosition = -1;
	int villagePosition = -1;
	int catPosition = -1;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			ttdSearchTemplateHandler = (TtdSearchTemplateHandler) getActivity();
		}catch(ClassCastException ex){
			Log.d("TtdProcessFragment : " , getActivity().getClass().getSimpleName() + 
					" not implementd " + TtdSearchTemplateHandler.class.getSimpleName());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//onMyStateRestored(savedInstanceState);
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
		
		outState.putSerializable("DIST_POSITION", this.distPosition);
		outState.putSerializable("MAND_POSITION", this.mandalPosition);
		outState.putSerializable("VILLAGE_POSITION", this.villagePosition);
		
		outState.putSerializable("CAT_DISPLAY", this.catName);
		outState.putSerializable("CAT_POSITION", this.catPosition);
	}
	
	private void onMyStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState != null){
			districtDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("DIST_LIST");
			mandalDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("MAND_LIST");
			villageDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("VILLAGE_LIST");
			
			this.distName =  (String) savedInstanceState.getSerializable("DIST_DISPLAY");
			this.mandalName =  (String) savedInstanceState.getSerializable("MAND_DISPLAY");
			this.villageName =  (String) savedInstanceState.getSerializable("VILLAGE_DISPLAY");
			this.catName =  (String) savedInstanceState.getSerializable("CAT_DISPLAY");
			
			this.distPosition = (Integer) savedInstanceState.getSerializable("DIST_POSITION");
			this.mandalPosition = (Integer) savedInstanceState.getSerializable("MAND_POSITION");
			this.villagePosition = (Integer) savedInstanceState.getSerializable("VILLAGE_POSITION");
			this.catPosition = (Integer) savedInstanceState.getSerializable("CAT_POSITION");
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		if(this.districtDataList == null){
			TtdDistrictTask dtask = new TtdDistrictTask(TtdProcessFragment.this, TtdTypeEnum.DISTRICT.toString());
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		view = inflater.inflate(R.layout.ttd_list_info_fragment, container,false);
		ttdCatetory = (Spinner) view.findViewById(R.id.ttd_categoryId);//new SpinnerHelper( view.findViewById(R.id.ttd_categoryId));
		ttdCatetory.setOnItemSelectedListener(this);
		ttdCatetory.setPrompt("Select District");
		catAdapter = ArrayAdapter.createFromResource(context,  R.array.ttd_category, android.R.layout.simple_spinner_item);
		// Drop down layout style - list view with radio button
		catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ttdCatetory.setAdapter(catAdapter);
		//district spinner
		ttdDistrict = (Spinner) view.findViewById(R.id.ttd_districtId);//new SpinnerHelper( view.findViewById(R.id.ttd_districtId));
		ttdDistrict.setOnItemSelectedListener(this);
		ttdDistrict.setPrompt("Select District");
		/*if(this.districtDataList == null){
			TtdDistrictTask dtask = new TtdDistrictTask(TtdProcessFragment.this, TtdTypeEnum.DISTRICT.toString());
			try{
				dtask.execute("all");
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}*/
		//district spinner ends here
		//Mandal
			ttdMandal = (Spinner) view.findViewById(R.id.ttd_mandalId);//new SpinnerHelper( view.findViewById(R.id.ttd_mandalId));
			ttdMandal.setOnItemSelectedListener(this);
			ttdMandal.setPrompt("Select Mandal");
		//end Mandal
			
		//Village
		ttdVillage = (Spinner) view.findViewById(R.id.ttd_villageId);//new SpinnerHelper( view.findViewById(R.id.ttd_villageId));
		ttdVillage.setOnItemSelectedListener(this);
		ttdVillage.setPrompt("Select Village");
		//end Village
		
		//search
		ttdSearch = (Button)view.findViewById(R.id.ttd_search);
		ttdSearch.setOnClickListener(this);
		
		/*ttdRegistration = (Button) view.findViewById(R.id.ttd_registration);
		ttdRegistration.setOnClickListener(this);*/
		
		return view;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		
		String itemName = ""; 
		if(parent.getItemAtPosition(position) == null || parent.getSelectedItem() == null ||  position == -1)
			return;
		if(R.id.ttd_districtId == parent.getId()){
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
			TtdDistrictTask dtask = new TtdDistrictTask(TtdProcessFragment.this, TtdTypeEnum.MANDAL.toString());
			try{
				dtask.execute(selectedDistId +"");
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
			
		}
		if(R.id.ttd_mandalId == parent.getId()){
			
			
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
			TtdDistrictTask dtask = new TtdDistrictTask(TtdProcessFragment.this, TtdTypeEnum.VILLAGE.toString());
			try{
				dtask.execute(selectedDistId +"",selectedMandalId +"");
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
			
			
		}
		if(R.id.ttd_villageId == parent.getId()){
			this.villageName = parent.getItemAtPosition(position).toString();
			if(this.villageName.contains("Select")){
				return;
			}
			if(this.villagePosition == position){
				return;
			}
			
			this.villagePosition = position;
			
		}
		if(R.id.ttd_categoryId == parent.getId()){
			this.catPosition = position;
			this.catName = parent.getItemAtPosition(position).toString();
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
	
	
	
	interface TtdSearchTemplateHandler{
		void onTtdSearchTemplate(String distName,String mandalName,String villageName,String catName);
	}



	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ttd_search:
			ttdSearchTemplateHandler.onTtdSearchTemplate(distName, mandalName, villageName,catName);
			return;
			
		case R.id.ttd_registration:
			//ttdSearchTemplateHandler.onTtdRegistrationTemplate();
			return;
		}
	}

}


/*public class TtdProcessFragment extends Fragment implements OnItemSelectedListener,OnClickListener{

	
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
	
	int selectedDistId;
	int selectedMandalId;
	int selectedVillageId;
	
	Button ttdSearch;
	String distName, mandalName, villageName,catName;
	
	private TtdSearchTemplateHandler ttdSearchTemplateHandler;
	
	Bundle saveStateBundle;
	View view;
	boolean isLoadRequired = false;
	int distPosition = -1;
	int mandalPosition = -1;
	int villagePosition = -1;
	int catPosition = -1;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			ttdSearchTemplateHandler = (TtdSearchTemplateHandler) getActivity();
		}catch(ClassCastException ex){
			Log.d("TtdProcessFragment : " , getActivity().getClass().getSimpleName() + 
					" not implementd " + TtdSearchTemplateHandler.class.getSimpleName());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//onMyStateRestored(savedInstanceState);
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
		
		outState.putSerializable("DIST_POSITION", this.distPosition);
		outState.putSerializable("MAND_POSITION", this.mandalPosition);
		outState.putSerializable("VILLAGE_POSITION", this.villagePosition);
		
		outState.putSerializable("CAT_DISPLAY", this.catName);
		outState.putSerializable("CAT_POSITION", this.catPosition);
	}
	
	private void onMyStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState != null){
			districtDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("DIST_LIST");
			mandalDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("MAND_LIST");
			villageDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("VILLAGE_LIST");
			
			this.distName =  (String) savedInstanceState.getSerializable("DIST_DISPLAY");
			this.mandalName =  (String) savedInstanceState.getSerializable("MAND_DISPLAY");
			this.villageName =  (String) savedInstanceState.getSerializable("VILLAGE_DISPLAY");
			this.catName =  (String) savedInstanceState.getSerializable("CAT_DISPLAY");
			
			this.distPosition = (Integer) savedInstanceState.getSerializable("DIST_POSITION");
			this.mandalPosition = (Integer) savedInstanceState.getSerializable("MAND_POSITION");
			this.villagePosition = (Integer) savedInstanceState.getSerializable("VILLAGE_POSITION");
			this.catPosition = (Integer) savedInstanceState.getSerializable("CAT_POSITION");
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		if(this.districtDataList != null){
			this.setDistrictData(this.districtDataList);
			this.isLoadRequired = false;
			this.ttdDistrict.setSelection(distPosition,false);
		}else{
			this.isLoadRequired = true;
		}
		if(this.mandalDataList != null){
			this.setMandalData(this.mandalDataList);
			this.isLoadRequired = false;
			this.ttdMandal.setSelection(mandalPosition,false);
		}else{
			this.isLoadRequired = true;
		}
		if(this.villageDataList != null){
			this.setVillageData(villageDataList);
			//this.isLoadRequired = true;
			this.ttdVillage.setSelection(villagePosition,false);
			this.ttdCatetory.setSelection(catPosition,false);
		}else{
			this.isLoadRequired = true;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		view = inflater.inflate(R.layout.ttd_list_info_fragment, container,false);
		ttdCatetory = (Spinner) view.findViewById(R.id.ttd_categoryId);//new SpinnerHelper( view.findViewById(R.id.ttd_categoryId));
		ttdCatetory.setOnItemSelectedListener(this);
		ttdCatetory.setPrompt("Select District");
		catAdapter = ArrayAdapter.createFromResource(context,  R.array.ttd_category, android.R.layout.simple_spinner_item);
		// Drop down layout style - list view with radio button
		catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ttdCatetory.setAdapter(catAdapter);
		//district spinner
		ttdDistrict = (Spinner) view.findViewById(R.id.ttd_districtId);//new SpinnerHelper( view.findViewById(R.id.ttd_districtId));
		ttdDistrict.setOnItemSelectedListener(this);
		if(this.districtDataList == null){
			//this.isLoadRequired = true;
			TtdDistrictTask dtask = new TtdDistrictTask(TtdProcessFragment.this, TtdTypeEnum.DISTRICT.toString());
			try{
				dtask.execute("all");
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}
		//district spinner ends here
		//Mandal
			ttdMandal = (Spinner) view.findViewById(R.id.ttd_mandalId);//new SpinnerHelper( view.findViewById(R.id.ttd_mandalId));
			ttdMandal.setOnItemSelectedListener(this);
			ttdMandal.setPrompt("Select Mandal");
		//end Mandal
			
		//Village
		ttdVillage = (Spinner) view.findViewById(R.id.ttd_villageId);//new SpinnerHelper( view.findViewById(R.id.ttd_villageId));
		ttdVillage.setOnItemSelectedListener(this);
		ttdVillage.setPrompt("Select Village");
		//end Village
		
		//search
		ttdSearch = (Button)view.findViewById(R.id.ttd_search);
		ttdSearch.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		
		String itemName = "";  
		if(R.id.ttd_districtId == parent.getId()){
			if(this.distPosition != -1 && position == 0){
				return;
			}
			//if(this.distPosition != position){
				this.distPosition = position;
				selectedDistId = ((SpinnerObject) parent.getSelectedItem()).getId();
				this.distName = parent.getItemAtPosition(position).toString();
				//Toast.makeText(parent.getContext(), "Selected: " + itemId +"," + itemName, Toast.LENGTH_LONG).show();
				TtdDistrictTask dtask = new TtdDistrictTask(TtdProcessFragment.this, TtdTypeEnum.MANDAL.toString());
				try{
					dtask.execute(selectedDistId +"");
				}catch (Exception e)
		        {
					dtask.cancel(true);
		            alert ("cancelled....");
		        }
			//}//else{
				this.setMandalData(this.mandalDataList);
				this.isLoadRequired = false;
				//this.distName = parent.getItemAtPosition(this.distPosition).toString();
			//}
		}
		if(R.id.ttd_mandalId == parent.getId()){
			//if(this.mandalPosition != position){
				this.mandalPosition = position;
				selectedMandalId = ((SpinnerObject) parent.getSelectedItem()).getId();
				this.mandalName = parent.getItemAtPosition(position).toString();
				//Toast.makeText(parent.getContext(), "Selected: " + itemId +"," + itemName, Toast.LENGTH_LONG).show();
				TtdDistrictTask dtask = new TtdDistrictTask(TtdProcessFragment.this, TtdTypeEnum.VILLAGE.toString());
				try{
					dtask.execute(selectedDistId +"",selectedMandalId +"");
				}catch (Exception e)
		        {
					dtask.cancel(true);
		            alert ("cancelled....");
		        }
			//}//else{
				this.setVillageData(villageDataList);
				this.isLoadRequired = false;
				//this.mandalName = parent.getItemAtPosition(this.mandalPosition).toString();
			//}
			
		}
		if(R.id.ttd_villageId == parent.getId()){
			//if(this.isLoadRequired){
				this.villagePosition = position;
				this.villageName = parent.getItemAtPosition(position).toString();
			//}else{
				//this.villageName = parent.getItemAtPosition(this.villagePosition).toString();
			//}
		}
		if(R.id.ttd_categoryId == parent.getId()){
			//if(this.isLoadRequired){
				this.catPosition = position;
				this.catName = parent.getItemAtPosition(position).toString();
			//}else{
				//this.catName = parent.getItemAtPosition(this.catPosition).toString();
				//isLoadRequired = true;
			//}
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
		this.ttdDistrict.setAdapter(districtAdapter);
	}
	
	public void setMandalData(ArrayList<SpinnerObject> data) {
		this.mandalDataList = data;
		mandalAdapter = new ArrayAdapter<SpinnerObject>(context, android.R.layout.simple_spinner_item, this.mandalDataList);
		mandalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.ttdMandal.setAdapter(mandalAdapter);
	}
	
	public void setVillageData(ArrayList<SpinnerObject> data) {
		this.villageDataList = data;
		villageAdapter = new ArrayAdapter<SpinnerObject>(context, android.R.layout.simple_spinner_item, this.villageDataList);
		villageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.ttdVillage.setAdapter(villageAdapter);
	}
	
	
	
	interface TtdSearchTemplateHandler{
		void onTtdSearchTemplate(String distName,String mandalName,String villageName,String catName);
	}



	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ttd_search:
			ttdSearchTemplateHandler.onTtdSearchTemplate(distName, mandalName, villageName,catName);
			return;
		}
	}

}*/
