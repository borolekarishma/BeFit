/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */



package com.example.befit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.InitiateMatchResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.*;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder.DeathRecipient;
import android.provider.Settings.System;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



/**
 * This sample demonstrates how to use the Sensors API of the Google Fit platform to find
 * available data sources and to register/unregister listeners to those sources. It also
 * demonstrates how to authenticate a user with Google Play Services.
 */
public class FitnessActivity extends FragmentActivity {
    public static final String TAG = "BasicSensorsApi";
    // [START auth_variable_references]
    private static final int REQUEST_OAUTH = 1;
    private static final String DATE_FORMAT = "yyyy.MM.dd";
    public Calendar cal;
    public String presentDate;
    public String retrivedDate;
    public int check = 0;
    public TextView user;
    public Field field2;
    public int refreshStepCount;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    public Bundle tempInstance;
    public Intent ustep;
    
    private GoogleApiClient mClient = null;
    // [END auth_variable_references]

    // [START mListener_variable_reference]
    // Need to hold a reference to this listener, as it's passed into the "unregister"
    // method in order to stop all sensors from sending data to this listener.
    private OnDataPointListener mListener;
    // [END mListener_variable_reference]
    
    
    public int mInitialNumberOfSteps;
    TextView mStepsTextView;
    private boolean mFirstCount = true;
    
    
    
    
    Button b1;
	FoodData f;
	ExpandableListView expandableList;
	MyExpandableAdapter listAdapter;
	SeekBar sb;
	TextView tv;
	public static int intake, total, cal_burnt;
	public static ArrayList<String> parentItems = new ArrayList<String>();
	public static ArrayList<String> breakfast = new ArrayList<String>();
	public static ArrayList<String> afternoon_snack = new ArrayList<String>();
	public static ArrayList<String> lunch = new ArrayList<String>();
	public static ArrayList<String> eve_snack = new ArrayList<String>();
	public static ArrayList<String> dinner = new ArrayList<String>();
	HashMap<String, ArrayList<String>> listDataChild = new HashMap<String, ArrayList<String>>();

	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fitness);
		tempInstance = savedInstanceState;
		
		
			Parse.initialize(this, "xnPlNwYZqdMge8t8lFZCb3huaqMTpLMosYJozHYn",
					"RH9Nqd5gNDb9MrNAQ3jrHZ021SzTBxwMDd6cCgjW");
			b1 = (Button) findViewById(R.id.button1);
			expandableList = (ExpandableListView) findViewById(R.id.expandableListView1);
	
			
			parentItems = new ArrayList<String>();
			breakfast = new ArrayList<String>();
			afternoon_snack = new ArrayList<String>();
			lunch = new ArrayList<String>();
			eve_snack = new ArrayList<String>();
			dinner = new ArrayList<String>();
			listDataChild = new HashMap<String, ArrayList<String>>();
	
			final String date = new SimpleDateFormat("MM-dd-yyyy")
					.format(new Date());
	
			parentItems.add("Breakfast");
			parentItems.add("Afternoon Snacks");
			parentItems.add("Lunch");
			parentItems.add("Evening Snacks");
			parentItems.add("Dinner");
			final ArrayList<FoodData> fd = new ArrayList<FoodData>();
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Calorie");
			query.whereEqualTo("Date", date);
			query.whereEqualTo("user", ParseUser.getCurrentUser());
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> arg0, ParseException arg1) {
					// TODO Auto-generated method stub
					if (arg1 == null) {
						f = null;
						intake = 0;
						for (ParseObject p : arg0) {
	
							f = new FoodData();
							f.setFooditem(p.getString("FoodItem"));
							f.setCal(p.getInt("Calorie"));
							f.setCategory(p.getString("Category"));
	
							if (f.getCategory().toString().equals("BreakFast")) {
								breakfast.add(f.getFooditem() + " : " + f.cal);
								Log.d("asd1", breakfast.toString());
							} else if (f.category.equals("Afternoon Snack")) {
								afternoon_snack.add(f.getFooditem() + " : " + f.cal);
							} else if (f.category.equals("Lunch")) {
								lunch.add(f.getFooditem() + " : " + f.cal);
							} else if (f.category.equals("Evening Snack")) {
								eve_snack.add(f.getFooditem() + " : " + f.cal);
							} else if (f.category.equals("Dinner")) {
								dinner.add(f.getFooditem() + " : " + f.cal);
							}
							fd.add(f);
	
							intake = intake + f.cal;
						}
					}
					listDataChild.put(parentItems.get(0), breakfast);
					listDataChild.put(parentItems.get(1), afternoon_snack);
					listDataChild.put(parentItems.get(2), lunch);
					listDataChild.put(parentItems.get(3), eve_snack);
					listDataChild.put(parentItems.get(4), dinner);
	
					listAdapter = new MyExpandableAdapter(FitnessActivity.this,
							parentItems, listDataChild);
					expandableList.setAdapter(listAdapter);
				}
	
			});
	
			
			expandableList.setOnGroupClickListener(new OnGroupClickListener() {
	
				@Override
				public boolean onGroupClick(ExpandableListView parent, View v,
						int groupPosition, long id) {
					return false;
				}
			});
	
				
			b1.setOnClickListener(new View.OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(FitnessActivity.this, HomeActivity.class);
					startActivity(i);
					finish();
	
				}
			});
	        
	        user = (TextView) findViewById(R.id.greetUser);
	        cal = Calendar.getInstance();
	        Date now = new Date();
	        cal.setTime(now);
	        long Time = cal.getTimeInMillis();
	        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
	        presentDate = dateFormat.format(Time);
	        
	        mStepsTextView = (TextView) findViewById(R.id.textViewSteps);
	        
	        // [START auth_oncreate_setup_ending]
	
	        
	        user.setText("Hello "+ParseUser.getCurrentUser().getUsername());
	        
	        if (savedInstanceState != null) {
	            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
	        }
	
	        buildFitnessClient();
	        
		}
