package com.example.lenovo.myviews;

import views.myscrollview;
import views.myviewpager;
import views.pager;
import views.myscrollview.onItemSeclectedListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class VIewPagerDemoActivity extends FragmentActivity implements
		onItemSeclectedListener {
	private myviewpager vp;
	private myscrollview tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager_demo);
		vp = (myviewpager) findViewById(R.id.vp);
		tabs = (myscrollview) findViewById(R.id.tabviews);
		tabs.setListener(this);
		vp.setTabs(tabs);
		vp.setAdapter(new FrgAdapter(getSupportFragmentManager(),7));
//		vp.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				return true;
//			}
//		});
	}


	@Override
	public void onItemSeclected(int position) {
		vp.setCurrentItem(position, true);

	}

}

class FrgAdapter extends FragmentPagerAdapter {
	int count;
	public FrgAdapter(FragmentManager fm,int count) {
		super(fm);
		this.count=count;
	}

	@Override
	public Fragment getItem(int position) {
		pager pag = new pager();
		pag.setText("Pager:" + (position + 1));
		return pag;
	}

	@Override
	public int getCount() {
		return count;
	}

}
