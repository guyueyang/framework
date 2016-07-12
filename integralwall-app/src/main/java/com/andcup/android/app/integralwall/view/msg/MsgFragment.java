package com.andcup.android.app.integralwall.view.msg;

import android.os.Bundle;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.view.IntegralWallActivity;
import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.job.JobReadMessage;
import com.andcup.android.app.integralwall.datalayer.model.MsgEntity;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.job.JobEntity;
import com.andcup.android.integralwall.commons.tab.Tab;
import com.andcup.android.integralwall.commons.tab.TabFragment;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/25.
 */
public class MsgFragment extends TabFragment{

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_with_back;
    }

    @Override
    protected Tab[] getTabs() {
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.MSG_STATE, String.valueOf(MsgEntity.STATE_READ_NO));
        Tab unReaded  = new Tab(MsgChildFragment.class, getString(R.string.msg_un_readed), bundle);

        bundle = new Bundle();
        bundle.putString(BundleKey.MSG_STATE, String.valueOf(MsgEntity.STATE_READ_YES));
        Tab readed = new Tab(MsgChildFragment.class, getString(R.string.msg_readed), bundle);
        return new Tab[]{unReaded, readed};
    }

    @Subscribe
    public void openMsg(MsgEntity msgEntity){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.MSG_ID, msgEntity.getMsgId());
        IntegralWallActivity.go(getActivity(), MsgDetailFragment.class, bundle);
        //call(msgEntity);
    }

    @OnClick(R.id.btn_back)
    public void onFinish(){
        getActivity().finish();
    }

    private void call(MsgEntity msgEntity){
        if(msgEntity.getStatus() == MsgEntity.STATE_READ_YES){
            return;
        }
        call(new JobReadMessage(msgEntity), new SimpleCallback<JobEntity>());
    }
}
