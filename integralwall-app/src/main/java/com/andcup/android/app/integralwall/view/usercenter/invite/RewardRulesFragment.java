package com.andcup.android.app.integralwall.view.usercenter.invite;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.widget.RadioButton;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.model.RewardEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.view.base.GateDialogFragment;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/3/29.
 */
public class RewardRulesFragment extends GateDialogFragment{

    @Bind(R.id.tv_content)
    TextView mTvContent;
    List<RewardEntity> mRewardList;
    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        load();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reward_rules;
    }
    private void load(){
        loader(RewardEntity.class, Where.user(), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                mRewardList = SqlConvert.convertAll(cursor, RewardEntity.class);
                for( int j = 0; j< mRewardList.size(); j++){
                    mTvContent.setText(Html.fromHtml(mRewardList.get(j).getRule()));
                }
            }
        });
    }
}
