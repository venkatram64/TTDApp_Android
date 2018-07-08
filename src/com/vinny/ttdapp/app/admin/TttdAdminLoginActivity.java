package com.vinny.ttdapp.app.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.vinny.ttdapp.R;
import com.vinny.ttdapp.TtdTempleAddActivity;
import com.vinny.ttdapp.TtdTempleAddFragment;
import com.vinny.ttdapp.util.TtdTempleInfo;
import com.vinny.ttdapp.util.TtdTypeEnum;

public class TttdAdminLoginActivity extends FragmentActivity implements TttdAdminLoginFragment.TtdPendingListHandler
					,TtdTempleApprovalFragment.TtdEditTemplateHandler,TtdTempleApprovalEditDeleteFragment.TtdEditDeleteTemplateHandler{
	
	boolean dualPane;
	FrameLayout adminLandscape;
	Bundle element;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ttd_admin_login_activity);
		adminLandscape = (FrameLayout)findViewById(R.id.ttd_admin_land);
		dualPane = (adminLandscape != null && adminLandscape.getVisibility() == View.VISIBLE);
	}

	@Override
	public void onPendingListHandler() {
		element = new Bundle();
		TtdTempleInfo info = new TtdTempleInfo();
		info.setType(TtdTypeEnum.ADMIN_DISPLAY.toString());
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), info);
		if(dualPane){
			TtdTempleApprovalFragment addFragment = new TtdTempleApprovalFragment();
			addFragment.setArguments(element);
			//addFragment.setRetainInstance(true);//to retain the status...
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.ttd_admin_land, addFragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}else{
			Intent intent = new Intent(this,TtdTempleApprovalActivity.class);
			intent.putExtra(TtdTypeEnum.DISPLAY.toString(), element);
			startActivity(intent);
		}
		
	}
	
	@Override
	public void onTtdEditTemplate(TtdTempleInfo temple) {
		//Toast.makeText(TtdTempleAddActivity.this, "You pressed.... " + temple.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle bundle = new Bundle();
		bundle.putSerializable(TtdTypeEnum.EDIT_DELETE.toString(), temple);
		TtdTempleApprovalEditDeleteFragment editDeleteFragment = new TtdTempleApprovalEditDeleteFragment();
		editDeleteFragment.setArguments(bundle);
		FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
		trx.replace(R.id.ttd_admin_land, editDeleteFragment);
		trx.commit();
	}
	
	
	@Override
	public void onTtdApproveTemplateHandler(TtdTempleInfo info) {
		Toast.makeText(TttdAdminLoginActivity.this, "You pressed....approve " + info.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle element = new Bundle();
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), info);
		TtdTempleApprovalFragment searchFragment = new TtdTempleApprovalFragment();
		searchFragment.setArguments(element);
		//searchFragment.setRetainInstance(true);//to retain the status...
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_admin_land, searchFragment);
		transaction.commit();
		
	}

	@Override
	public void onTtdUpdateTemplateHandler(TtdTempleInfo info) {
		Toast.makeText(TttdAdminLoginActivity.this, "You pressed....edit " + info.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle element = new Bundle();
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), info);
		TtdTempleApprovalFragment searchFragment = new TtdTempleApprovalFragment();
		searchFragment.setArguments(element);
		//searchFragment.setRetainInstance(true);//to retain the status...
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_admin_land, searchFragment);
		transaction.commit();
		
	}

	@Override
	public void onTtdDeleteTemplateHandler(TtdTempleInfo info) {
		Toast.makeText(TttdAdminLoginActivity.this, "You pressed....delete " + info.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle element = new Bundle();
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), info);
		TtdTempleApprovalFragment searchFragment = new TtdTempleApprovalFragment();
		searchFragment.setArguments(element);
		//searchFragment.setRetainInstance(true);//to retain the status...
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_admin_land, searchFragment);
		transaction.commit();
	}

	public void alert (String msg)
    {
        Toast.makeText(TttdAdminLoginActivity.this, msg, Toast.LENGTH_LONG).show();
    }	

}

	
