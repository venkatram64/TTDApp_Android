package com.vinny.ttdapp;

import com.vinny.ttdapp.TtdProcessFragment.TtdSearchTemplateHandler;
import com.vinny.ttdapp.util.TtdTempleInfo;
import com.vinny.ttdapp.util.TtdTypeEnum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class TtdMainActivity extends FragmentActivity implements TtdSearchTemplateHandler{
	
	boolean dualPane;
	FrameLayout searchLandscape;
	Bundle element;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ttd_main);
		searchLandscape = (FrameLayout)findViewById(R.id.ttd_search_land);
		dualPane = (searchLandscape != null && searchLandscape.getVisibility() == View.VISIBLE);
	}
	
	
	@Override
	public void onTtdSearchTemplate(String distName, String mandalName,String villageName,String catName) {
		/*String  text = "dist name: " + distName +",mandal name :" + mandalName + ", village Name: " + villageName + ", category name: " + catName;
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();*/
		element = new Bundle();
		element.putString(TtdTypeEnum.DISTRICT.toString(), distName);
		element.putString(TtdTypeEnum.MANDAL.toString(), mandalName);
		element.putString(TtdTypeEnum.VILLAGE.toString(), villageName);
		element.putString(TtdTypeEnum.CATEGORY.toString(), catName);
		if(dualPane){
			TtdTempleSearchFragment searchFragment = new TtdTempleSearchFragment();
			searchFragment.setArguments(element);
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.ttd_search_land, searchFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}else{
			Intent intent = new Intent(this,TtdTempleSearchActivity.class);
			intent.putExtra(TtdTypeEnum.SEARCH.toString(), element);
			startActivity(intent);
		}
	}
	
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(element != null){
			outState.putBundle("bundle", element);
		}
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			element = savedInstanceState.getBundle("bundle");
			if(element != null){
				String dist = element.getString(TtdTypeEnum.DISTRICT.toString());
				String mandal = element.getString(TtdTypeEnum.MANDAL.toString());
				String village = element.getString(TtdTypeEnum.VILLAGE.toString());
				String cat = element.getString(TtdTypeEnum.CATEGORY.toString());
				onTtdSearchTemplate(dist,mandal,village,cat);
			}
		}
	}
	
	
}
