package views;

import com.example.lenovo.myviews.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class blowView extends View {
	Bitmap aboveBm, backBm;
	Paint abovePaint, cotentPaint, textpaint;
	Canvas aboveCanvas;
	Path path;
	int index,defaultwidth,defaultheight;
	boolean intouch, hadstarted = false, hadtouched = false;
	String text = "�ο�Ӯȡ����!!!";
	Runnable redraw = new Runnable() {
		@Override
		public void run() {
			Log.d("redraw", "#$^&*()");
			abovePaint.setStyle(Style.FILL);
			aboveCanvas.drawRect(0, 0, getWidth(), getHeight(), abovePaint);
			invalidate();
		}
	};

	public blowView(Context context) {
		this(context, null);

	}

	public blowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		cotentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textpaint.setColor(Color.RED);
		path = new Path();
		abovePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		abovePaint.setStyle(Style.STROKE);
		abovePaint.setStrokeWidth(40);
		abovePaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
		abovePaint.setStrokeCap(Cap.ROUND);
		backBm = BitmapFactory.decodeResource(getResources(), R.drawable.lyf2);
		defaultwidth=backBm.getWidth();
		defaultheight=backBm.getHeight();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.d("sizechaged", "w=" + w + ";h=" + h);
		backBm = Bitmap.createScaledBitmap(backBm, w, h, true);
		aboveBm = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		aboveCanvas = new Canvas(aboveBm);
		aboveCanvas.drawColor(Color.GRAY);
		textpaint.setTextSize(h / 10);
		int textwidth = (int) textpaint.measureText(text);
		aboveCanvas.drawText(text, (getWidth() - textwidth) / 2,
				getHeight() / 2, textpaint);
		// new Thread(new checkPixels(++index)).start();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(backBm, 0, 0, cotentPaint);
		canvas.drawBitmap(aboveBm, 0, 0, cotentPaint);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		Log.d("bvdis", "x=" + event.getX() + "y=" + event.getY());
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case 0:
			intouch = true;
			path.moveTo(x, y);
			break;
		case 2:
			if (!hadtouched)
				hadtouched = true;
			drawpath(x, y);
			break;
		case 1:
			if (!hadtouched)
				hadtouched = true;
			intouch = false;
			drawpath(x, y);
			path.reset();
			break;
		}
		return true;
	}

	public void drawpath(float x, float y) {
		path.lineTo(x, y);
		aboveCanvas.drawPath(path, abovePaint);
		invalidate();
	}

	class checkPixels implements Runnable {
		private int flags;

		public checkPixels(int index) {
			flags = index;
		}

		@Override
		public void run() {
			int STRIDE = getWidth() + 2;
			int count = getWidth() * getHeight();
			boolean haddraw = false;
			Log.d("running", "count=" + count);
			int[] pixels = new int[STRIDE * getHeight()];
			while (flags == index && !haddraw) {
				int sum = 0;
				aboveBm.getPixels(pixels, 0, STRIDE, 0, 0, getWidth(),
						getHeight());
				for (int i : pixels) {
					if (i % STRIDE < getWidth() && i == 0) {
						sum++;
					}
				}
				Log.d("running", "sum=" + sum);
				if (sum > getWidth() * getHeight() / 2) {
					while (intouch) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					post(redraw);
					haddraw = true;
				}
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public int getMeasureWidth(int presize) {
		int mode=MeasureSpec.getMode(presize);
		int size=MeasureSpec.getSize(presize);
		int resultmode=1<<30;
		int resultsize=0;
		switch (mode) {
		case MeasureSpec.AT_MOST:
			resultsize=Math.min(defaultwidth, size);
			break;
		case MeasureSpec.EXACTLY:
			resultsize=size;
			break;
		case MeasureSpec.UNSPECIFIED:
			resultsize=defaultwidth;
			break;
		}
		return resultmode+resultsize;
	}
	public int getMeasureHeight(int presize) {
		int mode=MeasureSpec.getMode(presize);
		int size=MeasureSpec.getSize(presize);
		int resultmode=1<<30;
		int resultsize=0;
		switch (mode) {
		case MeasureSpec.AT_MOST:
			resultsize=Math.min(defaultheight, size);
			break;
		case MeasureSpec.EXACTLY:
			resultsize=size;
			break;
		case MeasureSpec.UNSPECIFIED:
			resultsize=defaultheight;
			break;
		}
		return resultmode+resultsize;
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		index++;
	}

	private static int getR32(int c) {
		return (c >> 16) & 0xFF;
	}

	private static int getG32(int c) {
		return (c >> 8) & 0xFF;
	}

	private static int getB32(int c) {
		return (c >> 0) & 0xFF;
	}

	private static int getA32(int c) {
		return (c >> 24) & 0xFF;
	}
}
