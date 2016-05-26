package views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import com.example.lenovo.myviews.R;

public class clipView extends View {
	Paint paint;
	Path path;
	Bitmap bm;

	public clipView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		path = new Path();
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.lyfa);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		path.addRoundRect(new RectF(0, 0, w / 2, h / 2), 10, 10, Direction.CW);
		path.addCircle(w - 20, h - 20, 20, Direction.CW);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.clipPath(path);
		canvas.drawBitmap(bm, 0, 0, paint);
	}

}
