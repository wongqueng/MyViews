package views;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class myviewpager extends ViewPager {
	private OnPageChangeListener listener;
	private onMyPageChangeListener mylistener;
	private Indicator tabs;

	public myviewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		throw new RuntimeException(
				"you shall not use this method,Please use setOnMyPageChangeListener()!!");
	}

	public interface onMyPageChangeListener {
		public void onPageScrollStateChanged(int arg0);

		public void onPageScrolled(int arg0, float arg1, int arg2);

		public void onPageSelected(int arg0);
	}

	public void setOnMyPageChangeListener(onMyPageChangeListener listen) {
		mylistener = listen;
	}

	public void setTabs(Indicator tabs) {
		if (tabs == null) {
			throw new RuntimeException("the params shall not be null");
		}
		this.tabs = tabs;
		listener = new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				myviewpager.this.tabs.onViewPageSelected(arg0);
				if (mylistener != null)
					mylistener.onPageSelected(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				myviewpager.this.tabs.setItemAndX(arg0, arg1);
				if (mylistener != null)
					mylistener.onPageScrolled(arg0, arg1, arg2);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				if (mylistener != null)
					mylistener.onPageScrollStateChanged(arg0);
			}
		};
		super.setOnPageChangeListener(listener);
	}

	@Override
	public void setAdapter(PagerAdapter arg0) {
		// TODO Auto-generated method stub
		super.setAdapter(arg0);
		tabs.setCount(arg0.getCount());
	}
}
