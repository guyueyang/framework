package com.andcup.android.app.integralwall.view.start.login.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.yl.android.cpa.R;

/**
 * Created by Administrator on 2016/3/14.
 */
public abstract class AbsRegisterFragment extends BaseFragment {
    private static final int COUNT_DOWN = 120;
    private int              mCountDownValue = 0;
    private Button mBtnCountDown;

    private Handler mCountDownHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if( null == mBtnCountDown){
                return;
            }
            mCountDownValue++;
            if(mCountDownValue >= COUNT_DOWN){
                onCountDownFinish();
                return;
            }
            String value = getActivity().getResources().getString(R.string.login_send_verify);
            String countDownValue = String.format(value, COUNT_DOWN - mCountDownValue);
            mBtnCountDown.setText(countDownValue);
            mCountDownHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    @Override
    protected void afterActivityCreate(Bundle bundle) {
        super.afterActivityCreate(bundle);
        mBtnCountDown = bindRetryVerifyCodeButton();
    }

    protected abstract Button bindRetryVerifyCodeButton();

    protected void startCountDown(){
        mCountDownValue = 0;
        mCountDownHandler.sendEmptyMessage(0);
        mBtnCountDown.setEnabled(false);
    }

    protected void stopCountDown(){
        mCountDownValue = 0;
        mCountDownHandler.removeMessages(0);
        mBtnCountDown.setEnabled(true);
        mBtnCountDown.setText(getResources().getString(R.string.login_try_send_verify));
    }

    protected  void   onCountDownFinish(){
        mBtnCountDown.setEnabled(true);
        mBtnCountDown.setText(getResources().getString(R.string.login_try_send_verify));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCountDown();
    }
}
