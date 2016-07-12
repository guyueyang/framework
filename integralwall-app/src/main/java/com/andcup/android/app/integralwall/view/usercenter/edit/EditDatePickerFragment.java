package com.andcup.android.app.integralwall.view.usercenter.edit;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.event.DatePickEvent;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/24.
 */
public class EditDatePickerFragment extends GateDialogFragment {

    @Bind(R.id.dp_date_picker)
    DatePicker mDpDate;
    DateFormat mDf = new SimpleDateFormat("yyyy-MM-dd");
    String      mDate;
    @Restore(BundleKey.KEY_TYPE)
    String      mKeyType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_date_picker;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected  void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        mDate = mDf.format(new Date());
        mDpDate.setMaxDate(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        mDpDate.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                mDate = "" + year + "-" + (month + 1) + "-" + day;
            }
        });
    }

    @Override
    protected void onOk(){
        mDate = "" + mDpDate.getYear() + "-" + (mDpDate.getMonth() + 1) + "-" + mDpDate.getDayOfMonth();
        if( null != mDate){
            EventBus.getDefault().post(new DatePickEvent(mDate, Integer.valueOf(mKeyType)));
        }
        dismissAllowingStateLoss();
    }
    @Override
    protected void onCancel(){
        dismissAllowingStateLoss();
    }
}
