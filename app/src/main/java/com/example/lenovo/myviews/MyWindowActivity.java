package com.example.lenovo.myviews;


import com.example.lenovo.myviews.MyBodyFragment.OnFragmentInteractionListener;
import views.MyWindow;
import views.blowView;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.app.Activity;
import android.content.Intent;

public class MyWindowActivity extends Activity implements OnFragmentInteractionListener{
	MyWindow myWindow;
	ImageView im;
	SeekBar seekBar;
	IBinder iBinder;
	EditText et;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_decor);
		seekBar=(SeekBar) findViewById(R.id.seekBar1);
		et=(EditText) findViewById(R.id.editText1);
		myWindow = new MyWindow(this);
		EditText et1=new EditText(this);
		et1.setMinEms(20);
		myWindow.setContentView(et1, null);
		setlistener();
		
	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			if(!myWindow.isShowing()){
			myWindow.show();
			}
			break;
		case R.id.button2:
			myWindow.dismiss();
			break;
		case R.id.button3:
			startService(new Intent(this,MyWindowService.class));
//			myWindow.setftagment(getFragmentManager(), new MyBodyFragment());
			break;

		}
		
	}
	public void setlistener() {
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				float alpha=(float)progress/100;
				myWindow.setAlpha(alpha);
				Log.d("seekbar", "alpha="+alpha);
				
			}
		});
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (myWindow.isShowing()&&myWindow.interceptDispatchMotion(ev)) {
			return true;
		}
		super.dispatchTouchEvent(ev);
		return true;
	}


	@Override
	protected void onResume() {
//		if (!myWindow.isShowing())
//			myWindow.show();
		super.onResume();
	}

	@Override
	protected void onPause() {
//		if (myWindow.isShowing())
//			myWindow.dismiss();
		super.onPause();
	}


	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}
	
}
