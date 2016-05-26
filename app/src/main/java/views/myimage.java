package views;

import com.example.lenovo.myviews.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class myimage extends View {
	Bitmap bm;
	ColorMatrixColorFilter cmcf1, cmcf2, cmcf3, cmcf4;
	Paint p;
	int w, h;

	public myimage(Context context) {
		this(context, null);
	}

	public myimage(Context context, AttributeSet attrs) {
		super(context, attrs);
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.a);
		Log.d("create",
				"count=" + bm.getByteCount() + "rowbytes=" + bm.getRowBytes());
		w = bm.getWidth();
		h = bm.getHeight();
		p = new Paint(Paint.ANTI_ALIAS_FLAG);
		cmcf1 = new ColorMatrixColorFilter(new float[] { 1, 0, 0, 0, 0,
				0, 1, 0, 0, 0, 
				0, 0, 1, 0, 0, 
				0, 0, 0, 1, 0 });
		cmcf2 = new ColorMatrixColorFilter(new float[] { 0, 0, 1, 0, 0, 0, 0,
				1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 });
		cmcf3 = new ColorMatrixColorFilter(new float[] { 0, 1, 0, 0, 0, 0, 1,
				0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0 });
		cmcf4 = new ColorMatrixColorFilter(new float[] { 1, 0, 0, 0, 0, 1, 0,
				0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 });
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		p.setColorFilter(cmcf1);
		canvas.drawBitmap(bm, 0, 0, p);
		p.setColorFilter(cmcf2);
		canvas.drawBitmap(bm, w, 0, p);
		p.setColorFilter(cmcf3);
		canvas.drawBitmap(bm, 0, h, p);
		p.setColorFilter(cmcf4);
		canvas.drawBitmap(bm, w, h, p);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == 0) {
			int color = bm.getPixel((int) event.getX() % w, (int) event.getY()
					% h);
			Log.d("bmcolor", "" + color);
		}

		return true;
	}

}
