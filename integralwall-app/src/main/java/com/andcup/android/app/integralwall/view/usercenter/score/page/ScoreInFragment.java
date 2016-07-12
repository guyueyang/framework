package com.andcup.android.app.integralwall.view.usercenter.score.page;

import com.andcup.android.app.integralwall.datalayer.model.ScoreDetailEntity;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.usercenter.score.AbsScoreFragment;
import com.yl.android.cpa.R;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ScoreInFragment extends AbsScoreFragment {

    @Override
    public int getType() {
        return ScoreDetailEntity.TYPE_IN;
    }
}
