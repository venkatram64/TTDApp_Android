package com.vinny.ttdapp.app.admin;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.vinny.ttdapp.R;
import com.vinny.ttdapp.TtdTempleEditDeleteFragment;
import com.vinny.ttdapp.util.TtdTempleInfo;
import com.vinny.ttdapp.util.TtdTypeEnum;

public class TtdTempleApprovalActivity extends FragmentActivity implements TtdTempleApprovalFragment.TtdEditTemplateHandler
				,TtdTempleApprovalEditDeleteFragment.TtdEditDeleteTemplateHandler{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			finish();
		}
		setContentView(R.layout.ttd_temple_search_activity);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getBundleExtra(TtdTypeEnum.DISPLAY.toString());
		TtdTempleApprovalFragment searchFragment = new TtdTempleApprovalFragment();
		searchFragment.setArguments(bundle);
		//searchFragment.setRetainInstance(true);//to retain the status...
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_temple_search_id, searchFragment);
		transaction.commit();
	}
	
	@Override
	public void onTtdEditTemplate(TtdTempleInfo temple) {
		//Toast.makeText(TtdTempleAddActivity.this, "You pressed.... " + temple.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle bundle = new Bundle();
		bundle.putSerializable(TtdTypeEnum.EDIT_DELETE.toString(), temple);
		TtdTempleApprovalEditDeleteFragment editDeleteFragment = new TtdTempleApprovalEditDeleteFragment();
		editDeleteFragment.setArguments(bundle);
		FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
		trx.replace(R.id.ttd_temple_search_id, editDeleteFragment);
		trx.commit();
	}
	
	@Override
	public void onTtdApproveTemplateHandler(TtdTempleInfo info) {
		Toast.makeText(TtdTempleApprovalActivity.this, "You pressed....approve " + info.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle element = new Bundle();
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), info);
		TtdTempleApprovalFragment searchFragment = new TtdTempleApprovalFragment();
		searchFragment.setArguments(element);
		//searchFragment.setRetainInstance(true);//to retain the status...
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_temple_search_id, searchFragment);
		transaction.commit();
		
	}

	@Override
	public void onTtdUpdateTemplateHandler(TtdTempleInfo info) {
		Toast.makeText(TtdTempleApprovalActivity.this, "You pressed....edit " + info.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle element = new Bundle();
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), info);
		TtdTempleApprovalFragment searchFragment = new TtdTempleApprovalFragment();
		searchFragment.setArguments(element);
		//searchFragment.setRetainInstance(true);//to retain the status...
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_temple_search_id, searchFragment);
		transaction.commit();
		
	}

	@Override
	public void onTtdDeleteTemplateHandler(TtdTempleInfo info) {
		Toast.makeText(TtdTempleApprovalActivity.this, "You pressed....delete " + info.getTempleName(), Toast.LENGTH_SHORT).show();
		Bundle element = new Bundle();
		element.putSerializable(TtdTypeEnum.DISPLAY.toString(), info);
		TtdTempleApprovalFragment searchFragment = new TtdTempleApprovalFragment();
		searchFragment.setArguments(element);
		//searchFragment.setRetainInstance(true);//to retain the status...
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.ttd_temple_search_id, searchFragment);
		transaction.commit();
	}

	public void alert (String msg)
    {
        Toast.makeText(TtdTempleApprovalActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}
