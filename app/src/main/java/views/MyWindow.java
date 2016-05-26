package views;

import com.example.lenovo.myviews.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.LinearLayout.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyWindow {
	static WindowManager wm;
	WindowManager.LayoutParams lp;
	LinearLayout DecorView;
	int MinWidth, MinHeight, MaxWidth, MaxHeight, startAlphaWidth;
	ImageButton tolittle, cancel;
	int titleheight, padding;
	RelativeLayout title;
	FrameLayout body, mainbody;
	TextView titleTextView;
	View contentView;
	LayoutInflater li;
	cornerView corner;
	boolean showing = false;
	boolean isBiggst = true;
	LayoutParams bigLayoutParams, smallLayoutParams, bodylp, titlelp;
	float startAlpha;

	public MyWindow(Context context) {
		getWmAndLp(context);
		setView();
		getMaxAndMin();
		measureSizes(MaxWidth, MaxHeight);
		setListener();
	}

	private void getWmAndLp(Context context) {
		wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
		lp = new WindowManager.LayoutParams(
				// WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_DIM_BEHIND
						// | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		lp.dimAmount = 0.4f;
		lp.alpha = 0.99f;
		lp.gravity = Gravity.LEFT | Gravity.TOP;
		li = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		DecorView = (LinearLayout) li.inflate(R.layout.mydecorview, null);
		padding = DecorView.getPaddingTop();
	}

	public void getMaxAndMin() {
		MaxWidth = wm.getDefaultDisplay().getWidth() * 2 / 3;
		MaxHeight = wm.getDefaultDisplay().getHeight() / 2;
		MinWidth = MaxWidth / 2;
		MinWidth = MinWidth < 70 ? 70 : MinWidth;
		MinHeight = MaxHeight / 2;
		MinHeight = MinHeight < 70 ? 70 : MinHeight;
		lp.x = MaxWidth / 4;
		lp.y = MaxHeight / 2;

	}

	private void measureSizes(int Width, int Height) {
		lp.width = Math.min(Math.max(Width, MinWidth), MaxWidth);
		lp.height = Math.min(Math.max(Height, MinHeight), MaxHeight);
		int titleheight = lp.height / 10 < 30 ? 30 : lp.height / 10;

		titlelp.width = bodylp.width = lp.width - padding * 2;
		titlelp.height = titleheight;
		bodylp.height = lp.height - titleheight - padding * 2;

		title.setLayoutParams(titlelp);
		body.setLayoutParams(bodylp);

	}

	private void setView() {
		tolittle = (ImageButton) DecorView.findViewById(R.id.tolittle);
		cancel = (ImageButton) DecorView.findViewById(R.id.cancel);
		titleTextView = (TextView) DecorView.findViewById(R.id.titleText);
		title = (RelativeLayout) DecorView.findViewById(R.id.title);
		body = (FrameLayout) DecorView.findViewById(R.id.body);
		mainbody = (FrameLayout) DecorView.findViewById(R.id.mainbody);
		corner = (cornerView) DecorView.findViewById(R.id.corner1);
		titlelp = (LayoutParams) title.getLayoutParams();
		bodylp = (LayoutParams) body.getLayoutParams();
	}

	int lastwidth, lastheight, lastx, lasty;
	float lastalpha;

	private void setListener() {
		DecorView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				Log.d("key",
						"keyCode=" + keyCode + ";event=" + event.getAction());
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					wm.removeView(DecorView);
					return true;
				}
				return false;
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showing = false;
				wm.removeView(DecorView);
			}
		});

		tolittle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImageButton bt = (ImageButton) v;
				if (isBiggst) {
					lp.dimAmount = 0;
					lastwidth = lp.width;
					lastheight = lp.height;
					lastalpha = lp.alpha;
					lp.alpha = 0.2f;
					isBiggst = false;
					lp.width = lp.width - titleTextView.getWidth();
					lp.x = MaxWidth;
					lp.y = 0;
					lp.height = titleTextView.getHeight() + padding * 2;
					smallLayoutParams = (LayoutParams) title.getLayoutParams();
					bigLayoutParams = new LayoutParams(smallLayoutParams);
					smallLayoutParams.width = smallLayoutParams.width
							- titleTextView.getWidth();
					title.setLayoutParams(smallLayoutParams);
					body.setVisibility(View.GONE);
					titleTextView.setVisibility(View.GONE);
					bt.setImageResource(android.R.drawable.arrow_down_float);
					refresh();
				} else {
					lp.dimAmount = 0.3f;
					isBiggst = true;
					lp.x = lp.x + lp.width - lastwidth;
					lp.alpha = lastalpha;
					lp.width = lastwidth;
					lp.height = lastheight;
					title.setLayoutParams(bigLayoutParams);
					titleTextView.setVisibility(View.VISIBLE);
					body.setVisibility(View.VISIBLE);
					bt.setImageResource(android.R.drawable.arrow_up_float);
					refresh();
				}

			}
		});

	}

	public void setContentView(View v, FrameLayout.LayoutParams params) {
		if (v == null) {
			throw new RuntimeException("ContentView shall not be null");
		}
		if (contentView != null) {
			mainbody.removeView(contentView);
		}
		contentView = v;
		if (params == null) {
			params = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
		}
		mainbody.addView(v, 0, params);
	}

	public void setContentView(int resource) {
		if (contentView != null) {
			mainbody.removeView(contentView);
		}
		contentView = li.inflate(resource, body, false);
		mainbody.addView(contentView, 0, contentView.getLayoutParams());
	}

	public void setftagment(FragmentManager fragmentManager, Fragment fragment) {
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.mainbody, fragment);
		ft.commit();
	}

	public void setAlpha(float alpha) {
		lp.alpha = Math.min(Math.max(0.2f, alpha), 0.99f);
		refresh();
	}

	private void refresh() {
		if (isShowing())
			wm.updateViewLayout(DecorView, lp);
	}

	public boolean isShowing() {
		return showing;
	}

	public void show() {
		if (showing || contentView == null)
			return;
		showing = true;
		wm.addView(DecorView, lp);
	}

	public void dismiss() {
		if (!showing)
			return;
		showing = false;
		wm.removeView(DecorView);
	}

	boolean isinchildwindow = false, isindrag = false, isincorner = false,
			needCheckDragOrInwindow = false, isdealtoAhpha = false;
	private int offx;
	private int offy;
	int downX, downY;

	public boolean interceptDispatchMotion(MotionEvent ev) {
		if (ev.getAction() != 0) {
			if (isinchildwindow) {
				Log.d("isinchildwindow", "#$%%^");
				ev.offsetLocation(-lp.x, -lp.y - 20);
				DecorView.dispatchTouchEvent(ev);
				return true;
			}
			if (isindrag) {
				Log.d("isintitle", "(*&%%");
				onTouchDrag(ev);
				return true;
			}
			if (isdealtoAhpha) {
				Log.d("isdealtoAlpha", "(*&%%");
				dealTouchAlpha(ev);
				return true;
			}
			if (isincorner) {
				Log.d("isincorner", "(*&%%");
				onTouchInCorner(ev);
				return true;
			}
			if (needCheckDragOrInwindow) {
				int x = (int) ev.getX();
				int y = (int) ev.getY();
				if (Math.abs(y - downY) > 10 || Math.abs(x - downX) > 10) {
					if (!isBiggst || Math.abs(y - downY) > 10
							|| downX <= lp.x + tolittle.getLeft()) {
						isindrag = true;
						onTouchDrag(ev);
					} else {
						startAlphaWidth = lp.width - tolittle.getLeft()
								- padding;
						startAlpha = lp.alpha;
						isdealtoAhpha = true;
						dealTouchAlpha(ev);
					}
					ev.setAction(3);
				}
				ev.offsetLocation(-lp.x, -lp.y - 20);
				DecorView.dispatchTouchEvent(ev);
				return true;
			}

		}
		if (ev.getAction() == 0 && interceptDownEvent(ev)) {
			return true;
		}
		return false;
	}

	private boolean interceptDownEvent(MotionEvent ev) {
		int x = downX = (int) ev.getX();
		int y = downY = (int) ev.getY();
		Log.d("down", "x=" + x + ";y=" + y);
		isinchildwindow = false;
		isindrag = false;
		isincorner = false;
		needCheckDragOrInwindow = false;
		isdealtoAhpha = false;
		if (x > lp.x && x < lp.x + lp.width && y > lp.y + 20
				&& y < lp.y + 20 + lp.height) {
			if (!isBiggst || (y < lp.y + 20 + title.getHeight() + padding)) {
				needCheckDragOrInwindow = true;
				onTouchDrag(ev);
				dealTouchAlpha(ev);
				ev.offsetLocation(-lp.x, -lp.y - 20);
				DecorView.dispatchTouchEvent(ev);
			} else if (y - lp.y - 20 + x - lp.x > -2 * padding + lp.width
					+ lp.height - 30) {
				isincorner = true;
				corner.setIsintouch(true);
				onTouchInCorner(ev);
			} else {
				isinchildwindow = true;
				ev.offsetLocation(-lp.x, -lp.y - 20);
				DecorView.dispatchTouchEvent(ev);
			}

			return true;
		}

		return false;
	}

	int offrightx, offbottomy;

	private void onTouchInCorner(MotionEvent ev) {
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		switch (ev.getAction()) {
		case 0:
			offrightx = lp.width - x;
			offbottomy = lp.height - y;
			break;
		case 2:
		case 1:
			int Width = x + offrightx;
			int Height = y + offbottomy;
			if (ev.getAction() == 1) {
				corner.setIsintouch(false);
			}
			measureSizes(Width, Height);
			refresh();
			break;
		}
	}

	private void onTouchDrag(MotionEvent ev) {
		int x = (int) ev.getX();
		int y = (int) ev.getY();
		switch (ev.getAction()) {
		case 0:
			offx = x - lp.x;
			offy = y - lp.y;
			break;
		case 2:
			lp.x = x - offx;
			lp.y = y - offy;
			refresh();
			break;
		case 1:
			break;
		}
	}

	private void dealTouchAlpha(MotionEvent ev) {
		float x = ev.getX();
		switch (ev.getAction()) {
		case 0:

			break;
		case 2:
		case 1:
			float alpha = startAlpha - (downX - x) / startAlphaWidth;
			setAlpha(alpha);
			break;

		}
	}
}
