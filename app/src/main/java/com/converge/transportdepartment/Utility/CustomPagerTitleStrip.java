package com.converge.transportdepartment.Utility;

import android.content.Context;
import android.support.v4.view.PagerTitleStrip;
import android.util.AttributeSet;

/**
 * Created by converge on 10/8/16.
 */
public class CustomPagerTitleStrip extends PagerTitleStrip {

    public CustomPagerTitleStrip(Context context) {
        super(context);
    }

    public CustomPagerTitleStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY),
                heightMeasureSpec);
    }
}
