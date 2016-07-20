package com.example.lenovo.myviews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent i1 = new Intent(this, VIewPagerDemoActivity.class);
			startActivity(i1);
			break;
		case R.id.button2:
			Intent i2 = new Intent(this, IpadPagerActivity.class);
			startActivity(i2);
			break;
		case R.id.button3:
			Intent i3 = new Intent(this, LoopViewActivity.class);
			startActivity(i3);
			break;
		case R.id.button4:
			Intent i4 = new Intent(this, ArcActivity.class);
			startActivity(i4);
			break;
		case R.id.button5:
			Intent i5 = new Intent(this, BrandListActivity.class);
			startActivity(i5);
			break;
		case R.id.button6:
			Intent i6 = new Intent(this, BlowActivity.class);
			startActivity(i6);
			break;
		case R.id.button7:  
			Intent i7 = new Intent(this, WindowActivity.class);
			startActivity(i7);
			break;
		case R.id.button8: 
			Intent i8 = new Intent(this, MyWindowActivity.class);
			startActivity(i8);
			break;
		case R.id.button9: 
			Intent i9 = new Intent(this, IndicatorActivity.class);
			startActivity(i9);
			break;
		case R.id.button10: 
			Intent i10 = new Intent(this, SlidingMenuActivity.class);
			startActivity(i10);
			break;
		case R.id.button11: 
			Intent i11 = new Intent(this, RollBackActivity.class);
			startActivity(i11);
			break;
		}
	}
}
