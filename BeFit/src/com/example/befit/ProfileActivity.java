/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */


package com.example.befit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ProfileActivity extends Activity {

	Spinner spinner, spinner1, spinner2, spinner3;
	TextView ht_ft, ht_in;
	EditText wght, ft, in, age1, hr1, min1;
	int kgs, cms;
	double active;
	Button save;
	public static UserInfo user = new UserInfo();
	String weight_unit = "kgs", height_unit = "ft", sex = "female";
	int bmi = 0, totalcal=0;
	double inch, weight, bmr, factor;


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (ft.getText().toString().equals("")
				|| in.getText().toString().equals("")
				|| wght.getText().toString().equals("")
				|| age1.getText().toString().equals("")
				|| hr1.getText().toString().equals("")
				|| min1.getText().toString().equals("")) {

			Toast.makeText(ProfileActivity.this,
					"Enter all the details", Toast.LENGTH_LONG).show();
		}
		Intent i = new Intent(ProfileActivity.this, FitnessActivity.class);
		startActivity(i);
		finish();
		super.onBackPressed();
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		Parse.initialize(this, "xnPlNwYZqdMge8t8lFZCb3huaqMTpLMosYJozHYn",
				"RH9Nqd5gNDb9MrNAQ3jrHZ021SzTBxwMDd6cCgjW");

		ht_ft = (TextView) findViewById(R.id.textView4);
		ht_in = (TextView) findViewById(R.id.textView5);

		wght = (EditText) findViewById(R.id.weight);
		ft = (EditText) findViewById(R.id.feet);
		in = (EditText) findViewById(R.id.inch);
		age1 = (EditText) findViewById(R.id.age);
		hr1 = (EditText) findViewById(R.id.hr);
		min1 = (EditText) findViewById(R.id.min);

		in.setText("0");

		final ParseQuery<ParseObject> query = ParseQuery.getQuery("UserData");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				if (arg1 == null) {
					for (ParseObject p : arg0) {
						wght.setText(p.get("weight").toString());
						age1.setText(p.get("age").toString());
						ft.setText(p.get("height").toString());
						hr1.setText("1");
						min1.setText("00");
						user.setHeight_cm(ft.getText().toString());
						user.setAge_int(age1.getText().toString());
						user.setHr(hr1.getText().toString());
						user.setMin(min1.getText().toString());
						if(p.getString("sex").equals("male"))
						{
						spinner2.setSelection(1);
						}
						user.setSex(p.getString("sex"));
						user.setFactor(Double.valueOf(p.getString("factor").toString()));
						user.setActive(Double.parseDouble(p.getString("active")));
					}
				} else {
					Log.d("score", "Error: " + arg1.getMessage());
				}
			}
		});

		spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.weight_units,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (spinner.getSelectedItem().equals("Kgs")) {
					weight_unit = "kgs";
				} else {
					weight_unit = "lbs";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		spinner1 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.height_units,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (spinner1.getSelectedItem().equals("Feet and inches")) {
					ht_ft.setText("Feet");
					in.setVisibility(View.VISIBLE);
					ht_in.setVisibility(View.VISIBLE);
					height_unit = "ft";
				} else {
					ht_ft.setText("cms");
					in.setVisibility(View.INVISIBLE);
					ht_in.setVisibility(View.INVISIBLE);
					height_unit = "cm";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		spinner2 = (Spinner) findViewById(R.id.spinner3);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.gender, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);

		
			
		
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (spinner2.getSelectedItem().equals("female")) {
					sex = "female";
				} else {
					sex = "male";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		spinner3 = (Spinner) findViewById(R.id.spinner4);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.workout_schedule,
				android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(adapter3);

		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (spinner3.getSelectedItem().equals("Sedentary")) {
					active = 1.2;
				} else if (spinner3.getSelectedItem().equals("Lightly Active")) {
					active = 1.375;
				} else if (spinner3.getSelectedItem().equals(
						"Moderately Active")) {
					active = 1.55;
				} else if (spinner3.getSelectedItem().equals("Highly Active")) {
					active = 1.725;
				} else if (spinner3.getSelectedItem().equals("Extra Active")) {
					active = 1.9;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		
		
		save = (Button) findViewById(R.id.button1);
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (ft.getText().toString().equals("")
						|| in.getText().toString().equals("")
						|| wght.getText().toString().equals("")
						|| age1.getText().toString().equals("")
						|| hr1.getText().toString().equals("")
						|| min1.getText().toString().equals("")) {

					Toast.makeText(ProfileActivity.this,
							"Enter all the details", Toast.LENGTH_SHORT).show();
				} else {

					if (height_unit.equals("ft")) {
						user.setHeight_ft(ft.getText().toString());
						user.setHeight_in(in.getText().toString());
						cms = (int) (30.48 * Integer.parseInt(ft.getText()
								.toString()));
					} else if (height_unit.equals("cm")
							&& !ft.getText().equals(null)) {
						user.setHeight_cm(ft.getText().toString());
						cms = Integer.parseInt(ft.getText().toString());
					}

					if (weight_unit.equals("kgs")) {
						user.setWeight_kg(wght.getText().toString());
						kgs = Integer.parseInt(wght.getText().toString());

					} else if (weight_unit.equals("lbs")
							&& !ft.getText().toString().equals("")) {
						user.setWeight_lbs(wght.getText().toString());
						kgs = Integer.parseInt(wght.getText().toString()) / 2;
					}

					final String date = new SimpleDateFormat("MM-dd-yyyy")
							.format(new Date());

					user.setSex(sex);
					user.setActive(active);
					user.setAge_int(age1.getText().toString());
					user.setHr(hr1.getText().toString());
					user.setMin(min1.getText().toString());

					if (user.getHeight_ft() == 0) {
						inch = user.getHeight_cm()/2.54;

					} else {
						inch = (12 * user.getHeight_ft()) + user.getHeight_in();
					}

					Log.d("inch", String.valueOf(inch));
					if (user.getWeight_lbs() == 0) {
						weight = 2 * user.getWeight_kg();
					} else {
						weight = user.getWeight_lbs();
					}

					bmi = (int) ((weight / (inch * inch)) * 703);

					if (user.getSex().equals("female")) {
						bmr = 655 + (4.35 * weight) + (4.7 * inch)
								- (4.7 * user.getAge_int());
					} else {
						bmr = 66 + (6.23 * weight) + (12.7 * inch)
								- (6.8 * user.getAge_int());
					}

					factor = (0.57 * (kgs *2) ) / 2200;
					totalcal = (int)( bmr * user.active);
					Log.d("totalcal", String.valueOf(totalcal));
					user.setBmi(bmi);
					user.setTotalcal(totalcal);
					user.setFactor(factor);
					
					Log.d("user", user.toString());
					ParseQuery<ParseObject> query = ParseQuery
							.getQuery("UserData");
					query.whereEqualTo("user", ParseUser.getCurrentUser());
					query.findInBackground(new FindCallback<ParseObject>() {

						@Override
						public void done(List<ParseObject> arg0,
								ParseException arg1) {
							// TODO Auto-generated method stub
							if (arg1 == null && arg0.size() != 0) {
								for (ParseObject p : arg0) {
									p.put("user", ParseUser.getCurrentUser());
									p.put("weight", kgs);
									p.put("age", Integer.parseInt(age1
											.getText().toString()));
									p.put("height", cms);
									p.put("active", String.valueOf(active));
									p.put("sex", sex);
									p.put("bmi", bmi);
									p.put("totalcal", totalcal);
									p.put("factor", String.valueOf(factor));

									p.saveEventually();
									Log.d("place", ParseUser.getCurrentUser().toString());

								}
							} else if (arg0.size() == 0) {
								ParseObject p = new ParseObject("UserData");
								
								p.put("user", ParseUser.getCurrentUser());
								p.put("weight", kgs);
								p.put("age", Integer.parseInt(age1.getText()
										.toString()));
								p.put("height", cms);
								p.put("active", String.valueOf(active));
								p.put("sex", sex);
								p.put("bmi", bmi);
								p.put("totalcal", totalcal);

								p.put("factor", String.valueOf(factor));
								Log.d("place", ParseUser.getCurrentUser().toString());
								p.saveEventually();
								
							} else {
								Log.d("score", "Error: " + arg1.getMessage());
							}
						}
					});
					
					ParseObject wgt = new ParseObject("Weight");
					wgt.put("user", ParseUser.getCurrentUser());
					wgt.put("weight", kgs);
					wgt.put("date", date);
					wgt.saveEventually();
				}

				Intent i = new Intent(ProfileActivity.this,
						FitnessActivity.class);
				startActivity(i);
				finish();
			}
		});

	}

}
