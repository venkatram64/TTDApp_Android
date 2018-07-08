package com.vinny.ttdapp.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.vinny.ttdapp.R;
import com.vinny.ttdapp.TtdTempleAddFragment;
import com.vinny.ttdapp.TtdTempleSearchFragment;

public class TtdDisplayTask extends AsyncTask<String, Integer, String>{
	private ProgressDialog progDialog;
	private String type;
	
	private Context displayContext;
	private TtdTempleAddFragment displayFragment;
	private List<NameValuePair> paramValues;
	
	private static final String debugTag = "TtdSearchTask";
	
	
	
	public TtdDisplayTask(TtdTempleAddFragment displayFragment,List<NameValuePair> params){
		super();
		this.displayFragment = displayFragment;
		this.paramValues = params;
		this.displayContext = this.displayFragment.getActivity().getApplicationContext();
		
	}
	
	@Override
    protected void onPreExecute() {  
        super.onPreExecute();
        String loadMsg = "";
        if(this.displayFragment != null && this.displayFragment.getActivity() != null){
        	loadMsg = this.displayContext.getResources().getString(R.string.load_update_msg);
        	progDialog = ProgressDialog.show(this.displayFragment.getActivity(), "Add",loadMsg , true, false);
        }
    }

	@Override
	protected String doInBackground(String... params) {
		try {
			this.type = params[0];
        	Log.d(debugTag,"Background:" + Thread.currentThread().getName());
        	JSONParser parser = new JSONParser();
            String result = parser.makeHttpRequest(this.type,params[1],paramValues);
            return result;
        } catch (Exception e) {
            return new String();
        }
	}
	
	@Override
    protected void onPostExecute(String result) 
    {
    	
    	ArrayList<TtdTempleInfo> displaydata = null;
    	if(progDialog != null && this.displayFragment != null && this.displayFragment.getActivity() != null){
    		progDialog.dismiss();
    	}
        if (result.length() == 0 && this.displayFragment != null && this.displayFragment.getActivity() != null) {
        	this.displayFragment.alert ("Unable to find District data. Try again later.");
            return;
        }
        
    try {
        	if(type == TtdTypeEnum.DISPLAY.toString() || type == TtdTypeEnum.SEARCH.toString()
        			|| type.equals(TtdTypeEnum.EDIT_DELETE.toString())
        			|| type.equals(TtdTypeEnum.DELETE.toString())){
        		displaydata = new ArrayList<TtdTempleInfo>();
	        	JSONObject respObj = new JSONObject(result);
				JSONArray data = respObj.getJSONArray("data");
				for(int i=0; i<data.length(); i++) {
					JSONObject d = data.getJSONObject(i);	
					String cat = d.getString("category");
					String templeName = d.getString("templeName");
					String distName = d.getString("distName");
					String vName = d.getString("villageName");
					String mName = d.getString("mandalName");
					String id = d.getString("id");
					String name = d.getString("name");
					String phone = d.getString("phone");
					String email = d.getString("email");
					displaydata.add(new TtdTempleInfo(id,distName,mName,vName,cat,templeName,templeName,name,email,phone,""));	
				}
	        }
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        if((type == TtdTypeEnum.DISPLAY.toString() || type == TtdTypeEnum.SEARCH.toString()
        		|| type.equals(TtdTypeEnum.EDIT_DELETE.toString())
        		|| type.equals(TtdTypeEnum.DELETE.toString()))
        		&& this.displayFragment != null){
        	this.displayFragment.setDisplaydata(displaydata);
        }
             
    }
}
