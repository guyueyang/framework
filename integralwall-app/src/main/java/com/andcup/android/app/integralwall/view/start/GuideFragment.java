package com.andcup.android.app.integralwall.view.start;

import android.os.Bundle;
import android.widget.ImageView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.yl.android.cpa.R;

import butterknife.Bind;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/29.
 */
public class GuideFragment extends BaseFragment{
    @Bind(R.id.iv_image)
    ImageView mIvImage;

    @Restore(BundleKey.DATA)
    String mPosition;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_guide;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        int position = Integer.valueOf(mPosition);
        switch (position){
            case 0:
                mIvImage.setBackgroundResource(R.drawable.bg_first);
                break;
            case 1:
                mIvImage.setBackgroundResource(R.drawable.bg_second);
                break;
            case 2:
                mIvImage.setBackgroundResource(R.drawable.bg_third);
                break;
            case 3:
                mIvImage.setBackgroundResource(R.drawable.bg_fourth);
                break;
        }
    }
}
