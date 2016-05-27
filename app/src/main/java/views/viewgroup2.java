package views;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class viewgroup2 extends ViewGroup {
    Context mContext;
    private View child;
    private boolean isopen, notfirst, isreturn;
    Scroller mScroller;
    viewgroup1 menuview;
    float lastx, lasty, movex;
    int fade;
    Paint paint;
    Matrix ma;
    private myrelative.OnSliderListener listener;
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    public viewgroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        mContext = context;
        ma = new Matrix();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffffffff);
        paint.setShadowLayer(20, 0, 0, Color.BLACK);
        mScroller = new Scroller(context, sInterpolator);
        isopen = false;
        isreturn = false;
        notfirst = false;

    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        child = getChildAt(0);
    }

    public void setMenuview(viewgroup1 menuview) {
        this.menuview = menuview;
    }

    public int getfade() {
        return fade;
    }

    public boolean isopen() {
        return isopen;
    }

    public Boolean isintercept(MotionEvent ev) {
        float dx = ev.getX() - lastx;
        float dy = ev.getY() - lasty;
        return Math.abs(dx) > Math.abs(dy) & dx < 0;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO �Զ����ɵķ������
        Log.d("viewgroup2", "dispatch" + ev.getAction() + "**" + ev.getX());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO �Զ����ɵķ������
        Log.d("viewgroup2", "onintercept" + ev.getAction() + "***" + ev.getX());
        if (ev.getAction() == 0) {
            lastx = ev.getX();
            lasty = ev.getY();
            notfirst = false;
            if (isopen && lastx > fade) {
                isreturn = true;
            }
            return isopen && lastx > fade;
        }
        if (ev.getAction() == 2) {
            float dx = ev.getX() - lastx;
            float dy = ev.getY() - lasty;
            if (!notfirst && (Math.abs(dx) > Math.abs(dy) && dx > 0 && !isopen)) {
                return true;
            }
            notfirst = true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO �Զ����ɵķ������
        super.onTouchEvent(event);
        Log.d("viewgroup2", event.getX() + "ontouch" + event.getAction());
        int action = event.getAction();
        switch (action) {
            case 2:
                movex = event.getX();
                if ((int) (lastx - movex) < 0 && (int) (lastx - movex) > -fade
                        && !isopen)
                    scrollTo((int) (lastx - movex), 0);
                if ((int) (lastx - movex) < fade && (int) (lastx - movex) > 0
                        && isopen)
                    scrollTo((int) (lastx - movex) - fade, 0);
                return true;
            case 1:
                float upx = event.getX();
                float upy = event.getY();
                long dtime = event.getEventTime() - event.getDownTime();
                if (isreturn && Math.abs(upx - lastx) < 10
                        && Math.abs(upy - lasty) < 10 && upy > 0.1 * getHeight()
                        && upy < 0.9 * getHeight() && dtime < 130) {
                    mScroller.startScroll(fade, 0, -fade, 0, 600);
                    isreturn = false;
                    isopen = false;
                    invalidate();
                    return true;
                }
                int scrollx = getScrollX();
                if (-scrollx <= fade / 2) {
                    int time = -scrollx * 800 / fade;
                    mScroller.abortAnimation();
                    mScroller.startScroll(-scrollx, 0, scrollx, 0, time);
                    // isleft=true;
                    Log.d("viewgroup2", "<<" + scrollx);
                    isopen = false;
                }
                if (-scrollx > fade / 2 & -scrollx <= fade) {
                    int time = (fade + scrollx) * 800 / fade;
                    mScroller.abortAnimation();
                    mScroller.startScroll(-scrollx, 0, fade + scrollx, 0, time);
                    // isleft=false;
                    Log.d("viewgroup2", ">>" + scrollx);
                    isopen = true;
                }
                invalidate();
        }
        return true;

    }

    @Override
    public void computeScroll() {
        // TODO �Զ����ɵķ������
        if (!mScroller.isFinished()) {
            if (mScroller.computeScrollOffset()) {
                int oldX = getScrollX();
                int x = mScroller.getCurrX();
                if (oldX != x) {
                    scrollTo(-x, 0);
                }
                invalidate();
            }
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        // TODO �Զ����ɵķ������
        super.scrollTo(x, y);
        float bili = 1.0f + 0.2f * (float) (x) / fade;
        child.setScaleY(bili);
        child.setScaleX(bili);
        menuview.setpercent(-(float) (x) / fade, x);
        if (listener != null) {
            listener.onscroll(-(float) (x) / fade,x);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO �Զ����ɵķ������
        child.layout(0, 0, r - l, b - t);
        child.setPivotY((b - t) / 2);
        child.setPivotX(0);
        fade = (r - l) * 2 / 3;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // TODO �Զ����ɵķ������
        Log.d("v2draw", "***");
        canvas.save();
        canvas.scale(1.0f, child.getScaleY(), 0, child.getHeight() / 2);
        canvas.drawRect(0, 0, child.getWidth(), child.getHeight(), paint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO �Զ����ɵķ������
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    public myrelative.OnSliderListener getListener() {
        return listener;
    }

    public void setListener(myrelative.OnSliderListener listener) {
        this.listener = listener;
    }
}
