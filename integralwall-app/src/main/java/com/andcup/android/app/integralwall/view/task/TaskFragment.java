package com.andcup.android.app.integralwall.view.task;

import com.andcup.android.app.integralwall.view.task.feature.FeatureTaskFragment;
import com.andcup.android.app.integralwall.view.task.limit.LimitTaskFragment;
import com.andcup.android.app.integralwall.view.task.union.UnionTaskFragment;
import com.andcup.android.integralwall.commons.tab.Tab;
import com.andcup.android.integralwall.commons.tab.TabFragment;
import com.yl.android.cpa.R;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/10.
 */
public class TaskFragment extends TabFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task;
    }

    @Override
    protected Tab[] getTabs() {
        Tab quickTask = new Tab(FeatureTaskFragment.class, getString(R.string.quick_task));
        Tab limitTask = new Tab(LimitTaskFragment.class, getString(R.string.limit_task));
        Tab unionTask = new Tab(UnionTaskFragment.class, getString(R.string.union_task));
        return new Tab[]{quickTask, limitTask, unionTask};
    }
}
