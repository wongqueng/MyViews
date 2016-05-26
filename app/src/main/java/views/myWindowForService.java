package views;

import com.example.lenovo.myviews.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class myWindowForService extends LinearLayout {
	static WindowManager wm;
	WindowManager.LayoutParams lp;
	int MinWidth, MinHeight, MaxWidth, MaxHeight, startAlphaWidth;
	ImageButton tolittle, cancel;
	int titleheight, padding;
	RelativeLayout title;
	FrameLayout body;
	TextView titleTextView;
	View contentView;
	LayoutInflater li;
	cornerView corner;
	boolean showing = false;
	boolean isBiggst = true;
	LayoutParams bigLayoutParams, smallLayoutParams, bodylp, titlelp;
	float startAlpha;
	Context context;

	public myWindowForService(Context context) {
		this(context, null);

	}

	public myWindowForService(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("init", getChildCount() + "");
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
				// WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
				WindowManager.LayoutParams.FLAG_DIM_BEHIND
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		lp.dimAmount = 0.4f;
		lp.alpha = 1.0f;
		lp.gravity = Gravity.LEFT | Gravity.TOP;
		lp.token = getWindowToken();
		li = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		padding = this.getPaddingTop();
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
		li.inflate(R.layout.title, this, true);
		li.inflate(R.layout.body, this, true);
		title = (RelativeLayout) getChildAt(0);
		body = (FrameLayout) getChildAt(1);
		cancel = (ImageButton) title.getChildAt(0);
		tolittle = (ImageButton) title.getChildAt(1);
		titleTextView = (TextView) title.getChildAt(2);
		corner = (cornerView) body.getChildAt(body.getChildCount() - 1);
		titlelp = (LayoutParams) title.getLayoutParams();
		bodylp = (LayoutParams) body.getLayoutParams();
	}

	int lastwidth, lastheight, lastx, lasty;
	float lastalpha;

	private void setListener() {
		setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				Log.d("key",
						"keyCode=" + keyCode + ";event=" + event.getAction());
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {
					wm.removeView(myWindowForService.this);
					return true;
				}
				return false;
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showing = false;
				wm.removeView(myWindowForService.this);
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
			body.removeView(contentView);
		}
		contentView = v;
		if (params == null) {
			params = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
		}
		body.addView(v, 0, params);
	}

	public void setContentView(int resource) {
		if (contentView != null) {
			body.removeView(contentView);
		}
		contentView = li.inflate(resource, body, false);
		body.addView(contentView, 0, contentView.getLayoutParams());
	}

	public void setAlpha(float alpha) {
		lp.alpha = Math.min(Math.max(0.2f, alpha), 1.0f);
		refresh();
	}

	private void refresh() {
		if (isShowing())
			wm.updateViewLayout(this, lp);
	}

	public boolean isShowing() {
		return showing;
	}

	public void show() {
		if (showing)
			return;
		showing = true;
		wm.addView(this, lp);
	}

	public void dismiss() {
		if (!showing)
			return;
		showing = false;
		wm.removeView(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("onkeydown", keyCode + "");
		return super.onKeyDown(keyCode, event);
	}

	boolean isinchildwindow = false, isindrag = false, isincorner = false,
			needCheckDragOrInwindow = false, isdealtoAhpha = false;
	private int offx;
	private int offy;
	int downX, downY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (isShowing() && interceptDispatchMotion(ev)) {
			return true;
		}
		Log.d("superdispatch", "^$#$%^");
		super.dispatchTouchEvent(ev);
		return true;
	}

	public boolean interceptDispatchMotion(MotionEvent ev) {
		if (ev.getAction() != 0) {
			if (isinchildwindow) {
				Log.d("isinchildwindow", "#$%%^");
				super.dispatchTouchEvent(ev);
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
							|| downX <= padding+tolittle.getLeft()) {
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
					needCheckDragOrInwindow = false;
				}
				super.dispatchTouchEvent(ev);
				return true;
			}

		}
		if (ev.getAction() == 0 && interceptDownEvent(ev)) {
			return true;
		}
		return false;
	}

	private boolean interceptDownEvent(MotionEvent ev) {
		downX = (int) ev.getX();
		downY = (int) ev.getY();
		Log.d("down", "x=" + downX + ";y=" + downY);
		isinchildwindow = false;
		isindrag = false;
		isincorner = false;
		needCheckDragOrInwindow = false;
		isdealtoAhpha = false;
		if (!isBiggst || (downY < title.getHeight() + padding)) {
			needCheckDragOrInwindow = true;
			onTouchDrag(ev);
			dealTouchAlpha(ev);
			super.dispatchTouchEvent(ev);
		} else if (downY + downX > -2 * padding + lp.width + lp.height - 30) {
			isincorner = true;
			corner.setIsintouch(true);
			onTouchInCorner(ev);
		} else {
			isinchildwindow = true;
			super.dispatchTouchEvent(ev);
		}

		return true;

	}

	int offrightx, offbottomy;

	private void onTouchInCorner(MotionEvent ev) {
		int x = (int) ev.getRawX();
		int y = (int) ev.getRawY();
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
		int x = (int) ev.getRawX();
		int y = (int) ev.getRawY();
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
