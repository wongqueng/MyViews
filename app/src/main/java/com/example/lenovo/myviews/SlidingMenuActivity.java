package com.example.lenovo.myviews;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SlidingMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sliding_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sliding_menu, menu);
		return true;
	}

}
