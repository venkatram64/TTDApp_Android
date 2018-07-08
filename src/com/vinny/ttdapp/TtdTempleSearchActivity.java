package com.vinny.ttdapp;

import com.vinny.ttdapp.util.TtdTypeEnum;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class TtdTempleSearchActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			finish();
		}
		setContentView(R.layout.ttd_temple_search_activity);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getBundleExtra(TtdTypeEnum.SEARCH.toString());
		TtdTempleSearchFragment searchFragment = new TtdTempleSearchFragment();
		searchFragment.setArguments(bundle);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_temple_search_id, searchFragment);
		transaction.commit();
	}
	

}
