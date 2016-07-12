package com.andcup.android.integralwall.commons.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/17.
 */
public class AdapterListView extends ListView {
    public AdapterListView(Context context) {
        super(context);
    }
    public AdapterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AdapterListView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
