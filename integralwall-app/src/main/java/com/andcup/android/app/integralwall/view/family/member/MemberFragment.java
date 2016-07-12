package com.andcup.android.app.integralwall.view.family.member;

import android.os.Bundle;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.job.JobGetFamilyInfo;
import com.andcup.android.app.integralwall.datalayer.model.FamilyInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.tools.ShareHelper;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.family.level.MemberLevelFragment;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/16.
 */
public class MemberFragment extends BaseFragment{

    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;

    @Bind(R.id.tv_member)
    TextView mTvMember;
    @Bind(R.id.tv_member2)
    TextView mTvMember2;
    @Bind(R.id.tv_score)
    TextView mTvScore;
    @Bind(R.id.tv_score2)
    TextView mTvScore2;
    @Bind(R.id.tv_family_number)
    TextView   mTvFamilyNumber;
    @Bind(R.id.tv_family_score)
    TextView   mTvFamilyScore;

    @BindString(R.string.family_level_1_title)
    String mMemberLevel1Title;
    @BindString(R.string.family_level_2_title)
    String mMemberLevel2Title;

    FamilyInfoEntity mFamilyInfoEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_memeber;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        mTvMember.setText(FormatString.newMemberLevel(mMemberLevel1Title));
        mTvMember2.setText(FormatString.newMemberLevel(mMemberLevel2Title));
        go(DynamicFragment.class, R.id.fr_dynamic);
        load();
        call();
    }

    private void load(){
        loader(FamilyInfoEntity.class, Where.user(), new SqlLoader.CallBack<FamilyInfoEntity>() {
            @Override
            public void onUpdate(FamilyInfoEntity familyInfoEntity) {
                if( null != familyInfoEntity){
                    mFamilyInfoEntity = familyInfoEntity;
                    mTvScore.setText(getString(R.string.family_level_score, familyInfoEntity.getFirstLevelTotal(), familyInfoEntity.getFirstLevelScore()));
                    mTvScore2.setText(getString(R.string.family_level_score, familyInfoEntity.getSecondLevelTotal(), familyInfoEntity.getSecondLevelScore()));
                    mTvFamilyNumber.setText(getString(R.string.family_member, familyInfoEntity.getTotalMember()));
                    mTvFamilyScore.setText(getString(R.string.family_total_in, familyInfoEntity.getTotalCommission()));
                }else{
                    mTvFamilyNumber.setText(getString(R.string.family_member, "0"));
                    mTvFamilyScore.setText(getString(R.string.family_total_in, "0"));
                }
            }
        });
    }

    private void call(){
        call(new JobGetFamilyInfo(), new SimpleCallback<BaseEntity<FamilyInfoEntity>>());
    }

    @OnClick(R.id.btn_invite)
    public void onInviteClick(){
        ShareHelper.share(getChildFragmentManager(), null);
    }

    @OnClick(R.id.ll_level_1)
    public void onLevel1Click(){
        go2(MemberLevelFragment.class, levelBundle(LEVEL_1));
    }

    @OnClick(R.id.ll_level_2)
    public void onLevel2Click(){
        go2(MemberLevelFragment.class, levelBundle(LEVEL_2));
    }

    private Bundle levelBundle(int level){
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.LEVEL, String.valueOf(level));
        if(level == LEVEL_1){
            bundle.putString(BundleKey.LEVEL_MEMBERS, null == mFamilyInfoEntity ? "-": String.valueOf(mFamilyInfoEntity.getFirstLevelTotal()));
            bundle.putString(BundleKey.LEVEL_SCORE, null == mFamilyInfoEntity ? "-":String.valueOf(mFamilyInfoEntity.getFirstLevelScore()));
        }else{
            bundle.putString(BundleKey.LEVEL_MEMBERS, null == mFamilyInfoEntity ? "-":String.valueOf(mFamilyInfoEntity.getSecondLevelTotal()));
            bundle.putString(BundleKey.LEVEL_SCORE, null == mFamilyInfoEntity ? "-" : String.valueOf((mFamilyInfoEntity.getSecondLevelScore())));
        }
        return bundle;
    }
}
