package views;




import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;

import com.example.lenovo.myviews.R;

public class loopvg extends ViewGroup{

	private int count;
	int[] source = new int[] {R.drawable.w7,R.drawable.w1, R.drawable.w4, R.drawable.w3,
			R.drawable.w7, R.drawable.w1, };
	private int vieww;
	private Scroller s;
	private int lastx,startx,dx;
	private int lastscrollx;
	private static final Interpolator sInterpolator = new Interpolator() {
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t * t * t + 1.0f;
		}
	};
	public loopvg(Context context) {
		this(context,null);
		
	}
	
	public loopvg(Context context, AttributeSet attrs) {
		super(context, attrs);
		s = new Scroller(context,sInterpolator);
		count = source.length;
		for (int i = 0; i < source.length; i++) {
			ImageView iv = new ImageView(context);
			iv.setBackgroundResource(source[i]);
			addView(iv, new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.FILL_PARENT));
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastx = x;
			lastscrollx = getScrollX();
			break;
		case MotionEvent.ACTION_MOVE:
			scrollTo(lastx - x + lastscrollx, 0);
			break;
		case MotionEvent.ACTION_UP:
			int scrollx = getScrollX();
			if (scrollx > -vieww/2&&scrollx<(count-2)*vieww-vieww/2) {
				int index = scrollx / vieww;
				int yu = scrollx % vieww;
				if (yu > vieww / 2)
					index++;
				startx=scrollx;
				dx=(index) * vieww - scrollx;
			}else if(scrollx<=-vieww/2){
				startx=(count-2)*vieww+scrollx;
				scrollTo(startx, 0);
				dx=-vieww-scrollx;
			}else if(scrollx>=(count-2)*vieww-vieww/2){
				startx=scrollx%vieww-vieww;
				scrollTo(startx, 0);
				dx=-startx;
			}
			int time = Math.abs(dx);
			s.startScroll(startx, 0, dx, 0, time);
			invalidate();
			break;
		}

		super.onTouchEvent(event);
		return true;
	}
	@Override
	public void computeScroll() {
		if (s.computeScrollOffset()) {
			int offx = s.getCurrX();
			scrollTo(offx, 0);
			invalidate();
		}
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		vieww = getWidth();
		int h = getHeight();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			int left = (i-1) * vieww;
			child.layout(left, 0, left + vieww, h);
		}
	}


}
