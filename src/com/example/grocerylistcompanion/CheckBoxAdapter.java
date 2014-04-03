package com.example.grocerylistcompanion;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

@SuppressWarnings("unused")
public class CheckBoxAdapter<T> extends BaseAdapter {
	
	private static final String TAG = "CHECK_BOX_ADAPTER";
	private LayoutInflater layoutInflater;
	//private List<ViewHolder> listViews;
	private List<T> listItems;
	private int resourceId;
	
	public CheckBoxAdapter(Context context, int theResourceId, List<T> theListItems) {
		
		layoutInflater = LayoutInflater.from(context);
		
		listItems = theListItems;
		resourceId = theResourceId;
	}
	
	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {

		if (position > -1 && position < listItems.size()) {
			return listItems.get(position);
		}
		else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final ViewHolder holder;
		
		if (convertView == null) {
			
			convertView = layoutInflater.inflate(resourceId, null);
			holder = new ViewHolder();
		}
		else {
			
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
		holder.textView = (TextView) convertView.findViewById(R.id.textView1);
		holder.textView.setText(this.listItems.get(position).toString());

		convertView.setTag(holder);
		
		
		return convertView;
	}
	
	private class ViewHolder {
		
		public CheckBox checkBox;
		public TextView textView;
	}
}