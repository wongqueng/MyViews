package views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lenovo on 2016/7/22.
 */
public class PathMeasureView extends View {
    Paint paint;
    Paint mpaint;
     Path path;
PathMeasure pm;
    float[] pos=new float[2];
    float[] tan=new float[2];
    float length;//修改标记
    final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
    private float mAnimatorValue;

    public PathMeasureView(Context context) {
       this(context,null);
    }

    public PathMeasureView(Context context, AttributeSet attrs) {
       this(context, attrs,0);

    }

    public PathMeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       paint=new Paint(Paint.ANTI_ALIAS_FLAG);
       mpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLUE);
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setStrokeWidth(10);
        mpaint.setColor(Color.RED);
        path=new Path();
        path.moveTo(200,400);
        path.quadTo(-100,0,200,150);
        path.quadTo(500,0,200,400);
        path.close();
        pm=new PathMeasure(path,true);
        length=pm.getLength();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(4000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pm.getPosTan(mAnimatorValue*pm.getLength(),pos,tan);
        canvas.drawPath(path,paint);
        canvas.drawCircle(pos[0],pos[1],20,mpaint);
    }
}
