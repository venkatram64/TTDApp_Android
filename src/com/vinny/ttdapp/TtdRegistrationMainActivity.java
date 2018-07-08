package com.vinny.ttdapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.vinny.ttdapp.util.TtdTempleInfo;
import com.vinny.ttdapp.util.TtdTypeEnum;

public class TtdRegistrationMainActivity extends FragmentActivity implements TtdRegistrationFragment.TtdAddTemplateHandler,
						TtdTempleEditDeleteFragment.TtdEditDeleteTemplateHandler,TtdTempleAddFragment.TtdEditTemplateHandler{
	
	FrameLayout searchLandscape;
	boolean dualPane;
	Bundle element;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ttd_main_reg);
		searchLandscape = (FrameLayout)findViewById(R.id.ttd_regi_land);
		dualPane = (searchLandscape != null && searchLandscape.getVisibility() == View.VISIBLE);
		
	}

	@Override
	public void onTtdAddTemplate(TtdTempleInfo temple) {
		element = new Bundle();
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), temple);
		if(dualPane){
			TtdTempleAddFragment addFragment = new TtdTempleAddFragment();
			addFragment.setArguments(element);
			//addFragment.setRetainInstance(true);//to retain the status...
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.ttd_regi_land, addFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}else{
			Intent intent = new Intent(this,TtdTempleAddActivity.class);
			intent.putExtra(TtdTypeEnum.DISPLAY.toString(), element);
			startActivity(intent);
		}
	}
	
	@Override
	public void onTtdEditTemplate(TtdTempleInfo temple) {
		Toast.makeText(TtdRegistrationMainActivity.this, "You pressed(Non Dual) " + temple.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle bundle = new Bundle();
		bundle.putSerializable(TtdTypeEnum.EDIT_DELETE.toString(), temple);
		TtdTempleEditDeleteFragment editDeleteFragment = new TtdTempleEditDeleteFragment();
		editDeleteFragment.setArguments(bundle);
		FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
		trx.replace(R.id.ttd_regi_land, editDeleteFragment);
		trx.commit();
	}
	
	@Override
	public void onTtdUpdateTemplateHandler(TtdTempleInfo info) {
		Toast.makeText(TtdRegistrationMainActivity.this, "You pressed....edit " + info.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle element = new Bundle();
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), info);
		TtdTempleAddFragment searchFragment = new TtdTempleAddFragment();
		searchFragment.setArguments(element);
		//searchFragment.setRetainInstance(true);//to retain the status...
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_regi_land, searchFragment);
		transaction.commit();
		
	}

	@Override
	public void onTtdDeleteTemplateHandler(TtdTempleInfo info) {
		Toast.makeText(TtdRegistrationMainActivity.this, "You pressed....delete " + info.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle element = new Bundle();
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), info);
		TtdTempleAddFragment searchFragment = new TtdTempleAddFragment();
		searchFragment.setArguments(element);
		//searchFragment.setRetainInstance(true);//to retain the status...
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_regi_land, searchFragment);
		transaction.commit();
	}
	
}
