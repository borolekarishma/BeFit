/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */


package com.example.befit;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class WeightListViewFragment extends Fragment {

	
	ListView listView;
	List<String> dates;
	List<Integer> weights;
	WeightStats stats;
	List<WeightStats> statsList1,statsList2;
	WeightListAdapter adapter;
	
	
	
	
	public WeightListViewFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_weight_list_view, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		listView = (ListView) getActivity().findViewById(R.id.listView1);
		dates = new ArrayList<String>();
		weights = new ArrayList<Integer>();
		
		statsList1 = new ArrayList<WeightStats>();
		statsList2 = new ArrayList<WeightStats>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Weight");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.addDescendingOrder("date");
		query.findInBackground(new FindCallback<ParseObject>() {
		   
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				Log.d("demo","Parse Objects size "+arg0.size());
				if (arg1 == null) {
		            Log.d("score", "Retrieved " + arg0.size() + " scores");
		            for(ParseObject p : arg0)
		            {
		            	Log.d("demonew",p.getString("date")+" "+p.get("steps"));
		            	stats = new WeightStats();
		            	dates.add(p.getString("date"));
		            	weights.add(p.getInt("weight"));
		            	stats.setDate(p.getString("date"));
		            	stats.setWeight(p.getInt("weight"));
		            	Log.d("demo", stats.toString());
		            	if(!statsList1.contains(stats))
		            	{
		            		statsList1.add(stats);
		            	}
		            	Log.d("demo", statsList1.toString());
		            }
		            
		        } else {
		            Log.d("score", "Error: " + arg1.getMessage());
		        }
				
				for(int i=0,j=(statsList1.size()-1);i<statsList1.size() && j>=0;i++,j--)
				{
					statsList2.add(i,statsList1.get(j));
				}
				
				Log.d("demo","dates "+dates+"");
				Log.d("demo","weights "+weights+"");
				Log.d("demo","StatsList1 "+statsList1+"");
				Log.d("demo","StatsList2 "+statsList1+"");
				
				adapter = new WeightListAdapter(getActivity(), R.layout.list_view_row, statsList2);
				try
				{
					listView.setAdapter(adapter);
				}
				catch(NullPointerException e)
				{
					
				}
				adapter.setNotifyOnChange(true);
				
			}
		});
		
	}
}
