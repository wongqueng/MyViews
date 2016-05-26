package com.example.lenovo.myviews;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WindowActivity extends Activity {
	WindowManager wm;
	LayoutParams lp;
	int width = 200, height = 200, titlewidth, titlwheight, offx, offy;
	RelativeLayout rl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_window);
		wm = (WindowManager) getSystemService(this.WINDOW_SERVICE);
		lp = new LayoutParams(LayoutParams.TYPE_SYSTEM_ALERT,
				LayoutParams.FLAG_NOT_TOUCHABLE);
		lp.dimAmount = 0.3f;
		lp.gravity = Gravity.LEFT | Gravity.TOP;
		lp.width = width;
		lp.height = height;
		rl = (RelativeLayout) ((LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.windowlayout, null);
		rl.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				wm.removeView(rl);
			}
		});
	}

	@Override
	protected void onStart() {
		super.onResume();
		wm.addView(rl, lp);
		final TextView tv = (TextView) rl.findViewById(R.id.titleText);
		tv.postDelayed(new Runnable() {
			@Override
			public void run() {
				titlewidth = tv.getWidth();
				titlwheight = tv.getHeight();
				
			}
		},500);
	}

	boolean isinchildwindow = false, isintitle = false;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.d("activitydispatch", "x=" + ev.getX() + "y=" + ev.getY());
		if (isinchildwindow) {
			Log.d("isinchildwindow", "#$%%^");
			ev.offsetLocation(-lp.x, -lp.y - 20);
			rl.dispatchTouchEvent(ev);
			if (ev.getAction() == 1 || ev.getAction() == 3) {
				isinchildwindow = false;
			}
			return true;
		}
		if (isintitle) {
			Log.d("isintitle", "(*&%%");
			onTouchEvent(ev);
			if (ev.getAction() == 1 || ev.getAction() == 3) {
				isintitle = false;
			}
			return true;
		}
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		if (ev.getAction() == 0) {
			if (x > lp.x && x < lp.x + width && y > lp.y + 20
					&& y < lp.y + 20 + height) {
				if (x > lp.x && x < lp.x + titlewidth && y > lp.y + 20
						&& y < lp.y + 20 + titlwheight) {
					isintitle = true;
					onTouchEvent(ev);
				} else {
					isinchildwindow = true;
					ev.offsetLocation(-lp.x, -lp.y - 20);
					rl.dispatchTouchEvent(ev);
				}

				return true;
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("activity", "ontouch");
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case 0:
			offx = x - lp.x;
			offy = y - lp.y;
			break;
		case 2:
			lp.x = x - offx;
			lp.y = y  - offy;
			wm.updateViewLayout(rl, lp);
			break;
		case 1:

			break;
		}

		return true;
	}
@Override
public void onBackPressed() {
	try {
		wm.removeView(rl);
	} catch (Exception e) {
	}
	super.onBackPressed();
}

}
