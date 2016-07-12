package com.andcup.android.app.integralwall.view.usercenter.invite;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.tools.ShareHelper;
import com.andcup.android.app.integralwall.tools.ShuoMClickableSpan;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.integralwall.commons.tools.AndroidUtils;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/26.
 */
public class MiInviteCodeFragment extends BaseFragment{

    @Bind(R.id.tv_invite_content)
    TextView mTvContent;
    @Bind(R.id.tv_score)
    TextView mTvScore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mi_invite_code;
    }

    @Override
    protected void afterActivityCreate(Bundle afterActivityCreate){
        super.afterActivityCreate(afterActivityCreate);
        String content=getString(R.string.invite_code_content);
        String rewardRules=getString(R.string.invite_code_reward_rules);
        SpannableString spannableString=new SpannableString(rewardRules);
        ClickableSpan clickableSpan= new ShuoMClickableSpan(rewardRules,getChildFragmentManager());
        spannableString.setSpan(clickableSpan, 0, rewardRules.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvContent.setText(content);
        mTvContent.append(spannableString);
        mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
        mTvScore.setText(FormatString.newInviteScore("100"));
    }

    @OnClick(R.id.btn_submit)
    public void onShare(){
        ShareHelper.share(getChildFragmentManager(), null);
    }
}
