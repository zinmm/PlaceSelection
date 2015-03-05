package com.zin.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class cityAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> values;
	public cityAdapter(Context context) {
		this.mContext = context;
		values = new ArrayList<String>();
	}
	
	@Override
	public int getCount() {
		return values.size();
	}

	@Override
	public Object getItem(int position) {
		return values.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh= null;
		if(convertView == null) {
			vh = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.listview_item, null);
			vh.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		vh.tv.setText(values.get(position));
		return convertView;
	}
	
	class ViewHolder {
		private TextView tv;
	}
	
	public void addItem(String value) {
		values.add(value);
	}
	
	public void clear() {
		values.clear();
	}
}
