package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.View;

public class myIndicator extends View implements Indicator{
	Paint paint;
	Path path;
	int rad;
	int startx;
	int starty;
	int index;
	int count;

	public myIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.GREEN);
		path = new Path();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		path.reset();
		rad = 8;
		startx = (w - 2 * rad * count) / 2 + rad;
		starty = h * 4 / 5;
		for (int i = 0; i < count; i++) {
			path.addCircle(startx + i * 2 * rad, starty, rad, Direction.CCW);

		}
		x=startx;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setARGB(100, 0, 0, 255);
		for (int i = 0; i < count; i++) {
			canvas.drawCircle(startx + i * 2 * rad, starty, rad,paint);
		}
		canvas.clipPath(path);
		paint.setColor(Color.BLUE);
		canvas.drawCircle(x, starty, rad, paint);

	}

	int x;

	public void setItemAndX(int index, float percent) {
		x=(int) (startx + (index+percent) * 2 * rad);
		invalidate();
		
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	public void onViewPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCount(int count) {
		this.count=count;
		requestLayout();
	}


}
