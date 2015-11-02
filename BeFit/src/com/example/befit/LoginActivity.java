/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */


package com.example.befit;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	
	public static Context obj;
	EditText et1,et2;
    Button b1,b2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Enable Local Datastore.
//		Parse.enableLocalDatastore(this);
		 
		Parse.initialize(this, "xnPlNwYZqdMge8t8lFZCb3huaqMTpLMosYJozHYn", "RH9Nqd5gNDb9MrNAQ3jrHZ021SzTBxwMDd6cCgjW");

		obj = LoginActivity.this;
		et1 = (EditText) findViewById(R.id.editTextEmail);
        et2 = (EditText) findViewById(R.id.editTextPassword);
        b1 = (Button) findViewById(R.id.buttonLogin);
        b2 = (Button) findViewById(R.id.buttonCreateNewAccount);
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNetworkAvailable())
				{
				
					if(et1.getText().toString().equals("") || et2.getText().toString().equals(""))
			        {
			        	Log.d("demo", "EMAIL PASSWORD");
						Toast.makeText(LoginActivity.this, "Enter Email or Password",Toast.LENGTH_SHORT).show();
			        }
					else
			        {
						ParseUser.logInInBackground(et1.getText().toString(), et2.getText().toString(), new LogInCallback() {
							  
							@Override
							public void done(ParseUser user, ParseException arg1) {
								// TODO Auto-generated method stub
								if (user != null) {
								      // Hooray! The user is logged in.
								    	Intent i1 = new Intent(LoginActivity.this, FitnessActivity.class);
								    	startActivity(i1);
								    	finish();
								    	
								    } else {
								      // Signup failed. Look at the ParseException to see what happened.
								    	Log.d("demo",""+arg1.getMessage());
								    	Toast.makeText(LoginActivity.this, "Login not successful",Toast.LENGTH_SHORT).show();
								    	
								    }
								
							}
							});
			        }
			        
				}
				else
					Toast.makeText(LoginActivity.obj, "No Intenet Connection", Toast.LENGTH_LONG).show();
			}
		});
        
        b2.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i2 = new Intent(LoginActivity.this,SignUp.class);
				startActivity(i2);
				finish();
				
			}
        });
		
		
	}
	

	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(ParseUser.getCurrentUser()!= null)
		{
			Intent intent = new Intent(this, FitnessActivity.class);
			startActivity(intent);
			finish();
		}
	}


}
