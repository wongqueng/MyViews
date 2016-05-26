package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class arcpercent extends View {
	Paint p, textp;
	RectF r;
	float angle;
	anglerun ar;
	int per;

	public arcpercent(Context context) {
		this(context, null);
	}

	public arcpercent(Context context, AttributeSet attrs) {
		super(context, attrs);
		p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setColor(Color.BLUE);
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(10);
		p.setStrokeCap(Cap.ROUND);
		textp = new Paint(Paint.ANTI_ALIAS_FLAG);
		textp.setTextSize(20);
		textp.setColor(Color.CYAN);
		r = new RectF(10, 10, 110, 110);
		ar = new anglerun();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		canvas.rotate(-90, 60, 60);
		canvas.drawArc(r, 0, angle, false, p);
		canvas.restore();
		float startx = 60 - textp.measureText(per + "%") / 2;
		canvas.drawText(per + "%", startx, 70, textp);
	}

	class anglerun implements Runnable {

		@Override
		public void run() {
			angle += 2;
			if (angle == 360) {
				angle = 0;
			}
			per = (int) (angle * 100 / 360);
			invalidate();
			postDelayed(this, 10);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		postDelayed(ar, 2000);
	}
}
