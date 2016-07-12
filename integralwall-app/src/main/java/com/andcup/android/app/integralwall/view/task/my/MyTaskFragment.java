package com.andcup.android.app.integralwall.view.task.my;

import android.os.Bundle;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.integralwall.commons.tab.Tab;
import com.andcup.android.integralwall.commons.tab.TabFragment;
import com.yl.android.cpa.R;

import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/28.
 */
public class MyTaskFragment extends TabFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_min;
    }

    @Override
    protected Tab[] getTabs() {
        Tab finishTaskTab = new Tab(FinishTaskFragment.class, getString(R.string.finish_task));
        Bundle bundle = new Bundle();
        bundle.putBoolean(BundleKey.TITLE, false);
        Tab todoTaskTab   = new Tab(TodoTaskFragment.class, getString(R.string.un_finish_task), bundle);
        return new Tab[]{todoTaskTab, finishTaskTab};
    }

    @OnClick(R.id.iv_back)
    public void onBackPressed(){
        getActivity().finish();
    }

}
