package com.andcup.android.app.integralwall.view.guide;

import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobFirstLandingScore;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.yl.android.cpa.R;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/5/30.
 */
public class NewAboutFragment extends BaseFragment {

    @Bind(R.id.pv_about_app)
    PhotoView mIvKnowApp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about_app;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        setTitle(R.string.first_landing);
        mIvKnowApp.setImageResource(R.drawable.bg_about_app);
        mIvKnowApp.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoZoom();
            }
        }, 15);
        //开始计时.
        mHandler.sendEmptyMessageDelayed(0, 15000);
    }

    private void autoZoom() {
        float systemWidth = getWindowSize().x;
        RectF rect = mIvKnowApp.getDisplayRect();
        Log.e(NewAboutFragment.class.getName(), " width = " + rect.width());
        float rate = systemWidth / rect.width();
        mIvKnowApp.setMaximumScale(rate);
        mIvKnowApp.setMediumScale(rate - 0.01f);
        mIvKnowApp.setMinimumScale(rate-0.02f);
        mIvKnowApp.setScale(rate, 0, 0, false);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                call(new JobFirstLandingScore(), new SimpleCallback<BaseEntity>() {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
            } catch (Exception e) {

            }
        }
    };

    @Override
    public void onDestroyView() {
        mHandler.removeMessages(0);
        super.onDestroyView();
    }
}
