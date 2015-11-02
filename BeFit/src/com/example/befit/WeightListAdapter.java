/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */


package com.example.befit;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class WeightListAdapter extends ArrayAdapter<WeightStats> {
	
	
	Context mContext;
	int mResource;
	List<WeightStats> mdata;
	
	public WeightListAdapter(Context context, int resource,
			List<WeightStats> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mResource = resource;
		this.mdata = objects;
		Log.d("demo", "Inside ListAdapter objects are: "+objects.toString());
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(mResource, parent, false);
		}
		
		WeightStats stat = mdata.get(position);
		
		
		TextView datesView = (TextView) convertView.findViewById(R.id.textViewDate);
		
		datesView.setText(stat.getDate());
		Log.d("demo",stat.getDate() );
		
		TextView weightsView = (TextView) convertView.findViewById(R.id.textViewStep);
		
		try{
			weightsView.setText(stat.getWeight()+"");
		}catch( Exception e)
		{
			
		}
		Log.d("demo",stat.getWeight()+"");
		
		return convertView;
		
	}
	

}
