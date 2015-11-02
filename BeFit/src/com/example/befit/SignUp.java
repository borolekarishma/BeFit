/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */



package com.example.befit;

import com.parse.Parse;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {

	
	Button b1,b2;
	EditText et1,et2,et3,et4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		// Enable Local Datastore.
//        Parse.enableLocalDatastore(this);
         
		Parse.initialize(this, "xnPlNwYZqdMge8t8lFZCb3huaqMTpLMosYJozHYn", "RH9Nqd5gNDb9MrNAQ3jrHZ021SzTBxwMDd6cCgjW");
		
		b1 = (Button) findViewById(R.id.buttonSignup);
		b2 = (Button) findViewById(R.id.buttonCancel);
		et1 = (EditText) findViewById(R.id.editTextUserName);
		et2 = (EditText) findViewById(R.id.editTextEmail);
		et3 = (EditText) findViewById(R.id.editTextPassword);
		et4 = (EditText) findViewById(R.id.editTextPasswordConfirm);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNetworkAvailable())
				{
					if(et1.getText().toString().equals("")||et2.getText().toString().equals("")||et3.getText().toString().equals(""))
						Toast.makeText(SignUp.this, "Enter values", Toast.LENGTH_SHORT).show();
					else
					{
						ParseUser user = new ParseUser();
						user.setUsername(et1.getText().toString());
						if(et3.getText().toString().equals(et4.getText().toString()))
							user.setPassword(et3.getText().toString());
						else
							Toast.makeText(SignUp.this, "Enter correct password", Toast.LENGTH_SHORT).show();
						user.setEmail(et2.getText().toString());
						 
						// other fields can be set just like with ParseObject
						user.put("phone", "650-253-0000");
						 
						user.signUpInBackground(new SignUpCallback() {
						 
	
						@Override
						public void done(com.parse.ParseException e) {
							// TODO Auto-generated method stub
							if (e == null) {
							      // Hooray! Let them use the app now.
								Toast.makeText(SignUp.this, "Login Successful", Toast.LENGTH_LONG).show();
								Intent intent = new Intent(SignUp.this, ProfileActivity.class);
								startActivity(intent);
								finish();
								
							} else {
								Log.d("demo", "Exception");
							      // Sign up didn't succeed. Look at the ParseException
							      // to figure out what went wrong
							}
						}
						});
					}
				}
				else
					Toast.makeText(LoginActivity.obj, "No Intenet Connection", Toast.LENGTH_LONG).show();
				
			}
		});
		
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignUp.this, LoginActivity.class);
				startActivity(i);
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

}
