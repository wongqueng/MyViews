package views;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class viewgroup1 extends ViewGroup {
    private View child;
    int shawder;
    int saveint;
    Camera mCamera;
    Matrix mm;
    private int type = 1;
    public viewgroup1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCamera = new Camera();
        mm = new Matrix();
    }

    @Override
    protected void onFinishInflate() {
        // TODO Auto-generated method stub
        super.onFinishInflate();
        child = getChildAt(0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO �Զ����ɵķ������
        Log.d("viewgroup1", "dispatch" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO �Զ����ɵķ������
        Log.d("viewgroup1", "onintercept" + ev.getAction());
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO �Զ����ɵķ������
        Log.d("viewgroup1", "ontouch" + event.getAction());
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO �Զ����ɵķ������
        Log.d("view1", l + "&&" + t + "**" + r + "**" + b);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        child.setPivotY(child.getHeight() / 2);
        child.setPivotX(-r + l);
    }

    public void setpercent(float percent, int offx) {

        shawder = (int) (255 * (1.0f - percent));
        if (getType() == 0) {
            mCamera.save();
            mCamera.rotateY(30 * (1.0f - percent));
            mCamera.getMatrix(mm);
            mm.preTranslate(0, -child.getHeight() / 2);
            mm.postTranslate(0, child.getHeight() / 2);
            mCamera.restore();
        } else if (getType() == 1) {
            child.setScaleY(0.8f+0.2f*percent);
            child.setScaleX(0.8f+0.2f*percent);
            child.setAlpha(0.5f+0.5f*percent);
        }
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // TODO �Զ����ɵķ������
        if (getType() == 0) {
            saveint = canvas.save();
            canvas.concat(mm);
            super.dispatchDraw(canvas);
            canvas.restoreToCount(saveint);
        } else if (getType() == 1) {
            super.dispatchDraw(canvas);
        }
        canvas.drawARGB(shawder, 0, 0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO �Զ����ɵķ������
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int newmeasure = MeasureSpec.makeMeasureSpec(width * 2 / 3,
                MeasureSpec.getMode(widthMeasureSpec));
        measureChildren(newmeasure, heightMeasureSpec);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
