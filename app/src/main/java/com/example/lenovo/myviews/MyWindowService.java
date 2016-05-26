package com.example.lenovo.myviews;

import views.myWindowForService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;

public class MyWindowService extends Service {
	myWindowForService myWindow;
	public MyWindowService() {

	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("servicecreate", "&&&&");
		 myWindow = (myWindowForService) ((LayoutInflater) this
		 .getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(
		 R.layout.mywindowforservice, null);
		 
		 myWindow.show(); 
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