//    }
    // [END auth_oncreate_setup_ending]

    // [START auth_build_googleapiclient_beginning]
    private void buildFitnessClient() {
        // Create the Google API Client
        mClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.API)
                .useDefaultAccount()
                .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {

                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(TAG, "Connected!!!");
                                // Now you can make calls to the Fitness APIs.
                                // Put application specific code here.
                                // [END auth_build_googleapiclient_beginning]
                                //  What to do? Find some data sources!
                                findFitnessDataSources();
                                // [START auth_build_googleapiclient_ending]
                            }

                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i == ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            // Called whenever the API client fails to connect.
                            @Override
                            public void onConnectionFailed(ConnectionResult result) {
                                Log.i(TAG, "Connection failed. Cause: " + result.toString());
                                if (!result.hasResolution()) {
                                    // Show the localized error dialog
                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
                                            FitnessActivity.this, 0).show();
                                    return;
                                }
                                // The failure has a resolution. Resolve it.
                                // Called typically when the app is not yet authorized, and an
                                // authorization dialog is displayed to the user.
                                if (!authInProgress) {
                                    try {
                                        Log.i(TAG, "Attempting to resolve failed connection");
                                        authInProgress = true;
                                        result.startResolutionForResult(FitnessActivity.this,
                                                REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                        Log.e(TAG,
                                                "Exception while starting resolution activity", e);
                                    }
                                }
                            }
                        }
                )
                .build();
    }
    // [END auth_build_googleapiclient_ending]

    
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            Log.d("Test", "Home button pressed!");
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    
    
    
    
    // [START auth_connection_flow_in_activity_lifecycle_methods]
    @Override
    protected void onStart() {
        super.onStart();
        // Connect to the Fitness API
        mFirstCount = true;
        
        
	    Log.d("demo","onStart() call" );
	    Log.d("demo", "inside onStart() before querying");
	    Log.d("demo", "inside query()");
    	ParseQuery<ParseObject> query = ParseQuery.getQuery("Walking");
	    query.whereEqualTo("user",ParseUser.getCurrentUser());
	    query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				Log.d("demo", "inside done()");
				if (arg1 == null) 
				{
					
					for(ParseObject p:arg0){
						retrivedDate = p.getString("date");
						if(presentDate.equals(retrivedDate))
						{
								mInitialNumberOfSteps = (int) p.get("steps");
								
								Log.d("demo", "inside onSatrt() retrived no of steps "+ mInitialNumberOfSteps);
								break;
						}
						else if(!presentDate.equals(retrivedDate))
							mInitialNumberOfSteps = 0;
						
					}
		        } 
				else 
				{
		        	mInitialNumberOfSteps = 0;
		            Log.d("score", "Error: " + arg1.getMessage());
		        }
				refreshStepCount = mInitialNumberOfSteps;
			}
		});
	    Log.d("demo", "inside onStart() after querying");
	    
	    
	    Log.i(TAG, "Connecting...");
	    try
	    {
	        mClient.connect();
	    }
	    catch(NullPointerException e)
	    {
	        	
	    }
	    return;
    }

    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mClient.isConnecting() && !mClient.isConnected()) {
                    mClient.connect();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }
    // [END auth_connection_flow_in_activity_lifecycle_methods]

    
    private void findFitnessDataSources() {
        // [START find_data_sources]
        Fitness.SensorsApi.findDataSources(mClient, new DataSourcesRequest.Builder()
                .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA)
                .setDataSourceTypes(DataSource.TYPE_DERIVED)
                .build())
                .setResultCallback(new ResultCallback<DataSourcesResult>() {
                    @Override
                    public void onResult(DataSourcesResult dataSourcesResult) {
                        Log.i(TAG, "Result: " + dataSourcesResult.getStatus().toString());
                        Log.d("demo","before for loop");
                        for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                        	Log.d("demo","inside forLoop");
                            Log.i(TAG, "Data source found: " + dataSource.toString());
                            Log.i(TAG, "Data Source type: " + dataSource.getDataType().getName());

                            //Let's register a listener to receive Activity data!
                            if (dataSource.getDataType().equals(DataType.TYPE_STEP_COUNT_DELTA)
                                    && mListener == null) {
                                Log.i(TAG, "Data source for TYPE_STEP_COUNT_DELTA found!  Registering.");
                                registerFitnessDataListener(dataSource,
                                        DataType.TYPE_STEP_COUNT_DELTA);
                            }
                        }
                    }
                });
        // [END find_data_sources]
    }

    /**
     * Register a listener with the Sensors API for the provided {@link DataSource} and
     * {@link DataType} combo.
     */
    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
        // [START register_data_listener]
        mListener = new OnDataPointListener() {
            @Override
            public void onDataPoint(DataPoint dataPoint) {
                for (Field field : dataPoint.getDataType().getFields()) {
                    Value val = dataPoint.getValue(field);
                    Log.i(TAG, "Detected DataPoint field: " + field.getName());
                    Log.i(TAG, "Detected DataPoint value: " + val);
                    field2 = field;
                    Log.d("demo","OnDatapoint "+mInitialNumberOfSteps);
                    updateTextViewWithStepCounter(val.asInt(),field.getName());
                }
            }
        };

        Fitness.SensorsApi.add(
                mClient,
                new SensorRequest.Builder()
                        .setDataSource(dataSource) // Optional but recommended for custom data sets.
                        .setDataType(dataType) // Can't be omitted.
                        .setSamplingRate(10, TimeUnit.SECONDS)
                        .build(),
                mListener)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.i(TAG, "Listener registered!");
                        } else {
                            Log.i(TAG, "Listener not registered.");
                        }
                    }
                });
        // [END register_data_listener]
    }
    
    
    
 // Update the Text Viewer with Counter of Steps..
    private void updateTextViewWithStepCounter(final int numberOfSteps,final String fieldName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), "On Step!", Toast.LENGTH_SHORT).show();

                if(mFirstCount && (numberOfSteps != 0)) {
//                  mInitialNumberOfSteps = numberOfSteps;
                    mFirstCount = false;
                }
                if(mStepsTextView != null || mStepsTextView == null ){
                	mInitialNumberOfSteps += numberOfSteps;
                	mStepsTextView.setText(fieldName.toUpperCase()+" " + String.valueOf(mInitialNumberOfSteps));
//                  mStepsTextView.setText(String.valueOf(numberOfSteps - mInitialNumberOfSteps));
                }
                Log.d("demo", mStepsTextView.getText().toString());
                try
                {
                onStop();
                }
                catch(IllegalStateException e)
                {
                	
                }
                
                
                
            }
        });
    }
    

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fitness, menu);
        return true;
    }

    

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
	        if (id == R.id.action_settings) {
				Intent i = new Intent(FitnessActivity.this, ProfileActivity.class);
				startActivity(i);
				finish();
				return true;
			}
	        else if (id == R.id.action_profile) {
	        	startActivity(new Intent(FitnessActivity.this, DetailActivity.class));
	        	finish();
				return true;
			} 
	        else if(id == R.id.action_statistics)
	        {
	        	startActivity(new Intent(this,ViewActivity.class));
	        	finish();
				return true;
	        }
	        else if(id == R.id.action_refresh)
	        {
	        	
	        	try
	        	{
	        	onStop();
	        	}
	        	catch(IllegalStateException e)
	        	{
	        		
	        	}
	        	try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	try
	        	{
	        	mStepsTextView.setText("STEPS "+ mInitialNumberOfSteps);
	        	 final ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UserData");
	     		query1.whereEqualTo("user", ParseUser.getCurrentUser());
	     		query1.findInBackground(new FindCallback<ParseObject>() {

	     			@Override
	     			public void done(List<ParseObject> arg0,
	     					com.parse.ParseException arg1) {
	     				// TODO Auto-generated method stub
	     				if (arg1 == null) {
	     					for (ParseObject p : arg0) {
	     						total = p.getInt("totalcal");
	     						cal_burnt = (int) (mInitialNumberOfSteps * Double.valueOf(p.getString("factor").toString()));
	     						Log.d("cal1", String.valueOf(cal_burnt)+ " " + mInitialNumberOfSteps);
	     						tv = (TextView) findViewById(R.id.cal);
	     						tv.setText("Total Calorie Intake: " + (intake-cal_burnt) + "/" + total + "\n" + "Calories burnt: " + cal_burnt);
	     	
	     					}
	     				} else {
	     					Log.d("score", "Error: " + arg1.getMessage());
	     				}
	     			}
	     		});
	        	}
	        	catch(NullPointerException e)
	        	{
	        		
	        	}
	        	
	        	return true;
	        }
	        else if (id == R.id.action_logout) {
	        	
	        	 
	        	 if(presentDate.equals(retrivedDate))
	             {
	             	check = 1;
	        		 Log.d("demo", "Inside if on refresh click");
	             	ParseQuery<ParseObject> query = ParseQuery.getQuery("Walking");
	         	    query.whereEqualTo("user",ParseUser.getCurrentUser() );
	         	    query.findInBackground(new FindCallback<ParseObject>() {
	         			
	         			@Override
	         			public void done(List<ParseObject> arg0, ParseException arg1) {
	         				// TODO Auto-generated method stub
	         				Log.d("demo", "Inside done() on logout click");
	         				if (arg1 == null) {
	         					
	         					for(ParseObject p:arg0){
	         						if(presentDate.equals(p.getString("date")))
	         						{
	         							
	         							ParseQuery<ParseObject> query = ParseQuery.getQuery("Walking");
	         							 
	         							// Retrieve the object by id
	         							query.getInBackground(p.getObjectId(), new GetCallback<ParseObject>() {
	         							  public void done(ParseObject gameScore, ParseException e) {
	         							    if (e == null) {
	         							      // Now let's update it with some new data. In this case, only cheatMode and score
	         							      // will get sent to the Parse Cloud. playerName hasn't changed.
	         							    	gameScore.put("steps", mInitialNumberOfSteps);
	         							    	gameScore.saveEventually();
	         							    	Log.d("demo", "user updated");
	         							    }
	         							  }
	         							});
	         							break;
	         						}
	         					}
	         		        } else {
	         		            Log.d("score", "Error: " + arg1.getMessage());
	         		        }
	         			}
	         		});
	
	             }
	             else if(!presentDate.equals(retrivedDate))
	             {
	             	check = 1;
	            	 ParseObject walking = new ParseObject("Walking");
	             	 walking.put("user", ParseUser.getCurrentUser());
	                 walking.put("date", presentDate);
	                 walking.put("steps", mInitialNumberOfSteps);
	                 walking.saveEventually();
	                 Log.d("demo", "user saved");
	             }
	        	 
	        	ParseUser.logOut();
				Intent intent = new Intent(this,LoginActivity.class);
				startActivity(intent);
				finish();
	        	return true;
	        }
	    return super.onOptionsItemSelected(item);
    }

    
    @Override
    protected void onStop() {
        super.onStop();
	        try
	        {
        	if (mClient.isConnected()) {
	            mClient.disconnect();
	        }
	        }
	        catch(NullPointerException e)
	        {
	        	
	        }
        
	        Log.d("demo", "present and retrived dates "+presentDate+" "+retrivedDate);
	        if((presentDate.equals(retrivedDate)) && check == 0)
	        {
	        	Log.d("demo", "Inside if onStop()");
	        	ParseQuery<ParseObject> query = ParseQuery.getQuery("Walking");
	    	    query.whereEqualTo("user",ParseUser.getCurrentUser() );
	    	    query.findInBackground(new FindCallback<ParseObject>() {
	    			
	    			@Override
	    			public void done(List<ParseObject> arg0, ParseException arg1) {
	    				// TODO Auto-generated method stub
	    				Log.d("demo", "Inside done() onStop()");
	    				if (arg1 == null) 
	    				{
	    					
	    					for(ParseObject p:arg0){
	    						if(presentDate.equals(p.getString("date")))
	    						{
	    							
	    							ParseQuery<ParseObject> query = ParseQuery.getQuery("Walking");
	    							 
	    							// Retrieve the object by id
	    							query.getInBackground(p.getObjectId(), new GetCallback<ParseObject>() {
	    							  public void done(ParseObject gameScore, ParseException e) {
	    							    if (e == null) {
	    							      // Now let's update it with some new data. In this case, only cheatMode and score
	    							      // will get sent to the Parse Cloud. playerName hasn't changed.
	    							    	gameScore.put("steps", mInitialNumberOfSteps);
	    							    	gameScore.saveEventually();
	    							    	Log.d("demo", "user updated");
	    							    }
	    							  }
	    							});
	    							break;
	    						}
	    					}
	    		        } 
	    				else 
	    				{
	    		            Log.d("score", "Error: " + arg1.getMessage());
	    		        }
	    			}
	    		});
	
	        }
	        else if((!presentDate.equals(retrivedDate)) && check == 0)
	        {
	        	ParseObject walking = new ParseObject("Walking");
	        	walking.put("user", ParseUser.getCurrentUser());
	            walking.put("date", presentDate);
	            walking.put("steps", mInitialNumberOfSteps);
	            walking.saveEventually();
	            Log.d("demo", "user saved");
	        }
	        
	        mFirstCount = true;
	     return;
    }
    
}