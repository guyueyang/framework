package com.andcup.android.app.integralwall.view.usercenter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.andcup.android.app.integralwall.datalayer.SimpleCallback;
import com.andcup.android.app.integralwall.datalayer.UserProvider;
import com.andcup.android.app.integralwall.datalayer.job.JobGetUserInfo;
import com.andcup.android.app.integralwall.datalayer.model.AvatarEntity;
import com.andcup.android.app.integralwall.datalayer.model.UserInfoEntity;
import com.andcup.android.app.integralwall.datalayer.model.base.BaseEntity;
import com.andcup.android.app.integralwall.event.BindPhoneEvent;
import com.andcup.android.app.integralwall.view.base.BaseFragment;
import com.andcup.android.app.integralwall.view.task.my.MyTaskFragment;
import com.andcup.android.app.integralwall.view.usercenter.invite.InviteCodeFragment;
import com.andcup.android.app.integralwall.view.usercenter.vip.VipExperienceFragment;
import com.andcup.android.app.integralwall.bundle.BundleKey;
import com.andcup.android.app.integralwall.view.msg.MsgBarFragment;
import com.andcup.android.app.integralwall.view.usercenter.edit.EditFragment;
import com.andcup.android.app.integralwall.view.usercenter.score.ScoreFragment;
import com.andcup.android.app.integralwall.view.usercenter.setting.SettingFragment;
import com.andcup.android.frame.datalayer.sql.SqlLoader;
import com.andcup.android.integralwall.commons.widget.SnackBar;
import com.andcup.android.integralwall.commons.widget.URIRoundedImageView;
import com.yl.android.cpa.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * site :  http://www.andcup.com
 * email:  amos@andcup.com
 * github: https://github.com/andcup
 * Created by Amos on 2016/3/10.
 */
public class UserFragment extends BaseFragment {

    @Bind(R.id.tv_nick_name)
    TextView mTvNickName;
    @Bind(R.id.riv_avatar)
    URIRoundedImageView mRivAvatar;
    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    @Bind(R.id.sdv_vip)
    ImageView mBtnVip;

    @Override
    protected void afterActivityCreate(Bundle savedInstanceState) {
        super.afterActivityCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle.putString(BundleKey.TITLE, getString(R.string.navigator_item_user));
        bundle.putBoolean(BundleKey.CALL, false);
        go(MsgBarFragment.class, R.id.fr_title, bundle);
        mRivAvatar.initWithAvatar();
        load();
        call();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @OnClick(R.id.tv_score_in_detail)
    protected void onDetail(){
        go2(ScoreFragment.class, null);
    }
    @OnClick(R.id.tv_score_invite_code)
    protected void onInviteCode(){
        go2(InviteCodeFragment.class,null);
    }
    @OnClick(R.id.tv_score_setting)
    protected void onSetting(){
        go2(SettingFragment.class,null);
    }
    @OnClick(R.id.btn_edit)
    protected void onEdit(){
        go2(EditFragment.class,null);
    }

    @OnClick(R.id.sdv_vip)
    protected void onSdvVip(){
        go2(VipExperienceFragment.class,null);
    }

    @OnClick(R.id.tv_mi_task)
    protected void onMiTask(){
        go2(MyTaskFragment.class, null);
    }

    @OnClick(R.id.tv_call_service)
    public void onCallService(){
        String url="mqqwpa://im/chat?chat_type=wpa&uin=2749792995";
        try{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }catch (ActivityNotFoundException exception){
            SnackBar.make(getActivity(), getString(R.string.please_install_qq)).show();
        }
    }

    private void call(){
        call(new JobGetUserInfo(), new SimpleCallback<BaseEntity<UserInfoEntity>>());
    }
    private void load(){
        loader(UserInfoEntity.class, new SqlLoader.CallBack<UserInfoEntity>() {
            @Override
            public void onUpdate(UserInfoEntity userInfoEntity) {
                if (null == userInfoEntity) {
                    return;
                }
                mTvNickName.setText(userInfoEntity.getNickName());
                mRivAvatar.setEditImageURI(Uri.parse(userInfoEntity.getAvatar()));
                mTvPhone.setText(userInfoEntity.getMobile());
                LoadingVIP(userInfoEntity.getLevel());
            }
        });
    }

    @Override
    public void onEvent(Object object) {
        super.onEvent(object);
        if(object instanceof AvatarEntity){
            mRivAvatar.setEditImageURI(Uri.parse(UserProvider.getInstance().getUserInfo().getAvatar()));
        }
        if(object instanceof UserInfoEntity){
            mTvNickName.setText(UserProvider.getInstance().getNickName());
        }
        if(object instanceof BindPhoneEvent){
            mTvPhone.setText(UserProvider.getInstance().getMobile());
        }
    }

    public void LoadingVIP(String VipLevel){
        int level= Integer.parseInt(VipLevel);
        mBtnVip.setImageLevel(level);
    }
}
