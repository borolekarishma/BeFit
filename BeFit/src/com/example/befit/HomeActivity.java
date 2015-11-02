/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */



package com.example.befit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class HomeActivity extends Activity {

	ProgressDialog PD;
	ListView listView;
	public static ArrayList<FoodData> fd;
	Button search;
	String fooditem;
	public static String type;
	EditText et1, serving;
	TextView food_label, food_cal, food_serv;
	Spinner spinner3;

	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(HomeActivity.this, FitnessActivity.class);
		startActivity(i);
		finish();
		super.onBackPressed();
	}
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		fd = new ArrayList<FoodData>();

		spinner3 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.meal_type, android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(adapter3);

		et1 = (EditText) findViewById(R.id.editText1);
		search = (Button) findViewById(R.id.search);

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nf = cm.getActiveNetworkInfo();
		if (nf != null && nf.isConnected()) {

			Log.d("conn", "Connected");

			search.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					fooditem = et1.getText().toString();
					fooditem = fooditem.replaceAll(" ", "%20");
					Log.d("conn1", fooditem);
					type = (String) spinner3.getSelectedItem();
					if (!fooditem.equals("")) {

						downloadFile async = new downloadFile();
						async.execute(fooditem);

						listView = (ListView) findViewById(R.id.foodlist);
						listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, final int position, long id) {
								// TODO Auto-generated method stub

								Log.d("fd", fd.toString());
								LayoutInflater li = LayoutInflater
										.from(HomeActivity.this);
								View promptsView = li.inflate(R.layout.prompt,
										null);
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										HomeActivity.this);
								alertDialogBuilder.setView(promptsView);

								serving = (EditText) promptsView
										.findViewById(R.id.editText1);

								final String date = new SimpleDateFormat(
										"MM-dd-yyyy").format(new Date());
								food_label = (TextView) promptsView
										.findViewById(R.id.f_label);
								food_cal = (TextView) promptsView
										.findViewById(R.id.f_cal);
								food_serv = (TextView) promptsView
										.findViewById(R.id.serv);
								Log.d("foodiitem", fd.get(position).fooditem);
								food_label.setText("Food: "
										+ fd.get(position).fooditem);
								food_cal.setText("Calorie: "
										+ fd.get(position).cal);
								food_serv.setText("Enter Serving: ");
								alertDialogBuilder
										.setCancelable(false)
										.setPositiveButton(
												"SAVE",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {

														if (!serving.equals("")
																|| serving
																		.equals("0")) {
															int calor = fd
																	.get(position).cal
																	* Integer
																			.parseInt(serving
																					.getText()
																					.toString());
															ParseObject Cal = new ParseObject(
																	"Calorie");
															Cal.put("FoodItem",
																	fd.get(position).fooditem);
															Cal.put("Calorie",
																	calor);
															Cal.put("Category",
																	type);
															Cal.put("Date",
																	date);
															Cal.put("user",
																	ParseUser
																			.getCurrentUser());
															Cal.saveEventually();
															Intent intent = new Intent(
																	HomeActivity.this,
																	FitnessActivity.class);
															startActivity(intent);
															finish();
														} else {
															Toast.makeText(
																	HomeActivity.this,
																	"Enter number of serving",
																	Toast.LENGTH_SHORT);
														}
													}

												})
										.setNegativeButton(
												"Cancel",
												new DialogInterface.OnClickListener() {
													public void onClick(
															DialogInterface dialog,
															int id) {
														dialog.cancel();
													}
												});

								AlertDialog alertDialog = alertDialogBuilder
										.create();

								alertDialog.show();

							}
						});
					}
				}
			});

		}

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
			Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
			startActivity(i);
			finish();
			return true;
		} else if (id == R.id.action_profile) {
			Intent i = new Intent(HomeActivity.this, DetailActivity.class);
			startActivity(i);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class downloadFile extends
			AsyncTask<String, Void, ArrayList<FoodData>> {

		@Override
		protected ArrayList<FoodData> doInBackground(String... params) {
			// TODO Auto-generated method stub
			URL request = null;
			try {

				request = new URL(
						"https://api.edamam.com/search?q="
								+ params[0]
								+ "&app_id=d7b8234a&app_key=baec222cb83cfbae656b46ac2ad7d22f");
				HttpURLConnection con = (HttpURLConnection) request
						.openConnection();
				con.setRequestMethod("GET");
				con.connect();
				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					Log.d("conn1", "URL Connected");
					InputStream in = con.getInputStream();
					BufferedReader BF = new BufferedReader(
							new InputStreamReader(con.getInputStream()));
					StringBuilder Str = new StringBuilder();
					String line = BF.readLine();

					while (line != null) {
						Str.append(line);
						line = BF.readLine();
					}

					JSONParser parser = new JSONParser();
					ArrayList<FoodData> f = parser.ParseJson(Str.toString());

					return f;

				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			PD = new ProgressDialog(HomeActivity.this);
			PD.setMessage("Loading Results ...");
			PD.setProgressStyle(PD.STYLE_SPINNER);
			PD.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ArrayList<FoodData> result) {
			// TODO Auto-generated method stub
			ArrayList<String> titles = new ArrayList<String>();

			for (FoodData r : result) {
				titles.add(r.getFooditem());
				fd.add(r);
			}
			PD.dismiss();

			Log.d("Titles", fd.toString());

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					HomeActivity.this, android.R.layout.simple_list_item_1,
					android.R.id.text1, titles);

			listView.setAdapter(adapter);

			super.onPostExecute(result);
		}

	}

}