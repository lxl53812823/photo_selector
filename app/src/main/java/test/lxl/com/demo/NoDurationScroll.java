package test.lxl.com.demo;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Luxulong on 2018/4/23.
 */

public class NoDurationScroll extends Scroller {

    public boolean noDuration;

    public void setNoDuration(boolean noDuration) {
        this.noDuration = noDuration;
    }

    public NoDurationScroll(Context context) {
        super(context);
    }

    public NoDurationScroll(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public NoDurationScroll(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        if(noDuration)
            //界面滑动不需要时间间隔
            super.startScroll(startX, startY, dx, dy, 0);
        else
            super.startScroll(startX, startY, dx, dy,duration);
    }
}
