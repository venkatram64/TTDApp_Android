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
import com.vinny.ttdapp.TtdTempleSearchFragment;

public class TtdSearchTask extends AsyncTask<String, Integer, String>{
	private ProgressDialog progDialog;
	private String type;
	
	private Context searchContext;
	private TtdTempleSearchFragment searchFragment;
	
	private static final String debugTag = "TtdSearchTask";
	
	
	
	public TtdSearchTask(TtdTempleSearchFragment searchFrag,String type){
		super();
		this.searchFragment = searchFrag;
		this.searchContext = this.searchFragment.getActivity().getApplicationContext();
		this.type = type;
	}
	
	@Override
    protected void onPreExecute() {  
        super.onPreExecute();
        String loadMsg = "";
        if(type == TtdTypeEnum.SEARCH.toString() && this.searchFragment != null && this.searchFragment.getActivity() != null){
        	loadMsg = this.searchContext.getResources().getString(R.string.load_msg);
        	progDialog = ProgressDialog.show(this.searchFragment.getActivity(), "Search",loadMsg , true, false);
        }
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
    	
    	ArrayList<TtdSearchObject> searchdata = null;
    	if(progDialog != null && type == TtdTypeEnum.SEARCH.toString() 
    			&& this.searchFragment != null && this.searchFragment.getActivity() != null){
    		progDialog.dismiss();
    	}
        if (result.length() == 0 && type == TtdTypeEnum.SEARCH.toString() 
        		&& this.searchFragment != null && this.searchFragment.getActivity() != null) {
        	this.searchFragment.alert ("Unable to find District data. Try again later.");
            return;
        }
        
        try {
        	if(type == TtdTypeEnum.SEARCH.toString()){
	        	searchdata = new ArrayList<TtdSearchObject>();
	        	JSONObject respObj = new JSONObject(result);
				JSONArray data = respObj.getJSONArray("data");
				for(int i=0; i<data.length(); i++) {
					JSONObject d = data.getJSONObject(i);	
					String cat = d.getString("category");
					String templeName = d.getString("templeName");
					String name = d.getString("name");
					String designation = d.getString("designation");
					String phone = d.getString("phone");
					String email = d.getString("email");
					searchdata.add(new TtdSearchObject(cat,templeName,name,designation,phone,email));	
				}
	        }
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        if(type == TtdTypeEnum.SEARCH.toString() && this.searchFragment != null){
        	this.searchFragment.setSearchData(searchdata);
        }
             
    }
}
