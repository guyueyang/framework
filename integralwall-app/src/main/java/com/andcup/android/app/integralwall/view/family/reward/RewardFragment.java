package com.andcup.android.app.integralwall.view.family.reward;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetRewards;
import com.andcup.android.app.integralwall.datalayer.model.RewardEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseListEntry;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.tools.ShareHelper;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.share.ShareContent;
import com.andcup.android.frame.datalayer.sql.SqlConvert;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/16.
 */
public class RewardFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    @Bind(R.id.rg_tabs)
    RadioGroup mRgReward;
    @Bind(R.id.tv_content)
    TextView mTvContent;
    @Bind(R.id.tv_rule)
    TextView mTvRule;
    List<RewardEntity>  mRewardList;
    RewardEntity        mRewardEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reward;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        call();
        load();
        mRgReward.setOnCheckedChangeListener(this);
    }

    private void call(){
        call(new JobGetRewards(), new SimpleCallback<BaseListEntry<RewardEntity>>());
    }

    @OnClick(R.id.btn_copy)
    public void onCopy(){
        if( null == mRewardEntity){
            return;
        }
        AndroidUtils.copy(mRewardEntity.getContent() + mRewardEntity.getUrl(), getActivity());
        SnackBar.make(getActivity(), getString(R.string.copy_success)).show();
    }

    @OnClick(R.id.btn_invite)
    public void onInvite(){
        if( null == mRewardEntity){
            return;
        }
        ShareContent content = new ShareContent(mRewardEntity.getContent(), mRewardEntity.getUrl(), mRewardEntity.getImageUrl(), null);
        ShareHelper.share(getChildFragmentManager(), content);
    }

    private void load(){
        loader(RewardEntity.class, Where.user(), new SqlLoader.CursorCallBack() {
            @Override
            public void onUpdate(Cursor cursor) {
                mRewardList = SqlConvert.convertAll(cursor, RewardEntity.class);
                genRewardTabs();
            }
        });
    }

    private void genRewardTabs(){
        mRgReward.removeAllViews();
        if(null == mRewardList || mRewardList.size() <=0){
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for(int i = 0; null != mRewardList && i < mRewardList.size(); i++ ){
            inflater.inflate(R.layout.group_item_reward, mRgReward);
            RadioButton rb = (RadioButton) mRgReward.getChildAt(i);
            // set view id.
            rb.setId(mRewardList.get(i).getItemId());
            // set view text.
            rb.setText(mRewardList.get(i).getTitle());
        }
        mRgReward.check(mRewardList.get(0).getItemId());
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int viewId) {
        for( int j = 0; j< mRewardList.size(); j++){
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(viewId);
            // find checked item.
            if(mRewardList.get(j).getItemId() == viewId && radioButton.isChecked()){
                // goto checked item.
                mRewardEntity = mRewardList.get(j);
                mTvContent.setText(mRewardEntity.getContent());
                mTvRule.setText(Html.fromHtml(mRewardEntity.getRule()));
            }
        }
    }
}
