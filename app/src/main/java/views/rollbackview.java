package views;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;

public class rollbackview extends RelativeLayout {
    Context mContext;
    int mwidth, mheight, zdistance;
    float radius;
    Scroller ms;
    Camera mc;
    Matrix mm;
    float lastx, lasty;
    Boolean intercepyed, handled, hadcharged;
    View child1, child2;
    View aboveview, startView;

    public rollbackview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public rollbackview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        intercepyed = false;
        handled = false;
        hadcharged = false;
        ms = new Scroller(context);
        mc = new Camera();
        mm = new Matrix();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == 0) {
            lastx = ev.getX();
            lasty = ev.getY();
            intercepyed = false;
            hadcharged = false;
            handled = false;
            startView = aboveview;
            if (ms.computeScrollOffset()) {
                Log.d("rollback", "dwa" + aboveview);
                ms.abortAnimation();
                abortAnimation();
            }
        }
        if (ev.getAction() == 2) {
            float movex = ev.getX();
            float movey = ev.getY();
            float offdx = movex - lastx;
            float dy = movey - lasty;
            if (!hadcharged) {
                if (Math.abs(offdx) < 10 && Math.abs(dy) < 10) {
                    return true;
                } else {
                    hadcharged = true;
                }
            }
            if (!handled) {
                handled = true;
                if (Math.abs(offdx) > Math.abs(dy)) {
                    intercepyed = true;
                } else {
                    intercepyed = false;
                }
            }
        }
        if (intercepyed) {
            float offdx = ev.getX() - lastx;

            if (ev.getAction() == 1) {
                //currentView=aboveview;
                MotionEvent cancel = MotionEvent.obtain(ev);
                cancel.setAction(3);
                startView.dispatchTouchEvent(cancel);
                cancel.recycle();
                int time = (int) (mwidth / 2 - Math.abs((mwidth / 2 - Math.abs(offdx))));
                if (Math.abs(offdx) > mwidth / 2) {
                    if (offdx < 0) {
                        ms.startScroll((int) offdx, 0, -mwidth - (int) (offdx), 0, time);
                    } else {
                        ms.startScroll((int) offdx, 0, mwidth - (int) (offdx), 0, time);
                    }
                } else {
                    ms.startScroll((int) offdx, 0, -(int) (offdx), 0, time);
                }
                invalidate();
                return true;
            }
            setpercent(offdx / mwidth, (int) offdx);
        } else {
            startView.dispatchTouchEvent(ev);
        }
        return true;//super.dispatchTouchEvent(ev);
    }


    private void setpercent(float percent, int offx) {
        // TODO �Զ����ɵķ������
        //mm.reset();
        radius = percent * 180;
        mc.save();
        mc.translate(0, 0, (float) (zdistance * (0.5f - Math.abs(0.5 - Math.abs(percent)))));
        if (Math.abs(radius) > 90) {
            aboveview = startView == child1 ? child2 : child1;
            mc.rotateY(radius - 180);
        } else {
            aboveview = startView;
            mc.rotateY(radius);
        }
        mc.getMatrix(mm);
        mm.preTranslate(-mwidth / 2, -mheight / 2);
        mm.postTranslate(mwidth / 2, mheight / 2);
        mc.restore();
        invalidate();
    }

    private void abortAnimation() {
        // TODO
        //mm.reset();
        mc.save();
        mc.getMatrix(mm);
        mc.restore();
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (ms.computeScrollOffset()) {
            int x = ms.getCurrX();
            setpercent((float) x / mwidth, x);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        child1 = getChildAt(0);
        child2 = getChildAt(1);
        child1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "child1", Toast.LENGTH_SHORT).show();
            }
        });
        child2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "child2", Toast.LENGTH_SHORT).show();
            }
        });
        aboveview = startView = child2;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (child != aboveview) {
            return false;
        } else {
            canvas.save();
            canvas.concat(mm);
            boolean draw = super.drawChild(canvas, aboveview, getDrawingTime());
            canvas.restore();
            return draw;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mwidth = r - l;
        mheight = b - t;
        zdistance = mwidth / 2;
    }

}