package com.example.lenovo.myviews;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class IpadPagerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ipad_pager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ipad_pager, menu);
		return true;
	}

}
