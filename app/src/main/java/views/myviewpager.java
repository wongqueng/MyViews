package views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class myviewpager extends ViewPager {
    private OnPageChangeListener listener;
    private OnPageChangeListener mylistener;
    private Indicator tabs;

    public myviewpager(Context context) {
        super(context);
    }

    public myviewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        // TODO Auto-generated constructor stub
    }
public void init(){
    listener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) {
            if (myviewpager.this.tabs != null) {
                myviewpager.this.tabs.onViewPageSelected(arg0);
            }
            if (mylistener != null) {
                mylistener.onPageSelected(arg0);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (myviewpager.this.tabs != null) {
                myviewpager.this.tabs.setItemAndX(arg0, arg1);
            }

            if (mylistener != null)
                mylistener.onPageScrolled(arg0, arg1, arg2);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (mylistener != null)
                mylistener.onPageScrollStateChanged(arg0);
        }
    };
    super.addOnPageChangeListener(listener);
}
    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        mylistener = listener;
    }

    @Override
    public void clearOnPageChangeListeners() {
        mylistener=null;
    }

    public void setTabs(Indicator tabs) {
        if (tabs == null) {
            throw new RuntimeException("the tabs shall not be null");
        }
        this.tabs = tabs;

    }

    @Override
    public void setAdapter(PagerAdapter arg0) {
        // TODO Auto-generated method stub
        super.setAdapter(arg0);
        tabs.setCount(arg0.getCount());
    }
}
