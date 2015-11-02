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
import com.jjoe64.graphview.series.DataPointInterface;
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

public class GraphFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	public 	GraphView graph;
	public LineGraphSeries<DataPoint>  series;
	
	public static final int LTGRAY = -3355444;
	
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	
	public List<String> dates = new ArrayList<String>();
    public List<Integer> steps = new ArrayList<Integer>();

	// TODO: Rename and change types and number of parameters
	public static GraphFragment newInstance(String param1, String param2) {
		GraphFragment fragment = new GraphFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public GraphFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Parse.initialize(LoginActivity.obj, "xnPlNwYZqdMge8t8lFZCb3huaqMTpLMosYJozHYn", "RH9Nqd5gNDb9MrNAQ3jrHZ021SzTBxwMDd6cCgjW");
		
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		
		Log.d("demo", "inside fragment onCreate()");
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
				View view = inflater.inflate(R.layout.fragment_graph, container, false);
				Log.d("demo", "inside fragment onCreateView()");
				return view;
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
			
	    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Walking");
	    	query.whereEqualTo("user", ParseUser.getCurrentUser());
	    	query.addDescendingOrder("date");
	    	query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					Log.d("demonew", " of dates "+arg0.size());
					if (arg1 == null) {
	    	        	
	    	        	for(ParseObject p: arg0)
	    	        	{
	    	        		dates.add((String) p.get("date"));
	    	        		Log.d("demonew", " of dates "+dates);
	    	        		steps.add(p.getInt("steps"));
	    	        	}
	    	        	
	    	        } else {
	    	        	
	    	        	Log.d("demo","Parse Exception");
	    	        }
					
					
					
					if(dates.size() >0)
			    	{
				    	Log.d("demo", "inside fragment retrived no of dates "+dates.size());
				    	Log.d("demo", "inside fragment retrived no of steps "+steps.size());
			
				    	
				    	final String[] _date = dates.toArray(new String[dates.size()]);
				    	final Integer[] _steps = steps.toArray(new Integer[steps.size()]);
				    	
				    	
				    	Log.d("demo", "inside fragment retrived no of _dates "+_date.length);
				    	Log.d("demo", "inside fragment retrived no of _steps "+_steps.length);
				    	
				    	
				    	
				    	List<DataPoint> graphdata1 = new ArrayList<DataPoint>();
				    	
				    	List<DataPoint> graphdata2 = new ArrayList<DataPoint>();
					    
					    for (int i = 0; (i < dates.size())&&(i < steps.size()); i++) 
					    {
				    		int y = Integer.parseInt(_date[i].substring(0, 4));
				    		int m = Integer.parseInt(_date[i].substring(5,7));
				    		int da = Integer.parseInt(_date[i].substring(8));
				    		m-=1;
				    		Calendar cal = Calendar.getInstance();
				    		cal.set(y, m, da);
				    		Date date = cal.getTime();
				    		
				    		graphdata1.add(new DataPoint(date,_steps[i]));
				    		
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
					 		graph.getGridLabelRenderer().setNumHorizontalLabels(2); // (dataPoint.length) only 4 because of the space
					 //
				//	 		// set manual x bounds to have nice steps
					 		graph.getViewport().setMinX(dataPoint[0].getX());
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
