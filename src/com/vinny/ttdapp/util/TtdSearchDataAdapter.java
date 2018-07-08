package com.vinny.ttdapp.util;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vinny.ttdapp.R;
import com.vinny.ttdapp.TtdTempleSearchFragment.TtdSearchViewHolder;


public class TtdSearchDataAdapter extends BaseAdapter{
	private ArrayList<TtdSearchObject> searchdata = null;
	private LayoutInflater layoutInflater;
	public TtdSearchDataAdapter(LayoutInflater layoutInflater,ArrayList<TtdSearchObject> searchdata){
		this.searchdata = searchdata;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount() {
		return searchdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		TtdSearchViewHolder holder;
		if (convertView == null) {
            convertView = layoutInflater.inflate (R.layout.ttd_search_row, parent, false);
            holder = new TtdSearchViewHolder();
            holder.category = (TextView) convertView.findViewById(R.id.ttd_cat);
            holder.templeName = (TextView) convertView.findViewById(R.id.ttd_temple_name);
            holder.name = (TextView) convertView.findViewById(R.id.ttd_name);
            holder.designation = (TextView) convertView.findViewById(R.id.ttd_designation);
            holder.phone = (TextView) convertView.findViewById(R.id.ttd_phone);
            holder.email = (TextView) convertView.findViewById(R.id.ttd_email);
            convertView.setTag(holder);
		}else {
            holder = (TtdSearchViewHolder) convertView.getTag();
        }
		
		TtdSearchObject sd = searchdata.get(pos);
		holder.searchData = sd;
		holder.category.setText(sd.getCategory());
		holder.templeName.setText(sd.getTempleName());
		holder.name.setText(sd.getName());
		holder.designation.setText(sd.getDesignation());
		holder.phone.setText(sd.getPhone());
		holder.email.setText(sd.getEmail());
		return convertView;
	}

}
