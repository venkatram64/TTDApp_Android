package com.vinny.ttdapp.app.admin;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vinny.ttdapp.R;
import com.vinny.ttdapp.admin.util.TtdApprovalFetchTask;
import com.vinny.ttdapp.admin.util.TtdDisplayTask;
import com.vinny.ttdapp.util.SpinnerObject;
import com.vinny.ttdapp.util.TtdDisplayDataAdapter;
import com.vinny.ttdapp.util.TtdTempleInfo;
import com.vinny.ttdapp.util.TtdTypeEnum;

public class TtdTempleApprovalFragment extends Fragment implements OnItemClickListener{
	
	LayoutInflater inflater;
	ListView displayListView;
	Context context;
	ArrayList<TtdTempleInfo> displaydata = null;
	TtdDisplayDataAdapter displayAdapter;
	TtdTempleInfo info = null;
	TtdEditTemplateHandler ttdEditTemplateHandler;
	
	ArrayList<SpinnerObject> districtDataList;
	ArrayList<SpinnerObject> mandalDataList;
	ArrayList<SpinnerObject> villageDataList;
	ArrayList<SpinnerObject> templeDataList;
	volatile boolean flag;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			ttdEditTemplateHandler = (TtdEditTemplateHandler)getActivity();
		} catch (ClassCastException ex) {
			Log.d("TtdTempleAddFragment : " , getActivity().getClass().getSimpleName() + 
					" not implementd " + TtdTempleApprovalFragment.class.getSimpleName());
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		this.context = getActivity();
		View view = inflater.inflate(R.layout.ttd_temple_display_fragment, container,false);
		displayListView = (ListView) view.findViewById(R.id.ttd_temple_display_list);
		Bundle element = this.getArguments();
		info = (TtdTempleInfo) element.getSerializable(TtdTypeEnum.DISPLAY.toString());
		
		return view;
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("DIST_LIST", districtDataList);
		outState.putSerializable("MAND_LIST", mandalDataList);
		outState.putSerializable("VILLAGE_LIST", villageDataList);
		outState.putSerializable("TEMPLE_LIST", templeDataList);
		outState.putSerializable("DISPLAY_DATA_LIST", displaydata);
		
	}
	
	private void onMyStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState != null){
			districtDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("DIST_LIST");
			mandalDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("MAND_LIST");
			villageDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("VILLAGE_LIST");
			templeDataList = (ArrayList<SpinnerObject>) savedInstanceState.getSerializable("TEMPLE_LIST");
			
			displaydata = (ArrayList<TtdTempleInfo>) savedInstanceState.getSerializable("DISPLAY_DATA_LIST");
			
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		// Building Parameters
		if(displaydata != null){
			setDisplaydata(displaydata);
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String id = info.getId() == null ? "0" : info.getId();
		
		TtdDisplayTask dtask = null;
		if(info.getType().equals(TtdTypeEnum.DISPLAY.toString())){
			try{
				params.add(new BasicNameValuePair("id",id));
				params.add(new BasicNameValuePair("catName", info.getCatName()));
				params.add(new BasicNameValuePair("distName", info.getDistName()));
				params.add(new BasicNameValuePair("mandalName", info.getMandalName()));
				params.add(new BasicNameValuePair("villageName", info.getVillageName()));
				params.add(new BasicNameValuePair("templeName", info.getTempleName()));
				params.add(new BasicNameValuePair("name", info.getName()));
				params.add(new BasicNameValuePair("email", info.getEmail()));
				params.add(new BasicNameValuePair("phone", info.getPhone()));
				dtask = new TtdDisplayTask(TtdTempleApprovalFragment.this, params);
				dtask.execute(TtdTypeEnum.DISPLAY.toString(),TtdTypeEnum.POST.toString());
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}
		if(info.getType().equals(TtdTypeEnum.SEARCH.toString())){
			try{
				params.add(new BasicNameValuePair("id",id));
				params.add(new BasicNameValuePair("catName", info.getCatName()));
				params.add(new BasicNameValuePair("distName", info.getDistName()));
				params.add(new BasicNameValuePair("mandalName", info.getMandalName()));
				params.add(new BasicNameValuePair("villageName", info.getVillageName()));
				params.add(new BasicNameValuePair("phone", info.getPhone()));
				dtask = new TtdDisplayTask(TtdTempleApprovalFragment.this, params);
				dtask.execute(TtdTypeEnum.SEARCH.toString(),TtdTypeEnum.POST.toString());
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}
		if(info.getType().equals(TtdTypeEnum.EDIT_DELETE.toString())){
			try{
				params.add(new BasicNameValuePair("id",info.getId()));
				params.add(new BasicNameValuePair("catName", info.getCatName()));
				params.add(new BasicNameValuePair("distName", info.getDistName()));
				params.add(new BasicNameValuePair("mandalName", info.getMandalName()));
				params.add(new BasicNameValuePair("villageName", info.getVillageName()));
				params.add(new BasicNameValuePair("templeName", info.getTempleName()));
				params.add(new BasicNameValuePair("name", info.getName()));
				params.add(new BasicNameValuePair("email", info.getEmail()));
				params.add(new BasicNameValuePair("phone", info.getPhone()));
				dtask =  new TtdDisplayTask(TtdTempleApprovalFragment.this, params);
				dtask.execute(TtdTypeEnum.EDIT_DELETE.toString(),TtdTypeEnum.POST.toString());
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}
		if(info.getType().equals(TtdTypeEnum.DELETE.toString())){
			try{
				params.add(new BasicNameValuePair("id",info.getId()));
				params.add(new BasicNameValuePair("catName", info.getCatName()));
				params.add(new BasicNameValuePair("distName", info.getDistName()));
				params.add(new BasicNameValuePair("mandalName", info.getMandalName()));
				params.add(new BasicNameValuePair("villageName", info.getVillageName()));
				params.add(new BasicNameValuePair("phone", info.getPhone()));
				dtask = new TtdDisplayTask(TtdTempleApprovalFragment.this, params);
				dtask.execute(TtdTypeEnum.DELETE.toString(),TtdTypeEnum.POST.toString());
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}
		if(info.getType().equals(TtdTypeEnum.ADMIN_DISPLAY.toString())){
			try{
				
				dtask = new TtdDisplayTask(TtdTempleApprovalFragment.this, null);
				dtask.execute(TtdTypeEnum.DISPLAY.toString(),TtdTypeEnum.GET.toString());
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}
		if(info.getType().equals(TtdTypeEnum.APPROVE.toString())){
			try{
				
				dtask = new TtdDisplayTask(TtdTempleApprovalFragment.this, null);
				params.add(new BasicNameValuePair("id",info.getId()));
				params.add(new BasicNameValuePair("catName", info.getCatName()));
				params.add(new BasicNameValuePair("distName", info.getDistName()));
				params.add(new BasicNameValuePair("mandalName", info.getMandalName()));
				params.add(new BasicNameValuePair("villageName", info.getVillageName()));
				params.add(new BasicNameValuePair("templeName", info.getTempleName()));
				params.add(new BasicNameValuePair("name", info.getName()));
				params.add(new BasicNameValuePair("email", info.getEmail()));
				params.add(new BasicNameValuePair("phone", info.getPhone()));
				dtask =  new TtdDisplayTask(TtdTempleApprovalFragment.this, params);
				dtask.execute(TtdTypeEnum.APPROVE.toString(),TtdTypeEnum.POST.toString());
			}catch (Exception e)
	        {
				dtask.cancel(true);
	            alert ("cancelled....");
	        }
		}
	}
	
	public void alert (String msg)
    {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
	
	public void setDisplaydata(ArrayList<TtdTempleInfo> displaydata){
		this.displaydata = displaydata;
		displayAdapter = new TtdDisplayDataAdapter(inflater, this.displaydata);
		this.displayListView.setAdapter(displayAdapter);
		displayListView.setOnItemClickListener(this);
	}
	
	public static class TtdDisplayViewHolder {
        public TextView distName,mandalName,villageName,category, templeName,etTempleName,name,phone,email;
        public TtdTempleInfo displayData;
    }
	
	private int getDisplayId(String type,String name){
		if(type.equals(TtdTypeEnum.DISTRICT.toString())){
			for(SpinnerObject s : districtDataList){
				if(s.getValue().equals(name)){
					return s.getId();
				}
			}
		}else if(type.equals(TtdTypeEnum.MANDAL.toString())){
			for(SpinnerObject s : mandalDataList){
				if(s.getValue().equals(name)){
					return s.getId();
				}
			}
		}else if(type.equals(TtdTypeEnum.VILLAGE.toString())){
			for(SpinnerObject s : villageDataList){
				if(s.getValue().equals(name)){
					return s.getId();
				}
			}
		}else if(type.equals(TtdTypeEnum.TEMPLE.toString())){
			for(SpinnerObject s : templeDataList){
				if(s.getValue().equals(name)){
					return s.getId();
				}
			}
		}
		return 0;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TtdTempleInfo templeInfo = (TtdTempleInfo) displayAdapter.getItem(position);
		TtdApprovalFetchTask fetchTask = null;
		int distId = 0;
		int manId = 0;
		int villId = 0;
		if(info.getDistrictDataList() == null || info.getDistrictDataList() != null){
			fetchTask = new TtdApprovalFetchTask(TtdTempleApprovalFragment.this,TtdTypeEnum.DISTRICT.toString());
			try{
				fetchTask.execute("all");
				fetchTask.get();
			}catch (Exception e)
	        {
				fetchTask.cancel(true);
	            alert ("cancelled....");
	        }
			templeInfo.setDistrictDataList(this.districtDataList);
		}/*else{
			templeInfo.setDistrictDataList(info.getDistrictDataList());
		}*/
		
		
		if(info.getMandalDataList() == null || info.getMandalDataList() != null){
			distId = getDisplayId(TtdTypeEnum.DISTRICT.toString(),templeInfo.getDistName());
			fetchTask = new TtdApprovalFetchTask(TtdTempleApprovalFragment.this,TtdTypeEnum.MANDAL.toString());
			try{
				fetchTask.execute(distId +"");
				fetchTask.get();
			}catch (Exception e)
	        {
				fetchTask.cancel(true);
	            alert ("cancelled....");
	        }
			templeInfo.setMandalDataList(this.mandalDataList);
		}/*else{
			templeInfo.setDistrictDataList(info.getMandalDataList());
		}*/
		
		if(info.getVillageDataList() == null || info.getVillageDataList() != null){
			manId = getDisplayId(TtdTypeEnum.MANDAL.toString(),templeInfo.getMandalName());
			fetchTask = new TtdApprovalFetchTask(TtdTempleApprovalFragment.this,TtdTypeEnum.VILLAGE.toString());
			try{
				fetchTask.execute(distId +"",manId+"");
				fetchTask.get();
			}catch (Exception e)
	        {
				fetchTask.cancel(true);
	            alert ("cancelled....");
	        }
			templeInfo.setVillageDataList(this.villageDataList);
		}/*else{
			templeInfo.setDistrictDataList(info.getVillageDataList());
		}*/
		
		if(info.getTempleDataList() == null || info.getTempleDataList() != null){
			villId = getDisplayId(TtdTypeEnum.VILLAGE.toString(),templeInfo.getVillageName());
			fetchTask = new TtdApprovalFetchTask(TtdTempleApprovalFragment.this,TtdTypeEnum.SEARCH.toString());
			try{
				fetchTask.execute(info.getCatName() + "",distId +"",manId+"",villId + "");
				fetchTask.get();
			}catch (Exception e)
	        {
				fetchTask.cancel(true);
	            alert ("cancelled....");
	        }
			templeInfo.setTempleDataList(this.templeDataList );
		}/*else{
			templeInfo.setDistrictDataList(info.getTempleDataList());
		}*/
		
		templeInfo.setType(TtdTypeEnum.DISPLAY.toString());
		//templeInfo.setId(info.getId());
		ttdEditTemplateHandler.onTtdEditTemplate(templeInfo);
	}
	
	interface TtdEditTemplateHandler{
		void onTtdEditTemplate(TtdTempleInfo temple);
	}
	

	public ArrayList<SpinnerObject> getDistrictDataList() {
		return districtDataList;
	}
	public void setDistrictDataList(ArrayList<SpinnerObject> districtDataList) {
		this.districtDataList = districtDataList;
		this.flag = true;
	}
	public ArrayList<SpinnerObject> getMandalDataList() {
		return mandalDataList;
	}
	public void setMandalDataList(ArrayList<SpinnerObject> mandalDataList) {
		this.mandalDataList = mandalDataList;
		this.flag = true;
	}
	public ArrayList<SpinnerObject> getVillageDataList() {
		return villageDataList;
	}
	public void setVillageDataList(ArrayList<SpinnerObject> villageDataList) {
		this.villageDataList = villageDataList;
		this.flag = true;
	}
	public ArrayList<SpinnerObject> getTempleDataList() {
		return templeDataList;
	}
	public void setTempleDataList(ArrayList<SpinnerObject> templeDataList) {
		this.templeDataList = templeDataList;
		this.flag = true;
	}

}
