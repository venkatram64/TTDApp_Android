package com.vinny.ttdapp.util;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vinny.ttdapp.R;
import com.vinny.ttdapp.TtdTempleAddFragment.TtdDisplayViewHolder;
import com.vinny.ttdapp.TtdTempleSearchFragment.TtdSearchViewHolder;


public class TtdDisplayDataAdapter extends BaseAdapter{
	private ArrayList<TtdTempleInfo> displaydata = null;
	private LayoutInflater layoutInflater;
	public TtdDisplayDataAdapter(LayoutInflater layoutInflater,ArrayList<TtdTempleInfo> displaydata){
		this.displaydata = displaydata;
		this.layoutInflater = layoutInflater;
	}

	@Override
	public int getCount() {
		return displaydata.size();
	}

	@Override
	public Object getItem(int position) {
		return displaydata.get(position);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		TtdDisplayViewHolder holder;
		if (convertView == null) {
            convertView = layoutInflater.inflate (R.layout.ttd_display_row, parent, false);
            holder = new TtdDisplayViewHolder();
            holder.category = (TextView) convertView.findViewById(R.id.ttd_dcat);
            holder.templeName = (TextView) convertView.findViewById(R.id.ttd_dtempl_name);
            holder.name = (TextView) convertView.findViewById(R.id.ttd_dname);
            holder.distName = (TextView) convertView.findViewById(R.id.ttd_ddist);
            holder.mandalName = (TextView) convertView.findViewById(R.id.ttd_dmandal);
            holder.villageName = (TextView) convertView.findViewById(R.id.ttd_dvillage);
            holder.phone = (TextView) convertView.findViewById(R.id.ttd_dphone);
            holder.email = (TextView) convertView.findViewById(R.id.ttd_demail);
            convertView.setTag(holder);
		}else {
            holder = (TtdDisplayViewHolder) convertView.getTag();
        }
		
		TtdTempleInfo sd = displaydata.get(pos);
		holder.displayData = sd;
		holder.category.setText(sd.getCatName());
		holder.templeName.setText(sd.getTempleName());
		holder.name.setText(sd.getName());
		holder.distName.setText(sd.getDistName());
		holder.mandalName.setText(sd.getMandalName());
		holder.villageName.setText(sd.getVillageName());
		holder.phone.setText(sd.getPhone());
		holder.email.setText(sd.getEmail());
		return convertView;
	}

}
