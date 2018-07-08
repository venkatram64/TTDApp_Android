package com.vinny.ttdapp;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vinny.ttdapp.util.TtdSearchDataAdapter;
import com.vinny.ttdapp.util.TtdSearchDisplayEnum;
import com.vinny.ttdapp.util.TtdSearchObject;
import com.vinny.ttdapp.util.TtdSearchTask;
import com.vinny.ttdapp.util.TtdTypeEnum;


public class TtdTempleSearchFragment extends Fragment{
	
	static final String[] FROM = {TtdSearchDisplayEnum.CATEGORY.getName(),TtdSearchDisplayEnum.TEMPLE_NAME.getName(),
		TtdSearchDisplayEnum.NAME.getName(),TtdSearchDisplayEnum.DESIGNATION.getName(),
		TtdSearchDisplayEnum.PHONE.getName(),TtdSearchDisplayEnum.EMAIL.getName()};
	static final int[] TO = {TtdSearchDisplayEnum.CATEGORY.getId(),TtdSearchDisplayEnum.TEMPLE_NAME.getId(),
		TtdSearchDisplayEnum.NAME.getId(),TtdSearchDisplayEnum.DESIGNATION.getId(),
		TtdSearchDisplayEnum.PHONE.getId(),TtdSearchDisplayEnum.EMAIL.getId()};
	
	ListView searchListView;
	Context context;
	ArrayList<TtdSearchObject> searchdata = null;
	TtdSearchDataAdapter searchAdapter;
	LayoutInflater inflater;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		View view = inflater.inflate(R.layout.ttd_temple_search_fragment, container,false);
		this.context = getActivity();
		searchListView = (ListView) view.findViewById(R.id.ttd_temple_search_list);
		Bundle element = this.getArguments();
		String distName = element.getString(TtdTypeEnum.DISTRICT.toString());
		String mandalName = element.getString(TtdTypeEnum.MANDAL.toString());
		String villageName = element.getString(TtdTypeEnum.VILLAGE.toString());
		String catName = element.getString(TtdTypeEnum.CATEGORY.toString());
		TtdSearchTask dtask = new TtdSearchTask(TtdTempleSearchFragment.this, TtdTypeEnum.SEARCH.toString());
		try{
			dtask.execute(catName,distName,mandalName,villageName);
		}catch (Exception e)
        {
			dtask.cancel(true);
            alert ("cancelled....");
        }
		return view;
	}
	
	public void alert (String msg)
    {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
	
	public void setSearchData(ArrayList<TtdSearchObject> searchdata){
		this.searchdata = searchdata;
		searchAdapter = new TtdSearchDataAdapter(inflater, this.searchdata);
		this.searchListView.setAdapter(searchAdapter);
	}

	public static class TtdSearchViewHolder {
        public TextView category, templeName,name,designation,phone,email;
        public TtdSearchObject searchData;
    }

}
