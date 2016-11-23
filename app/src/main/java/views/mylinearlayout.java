package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by lenovo on 2016/11/23.
 */

public class mylinearlayout extends LinearLayout {
    public mylinearlayout(Context context) {
        super(context);
    }

    public mylinearlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public mylinearlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    boolean hasmeasured = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!hasmeasured) {
            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = MeasureSpec.getSize(heightMeasureSpec);
            ViewGroup.LayoutParams lp = getLayoutParams();
            lp.width = w;
            lp.height = h;
            setLayoutParams(lp);
            hasmeasured = true;
        }
    }
}
