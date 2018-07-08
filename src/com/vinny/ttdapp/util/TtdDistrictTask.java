package com.vinny.ttdapp.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vinny.ttdapp.R;
import com.vinny.ttdapp.TtdProcessFragment;
import com.vinny.ttdapp.TtdTempleSearchFragment;

public class TtdDistrictTask extends AsyncTask<String, Integer, String>{
	
	private ProgressDialog progDialog;
	private Context context;
	private TtdProcessFragment activity;
	private String type;
	
	
	private static final String debugTag = "TtdDistrictTask";
	
	public TtdDistrictTask(TtdProcessFragment activity,String type){
		super();
		this.activity = activity;
		this.context = this.activity.getActivity().getApplicationContext();
		this.type = type;
	}
	
	
	@Override
    protected void onPreExecute() {  
        super.onPreExecute();
        String loadMsg = "";
        if(type == TtdTypeEnum.DISTRICT.toString()){
        	loadMsg = this.context.getResources().getString(R.string.load_dmsg);
        }else if(type == TtdTypeEnum.MANDAL.toString()){
        	loadMsg = this.context.getResources().getString(R.string.load_mmsg);
        }else if(type == TtdTypeEnum.VILLAGE.toString()){
        	loadMsg = this.context.getResources().getString(R.string.load_vmsg);
        }
    	progDialog = ProgressDialog.show(this.activity.getActivity(), "Search",loadMsg , true, false);
    }

	@Override
	protected String doInBackground(String... params) {
		try {
        	Log.d(debugTag,"Background:" + Thread.currentThread().getName());
            String result = TtdDropdownData.downloadFromServer(type,params);
            return result;
        } catch (Exception e) {
            return new String();
        }
	}
	
	@Override
    protected void onPostExecute(String result) 
    {
    	
    	ArrayList<SpinnerObject> districtdata = null;
    	ArrayList<TtdSearchObject> searchdata = null;
    	if(progDialog != null && this.activity != null && this.activity.getActivity() != null){
    		progDialog.dismiss();
    	}
        if (result.length() == 0) {
        	this.activity.alert ("Unable to find District data. Try again later.");
            return;
        }
        
        try {
        	if(type == TtdTypeEnum.DISTRICT.toString()){
        		districtdata = new ArrayList<SpinnerObject>();
				JSONObject respObj = new JSONObject(result);
				JSONArray data = respObj.getJSONArray("data");
				for(int i=0; i<data.length(); i++) {
					JSONObject d = data.getJSONObject(i);	
					String id = d.getString("distId");
					String name = d.getString("distName");
					districtdata.add(new SpinnerObject(Integer.parseInt(id),name));				
				}
	        }else if(type == TtdTypeEnum.MANDAL.toString()){
	        	districtdata = new ArrayList<SpinnerObject>();
	        	JSONObject respObj = new JSONObject(result);
				JSONArray data = respObj.getJSONArray("data");
				for(int i=0; i<data.length(); i++) {
					JSONObject d = data.getJSONObject(i);	
					String id = d.getString("mandalId");
					String name = d.getString("mandalName");
					districtdata.add(new SpinnerObject(Integer.parseInt(id),name));	
				}
	        }else if(type == TtdTypeEnum.VILLAGE.toString()){
	        	districtdata = new ArrayList<SpinnerObject>();
	        	JSONObject respObj = new JSONObject(result);
				JSONArray data = respObj.getJSONArray("data");
				for(int i=0; i<data.length(); i++) {
					JSONObject d = data.getJSONObject(i);	
					String id = d.getString("villageId");
					String name = d.getString("villageName");
					districtdata.add(new SpinnerObject(Integer.parseInt(id),name));	
				}
	        }
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        if(type == TtdTypeEnum.DISTRICT.toString()){
        	this.activity.setDistrictData(districtdata);
        }else if(type == TtdTypeEnum.MANDAL.toString()){
        	this.activity.setMandalData(districtdata);
        }else if(type == TtdTypeEnum.VILLAGE.toString()){
        	this.activity.setVillageData(districtdata);
        }
             
    }
}
