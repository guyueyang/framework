package com.andcup.android.app.integralwall.view.usercenter.score;

import android.os.Bundle;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.view.usercenter.score.page.ScoreInFragment;
import com.andcup.android.app.integralwall.view.usercenter.score.page.ScoreOutFragment;
import com.andcup.android.frame.view.annotations.Restore;
import com.andcup.android.integralwall.commons.tab.Tab;
import com.andcup.android.integralwall.commons.tab.TabFragment;
import com.yl.android.cpa.R;

import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ScoreFragment extends TabFragment {

    @Restore(BundleKey.IS_SCORE_OUT)
    boolean mIsScoreOut = false;

    @Override
    protected Tab[] getTabs() {
        Tab ScoreInFragments = new Tab(ScoreInFragment.class, getString(R.string.score_in_details));
        Tab ScoreOutFragments = new Tab(ScoreOutFragment.class, getString(R.string.score_out_detail));
        return new Tab[]{ScoreInFragments, ScoreOutFragments};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_score;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        if(!mIsScoreOut){
            return;
        }
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mVpTab.setCurrentItem(1);
            }
        }, 10);
    }

    @OnClick(R.id.iv_back)
    public void close(){
        getActivity().finish();
    }
}
