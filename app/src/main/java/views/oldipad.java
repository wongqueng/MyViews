package views;

import com.example.lenovo.myviews.R;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

public class oldipad extends ViewGroup {
	int[] source = new int[] { R.drawable.w7,R.drawable.w1, R.drawable.w4, R.drawable.w3,
			R.drawable.w7, R.drawable.w1, };
	private boolean seted = false;
	private int count;
	private int front;
	private int childw, vieww;
	private Scroller s;
	private int lastx;
	private int lastscrollx;
	Camera camera;
	Matrix matrix;

	public oldipad(Context context) {
		
		super(context);
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
//		setChildrenDrawingOrderEnabled(true);
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
			int index = 0;
			if (scrollx > 0) {
				index = scrollx / vieww;
				int yu = scrollx % vieww;
				if (yu > childw / 2)
					index++;
				if (index > count-2)
					index = count-2;
			}else if(scrollx<-vieww/2){
				index=-1;
			}
			int time = Math.abs(index * vieww - scrollx) * 5;
			s.startScroll(scrollx, 0, index * vieww - scrollx, 0, time);
			invalidate();
			break;
		}

		super.onTouchEvent(event);
		return true;
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if (s.computeScrollOffset()) {
			int offx = s.getCurrX();
			scrollTo(offx, 0);
			// invalidate();
		}
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
//		int scrollx = getScrollX();
//		for (int i = 0; i < count; i++) {
//			View child = getChildAt(i);
//			int childleft = child.getLeft();
//			int offx = childleft - scrollx;
//			if (offx < vieww*3/2&& offx >= vieww/2) {
//				setFront(i);
//			}
//		}
		super.dispatchDraw(canvas);
		
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		// TODO Auto-generated method stub
		int scrollx = getScrollX();
		int childleft = child.getLeft();
		int childright=child.getRight();
		int offx = childleft - scrollx;
		if(offx>-vieww&&offx < 3 * vieww){
			int transx=offx>vieww?childright:childleft;
			int centery=child.getTop()+child.getHeight()/2;
			canvas.save();
			camera.save();
			matrix.reset();
			float per=(float)(vieww-offx)/vieww;
			camera.rotateY(per*45);
			camera.getMatrix(matrix);
			if(offx < 2 * vieww&&offx>=0){
				float percent=(float)(vieww-Math.abs(vieww-offx))/vieww;
				percent=1-(1-percent)*(1-percent);
				canvas.scale(1+0.5f*percent,1+0.5f*percent,childleft+childw/2,centery);
			}
			canvas.translate(transx, centery);
			canvas.concat(matrix);
			canvas.translate(-transx, -centery);
			boolean draw=super.drawChild(canvas, child, drawingTime);
			if(offx<vieww/2||offx>vieww*3/2){
			float per1=(float)(Math.abs(offx-vieww))/vieww-0.5f;
			if(offx<vieww/2)canvas.clipRect(childright-vieww*per1, child.getTop(), childright, child.getBottom());
			else {canvas.clipRect(childleft, child.getTop(), childleft+vieww*per1, child.getBottom());}
			canvas.drawColor(Color.argb(150, 0, 0, 0));}
			canvas.restore();
			camera.restore();
			return draw;
		}else{
			return false;
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		childw = getWidth() /3;
		vieww = getWidth()/3;
		int h = getHeight();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			int left = i * vieww;
			child.layout(left, 20, left + childw, 120);
		}
	}

	@Override
	protected int getChildDrawingOrder(int childCount, int i) {
		// TODO Auto-generated method stub
		if (seted && i >= front) {
			return count - 1 - i + front;
		}
		return super.getChildDrawingOrder(childCount, i);
	}

	public void setFront(int front) {
		seted = true;
		this.front = front;
	}

	public void reverse() {
		seted = false;
	}
	public float topercent(float p) {
		return 1-4*(p-0.5f)*(p-0.5f);
	}
}
