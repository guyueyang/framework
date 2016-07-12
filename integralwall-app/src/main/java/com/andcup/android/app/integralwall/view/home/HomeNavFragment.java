package com.andcup.android.app.integralwall.view.home;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.where.Where;
import com.andcup.android.app.integralwall.event.CheckNavigatorItemEvent;
import com.andcup.sdk.push.PushHelper;
import com.andcup.android.app.integralwall.tools.ShareHelper;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.cash.CashFragment;
import com.andcup.android.app.integralwall.view.home.rank.RankFragment;
import com.andcup.android.app.integralwall.view.home.sign.CalendarFragment;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/11.
 */

public class HomeNavFragment extends BaseFragment{

    @Bind(R.id.tv_mine_id)
    TextView mTvMineId;
    @Bind(R.id.tv_today_in_score)
    TextView mTvTodayInScore;
    @Bind(R.id.tv_today_new_member)
    TextView mTvTodayNewMember;
    @Bind(R.id.tv_score)
    TextView mTvScore;

    private UserInfoEntity mUserInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_header;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        // monitor user info changed.
        monitorUserInfo();
    }

    private void monitorUserInfo(){
        loader(UserInfoEntity.class, Where.user(), new SqlLoader.CallBack<UserInfoEntity>() {
            @Override
            public void onUpdate(UserInfoEntity userInfoEntity) {
                UserProvider.getInstance().setUserInfo(userInfoEntity);
                // set user info.
                mUserInfo = UserProvider.getInstance().getUserInfo();
                mTvMineId.setText(getString(R.string.mine_id, UserProvider.getInstance().getUid()));
                mTvTodayInScore.setText(getString(R.string.today_in_score, mUserInfo.getTodayInScore()));
                mTvTodayNewMember.setText(getString(R.string.today_new_member, mUserInfo.getTodayNewMember()));
                // set score.
                setScore();
            }
        });
    }

    private void setScore(){
        String score = getString(R.string.mine_score, mUserInfo.getScore());
        SpannableString spannable = new SpannableString(score);
        //text size
        int scoreEnd = mUserInfo.getScore().length();
        spannable.setSpan(new AbsoluteSizeSpan(40, true), 0, scoreEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AbsoluteSizeSpan(13, true), scoreEnd, score.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //text bold
        spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, scoreEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), scoreEnd, score.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //text color
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.yellow_fea556)), 0, scoreEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.gray_666666)), scoreEnd, score.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //set text.
        mTvScore.setText(spannable);
    }

    @OnClick(R.id.btn_task)
    public void onTaskClick(){
        EventBus.getDefault().post(new CheckNavigatorItemEvent(1));
    }

    @OnClick(R.id.tv_calendar)
    public void onCalendayClick(){
        go2(CalendarFragment.class, null);
    }

    @OnClick(R.id.tv_check_cash)
    public void onCashClick(){
        go2(CashFragment.class, null);
    }

    @OnClick(R.id.tv_invite)
    public void onInvite(){
        ShareHelper.share(getChildFragmentManager(), null);
    }

    @OnClick(R.id.tv_rank)
    public void onRankdClick(){
        go2(RankFragment.class, null);
    }
}
