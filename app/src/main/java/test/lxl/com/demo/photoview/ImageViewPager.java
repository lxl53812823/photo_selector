package test.lxl.com.demo.photoview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import test.lxl.com.demo.FastJumpViewPagerHelper;
import test.lxl.com.demo.NoDurationScroll;

public class ImageViewPager extends ViewPager {
    private FastJumpViewPagerHelper helper;
    private boolean isJumpFast;

    public void setJumpFast(boolean jumpFast) {
        isJumpFast = jumpFast;
    }

    public ImageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        helper = new FastJumpViewPagerHelper(this);
    }

    public ImageViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    //
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        NoDurationScroll scroller = helper.getScroller();
        if (Math.abs(getCurrentItem() - item) > 1 && isJumpFast) {
            scroller.setNoDuration(true);
            super.setCurrentItem(item, smoothScroll);
            scroller.setNoDuration(false);
        } else {
            scroller.setNoDuration(false);
            super.setCurrentItem(item, smoothScroll);
        }
    }


}
