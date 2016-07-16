package views;


import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.example.lenovo.myviews.R;

public class copyipad extends ViewGroup {
	int[] source = new int[] { R.drawable.f, R.drawable.lyf2, R.drawable.lyfa,
			R.drawable.w7, R.drawable.w1, R.drawable.w4, R.drawable.w3,
			R.drawable.a, R.drawable.b, R.drawable.d, R.drawable.e,
			R.drawable.f, R.drawable.lyf2, R.drawable.lyfa, R.drawable.w7,
			R.drawable.w1, R.drawable.w4 };
	private int count;
	private int front;
	private int childw, vieww, paddingx, centerleftx;
	private Scroller s;
	private int lastx;
	private int lastscrollx;
	private int delaytime;
	Camera camera;
	Matrix matrix;
	private Runnable loop;
	private boolean haslooped = false;

	public copyipad(Context context) {
		this(context, null);
		
	}

	public copyipad(Context context, AttributeSet attrs) {
		super(context, attrs);
		camera = new Camera();
		matrix = new Matrix();
		s = new Scroller(context);
		count = source.length;
		for (int i = 0; i < source.length; i++) {
			ImageView iv = new ImageView(context);
			iv.setBackgroundResource(source[i]);
			addView(iv, new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT));
		}
		setChildrenDrawingOrderEnabled(true);
		loop = new Runnable() {

			@Override
			public void run() {
				startscrollto(getFront() + 1);
			}
		};
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			removeCallbacks(loop);
			lastx = x;
			lastscrollx = getScrollX();
			break;
		case MotionEvent.ACTION_MOVE:
			scrollTo(lastx - x + lastscrollx, 0);
			break;
		case MotionEvent.ACTION_UP:
			startscrollto(getFront());
			break;
		}
		super.onTouchEvent(event);
		return true;
	}

	private void startscrollto(int index) {
		int scrollx = getScrollX();
		View v = getChildAt(index);

		if (index <= 2) {
			scrollTo(scrollx + (count - 6) * childw, 0);
			v = getChildAt(index + count - 6);
		} else if (index >= count - 3) {
			scrollTo(scrollx - (count - 6) * childw, 0);
			v = getChildAt(index + 6 - count);
		} else {
			v = getChildAt(index);
		}
		int dx = v.getLeft() - centerleftx - getScrollX();
		int time = Math.abs(dx) * 5;
		s.startScroll(getScrollX(), 0, dx, 0, time);
		invalidate();
		postDelayed(loop, time + delaytime);
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if (s.computeScrollOffset()) {
			int x = s.getCurrX();
			scrollTo(x, 0);
			invalidate();
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int scrollx = getScrollX();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			int offx = child.getLeft() - scrollx;
			if (offx < paddingx + childw && offx >= paddingx) {
				setFront(i);
				break;
			}
		}
		super.dispatchDraw(canvas);

	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		// TODO Auto-generated method stub
		int scrollx = getScrollX();
		int childleft = child.getLeft();
		int childright = child.getRight();
		int offx = childleft - scrollx;
		int transx = offx > centerleftx ? childright : childleft;
		int centery = child.getTop() + child.getHeight() / 2;
		int offcenterx = centerleftx - offx;
		canvas.save();
		camera.save();
		matrix.reset();
		float per = (float) (offcenterx) / childw;
		float percent = 1.0f - Math.abs(per);
		if (per > -2 && per < 2) {
			canvas.translate(topercent((float) offcenterx * 4 / getWidth())
					* paddingx, 0);
			canvas.scale(1.0f + 0.4f * percent, 1.0f + 0.4f * percent,
					child.getLeft() + vieww / 2, centery);
			camera.rotateY(per * 20);
			camera.getMatrix(matrix);
			canvas.translate(transx, centery);
			canvas.concat(matrix);
			canvas.translate(-transx, -centery);
		}
		boolean draw = super.drawChild(canvas, child, drawingTime);
		if (offx < paddingx || offx > paddingx + childw) {
			float per1 = Math.abs(per) - 0.6f;
			if (per1 > 0) {
				if (offx < paddingx)
					canvas.clipRect(childright - vieww * per1, child.getTop(),
							childright, child.getBottom());
				else {
					canvas.clipRect(childleft, child.getTop(), childleft
							+ vieww * per1, child.getBottom());
				}
				int dark = (int) (per1 <= 0.4f ? per1 * 375 : 150);
				canvas.drawColor(Color.argb(dark, 0, 0, 0));
			}
		}
		canvas.restore();
		camera.restore();
		return draw;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.d("onlayout", "*****");
		childw = getWidth() / 2;
		vieww = getWidth() * 3 / 8;
		paddingx = getWidth() / 16;
		centerleftx = vieww - paddingx;
		delaytime = getWidth() * 10;
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			int left = centerleftx + (i - 3) * childw;
			child.layout(left, 20, left + vieww, 100);
		}
		if (!haslooped) {
			haslooped = true;
			postDelayed(loop, delaytime);
		}
	}

	@Override
	protected int getChildDrawingOrder(int childCount, int i) {
		// TODO Auto-generated method stub
		if (i >= front) {
			return count - 1 - i + front;
		}
		return super.getChildDrawingOrder(childCount, i);
	}

	public void setFront(int front) {
		this.front = front;
	}

	public int getFront() {
		return front;
	}

	public float topercent(float p) {
		if (p <= 1.0f && p >= -1.0f) {
			return p * 0.9f;
		} else if (p > 1.0f) {
			return 2 * p - 1.1f;
		} else {
			return 2 * p + 1.1f;
		}
	}

}
