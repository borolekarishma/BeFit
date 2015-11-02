/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */


package com.example.befit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer.GridStyle;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.parse.FindCallback;
import com.parse.Parse;
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

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class WeightGraphFragment extends Fragment {

	
	public 	GraphView graph;
	public LineGraphSeries<DataPoint>  series;
	
	public static final int LTGRAY = -3355444;
	
	public List<String> dates = new ArrayList<String>();
    public List<Integer> weights = new ArrayList<Integer>();
	
	
	public WeightGraphFragment() {
		// Required empty public constructor
	}
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Parse.initialize(LoginActivity.obj, "xnPlNwYZqdMge8t8lFZCb3huaqMTpLMosYJozHYn", "RH9Nqd5gNDb9MrNAQ3jrHZ021SzTBxwMDd6cCgjW");
		
		super.onCreate(savedInstanceState);
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_weight_graph, container,
				false);
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		Log.d("demo", "inside fragment onActivityCreated()");
		graph = (GraphView) getView().findViewById(R.id.graph);
		
		if(ParseUser.getCurrentUser() != null)
		{
			
			Log.d("demo", "inside fragment onActivityCreated() user not equal to null");
			
			Log.d("demonew",ParseUser.getCurrentUser()+"");
			
	    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Weight");
	    	query.whereEqualTo("user", ParseUser.getCurrentUser());
	    	query.addDescendingOrder("date");
	    	query.findInBackground(new FindCallback<ParseObject>() {
	//    	   
				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					Log.d("demonew", " of dates "+arg0.size());
					if (arg1 == null) {
	    	        	
	    	        	for(ParseObject p: arg0)
	    	        	{
	    	        		dates.add((String) p.get("date"));
	    	        		Log.d("demonew", " of dates "+dates);
	    	        		weights.add(p.getInt("weight"));
	    	        	}
	    	        	
	    	        } else {
	    	        	
	    	        	Log.d("demo","Parse Exception");
	    	        }
					
					
					
					if(dates.size() >0)
			    	{
				    	Log.d("demo", "inside fragment retrived no of dates "+dates.size());
				    	Log.d("demo", "inside fragment retrived no of steps "+weights.size());
			
				    	
				    	final String[] _date = dates.toArray(new String[dates.size()]);
				    	final Integer[] _weights = weights.toArray(new Integer[weights.size()]);
				    	
				    	
				    	Log.d("demo", "inside fragment retrived no of _dates "+_date.length);
				    	Log.d("demo", "inside fragment retrived no of _steps "+_weights.length);
				    	
				    	
				    	
				    	List<DataPoint> graphdata1 = new ArrayList<DataPoint>();
				    	
				    	List<DataPoint> graphdata2 = new ArrayList<DataPoint>();
					    
					    for (int i = 0; (i < dates.size())&&(i < weights.size()); i++) 
					    {
				    		
				    		int m = Integer.parseInt(_date[i].substring(0,2));
				    		int da = Integer.parseInt(_date[i].substring(3,5));
				    		int y = Integer.parseInt(_date[i].substring(6));
				    		
				    		m-=1;
				    		Calendar cal = Calendar.getInstance();
				    		cal.set(y, m, da);
				    		Date date = cal.getTime();
				    		
				    		graphdata1.add(new DataPoint(date,_weights[i]));
				    		
					    }
					    
					    for(int j=0,k=(graphdata1.size()-1);j<graphdata1.size() && k>=0;j++,k--)
					    {
					    	graphdata2.add(j, graphdata1.get(k));
					    }
					    
					    Log.d("demo", "inside fragment graphdata list size "+graphdata1.size());
					    
					    Log.d("demo", "inside fragment graphdata list size "+graphdata2.size());
				    	
					    
					    DataPoint[] dataPoint = graphdata2.toArray(new DataPoint[graphdata2.size()]);
					    series = new LineGraphSeries<DataPoint>(dataPoint);
					    graph.addSeries(series);
					    
					    
					    Log.d("demo", "inside fragment dataPoint size "+dataPoint.length);
					    
					    if(dataPoint.length>0)
					    {
					 // set date label formatter
					 		graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(LoginActivity.obj));
					 		if(dataPoint.length>1)
					 			graph.getGridLabelRenderer().setNumHorizontalLabels(2); // (dataPoint.length) only 4 because of the space
					 		else
					 			graph.getGridLabelRenderer().setNumHorizontalLabels(1);
					 		//	 		// set manual x bounds to have nice steps
					 		graph.getViewport().setMinX(dataPoint[0].getX());
					 		if(dataPoint.length>1)
					 			graph.getViewport().setMaxX(dataPoint[(dataPoint.length-1)].getX());
					 		graph.getViewport().setXAxisBoundsManual(true);
					 	    
					 	    
					 	    graph.getGridLabelRenderer().setGridColor(LTGRAY);
					 	    graph.getGridLabelRenderer().setGridStyle(GridStyle.BOTH);
					 	    graph.getGridLabelRenderer().setHorizontalAxisTitleTextSize((float) 1.2);
					    
					    }
					    else
					    {
					    	Log.d("demo", "datapoint is empty");
					    }
				
				    }
			    	
			    	else{	
						Log.d("demonew", "arun");
						
			    	}
					
				}
	    	});
		
		}
		else
			Log.d("demo", "inside fragment onActivityCreated() User is equal to null");
		
	}

}
