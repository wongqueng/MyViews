package views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class myCreateBitmap extends View {

	private static final int WIDTH = 200;
	private static final int HEIGHT = 200;
	private static final int STRIDE = 220; // must be >= WIDTH
	Bitmap bm;
	Paint p;

	public myCreateBitmap(Context context, AttributeSet attrs) {
		super(context, attrs);
		int[] colors = createColors();
		bm = Bitmap.createBitmap(colors, 0, STRIDE, WIDTH, HEIGHT,
				Bitmap.Config.ARGB_8888);
		p = new Paint(Paint.ANTI_ALIAS_FLAG);
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

	private static int[] createColors() {
		int[] colors = new int[STRIDE * HEIGHT];
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				int r = x * 255 / (WIDTH - 1);
				int g = y * 255 / (HEIGHT - 1);
				int b = 0;
				int a = 255;
				colors[y * STRIDE + x] = (a << 24) | (r << 16) | (g << 8) | b;
			}
		}
		return colors;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(bm, 0, 0, p);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == 0) {
			int color = bm.getPixel((int) event.getX() % WIDTH,
					(int) event.getY() % HEIGHT);
			Log.d("bmcolor", "R=" + getR32(color) + ";G=" + getG32(color)
					+ ";B=" + getB32(color) + ";A=" + getA32(color));
		}
		return true;
	}
}
