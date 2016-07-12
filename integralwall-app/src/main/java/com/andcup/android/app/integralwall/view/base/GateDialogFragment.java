package com.andcup.android.app.integralwall.view.base;


import com.andcup.android.app.integralwall.event.DialogEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public abstract class GateDialogFragment extends BaseFragment{

    protected void dismissAllowingStateLoss(){
        EventBus.getDefault().post(DialogEvent.DISMISS);
    }

    @Subscribe
    public void onClick(DialogEvent dialogEvent){
        if(dialogEvent == DialogEvent.OK){
            onOk();
        }else if(dialogEvent == DialogEvent.CANCEL){
            onCancel();
        }
    }

    protected void onOk(){

    }

    protected void onCancel(){
        dismissAllowingStateLoss();
    }

}
