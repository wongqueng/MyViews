package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

public class mytext extends TextView{
    int offx,offy,x;
    Paint paint;
    String text;
    Rect r;
  direction d=direction.LEFT;
	public mytext(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint =new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.GREEN);
		r=new Rect();
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(d==direction.LEFT){
			canvas.clipRect(0, 0, x, getHeight());
		}else{
			canvas.clipRect(x, 0, getWidth(), getHeight());
		}
		
		canvas.drawText(text, offx, offy, paint);
	}
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		 TODO Auto-generated method stub
//		Log.d("ontouch","action="+event.getAction());
//		super.onTouchEvent(event);
//		x=(int) event.getX();
//		invalidate();
//		return true;
//	}
	public void setDirectionAndX(direction md,float percent){
		if(d!=md)d=md;
		x=(int) (getWidth()*percent);
		invalidate();
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		offx=getPaddingLeft();
		int textSize=(int) getTextSize();
		offy=getBaseline();
		text=(String) getText();
		paint.setTextSize(textSize);
	}

}

