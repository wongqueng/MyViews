package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

public class myscrollview extends HorizontalScrollView implements Indicator{
	ViewGroup child;
	View[] grandchildren;
	int x = 0, y = 0, downx = 0, downy = 0, toscrollx = 0,currentindex=0;
	boolean isscroll = false, fromman = false;
	private onItemSeclectedListener listener;
	Runnable myrun = new Runnable() {
		@Override
		public void run() {
			fromman = false;
		}
	};

	public myscrollview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setListener(onItemSeclectedListener listener) {
		this.listener = listener;
	}

	@Override
	public void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		ViewGroup v = (ViewGroup) getChildAt(0);
		if (v instanceof ViewGroup) {
			child = (ViewGroup) v;
			grandchildren = new View[child.getChildCount()];
			for (int i = 0; i < grandchildren.length; i++) {
				grandchildren[i] = child.getChildAt(i);
			}
			mytext text1 = (mytext) grandchildren[0];
			text1.setDirectionAndX(direction.RIGHT, 0.0f);
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		x = (int) ev.getX();
		y = (int) ev.getY();
		switch (ev.getAction()) {
		case 0:
			isscroll = false;
			downx = x;
			downy = y;
			break;
		case 2:
			if (Math.abs(downx - x) > 8 || Math.abs(downy - y) > 8) {
				isscroll = true;
			}
			break;
		case 1:
			if (!isscroll) {
				if (Math.abs(downx - x) < 8 && Math.abs(downy - y) < 8) {
					int scrollx = getScrollX();
					int index = -1;
					for (int i = 0; i < grandchildren.length; i++) {
						View gv = grandchildren[i];
						if (x > gv.getLeft() - scrollx
								&& x < gv.getRight() - scrollx) {
							index = i;
							break;
						}
					}
					if (index >= 0) {
						if (listener != null) {
							listener.onItemSeclected(index);
						}
						scrolltograndchild(index);
						fromman = true;
						postDelayed(myrun, 250);
					}
				}
			}
			break;
		}
		return true;
	}   

	public void scrolltograndchild(int index) {
		View g = grandchildren[index];
		cleargrandselet();
		currentindex=index;
		mytext text1 = (mytext) grandchildren[index];
		text1.setDirectionAndX(direction.RIGHT, 0.0f);
		toscrollx = g.getLeft() + g.getWidth() / 2 - getWidth() / 2;
		smoothScrollTo(toscrollx, 0);
	}

	public void cleargrandselet() {
		mytext text1 = (mytext) grandchildren[currentindex];
		text1.setDirectionAndX(direction.RIGHT, 1.0f);
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		super.computeScroll();
	}


	public static interface onItemSeclectedListener {
		void onItemSeclected(int position);
	}

	public void setItemAndX(int arg0, float arg1) {
		if (!fromman) {
			mytext text1 = (mytext) grandchildren[arg0];
			text1.setDirectionAndX(direction.RIGHT, arg1);
			if (arg0 != 6) {
				mytext text2 = (mytext) grandchildren[arg0 + 1];
				text2.setDirectionAndX(direction.LEFT, arg1);
			}
		}
	}

	@Override
	public void onViewPageSelected(int arg0) {
		scrolltograndchild(arg0);
	}

	@Override
	public void setCount(int count) {
		// TODO Auto-generated method stub
		
	}

}
