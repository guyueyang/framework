package com.andcup.android.app.integralwall.view.cash;

import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.job.JobIsQbCanCheck;
import com.andcup.android.app.integralwall.datalayer.model.CheckQbEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.event.QBEvent;
import com.andcup.android.app.integralwall.tools.Dialog;
import com.andcup.android.app.integralwall.tools.FormatString;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.cash.qb.DoTaskFragment;
import com.andcup.android.app.integralwall.view.cash.qb.QBFragment;
import com.andcup.android.app.integralwall.view.dialog.BindPhoneNumberFragment;
import com.andcup.android.app.integralwall.view.dialog.DisabledExchangeFragment;
import com.andcup.android.app.integralwall.view.usercenter.score.ScoreFragment;
import com.andcup.android.frame.datalayer.CallBack;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.yl.android.cpa.R;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/22.
 */
public class CashFragment extends BaseFragment{

    @Bind(R.id.iv_banner)
    ImageView mIvBanner;
    @Bind(R.id.tv_score)
    TextView  mTvScore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cashing;
    }

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        setTitle(R.string.home_check_cash);
        Point point = getWindowSize();
        ViewGroup.LayoutParams lp = mIvBanner.getLayoutParams();
        lp.height = (int) (point.x / 2.4);
        mIvBanner.setLayoutParams(lp);

        monitorUserInfo();
    }

    @OnClick(R.id.tv_phone_fare)
    public void onPhoneFareClick(){
        if(prohibited()){
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.CASH_ITEM, CashItem.PHONEFARE);
        go2(CashItemFragment.class, bundle);
    }

    @OnClick(R.id.tv_alipay)
    public void onAlipayClick(){
        if(prohibited()){
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.CASH_ITEM, CashItem.ALIPAY);
        go2(CashItemFragment.class, bundle);
    }

    @OnClick(R.id.tv_bank)
    public void onBankClick(){
        if(prohibited()){
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.CASH_ITEM, CashItem.BANKCARD);
        go2(CashItemFragment.class, bundle);
    }

    @OnClick(R.id.tv_qb)
    public void onQbClick(){
        if(!bindPhone()){
            return;
        }
        if(prohibited()){
            return;
        }
        showLoading();
        call();
    }

    @OnClick(R.id.tv_exchange_history)
    public void onExchangeHistory(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleKey.IS_SCORE_OUT, true);
        go2(ScoreFragment.class, bundle);
    }

    private void call(){
        call(new JobIsQbCanCheck(), new SimpleCallback<CheckQbEntity>() {
            @Override
            public void onSuccess(CheckQbEntity jobEntity) {
                if (null != jobEntity) {
                    int stateCode = jobEntity.getErrCode();
                    if (stateCode == CheckQbEntity.QB_EXCHANGE_READY) {
                        Dialog.QB.build(QBFragment.class, null).show(getChildFragmentManager());
                        //
                    } else {
                        Dialog.QB_TASK.build(DoTaskFragment.class, null).show(getChildFragmentManager());
                        //Dialog.QB.build(QBFragment.class, null).show(getChildFragmentManager());
                    }
                }

            }

            @Override
            public void onFinish() {
                hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                hideLoading();
                SnackBar.make(getContext(), e.getMessage()).show();
            }
        });
    }

    protected boolean bindPhone(){
        if(TextUtils.isEmpty(UserProvider.getInstance().getUserInfo().getMobile())){
            Bundle bundle = new Bundle();
            bundle.putSerializable(BundleKey.CONTENT, getString(R.string.bind_phone_number));
            Dialog.BIND_PHONE
                    .build(BindPhoneNumberFragment.class, bundle)
                    .okText(getString(R.string.to_bind_phone_number))
                    .show(getChildFragmentManager());
            return false;
        }
        return true;
    }

    protected boolean prohibited(){
        try{
            boolean isDisabled = UserProvider.getInstance().getUserInfo().isDisableExchange();
            if(isDisabled){
                Dialog.EXCHANGE_DISABLED.build(DisabledExchangeFragment.class, null).show(getChildFragmentManager());
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    @Subscribe
    public void refreshUserInfo(QBEvent aBEvent){
        call(new JobGetUserInfo(), new CallBack<BaseEntity<UserInfoEntity>>() {
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(BaseEntity<UserInfoEntity> userInfoEntityBaseEntity) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void monitorUserInfo(){
        loader(UserInfoEntity.class, new SqlLoader.CallBack<UserInfoEntity>() {
            @Override
            public void onUpdate(UserInfoEntity userInfoEntity) {
                if (null == userInfoEntity) {
                    return;
                }
                mTvScore.setText(FormatString.newCashing(userInfoEntity.getScore()));
            }
        });
    }
}
