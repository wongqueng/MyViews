package com.example.lenovo.myviews;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import views.myIndicator;
import views.myviewpager;

public class IndicatorActivity extends FragmentActivity {
	myviewpager vp;
	myIndicator indicator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_indicator);
		vp=(myviewpager) findViewById(R.id.pager);
		indicator=(myIndicator) findViewById(R.id.indicator);
		vp.setTabs(indicator);
		vp.setAdapter(new FrgAdapter(getSupportFragmentManager(),4));
	}

}
