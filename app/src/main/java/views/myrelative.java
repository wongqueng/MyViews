package views;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class myrelative extends RelativeLayout {
    viewgroup1 mygroup1;
    ViewGroup mtargetview = null, mcancelview = null;
    Paint paint;
    viewgroup2 mygroup2;
    boolean isskip, hadcharged, willcharge, willdraw, isdown, isup;
    float lastx, lasty, movex, movey;
    public myrelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        isskip = false;
        hadcharged = false;
        willcharge = true;
        isdown = false;
        isup = false;
        willdraw = false;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffff0000);
        // TODO �Զ����ɵĹ��캯�����
    }

    public myrelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO �Զ����ɵĹ��캯�����
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO �Զ����ɵķ������
        Log.d("relative", "dispatch" + ev.getAction());
        if (mtargetview != null) {
            mtargetview.dispatchTouchEvent(ev);
            if (ev.getAction() == 1) {
                mtargetview = null;
            }
            return true;
        } else {
            if (ev.getAction() == 1 & isskip) {
                MotionEvent cancelev1 = MotionEvent.obtain(ev);
                cancelev1.setAction(3);
                mygroup1.dispatchTouchEvent(ev);
                mygroup2.dispatchTouchEvent(cancelev1);
                cancelev1.recycle();
                return true;
            }
        }
        if (ev.getAction() == 0) {
            lastx = ev.getX();
            lasty = ev.getY();
            hadcharged = false;
            isskip = false;
            willcharge = true;
            if (mygroup2.isopen()) {
                if (lastx < mygroup2.getfade()) {
                    MotionEvent ev1 = MotionEvent.obtain(ev);
                    MotionEvent ev2 = MotionEvent.obtain(ev);
                    mygroup1.dispatchTouchEvent(ev1);
                    mygroup2.dispatchTouchEvent(ev2);
                    ev1.recycle();
                    ev2.recycle();
                    isskip = true;
                    return true;
                } else {
                    willcharge = false;
                }
            }
        }
        if (willcharge & !hadcharged & ev.getAction() == 2) {
            movex = ev.getX();
            movey = ev.getY();
            float dx = movex - lastx;
            float dy = movey - lasty;
            if (Math.abs(dx) <= 10 & Math.abs(dy) <= 10) {
                return true;
            } else {
                hadcharged = true;
            }
        }
        if (isskip && ev.getAction() == 2) {
            mtargetview = mygroup2.isintercept(ev) ? mygroup2 : mygroup1;
            mcancelview = mtargetview == mygroup1 ? mygroup2 : mygroup1;
            mtargetview.dispatchTouchEvent(ev);
            MotionEvent cancel = MotionEvent.obtain(ev);
            cancel.setAction(3);
            mcancelview.dispatchTouchEvent(cancel);
            cancel.recycle();
            mcancelview = null;
            isskip = false;
            return true;
        }
        mygroup2.dispatchTouchEvent(ev);//super.dispatchTouchEvent(ev);
        return true;
        //return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO �Զ����ɵķ������
        mygroup1 = (viewgroup1) getChildAt(0);
        mygroup2 = (viewgroup2) getChildAt(1);
        mygroup2.setMenuview(mygroup1);
        mygroup1.layout(0, 0, r - l, b - t);
        mygroup2.layout(0, 0, r - l, b - t);
    }
    public OnSliderListener getListener() {
        return mygroup2.getListener();
    }

    public void setListener(OnSliderListener listener) {
        mygroup2.setListener(listener);
    }
    public interface OnSliderListener {
        void onscroll(float percent,int x);
    }

}
