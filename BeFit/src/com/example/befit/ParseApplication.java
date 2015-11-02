/*
 * Group_14C
 * Arun Sai Sangawar Vijay: 800890154
 * Karishma Borole: 800862169  */


package com.example.befit;

import com.parse.Parse;


import android.app.Application;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		
       
		 Parse.initialize(this, "xnPlNwYZqdMge8t8lFZCb3huaqMTpLMosYJozHYn", "RH9Nqd5gNDb9MrNAQ3jrHZ021SzTBxwMDd6cCgjW");

		super.onCreate();
	}

}
