package com.vinny.ttdapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TtdStartingMainActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ttd_main_starting_activity);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ttd_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch(item.getItemId()){
		case R.id.ttd_search:
			intent = new Intent("com.vinny.ttdapp.TTDMAINACTIVITY");
			startActivity(intent);
			break;
			
		case R.id.ttd_register:
			intent = new Intent("com.vinny.ttdapp.TTDREGISTRATIONMAINACTIVITY");
			startActivity(intent);
			break;
			
		case R.id.ttd_admin:
			intent = new Intent("com.vinny.ttdapp.app.admin.TTDADMINLOGINACTIVITY");
			startActivity(intent);
			break;
			
		case R.id.ttd_aboutUs:
			intent = new Intent("com.vinny.ttdapp.TTDABOUTUS");
			startActivity(intent);
			break;
		
		}
		return false;
	}
}
