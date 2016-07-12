package com.andcup.android.app.integralwall.view.usercenter.edit;

import android.os.Bundle;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.event.SexEvent;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.integralwall.commons.numberpicker.NumberPicker;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/24.
 */
public class EditSexFragment extends GateDialogFragment {

    @Bind(R.id.np_sex)
    NumberPicker mNumberPicker;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_sex;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        String[] sexArray = getResources().getStringArray(R.array.array_sex);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(2);
        int sex = UserProvider.getInstance().getUserInfo().getmSex();
        mNumberPicker.setValue(sex == UserInfoEntity.SEX_NONE ? 1 : (sex == UserInfoEntity.SEX_MALE ? 2 : 0));
        mNumberPicker.setDisplayedValues(sexArray);
        mNumberPicker.setFocusable(true);
        mNumberPicker.setFocusableInTouchMode(true);
    }

    @Override
    protected void onOk(){
        SexEvent sexEvent;
        int value = mNumberPicker.getValue();
        switch (value){
            case 0:
                sexEvent = new SexEvent(UserInfoEntity.SEX_FEMALE);
                break;
            case 2:
                sexEvent = new SexEvent(UserInfoEntity.SEX_MALE);
                break;
            default:
                sexEvent = new SexEvent(UserInfoEntity.SEX_NONE);
        }

        EventBus.getDefault().post(sexEvent);
        dismissAllowingStateLoss();
    }

    @Override
    protected void onCancel(){
        dismissAllowingStateLoss();
    }

}
