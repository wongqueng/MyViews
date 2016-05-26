package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class cornerView extends View {
	Paint paint;
	Path path;
	boolean isintouch=false;

	public cornerView(Context context) {
		this(context, null);
	}

	public cornerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		path = new Path();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setARGB(100, 0, 0, 220);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		path.moveTo(w, 0);
		path.lineTo(0,h);
		path.lineTo(w, h);
		path.close();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(path, paint);
	}

	int downx, downy;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

	public boolean isIsintouch() {
		return isintouch;
	}

	public void setIsintouch(boolean isintouch) {
		if(this.isintouch != isintouch){
			if (isintouch) {
				paint.setARGB(180, 0, 0, 220);
			} else {
				paint.setARGB(100, 0, 0, 220);
			}
			this.isintouch = isintouch;
			invalidate();
		}
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (getMeasuredHeight() == getMeasuredWidth())
			return;
		int smallestsize = getMeasuredHeight() > getMeasuredWidth() ? getMeasuredWidth()
				: getMeasuredHeight();
		smallestsize=smallestsize>30?30:smallestsize;
		setMeasuredDimension(smallestsize,smallestsize);

	}
}
